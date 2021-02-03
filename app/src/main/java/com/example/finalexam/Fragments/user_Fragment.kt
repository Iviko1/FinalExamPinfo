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
import com.example.finalexam.*
import com.example.finalexam.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class user_Fragment:Fragment(R.layout.user_fragment) {

    private lateinit var signOutButton: Button
    private lateinit var mAuth: FirebaseAuth
    private lateinit var db: DatabaseReference
    private lateinit var userNameText: TextView
    private lateinit var userAgeText: TextView
    private lateinit var userStatusText: TextView
    private lateinit var userWorkText: TextView
    private lateinit var userHobbiesText: TextView
    private lateinit var userPicture: ImageView
    private lateinit var recyclerButton: Button
    private lateinit var saveToDbButton: Button

    private lateinit var navController: NavController
    companion object {
        var userList = ArrayList<UserInfo>()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mAuth = FirebaseAuth.getInstance()
        db = FirebaseDatabase.getInstance().getReference("UserInfo")

        val reference : DatabaseReference = FirebaseDatabase.getInstance().reference.child("Users")
        val reference2 : DatabaseReference = FirebaseDatabase.getInstance().reference.child("Users_static")
        userNameText = view.findViewById(R.id.userNameText)
        userAgeText = view.findViewById(R.id.userAgeText)
        userStatusText = view.findViewById(R.id.userStatusText)
        userPicture = view.findViewById(R.id.userPicture)
        userHobbiesText = view.findViewById(R.id.userHobbiesText)
        userWorkText = view.findViewById(R.id.userWorkText)
        navController = Navigation.findNavController(view)
        signOutButton = view.findViewById(R.id.signOutButton)
        recyclerButton = view.findViewById(R.id.recyclerButton)
        saveToDbButton = view.findViewById(R.id.saveTodb)

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
            startActivity(intent)
            uploadList(view)
        }
        saveToDbButton.setOnClickListener {
            fun uploadList(view: View){
                reference2.setValue(userList).addOnCompleteListener { task ->
                    if(task.isSuccessful){
                        Toast.makeText(activity, "Success", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(activity, "Error", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            uploadList(view)
        }


        signOutButton.setOnClickListener {
            mAuth.signOut()
            val intent = Intent(activity, MainActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
        if (mAuth.currentUser?.uid != null) {
            reference.child("Users_static").addListenerForSingleValueEvent(object: ValueEventListener{
                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(activity, "Error", Toast.LENGTH_SHORT).show()
                }
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(activity != null){
                        snapshot.children.forEach {
                            val x = it.getValue(UserInfo::class.java)
                            val name = x?.name
                            val url = x?.url
                            val age = x?.age
                            val status = x?.status
                            val work = x?.work
                            val hobbies = x?.hobbies
                            userList.add(UserInfo(name,url,age,status,work,hobbies))
                        }
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
                        userAgeText.text = p.age
                        userStatusText.text = p.status
                        userWorkText.text = p.work
                        userHobbiesText.text = p.hobbies

                        Glide.with(this@user_Fragment)
                                .load(p.url)
                                .centerCrop()
                                .placeholder(R.drawable.ic_launcher_background)
                                .into(userPicture)
                        var isInList: Boolean = false
                        for (i in userList){
                            if (i.name == p.name && i.url == p.url && i.age == p.age && i.status == p.status && p.work == i.work && p.hobbies == i.hobbies){
                                isInList = true
                            }
                        }
                        if (!isInList && p.name != null && p.url != null && p.age != null && p.status != null && p.work != null && p.hobbies != null){
                            userList.add(UserInfo(p.name,p.url,p.age,p.status,p.work,p.hobbies))
                        }

                    }
                }
            })
        }
    }
}


