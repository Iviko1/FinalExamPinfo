package com.example.finalexam.Fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
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
import com.example.finalexam.*
import com.example.finalexam.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.lang.reflect.Type

import kotlin.math.sign

class user_Fragment:Fragment(R.layout.user_fragment) {

    private lateinit var signOutButton: Button
    private lateinit var mAuth: FirebaseAuth
    private lateinit var db: DatabaseReference
    private lateinit var userNameText: TextView
    private lateinit var userPicture: ImageView
    private lateinit var recyclerButton: Button

    private lateinit var navController: NavController
    companion object {
        var userList = ArrayList<UserInfo>()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mAuth = FirebaseAuth.getInstance()
        db = FirebaseDatabase.getInstance().getReference("UserInfo")

        val reference : DatabaseReference = FirebaseDatabase.getInstance().reference.child("Users")
        userNameText = view.findViewById(R.id.userNameText)
        userPicture = view.findViewById(R.id.userPicture)
        navController = Navigation.findNavController(view)
        signOutButton = view.findViewById(R.id.signOutButton)
        recyclerButton = view.findViewById(R.id.recyclerButton)

        recyclerButton.setOnClickListener {
            val intent = Intent(activity, RecyclerActivity::class.java)
            fun uploadList(view: View){
                reference.setValue(userList).addOnCompleteListener { task ->
                    if(task.isSuccessful){
                        Toast.makeText(activity, "Success", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(activity, "Error", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            uploadList(view)
            startActivity(intent)
        }


        signOutButton.setOnClickListener {
            mAuth.signOut()
            val intent = Intent(activity, MainActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
        if (mAuth.currentUser?.uid != null) {

            reference.child("Users").addListenerForSingleValueEvent(object: ValueEventListener{
                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(activity, "Error", Toast.LENGTH_SHORT).show()
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    val d = snapshot.getValue(UserInfo::class.java)
                    
                    if(d != null && activity != null){
                        //for (i in d) {
                           // val name: String? = d.name
                            //val url: String? = d.url
                           // userList.add(i)
                       //}
                    }
                }
            })


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
                        var isInList: Boolean = false
                        for (i in userList){
                            if (i.name == p.name && i.url == p.url){
                                isInList = true
                            }
                        }
                        if (!isInList && p.name != null && p.url != null){
                            userList.add(UserInfo(p.name,p.url))
                        }

                    }
                }
            })
        }
    }

}


