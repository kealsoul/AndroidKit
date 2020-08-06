package com.keal.androidKit

import android.app.Application
import com.keal.base.autoregister.Docking


/**
 * @author Keal
 * @date 2018/8/6.
 * GitHub：https://github.com/kealsoul
 * email：xiangshifukeal@gmail.com
 * description：
 */
class App : Application() {
    init {
        Docking.init(this, true)
    }
}
