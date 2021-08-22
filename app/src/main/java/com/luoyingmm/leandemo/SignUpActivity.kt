package com.luoyingmm.leandemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import cn.leancloud.LCObject

import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import java.util.*
import cn.leancloud.LCUser

import cn.leancloud.LCUser.currentUser
import io.reactivex.Observer
import cn.leancloud.LCQuery





class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        et_SignUpUsername.addTextChangedListener(wather)
        et_SignUpPassword.addTextChangedListener(wather)
        pb_SignUp.visibility = View.INVISIBLE
        btn_SingUpConfirm.setOnClickListener {
            val user: LCObject = LCObject("User_data")
            val name = et_SignUpUsername.text?.trim().toString()
            val pwd = et_SignUpPassword.text?.trim().toString()
            user.put("username",name)
            user.put("password",pwd)
            pb_SignUp.visibility = View.VISIBLE
            user.saveInBackground().subscribe(object : Observer<LCObject> {
                override fun onSubscribe(disposable: Disposable) {}
                override fun onNext(todo: LCObject) {
                    // 成功保存之后，执行其他逻辑
                    Toast.makeText(applicationContext, "保存成功", Toast.LENGTH_SHORT).show()
                    pb_SignUp.visibility = View.INVISIBLE
                    val query = LCQuery<LCObject>("User_data")
                    query.getInBackground(user.objectId)
                        .subscribe(object : Observer<LCObject> {
                            override fun onSubscribe(disposable: Disposable) {}
                            override fun onNext(todo: LCObject) {
                                val user = todo.getString("username")
                                val password = todo.getString("password")
                                if (name.equals(user) && pwd.equals(password)){
                                    startActivity(Intent(this@SignUpActivity,MainActivity::class.java).also {
                                       it.flags =  Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                    })
                                    finish()
                                }
                            }

                            override fun onError(throwable: Throwable) {
                                Toast.makeText(applicationContext, "${throwable.message}", Toast.LENGTH_SHORT).show()
                            }
                            override fun onComplete() {}
                        })
                }

                override fun onError(throwable: Throwable) {
                    Toast.makeText(applicationContext, "${throwable.message}", Toast.LENGTH_SHORT).show()
                    pb_SignUp.visibility = View.INVISIBLE
                }

                override fun onComplete() {}
            })




        }

    }

    private val wather = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val t1 = et_SignUpUsername.text.toString().isNotEmpty()
            val t2 = et_SignUpPassword.text.toString().isNotEmpty()
            btn_SingUpConfirm.isEnabled = t1 and t2
        }

        override fun afterTextChanged(s: Editable?) {

        }

    }
}