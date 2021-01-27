package com.example.finalexam.Fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.finalexam.MainActivity
import com.example.finalexam.NavigationActivity
import com.example.finalexam.R
import com.example.finalexam.UserInfo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

import kotlin.math.sign

class user_Fragment:Fragment(R.layout.user_fragment) {

    private lateinit var signOutButton: Button
    private lateinit var mAuth: FirebaseAuth
    private lateinit var db: DatabaseReference
    private lateinit var userNameText: TextView
    private lateinit var userPicture: ImageView



    private lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mAuth = FirebaseAuth.getInstance()
        db = FirebaseDatabase.getInstance().getReference("UserInfo")

        userNameText = view.findViewById(R.id.userNameText)
        userPicture = view.findViewById(R.id.userPicture)
        navController = Navigation.findNavController(view)
        signOutButton = view.findViewById(R.id.signOutButton)



        signOutButton.setOnClickListener {
            mAuth.signOut()
            val intent = Intent(activity, MainActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
        if (mAuth.currentUser?.uid != null) {
            db.child(mAuth.currentUser?.uid!!).addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(activity, "Error!", Toast.LENGTH_SHORT).show()
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    val p = snapshot.getValue(UserInfo::class.java)

                    if (p != null && activity != null) {
                        userNameText.text = p.name

                        Glide.with(this@user_Fragment)
                                .load(p.url)
                                .centerCrop()
                                .placeholder(R.drawable.ic_launcher_background)
                                .into(userPicture)
                    }
                }
            })
        }
    }
}


