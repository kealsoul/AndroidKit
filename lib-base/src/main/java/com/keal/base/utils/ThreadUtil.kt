package com.keal.base.utils

import android.os.Handler
import android.os.Looper
import android.util.Log
import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.ThreadFactory
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger

/**
 * AndroidUtil 主要负责硬件层级
 * App主要负责软件层级
 */
object ThreadUtil {
    const val TAG = "ThreadUtil"
    private val CPU_COUNT = Runtime.getRuntime().availableProcessors()
    private val INIT_THREAD_COUNT = CPU_COUNT + 1
    private val MAX_THREAD_COUNT = INIT_THREAD_COUNT
    private const val SURPLUS_THREAD_LIFE = 30L
    val defaultThreadPool =
        createThreadPool()
    private val mainHandler = Handler(Looper.getMainLooper())
    val isMainThread: Boolean
        get() = Thread.currentThread() === Looper.getMainLooper().thread

    fun runOnUiThread(runnable: Runnable) {
        if (isMainThread) {
            runnable.run()
        } else {
            mainHandler.post(runnable)
        }
    }

    fun postOnUiThread(runnable: Runnable?) {
        mainHandler.post(runnable)
    }

    fun runOnNewThread(runnable: Runnable) {
        if (isMainThread) {
            defaultThreadPool.execute(runnable)
        } else {
            runnable.run()
        }
    }

    private fun createThreadPool(): ThreadPoolExecutor {
        return ThreadPoolExecutor(
            INIT_THREAD_COUNT,
            MAX_THREAD_COUNT,
            SURPLUS_THREAD_LIFE,
            TimeUnit.SECONDS,
            ArrayBlockingQueue(64),
            DefaultThreadFactory()
        )
    }

    class DefaultThreadFactory : ThreadFactory {
        private val poolNumber =
            AtomicInteger(1)
        private val threadNumber =
            AtomicInteger(1)
        private val group: ThreadGroup
        private val namePrefix: String
        override fun newThread(runnable: Runnable): Thread {
            val threadName = namePrefix + threadNumber.getAndIncrement()
            Log.e(
                TAG,
                "Thread production, name is [$threadName]"
            )
            val thread = Thread(group, runnable, threadName, 0)
            //设为非后台线程
            if (thread.isDaemon) {
                thread.isDaemon = false
            }
            //优先级为normal
            if (thread.priority != Thread.NORM_PRIORITY) {
                thread.priority = Thread.NORM_PRIORITY
            }

            // 捕获多线程处理中的异常
            thread.uncaughtExceptionHandler =
                Thread.UncaughtExceptionHandler { thread1: Thread, ex: Throwable ->
                    Log.e(
                        TAG,
                        "Running task appeared exception! Thread [" + thread1.name + "], because [" + ex.message + "]"
                    )
                }
            return thread
        }

        init {
            val s = System.getSecurityManager()
            group = if (s != null) s.threadGroup else Thread.currentThread()
                .threadGroup
            namePrefix = "ThreadPool No." + poolNumber.getAndIncrement() + ", thread No."
        }
    }
}