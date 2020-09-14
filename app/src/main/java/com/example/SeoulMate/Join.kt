package com.example.SeoulMate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_join.*

// 회원가입 화면
class Join : AppCompatActivity() {

    private var auth : FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)

        auth = FirebaseAuth.getInstance()

        btn_join_ok.setOnClickListener {
            createAndLoginEmail()
            finish()
        }
    }

    private fun createAndLoginEmail() {
        if (make_id.text.toString().isNullOrEmpty() || make_pwd.text.toString().isNullOrEmpty()) {
            Toast.makeText(this, "이메일과 비밀번호를 입력해주세요", Toast.LENGTH_LONG).show()
            return
        }

        var email = make_id.text.toString()
        var password = make_pwd.text.toString()
        progressBar2.visibility = View.VISIBLE
        auth?.createUserWithEmailAndPassword(email, password)
            ?.addOnCompleteListener { task ->
                progressBar2.visibility = View.GONE
                if (task.isSuccessful) {
                    //아이디 생성이 성공했을 경우
                    Toast.makeText(this,
                        "회원가입 성공",
                        Toast.LENGTH_LONG).show()
                } else {
                    //회원가입 에러가 발생했을 경우
                    Toast.makeText(this,
                        task.exception?.message, Toast.LENGTH_LONG).show()
                }
            }
    }
}
