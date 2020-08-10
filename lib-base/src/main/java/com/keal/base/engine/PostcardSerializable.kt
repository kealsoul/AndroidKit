package com.keal.base.engine

import android.net.Uri
import android.os.Bundle
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.launcher.ARouter

/**
 * @author Keal
 * @date 2020/8/6.
 * GitHub：https://github.com/kealsoul
 * email：xiangshifukeal@gmail.com
 * description：将开启 activity的意图序列化为 Bundle 对象用于 activity之间的传递
 * Scenes: 当某个【开启activity的意图】被 拦截器拦截掉，就会跳转到登录页面，登录成功时候要继续原来【开启activity的意图】
 */
object PostcardSerializable {
    private const val URI = "Uri"
    private const val EXTRAS = "Extras"
    private const val PATH = "Path"
    fun toBundle(postcard: Postcard): Bundle {
        val bundle = Bundle()
        bundle.putParcelable(URI, postcard.uri)
        bundle.putBundle(EXTRAS, postcard.extras)
        bundle.putString(PATH, postcard.path)
        return bundle
    }

    fun toPostcard(bundle: Bundle): Postcard {
        val uri = bundle.getParcelable<Uri>(URI)
        val path = bundle.getString(PATH)
        val extras = bundle.getBundle(EXTRAS)
        return ARouter.getInstance().build(path).with(extras).setUri(uri)
    }
}