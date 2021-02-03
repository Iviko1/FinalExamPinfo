package com.example.finalexam

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class RecyclerActivity : AppCompatActivity() {

    private lateinit var db : DatabaseReference
    private lateinit var recycler : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler)
        supportActionBar?.hide()
        recycler = findViewById(R.id.recyclerView)
        db = FirebaseDatabase.getInstance().reference

        val adapter =Adapter(MainActivity.userList, this)
        recycler.adapter = adapter
        recycler.layoutManager = GridLayoutManager(this,1)

    }
}