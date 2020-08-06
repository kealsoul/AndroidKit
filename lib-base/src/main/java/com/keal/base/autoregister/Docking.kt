package com.keal.base.autoregister

import android.app.Application
import android.content.Context
import com.blankj.utilcode.util.ProcessUtils

/**
 * @author Keal
 * @date 2020/8/6.
 * GitHub：https://github.com/kealsoul
 * email：xiangshifukeal@gmail.com
 * description：
 */
object Docking {
    var mApplication: Application? = null
    var isDebug = false

    /**
     * 在Application 的 非静态代码块 中调用
     *
     * @param application
     * @param isDebug
     */
    fun init(application: Application?, isDebug: Boolean) {
        mApplication = application
        Docking.isDebug = isDebug
        for (app in ApplicationManager.apps) {
            var mainProgress: MainProcess? = null
            try {
                mainProgress = app.javaClass.getMethod("init", Application::class.java)
                    .getAnnotation(
                        MainProcess::class.java
                    )
            } catch (e: NoSuchMethodException) {
                e.printStackTrace()
            }
            if (mainProgress != null) {
                if (ProcessUtils.isMainProcess()) {
                    app.init(application!!)
                }
            } else {
                app.init(application!!)
            }
        }
    }

    fun notifyOnCreate(application: Application?) {
        for (app in ApplicationManager.apps) {
            var mainProgress: MainProcess? = null
            try {
                mainProgress =
                    app.javaClass.getMethod("onCreate", Application::class.java)
                        .getAnnotation(
                            MainProcess::class.java
                        )
            } catch (e: NoSuchMethodException) {
                e.printStackTrace()
            }
            if (mainProgress != null) {
                if (ProcessUtils.isMainProcess()) {
                    app.onCreate(application!!)
                }
            } else {
                app.onCreate(application!!)
            }
        }
    }

    fun notifyAttachBaseContext(context: Context?) {
        for (app in ApplicationManager.apps) {
            var mainProgress: MainProcess? = null
            try {
                mainProgress = app.javaClass.getMethod(
                    "attachBaseContext",
                    Context::class.java
                ).getAnnotation(
                    MainProcess::class.java
                )
            } catch (e: NoSuchMethodException) {
                e.printStackTrace()
            }
            if (mainProgress != null) {
                if (ProcessUtils.isMainProcess()) {
                    app.attachBaseContext(context!!)
                }
            } else {
                app.attachBaseContext(context!!)
            }
        }
    }
}