package com.example.SeoulMate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_apply.*
import kotlinx.android.synthetic.main.activity_enrollment.*
import kotlinx.android.synthetic.main.activity_review.*
import kotlinx.android.synthetic.main.activity_review.btn_save
import java.text.SimpleDateFormat
import java.util.*
import com.example.SeoulMate.Apply as Apply1

class Review : AppCompatActivity(){

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.action_btn2 -> {
                firebaseAuth = FirebaseAuth.getInstance()
                firebaseAuth?.signOut()
                var myIntent = Intent(this, MainActivity::class.java)
                startActivity(myIntent)
            }

            R.id.action_btn1 -> {

                var myIntent = Intent(this, MyPage::class.java)
                startActivity(myIntent)
            }

            R.id.action_btn3 -> {

                var myIntent = Intent(this, Home::class.java)
                startActivity(myIntent)
            }

            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
        return true
    }

    private var firestore : FirebaseFirestore? = null
    private var firebaseAuth: FirebaseAuth?=null
    private var auth : FirebaseAuth? = null
    val currentTime = System.currentTimeMillis()

    val textTime = SimpleDateFormat("YYYY/MM/DD HH:mm:ss", Locale.KOREA).format(currentTime)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review)

        auth = FirebaseAuth.getInstance()
        review_date_text.text = "${textTime}"
        review_mate_text.text = intent.getStringExtra("review_mate")

        btn_save.setOnClickListener {
            addDatabase()
        }
    }

    private fun addDatabase() {
        if (review_id_input.text.isEmpty() || editText3.text.isEmpty()) {
            Toast.makeText(this, "입력되지 않은 값이 있습니다.", Toast.LENGTH_LONG).show()
            //txtAddResult.text = "입력되지 않은 값이 있습니다."
            return
        }

        val reviewDTO = ReviewDTO(
            review_id_input.text.toString(),
            textTime.toString(),
            review_mate_text.text.toString(),
            editText3.text.toString()
        )

        val document = review_id_input.text.toString()

        progressBar7.visibility = View.VISIBLE
        firestore = FirebaseFirestore.getInstance()

        firestore?.collection("reviewList")?.document(document)
            ?.set(reviewDTO)?.addOnCompleteListener { task ->
                progressBar7.visibility = View.GONE
                if (task.isSuccessful) {
                    Toast.makeText(this, "리뷰 등록 성공", Toast.LENGTH_LONG).show()
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
