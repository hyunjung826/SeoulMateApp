package com.example.SeoulMate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_home.*

// 어플 메인화면
class Home : AppCompatActivity() {private var adapter: MateAdapter? = null

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


            R.id.action_btn1 -> {

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
        setContentView(R.layout.activity_home)

        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = MateAdapter(this)
        {
            var myIntent = Intent(this, Apply::class.java)

            myIntent.putExtra("title", it.title)
            myIntent.putExtra("name", it.name)
            myIntent.putExtra("age", it.age)
            myIntent.putExtra("gender", it.gender)
            myIntent.putExtra("email", it.email)
            myIntent.putExtra("spot", it.spot)
            myIntent.putExtra("date", it.date)
            myIntent.putExtra("budget", it.budget)
            myIntent.putExtra("plan", it.plan)
            myIntent.putExtra("homeStay", it.homeStay)
            myIntent.putExtra("photo",it.photo)

            startActivity(myIntent)
            //Toast.makeText(this, "name: ${it.name}, age: ${it.age},gender: ${it.gender}, spot: ${it.spot}", Toast.LENGTH_SHORT).show()
        }

        recyclerView.adapter = adapter

        viewDatabase()
    }

    private fun viewDatabase() {

        progressBar4.visibility = View.VISIBLE
        firestore = FirebaseFirestore.getInstance()


        firestore?.collection("mateList")?.get()
            ?.addOnCompleteListener { task ->
                progressBar4.visibility = View.GONE
                if (task.isSuccessful) {
                    var mateList = ArrayList<mateDTO>()
                    for (dc in task.result!!.documents) {
                        var mateDTO = dc.toObject(mateDTO::class.java)
                        mateList.add(mateDTO!!)
                    }
                    adapter?.setItems(mateList)
                    adapter?.notifyDataSetChanged()
                } else {
                    Toast.makeText(this, task.exception?.message, Toast.LENGTH_LONG).show()
                }
            }


    }
}

