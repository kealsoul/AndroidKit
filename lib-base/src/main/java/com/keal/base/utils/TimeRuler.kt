package com.keal.base.utils

import android.util.Log
import java.util.*

/**
 * @author ooftf
 * @email 994749769@qq.com
 * @date 2018/12/12 0012
 */
object TimeRuler {
    private val map: MutableMap<String, Chain> =
        HashMap()

    fun start(key: String, message: String?) {
        map[key] = Chain(key, message)
    }

    fun marker(key: String, message: String?) {
        var info = map[key]
        if (info == null) {
            info = Chain(key, message)
            map[key] = info
        } else {
            info.marker(message)
        }
    }

    fun end(key: String?, message: String?) {
        val info = map[key]
        if (info == null) {
            Chain(key, message)
        } else {
            info.marker(message)
            map.remove(key)
        }
    }

    class Chain internal constructor(var key: String?, message: String?) {
        var markers: MutableList<Marker> = ArrayList()
        fun marker(message: String?) {
            markers.add(Marker(System.currentTimeMillis(), message))
            log()
        }

        private fun log() {
            val ss = StringBuilder()
            var lastTime: Long = 0
            for (i in markers.indices) {
                val m = markers[i]
                if (i != 0) {
                    ss.append(" === ").append((m.time - lastTime) / 1000f).append(" ===》 ")
                }
                ss.append("[").append(m.message).append("]")
                lastTime = m.time
            }
            if (markers.size > 1) {
                ss.append("[")
                    .append((markers[markers.size - 1].time - markers[0].time) / 1000f)
                    .append("]")
            }
            Log.e(key, ss.toString())
        }

        init {
            markers.add(Marker(System.currentTimeMillis(), message))
            log()
        }
    }

    class Marker(var time: Long, var message: String?)
}