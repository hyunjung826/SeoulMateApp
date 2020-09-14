package com.example.SeoulMate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_update.*
import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_apply.*

// 업데이트 화면
class Update : AppCompatActivity() {

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

        firebaseAuth = FirebaseAuth.getInstance()

        var update_title = intent.getStringExtra("title")
        var update_name = intent.getStringExtra("name")
        var update_age = intent.getStringExtra("age")
        var update_gender = intent.getStringExtra("gender")
        var update_email = intent.getStringExtra("email")
        var update_where = intent.getStringExtra("spot")
        var update_when = intent.getStringExtra("date")
        var update_budget = intent.getStringExtra("budget")
        var update_plan = intent.getStringExtra("plan")
        var update_homeStay = intent.getStringExtra("homeStay")
        val photoURI = intent.getStringExtra("photo")

        Glide.with(this).load(photoURI).into(profile_photo3)

        title_input3.setText(update_title)
        name_input3.setText(update_name)
        age_input3.setText(update_age)
        gender_input3.setText(update_gender)
        email_input3.setText(update_email)
        where_input3.setText(update_where)
        when_input3.setText(update_when)
        budget_input3.setText(update_budget)
        plan_input3.setText(update_plan)
        home_stay_input3.setText(update_homeStay)

        btn_update.setOnClickListener {
            updateDatabase()
        }

        btn_delete.setOnClickListener {
            deleteDatabase()
        }
    }


    private fun updateDatabase(){
        if (name_input3.text.isEmpty() || age_input3.text.isEmpty() || gender_input3.text.isEmpty()||
                email_input3.text.isEmpty() || where_input3.text.isEmpty() || when_input3.text.isEmpty() ||
                budget_input3.text.isEmpty() || plan_input3.text.isEmpty() || home_stay_input3.text.isEmpty()) {
            Toast.makeText(this, "입력되지 않은 값이 있습니다.", Toast.LENGTH_SHORT).show()
            return
        }

        var document = title_input3.text.toString()

        var map = mutableMapOf<String, Any>()
        map["name"] = name_input3.text.toString()
        map["age"] = age_input3.text.toString()
        map["gender"] = gender_input3.text.toString()
        map["email"] = email_input3.text.toString()
        map["spot"] = where_input3.text.toString()
        map["date"] = when_input3.text.toString()
        map["budget"] = budget_input3.text.toString()
        map["plan"] = plan_input3.text.toString()
        map["homeStay"] = home_stay_input3.text.toString()

        progressBar6.visibility = View.VISIBLE
        firestore = FirebaseFirestore.getInstance()
        firestore?.collection("mateList")?.document(document)?.update(map)
            ?.addOnCompleteListener { task ->
                progressBar6.visibility = View.GONE
                if (task.isSuccessful) {
                    Toast.makeText(this, "mate 수정 성공", Toast.LENGTH_LONG).show()
                    moveHomePage(firebaseAuth?.currentUser)
                } else {
                    Toast.makeText(this, task.exception?.message, Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun deleteDatabase() {
        if (title_input3.text.isEmpty()) {
            Toast.makeText(this, "잘못된 메이트 리스트입니다.", Toast.LENGTH_SHORT).show()
            return
        }

        var document = title_input3.text.toString()
        progressBar6.visibility = View.VISIBLE
        firestore = FirebaseFirestore.getInstance()
        firestore?.collection("mateList")?.document(document)?.delete()
            ?.addOnCompleteListener { task ->
                progressBar6.visibility = View.GONE
                if (task.isSuccessful) {
                    Toast.makeText(this, "mate 삭제 성공", Toast.LENGTH_LONG).show()
                    moveHomePage(firebaseAuth?.currentUser)
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
