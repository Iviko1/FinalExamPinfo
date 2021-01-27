package com.example.finalexam.Fragments

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.finalexam.R
import com.example.finalexam.UserInfo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class info_Fragment: Fragment(R.layout.info_fragment) {

    private lateinit var userNameInput : EditText
    private lateinit var userUrlInput : EditText

    private lateinit var saveButton: Button
    private lateinit var db: DatabaseReference
    private lateinit var mAuth: FirebaseAuth


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
        db = FirebaseDatabase.getInstance().getReference("UserInfo")

        userNameInput = view.findViewById(R.id.personName)
        userUrlInput = view.findViewById(R.id.personPicture)
        saveButton = view.findViewById(R.id.saveButton)


        saveButton.setOnClickListener {

            val name = userNameInput.text.toString()
            val url = userUrlInput.text.toString()


            val personInfo = UserInfo(name,url)

            if (mAuth.currentUser?.uid != null) {
                db.child(mAuth.currentUser?.uid!!).setValue(personInfo).addOnCompleteListener { task ->
                    if (task.isSuccessful){
                        Toast.makeText(activity,"Operation Successful",Toast.LENGTH_SHORT).show()
                        userNameInput.text = null
                        userUrlInput.text = null

                    }else{
                        Toast.makeText(activity,"Error!",Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

    }
}