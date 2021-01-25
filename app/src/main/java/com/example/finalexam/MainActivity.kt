package com.example.finalexam

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout
import androidx.constraintlayout.widget.ConstraintLayout
import android.view.View
import android.widget.Button


class MainActivity : AppCompatActivity() {

    private lateinit var root_layout : ConstraintLayout
    private lateinit var loginButton : Button
    private lateinit var registrationButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        root_layout = findViewById(R.id.root_layout)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

        val animDrawable = root_layout.background as AnimationDrawable
        animDrawable.setEnterFadeDuration(10)
        animDrawable.setExitFadeDuration(5000)
        animDrawable.start()

        registrationButton = findViewById(R.id.registrationButton)
        loginButton = findViewById(R.id.loginButton)


        loginButton.setOnClickListener {
            startActivity(Intent(this,NavigationActivity::class.java))
            finish()
        }


    }
}