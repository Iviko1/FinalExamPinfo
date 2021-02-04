package com.example.finalexam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class PasswordResetActivity : AppCompatActivity() {
    private lateinit var resetButton: Button
    private lateinit var inputEmail : EditText
    private lateinit var newAccButton: Button
    private lateinit var backToLoginButton: Button
    private lateinit var mAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password_reset)
        supportActionBar?.hide()

        mAuth = FirebaseAuth.getInstance()
        newAccButton = findViewById(R.id.newAccButton)
        resetButton = findViewById(R.id.resetButton)
        backToLoginButton = findViewById(R.id.backToLoginButton)
        inputEmail = findViewById(R.id.inputEmail)

        backToLoginButton.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        newAccButton.setOnClickListener {
            val intent = Intent(this,RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }

        resetButton.setOnClickListener {
            val email = inputEmail.text.toString()
            if (email.isEmpty()){
                Toast.makeText(this, "Email Box Can't Be Empty", Toast.LENGTH_SHORT).show()
            } else {
                mAuth.sendPasswordResetEmail(email).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        startActivity(Intent(this,MainActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this, "Error!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}