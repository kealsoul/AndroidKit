package com.keal.home

import android.app.Application
import android.content.Context
import android.util.Log
import com.keal.base.autoregister.IApplication
import com.keal.base.autoregister.MainProcess

/**
 * @author Keal
 * @date 2020/8/6.
 * GitHub：https://github.com/kealsoul
 * email：xiangshifukeal@gmail.com
 * description：
 */
class HomeApp  : IApplication {
    private val TAG = "HomeApplication"

    @MainProcess
    override fun onCreate(application: Application) {
        Log.d(TAG,"Home_OnCreate")
    }


    override fun attachBaseContext(context: Context) {
    }

    override fun getPriority(): Int {
        return 10
    }

    override fun init(application: Application) {
    }
}