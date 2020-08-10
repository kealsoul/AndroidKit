package com.keal.androidKit

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.TimeUtils
import com.keal.base.provider.param.IGlobalParamProviders
import java.util.*

/**
 * @author Keal
 * @date 2020/8/10.
 * GitHub：https://github.com/kealsoul
 * email：xiangshifukeal@gmail.com
 * description：
 */
@Route(path = "/app/GlobalParamProviders", name = "参数提供")
class GlobalParamProviders : IGlobalParamProviders {
    override fun getBaseUrl(): String {
        TODO("Not yet implemented")
    }

    override fun getBulgyAppId(): String {
        if (BuildConfig.DEBUG) {
            return BUGLY_DEBUG
        }else{
            return BUGLY_DEBUG
        }
    }

    override fun isNetRelease(): Boolean {
        return BuildConfig.NET_TYPE_IS_RELEASE
    }

    override fun isBuildDebug(): Boolean {
        return BuildConfig.DEBUG
    }

    override fun buildTime(): String {
        return BuildConfig.APP_BUILD_TIME
    }

    override fun getAppStartTime(): String {
        return TimeUtils.date2String(Date(startMillis))
    }

    override fun init(context: Context?) {

    }

    companion object {
        val BUGLY_DEBUG = "94b23b517e"

        val BUGLY_RELEASE = "1f6953989c"

        var startMillis: Long = 0
    }

}