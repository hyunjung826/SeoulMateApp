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
import kotlinx.android.synthetic.main.activity_my_page.*


// 마이페이지 화면
class MyPage : AppCompatActivity(){private var adapter: MyPostAdapter? = null

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

    val user = FirebaseAuth.getInstance().currentUser
    val user_id = user?.uid

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_page)

        btn_insert.setOnClickListener {
            var myIntent = Intent(this, Enrollment::class.java)
            startActivity(myIntent)
        }

        post_list.layoutManager = LinearLayoutManager(this)

        adapter = MyPostAdapter(this)
        {
            var myIntent2 = Intent(this, Update::class.java)
            myIntent2.putExtra("title", it.title)
            myIntent2.putExtra("name", it.name)
            myIntent2.putExtra("age", it.age)
            myIntent2.putExtra("gender", it.gender)
            myIntent2.putExtra("email", it.email)
            myIntent2.putExtra("spot", it.spot)
            myIntent2.putExtra("date", it.date)
            myIntent2.putExtra("budget", it.budget)
            myIntent2.putExtra("plan", it.plan)
            myIntent2.putExtra("homeStay", it.homeStay)
            myIntent2.putExtra("photo",it.photo)

            startActivity(myIntent2)
            //Toast.makeText(this, "name: ${it.name}, age: ${it.age},gender: ${it.gender}, spot: ${it.spot}", Toast.LENGTH_SHORT).show()
        }

        post_list.adapter = adapter

        viewDatabase()
    }

    private fun viewDatabase() {
        progressBar5.visibility = View.VISIBLE
        firestore = FirebaseFirestore.getInstance()

        firestore?.collection("mateList")?.whereEqualTo("uid",user_id)?.get()
            ?.addOnCompleteListener { task ->
                progressBar5.visibility = View.GONE
                if (task.isSuccessful) {
                    var postList = ArrayList<mateDTO>()
                    for (dc in task.result!!.documents) {
                        var mateDTO = dc.toObject(mateDTO::class.java)
                        postList.add(mateDTO!!)
                    }
                    adapter?.setItems(postList)
                    adapter?.notifyDataSetChanged()
                }
                else {
                    Toast.makeText(this, task.exception?.message, Toast.LENGTH_LONG).show()
                }
            }

    }
}
