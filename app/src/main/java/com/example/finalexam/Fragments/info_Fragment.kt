package com.example.finalexam.Fragments

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.finalexam.R
import com.example.finalexam.UserInfo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class info_Fragment: Fragment(R.layout.info_fragment) {

    private lateinit var userNameInput : EditText
    private lateinit var userUrlInput : EditText
    private lateinit var userAgeInput : EditText
    private lateinit var userStatusInput : EditText
    private lateinit var userWorkInput : EditText
    private lateinit var userHobbiesInput : EditText
    private lateinit var saveButton: Button
    private lateinit var db: DatabaseReference
    private lateinit var mAuth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mAuth = FirebaseAuth.getInstance()
        db = FirebaseDatabase.getInstance().getReference("UserInfo")

        userNameInput = view.findViewById(R.id.personName)
        userUrlInput = view.findViewById(R.id.personPicture)
        userStatusInput = view.findViewById(R.id.personStatus)
        userAgeInput = view.findViewById(R.id.personAge)
        userHobbiesInput = view.findViewById(R.id.personHobbies)
        userWorkInput = view.findViewById(R.id.personWork)
        saveButton = view.findViewById(R.id.saveButton)

        saveButton.setOnClickListener {

            val name = userNameInput.text.toString()
            val url = userUrlInput.text.toString()
            val age = userAgeInput.text.toString()
            val status = userStatusInput.text.toString()
            val hobbies = userHobbiesInput.text.toString()
            val work = userWorkInput.text.toString()

            val personInfo = UserInfo(name,url,age,status,work,hobbies)

            if (mAuth.currentUser?.uid != null) {
                db.child(mAuth.currentUser?.uid!!).setValue(personInfo).addOnCompleteListener { task ->
                    if (task.isSuccessful){
                        Toast.makeText(activity,"Operation Successful",Toast.LENGTH_SHORT).show()
                        userNameInput.text = null
                        userUrlInput.text = null
                        userStatusInput.text = null
                        userAgeInput.text = null
                        userHobbiesInput.text = null
                        userWorkInput.text = null
                    }else{
                        Toast.makeText(activity,"Error!",Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}