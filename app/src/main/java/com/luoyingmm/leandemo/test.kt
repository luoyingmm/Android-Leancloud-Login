package com.luoyingmm.leandemo

import kotlin.jvm.JvmStatic
import cn.leancloud.LCObject
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

object test {
    @JvmStatic
    fun main(args: Array<String>) {

// 构建对象
        val todo = LCObject("Todo")

// 为属性赋值
        todo.put("title", "工程师周会")
        todo.put("content", "周二两点，全体成员")

// 将对象保存到云端
        todo.saveInBackground().subscribe(object : Observer<LCObject> {
            override fun onSubscribe(disposable: Disposable) {}
            override fun onNext(todo: LCObject) {
                // 成功保存之后，执行其他逻辑
                println("保存成功。objectId：" + todo.objectId)
            }

            override fun onError(throwable: Throwable) {
                // 异常处理
            }

            override fun onComplete() {}
        })
    }
}