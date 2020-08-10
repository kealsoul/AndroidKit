package com.keal.base.base

import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.gyf.immersionbar.ImmersionBar
import com.keal.base.R
import com.keal.base.engine.PostcardSerializable
import com.trello.lifecycle2.android.lifecycle.AndroidLifecycle
import com.trello.rxlifecycle3.LifecycleProvider
import com.trello.rxlifecycle3.LifecycleTransformer
import io.reactivex.Observable

/**
 * @author Keal
 * @date 2020/8/6.
 * GitHub：https://github.com/kealsoul
 * email：xiangshifukeal@gmail.com
 * description：
 */
open class BaseActivity : AppCompatActivity() {
    private val defaultRequestCode = 837
    var successIntent: Bundle? = null
    var failIntent: Bundle? = null


    val provider: LifecycleProvider<Lifecycle.Event> by lazy {
        AndroidLifecycle.createLifecycleProvider(this)
    }

    fun <T> bindDestroy(): LifecycleTransformer<T> {
        return provider.bindUntilEvent(Lifecycle.Event.ON_DESTROY)
    }


    fun <T> bindAuto(): LifecycleTransformer<T> {
        return provider.bindToLifecycle()
    }

    fun <T> Observable<T>.bindDestroy(): Observable<T> {
        return this.compose(this@BaseActivity.bindDestroy())
    }

    fun getBaseActivity(): BaseActivity {
        return this
    }

    init {
        if (isImmersionEnable()) {
            lifecycle.addObserver(object : LifecycleObserver {
                @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
                fun create() {
                    ImmersionBar.with(this@BaseActivity).statusBarDarkFont(isDarkFont())
                        .navigationBarColorInt(Color.WHITE)
                        .keyboardEnable(true, WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
                        .init()
                    var list: MutableList<View> = ArrayList()
                    getToolbarId().forEach {
                        findViewById<View>(it)?.let { it ->
                            list.add(it)
                        }
                    }
                    getToolbar().forEach {
                        list.add(it)
                    }
                    ImmersionBar.setTitleBar(this@BaseActivity, *list.toTypedArray())
                }

            })
        }
    }

    fun finishSuccess() {
        val successIntent = intent.getBundleExtra(INTENT_DATA_SUCCESS_INTENT)
        if (successIntent != null) {
            PostcardSerializable.toPostcard(successIntent).navigation(this)
        }
        setResult(Activity.RESULT_OK)
        finish()

    }

    fun finishFail() {
        val failIntent = intent.getBundleExtra(INTENT_DATA_FAIL_INTENT)
        if (failIntent != null) {
            PostcardSerializable.toPostcard(failIntent).navigation(this)
        }
        finish()
    }

    open fun getToolbarId(): IntArray {
        return intArrayOf(R.id.toolbar)
    }

    open fun getToolbar(): Array<View> {
        return emptyArray()
    }


    open fun isDarkFont(): Boolean {
        return true
    }

    open fun isImmersionEnable(): Boolean {
        return true
    }


    fun startActivity(cla: Class<*>) {
        startActivity(Intent(this, cla))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        if (isScreenForcePortrait()) {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
        super.onCreate(savedInstanceState)
        /*try {
            this.onCreate(savedInstanceState)
        } catch (e: Throwable) {
            CrashReport.postCatchedException(e)
            if (BuildConfig.DEBUG) {
                toast(e.toString())
            }
            setContentView(R.layout.base_layout_error_default) // TODO
        }
*/
    }


    /**
     * 是否强制竖屏
     */
    fun isScreenForcePortrait() = true

    private var mActivityResultCallback: ForResultCallback? = null
    fun startActivityForResult(intent: Intent?, callback: ForResultCallback) {
        mActivityResultCallback = callback
        super.startActivityForResult(intent, defaultRequestCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == defaultRequestCode && mActivityResultCallback != null) {
            mActivityResultCallback?.callback(resultCode, data)
            mActivityResultCallback = null
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    interface ForResultCallback {
        fun callback(resultCode: Int, data: Intent?)
    }

    companion object {
        const val INTENT_DATA_SUCCESS_INTENT = "INTENT_DATA_SUCCESS_INTENT"
        const val INTENT_DATA_FAIL_INTENT = "INTENT_DATA_FAIL_INTENT"
    }
}

