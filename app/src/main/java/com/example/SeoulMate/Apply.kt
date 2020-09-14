package com.example.SeoulMate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_apply.*
import kotlinx.android.synthetic.main.activity_enrollment.*

class Apply : AppCompatActivity() {

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

    private var adapter: ReviewAdapter? = null
    private var firestore: FirebaseFirestore? = null
    private var firebaseAuth: FirebaseAuth?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apply)

        title_input2.text = intent.getStringExtra("title")
        name_input2.text = intent.getStringExtra("name")
        age_input2.text = intent.getStringExtra("age")
        gender_input2.text = intent.getStringExtra("gender")
        email_input2.text = intent.getStringExtra("email")
        val photoURI = intent.getStringExtra("photo")
        Glide.with(this).load(photoURI).into(photo_input2)
        where_input2.text = intent.getStringExtra("spot")
        when_input2.text = intent.getStringExtra("date")
        budget_input2.text = intent.getStringExtra("budget")
        plan_input2.text = intent.getStringExtra("plan")
        home_stay_input2.text = intent.getStringExtra("homeStay")

        btn_contact.setOnClickListener{
            var myIntent =  Intent(this, Contact::class.java)
            startActivity(myIntent)
        }

        btn_review.setOnClickListener {
            var myIntent = Intent(this, Review::class.java)
            myIntent.putExtra("review_mate", title_input2.text.toString())
            startActivity(myIntent)
        }

        btn_map.setOnClickListener {
            startActivity(Intent(this, MapsActivity::class.java))
        }


        review_list.layoutManager = LinearLayoutManager(this)

        adapter = ReviewAdapter(this) {
            Toast.makeText(this, "ID: ${it.id}, DATE: ${it.date}", Toast.LENGTH_SHORT).show()
        }
        //리뷰 리스트에 연결
        review_list.adapter = adapter

        viewDatabase()
    }

    private fun viewDatabase() {

        // val document1 = user?.uid

        progressBar8.visibility = View.VISIBLE
        firestore = FirebaseFirestore.getInstance()
            firestore?.collection("reviewList")?.whereEqualTo("mate",title_input2.text)?.get()
                ?.addOnCompleteListener { task ->
                    progressBar8.visibility = View.GONE
                    if (task.isSuccessful) {
                        var reviewList = ArrayList<ReviewDTO>()
                        for (dc in task.result!!.documents) {
                            var reviewDTO = dc.toObject(ReviewDTO::class.java)
                            reviewList.add(reviewDTO!!)
                        }
                        adapter?.setItems(reviewList)
                        adapter?.notifyDataSetChanged()
                    }
                    else {
                        Toast.makeText(this, task.exception?.message, Toast.LENGTH_LONG).show()
                    }
                }
        }

}
