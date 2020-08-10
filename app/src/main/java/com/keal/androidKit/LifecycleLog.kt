package com.chaitai.crm

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.blankj.utilcode.util.LogUtils
import java.util.*

/**
 * 打印 activity和fragment的生命周期方法
 */
object LifecycleLog {
    private val fragmentLifecycleCallbacks =
        FragmentLifecycleCallbacks()

    fun init(application: Application) {
        application.registerActivityLifecycleCallbacks(object :
            Application.ActivityLifecycleCallbacks {
            override fun onActivityCreated(
                activity: Activity,
                savedInstanceState: Bundle
            ) {
                log(activity, savedInstanceState)
                if (activity is FragmentActivity) {
                    activity.supportFragmentManager.registerFragmentLifecycleCallbacks(
                        fragmentLifecycleCallbacks,
                        true
                    )
                }
            }

            override fun onActivityStarted(activity: Activity) {
                log(activity)
            }

            override fun onActivityResumed(activity: Activity) {
                log(activity)
            }

            override fun onActivityPaused(activity: Activity) {
                log(activity)
            }

            override fun onActivityStopped(activity: Activity) {
                log(activity)
            }

            override fun onActivitySaveInstanceState(
                activity: Activity,
                outState: Bundle
            ) {
                log(activity, outState)
            }

            override fun onActivityDestroyed(activity: Activity) {
                log(activity)
            }
        })
    }

    fun log(activityOrFragment: Any, vararg message: Any?) {
        val list: MutableList<Any> = ArrayList()
        list.add(activityOrFragment.javaClass.simpleName)
        list.add(Thread.currentThread().stackTrace[3].methodName)
        val list1 =
            Arrays.asList<Any>(*message)
        list.addAll(list1)
        var tag: String? = null
        if (activityOrFragment is Activity) {
            tag = "Activity-lifecycle"
        } else if (activityOrFragment is Fragment) {
            tag = "Fragment-lifecycle"
        }
        LogUtils.i(tag, 32, list.toTypedArray())
    }

    internal class FragmentLifecycleCallbacks :
        FragmentManager.FragmentLifecycleCallbacks() {
        override fun onFragmentPreAttached(
            fm: FragmentManager,
            f: Fragment,
            context: Context
        ) {
            log(f, context)
        }

        override fun onFragmentAttached(
            fm: FragmentManager,
            f: Fragment,
            context: Context
        ) {
            log(f, context)
        }

        override fun onFragmentPreCreated(
            fm: FragmentManager,
            f: Fragment,
            savedInstanceState: Bundle?
        ) {
            log(f, savedInstanceState)
        }

        override fun onFragmentCreated(
            fm: FragmentManager,
            f: Fragment,
            savedInstanceState: Bundle?
        ) {
            log(f, savedInstanceState)
        }

        override fun onFragmentActivityCreated(
            fm: FragmentManager,
            f: Fragment,
            savedInstanceState: Bundle?
        ) {
            log(f, savedInstanceState)
        }

        override fun onFragmentViewCreated(
            fm: FragmentManager,
            f: Fragment,
            v: View,
            savedInstanceState: Bundle?
        ) {
            log(f, v, savedInstanceState)
        }

        override fun onFragmentStarted(
            fm: FragmentManager,
            f: Fragment
        ) {
            log(f)
        }

        override fun onFragmentResumed(
            fm: FragmentManager,
            f: Fragment
        ) {
            log(f)
        }

        override fun onFragmentPaused(
            fm: FragmentManager,
            f: Fragment
        ) {
            log(f)
        }

        override fun onFragmentStopped(
            fm: FragmentManager,
            f: Fragment
        ) {
            log(f)
        }

        override fun onFragmentSaveInstanceState(
            fm: FragmentManager,
            f: Fragment,
            outState: Bundle
        ) {
            super.onFragmentSaveInstanceState(fm, f, outState)
        }

        override fun onFragmentViewDestroyed(
            fm: FragmentManager,
            f: Fragment
        ) {
            log(f)
        }

        override fun onFragmentDestroyed(
            fm: FragmentManager,
            f: Fragment
        ) {
            log(f)
        }

        override fun onFragmentDetached(
            fm: FragmentManager,
            f: Fragment
        ) {
            log(f)
        }
    }
}