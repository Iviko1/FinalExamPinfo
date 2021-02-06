package com.example.finalexam


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {

    private lateinit var loginButton : Button
    private lateinit var registrationButton : Button
    private lateinit var forgotButton : Button
    private lateinit var mAuth: FirebaseAuth
    private lateinit var loginEmail : EditText
    private lateinit var loginPassword : EditText
    companion object {
        var userList = ArrayList<com.example.finalexam.UserInfo>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mAuth = FirebaseAuth.getInstance()
        supportActionBar?.hide()
        val reference2 : DatabaseReference = FirebaseDatabase.getInstance().reference
        if (mAuth.currentUser != null){
            startActivity(Intent(this,NavigationActivity::class.java))
            reference2.child("UserInfo").addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    return
                }

                override fun onDataChange(snapshot: DataSnapshot) {

                    snapshot.children.forEach {
                        val x = it.getValue(UserInfo::class.java)
                        val name = x?.name
                        val url = x?.url
                        val age = x?.age
                        val status = x?.status
                        val work = x?.work
                        val hobbies = x?.hobbies
                        var isInList: Boolean = false
                        for (i in MainActivity.userList) {
                            if (i.name == name && i.url == url && i.age == age && i.status == status && work == i.work && hobbies == i.hobbies) {
                                isInList = true
                            }
                        }
                        if (!isInList && name != null && url != null && age != null && status != null && work != null && hobbies != null) {
                            MainActivity.userList.add(UserInfo(name, url, age, status, work, hobbies))
                        }
                    }

                }
            })
            finish()
        }

        loginEmail = findViewById(R.id.loginEmail)
        loginPassword = findViewById(R.id.loginPassword)
        registrationButton = findViewById(R.id.registrationButton)
        loginButton = findViewById(R.id.loginButton)
        forgotButton = findViewById(R.id.forgotButton)


        forgotButton.setOnClickListener {
            intent = Intent(this,PasswordResetActivity::class.java)
            startActivity(intent)
        }

        loginButton.setOnClickListener {
            val email = loginEmail.text.toString()
            val password = loginPassword.text.toString()

            if (email.isEmpty() || password.isEmpty()){
                Toast.makeText(this, "Please Fill Both Boxes", Toast.LENGTH_SHORT).show()
            }else {
                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener {task ->
                    if (task.isSuccessful) {
                        startActivity(Intent(this,NavigationActivity::class.java))
                        finish()
                    }else{
                        Toast.makeText(this, "Invalid Credentials", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            reference2.child("UserInfo").addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    return
                }

                override fun onDataChange(snapshot: DataSnapshot) {

                        snapshot.children.forEach {
                            val x = it.getValue(UserInfo::class.java)
                            val name = x?.name
                            val url = x?.url
                            val age = x?.age
                            val status = x?.status
                            val work = x?.work
                            val hobbies = x?.hobbies
                            var isInList : Boolean = false
                            for (i in MainActivity.userList){
                                if (i.name == name && i.url == url && i.age == age && i.status == status && work == i.work && hobbies == i.hobbies){
                                    isInList = true
                                }
                            }
                            if (!isInList && name != null && url != null && age != null && status != null && work != null && hobbies != null){
                                MainActivity.userList.add(UserInfo(name,url,age,status,work,hobbies))
                            }
                        }

                }
            })
        }

        registrationButton.setOnClickListener {
            startActivity(Intent(this,RegisterActivity::class.java))
            finish()
        }
    }
}