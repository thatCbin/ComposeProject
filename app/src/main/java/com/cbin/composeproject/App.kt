package com.cbin.composeproject

import android.app.Application
import android.content.Context

/**
 * @author Cbin
 * @CreateDate 2021/8/12
 * @describe
 */
class App : Application() {
    init {
        instance = requireNotNull(this)
    }

    companion object {
        private lateinit var instance: App

        fun applicationContext(): Context {
            return instance
        }
    }
}