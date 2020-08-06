package com.keal.base.autoregister

import android.util.Log
import java.util.*

/**
 * @author Keal
 * @date 2020/8/6.
 * GitHub：https://github.com/kealsoul
 * email：xiangshifukeal@gmail.com
 * description：
 */
internal object ApplicationManager {
    internal var apps: MutableList<IApplication> =
        ArrayList<IApplication>()

    fun init() {}

    /**
     * 添加IApplication 模块
     */
    fun register(proxy: IApplication) {
        if (Docking.isDebug) {
            Log.e("Docking", "register::" + proxy.toString())
        }
        apps.add(proxy)
    }

    init {
        init()
        Collections.sort(
            apps,
            Comparator { o1, o2 -> o2.getPriority() - o1.getPriority() })
        if (Docking.isDebug) {
            Log.e("Docking", "register-sort::$apps")
        }
    }
}