package com.example.finalexam

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.firebase.auth.FirebaseAuth


class RegisterActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var inputEmail : EditText
    private lateinit var inputPassword : EditText
    private lateinit var inputPassword2 : EditText
    private lateinit var registerButton: Button
    private lateinit var backButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        mAuth = FirebaseAuth.getInstance()
        supportActionBar?.hide()

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

        inputEmail = findViewById(R.id.signUpEmail)
        inputPassword = findViewById(R.id.signUpPassword)
        inputPassword2 = findViewById(R.id.signUpPassword2)
        registerButton = findViewById(R.id.registerButton)
        backButton = findViewById(R.id.backButton)

        backButton.setOnClickListener {
            intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        registerButton.setOnClickListener {

            val email = inputEmail.text.toString()
            val password = inputPassword.text.toString()
            val password2 = inputPassword2.text.toString()

            if (email.isEmpty() || password.isEmpty() || password2.isEmpty()){
                Toast.makeText(this, "Please Fill All Boxes", Toast.LENGTH_LONG).show()
            } else if(password != password2) {
                Toast.makeText(this, "Both Passwords Must Be Identical", Toast.LENGTH_LONG).show()
            } else {
                mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this, "Error!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}