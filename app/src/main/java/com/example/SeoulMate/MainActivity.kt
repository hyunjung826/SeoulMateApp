package com.example.SeoulMate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_main.*

// 로그인 화면(첫 번째 화면)
class MainActivity : AppCompatActivity() {

    private var auth : FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()

        btn_login.setOnClickListener {
            emailLogin()
        }

        btn_join.setOnClickListener {
            startActivity(Intent(this, Join::class.java))
        }
    }

    private fun emailLogin() {
        if (id_input.text.toString().isNullOrEmpty() || pwd_input.text.toString().isNullOrEmpty()) {
            Toast.makeText(this, "이메일과 비밀번호를 입력해주세요", Toast.LENGTH_LONG).show()
            return
        }

        var email = id_input.text.toString()
        var password = pwd_input.text.toString()
        progressBar.visibility = View.VISIBLE
        auth?.signInWithEmailAndPassword(email, password)
            ?.addOnCompleteListener { task ->
                progressBar.visibility = View.GONE
                if (task.isSuccessful) {
                    Toast.makeText(this, "Email 로그인 성공", Toast.LENGTH_LONG).show()
                    moveHomePage(auth?.currentUser)
                } else {
                    Toast.makeText(this, task.exception?.message, Toast.LENGTH_LONG).show()
                }
            }
    }
    private fun moveHomePage(user: FirebaseUser?) {
        if (user != null) {
            startActivity(Intent(this, Home::class.java))
            finish()
        }

    }
}
