package com.luoyingmm.leandemo

import android.app.Application
import cn.leancloud.LCObject
import cn.leancloud.LeanCloud


class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        LeanCloud.initialize(this,
            "wAEdiqI9mrVR0GvTdeXCKjzh-9Nh9j0Va",
            "F3NnzLDqSieyPwiJ1mqKxA6H",
            "https://waediqi9.lc-cn-e1-shared.com")


    }
}