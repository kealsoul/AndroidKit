package com.keal.base.autoregister

import android.app.Application
import android.content.Context

/**
 * @author Keal
 * @date 2020/8/6.
 * GitHub：https://github.com/kealsoul
 * email：xiangshifukeal@gmail.com
 * description：
 */
interface IApplication {
    fun init(application: Application)

    fun onCreate(application: Application)

    fun attachBaseContext(context: Context)

    fun getPriority(): Int
}