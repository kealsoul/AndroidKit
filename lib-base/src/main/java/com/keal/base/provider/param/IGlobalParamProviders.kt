package com.keal.base.provider.param

import com.alibaba.android.arouter.facade.template.IProvider

/**
 * @author Keal
 * @date 2020/8/10.
 * GitHub：https://github.com/kealsoul
 * email：xiangshifukeal@gmail.com
 * description：
 */
interface IGlobalParamProviders : IProvider {
    fun getBaseUrl(): String
    fun getBulgyAppId(): String
    fun isNetRelease(): Boolean
    fun isBuildDebug(): Boolean
    fun buildTime(): String
    fun getAppStartTime(): String
}