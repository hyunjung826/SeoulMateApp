package com.example.SeoulMate

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_enrollment.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import kotlinx.android.synthetic.main.activity_my_page.*
import java.io.IOException
import java.util.*
import kotlin.collections.HashMap
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_apply.*


// mate 등록 화면
class Enrollment : AppCompatActivity() {

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

    private val PICK_IMAGE_REQUEST = 71
    private var firebaseStore:FirebaseStorage?= null
    private var firebaseAuth: FirebaseAuth?=null
    private var storageReference: StorageReference? = null
    private var filePath: Uri? = null
    private var photoUri:String? = null
    private var firestore: FirebaseFirestore? = null
    private var auth: FirebaseAuth? = null

    val user = FirebaseAuth.getInstance().currentUser
    val user_id = user?.uid

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enrollment)

        auth = FirebaseAuth.getInstance()
        firebaseStore= FirebaseStorage.getInstance()
        storageReference = FirebaseStorage.getInstance().reference
        btn_save.setOnClickListener {
            addDatabase()
        }
        img_pick_btn.setOnClickListener {
            launchGallery()
        }
    }
    private fun launchGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            if(data == null || data.data == null){
                return
            }
            filePath = data.data
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filePath)
                image_view.setImageBitmap(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun addUploadRecordToDb(uri: String){
        val db = FirebaseFirestore.getInstance()
        val data = HashMap<String, Any>()
        data["imageUrI"] = uri

        val documentImg = title_input.text.toString()
        db.collection("posts")?.document(documentImg)
            .set(data)
            .addOnSuccessListener { task ->
                Toast.makeText(this, "Saved to DB", Toast.LENGTH_LONG).show()
            }
            .addOnFailureListener { task ->
                Toast.makeText(this, "Error saving to DB", Toast.LENGTH_LONG).show()
            }
    }

    private fun addDatabase() {
        if (filePath != null) {
            val ref = storageReference?.child("uploads/" + UUID.randomUUID().toString())
            val uploadTask = ref?.putFile(filePath!!)

            val urlTask =
                uploadTask?.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
                    if (!task.isSuccessful) {
                        task.exception?.let {
                            throw it
                        }
                    }
                    return@Continuation ref.downloadUrl
                })?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val downloadUri = task.result
                        addUploadRecordToDb(downloadUri.toString())
                        photoUri = downloadUri.toString()

                        val mateDTO = mateDTO(
                            title_input.text.toString(),
                            name_input.text.toString(), age_input.text.toString(),
                            gender_input.text.toString(), email_input.text.toString(),
                            photoUri.toString(), where_input.text.toString(), when_input.text.toString(),
                            budget_input.text.toString(), plan_input.text.toString(),
                            home_stay_input.text.toString(), user_id.toString()
                        )

                        val document = title_input.text.toString()

                        progressBar3.visibility = View.VISIBLE
                        firestore = FirebaseFirestore.getInstance()
                        firestore?.collection("mateList")?.document(document)
                            ?.set(mateDTO)?.addOnCompleteListener { task ->
                                progressBar3.visibility = View.GONE
                                if (task.isSuccessful) {
                                    Toast.makeText(this, "mate 등록 성공", Toast.LENGTH_LONG).show()
                                    moveHomePage(auth?.currentUser)
                                } else {
                                    Toast.makeText(this, task.exception?.message, Toast.LENGTH_LONG).show()
                                }
                            }
                    } else {
                        Toast.makeText(this, "이미지 등록 안됨", Toast.LENGTH_SHORT).show()
                    }
                }?.addOnFailureListener {

                }
        } else {
            Toast.makeText(this, "Please Upload an Image", Toast.LENGTH_SHORT).show()
        }

        if (title_input.text.isEmpty() || name_input.text.isEmpty() || age_input.text.isEmpty() ||
            gender_input.text.isEmpty() || email_input.text.isEmpty() ||
            where_input.text.isEmpty() || when_input.text.isEmpty() ||
            budget_input.text.isEmpty() || plan_input.text.isEmpty() ||
            home_stay_input.text.isEmpty())
        {
            Toast.makeText(this, "입력되지 않은 값이 있습니다.", Toast.LENGTH_LONG).show()
            //txtAddResult.text = "입력되지 않은 값이 있습니다."
            return
        }

    }

    private fun moveHomePage(user: FirebaseUser?) {
        if (user != null) {
            startActivity(Intent(this, Home::class.java))
            finish()
        }
    }
}