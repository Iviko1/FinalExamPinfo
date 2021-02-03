package com.example.finalexam

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.text.Layout
import androidx.constraintlayout.widget.ConstraintLayout
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ShareCompat
import com.example.finalexam.Fragments.user_Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserInfo
import java.lang.reflect.Type


class MainActivity : AppCompatActivity() {

    //private lateinit var root_layout : ConstraintLayout
    private lateinit var loginButton : Button
    private lateinit var registrationButton : Button

    private lateinit var mAuth: FirebaseAuth
    private lateinit var loginEmail : EditText
    private lateinit var loginPassword : EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mAuth = FirebaseAuth.getInstance()
        supportActionBar?.hide()


        if (mAuth.currentUser != null){
            startActivity(Intent(this,NavigationActivity::class.java))
            finish()
        }

        loginEmail = findViewById(R.id.loginEmail)
        loginPassword = findViewById(R.id.loginPassword)
        //root_layout = findViewById(R.id.root_layout)
        //window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

        //val animDrawable = root_layout.background as AnimationDrawable
        //animDrawable.setEnterFadeDuration(10)
        //animDrawable.setExitFadeDuration(5000)
        //animDrawable.start()

        registrationButton = findViewById(R.id.registrationButton)
        loginButton = findViewById(R.id.loginButton)



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
        }
        registrationButton.setOnClickListener {
            startActivity(Intent(this,RegisterActivity::class.java))
        }
    }
}