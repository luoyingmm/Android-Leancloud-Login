package com.luoyingmm.leandemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import cn.leancloud.LCObject
import cn.leancloud.LCQuery
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        btn_login.isEnabled = false
        et_loginIUser.addTextChangedListener(wather)
        et_loginPassword.addTextChangedListener(wather)
        pb_login.visibility = View.INVISIBLE
        btn_signUp.setOnClickListener {
            startActivity(Intent(this,SignUpActivity::class.java))
        }

        btn_login.setOnClickListener {
            pb_login.visibility = View.VISIBLE
            val query = LCQuery<LCObject>("User_data")
            query.whereEqualTo("username", et_loginIUser.text.toString().trim())
            query.findInBackground().subscribe(object : Observer<List<LCObject?>?> {
                override fun onSubscribe(disposable: Disposable) {}
                override fun onNext(t: List<LCObject?>) {
                    // students 是包含满足条件的 Student 对象的数组
                    if (t.isNotEmpty() && t[0]?.getString("password").toString() == et_loginPassword.text.toString().trim()){
                        startActivity(Intent(this@LoginActivity,MainActivity::class.java))
                        finish()
                    }else{
                        Toast.makeText(this@LoginActivity, "账号密码错误", Toast.LENGTH_SHORT).show()
                        pb_login.visibility = View.INVISIBLE
                    }
                }

                override fun onError(throwable: Throwable) {
                    pb_login.visibility = View.INVISIBLE
                    Toast.makeText(applicationContext, "${throwable.message}", Toast.LENGTH_SHORT).show()
                }
                override fun onComplete() {}
            })
        }
    }

    private val wather = object : TextWatcher{
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
           val t1 = et_loginIUser.text.toString().isNotEmpty()
           val t2 = et_loginPassword.text.toString().isNotEmpty()
            btn_login.isEnabled = t1 and t2
        }

        override fun afterTextChanged(s: Editable?) {

        }

    }
}