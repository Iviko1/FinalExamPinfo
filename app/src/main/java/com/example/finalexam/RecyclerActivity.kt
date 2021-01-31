package com.example.finalexam

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalexam.Fragments.user_Fragment
import com.google.firebase.database.*

class RecyclerActivity : AppCompatActivity() {

    private lateinit var db : DatabaseReference
    private lateinit var recycler : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler)

        recycler = findViewById(R.id.recyclerView)
        db = FirebaseDatabase.getInstance().reference

        val adapter =Adapter(user_Fragment.userList, this)
        recycler.adapter = adapter
        recycler.layoutManager = GridLayoutManager(this,1)

    }
}