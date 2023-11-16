package com.example.group6

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class EditProject: androidx.activity.ComponentActivity() {
    private lateinit var editStudName: EditText
    private lateinit var editProjName: EditText
    private lateinit var editProjDesc: EditText
    private lateinit var updateBtn: Button
    private var projIDBack: String ?= null


    private val database = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_project_page)
        var bundle: Bundle? = intent.extras
        var projIdent =bundle?.getString("projectID")
        var personName =bundle?.getString("studName")
        var projectName=bundle?.getString("projName")
        var projectDescr =bundle?.getString("projDesc")
        var projEvent =bundle?.getString("evID")
        var projUser =bundle?.getString("studID")
        var projID = projIdent.toString()
        editStudName = findViewById<EditText>(R.id.editName)
        editProjName = findViewById<EditText>(R.id.editProj)
        editProjDesc = findViewById<EditText>(R.id.editDesc)
        editStudName.setText(personName)
        editProjName.setText(projectName)
        editProjDesc.setText(projectDescr)

        updateBtn = findViewById<Button>(R.id.submitProj)

        updateBtn.setOnClickListener{
            val editedName = editStudName.text.toString()
            val editedProjName = editProjName.text.toString()
            val editedProjDesc = editProjDesc.text.toString()
            val projUpdate = hashMapOf(
                "userName" to editedName,
                "name" to editedProjName,
                "description" to editedProjDesc,
                "eventID" to projEvent,
                "userID" to projUser,
                "projID" to projID

            )
            database.collection("Projects").document(projID).update(projUpdate as Map<String, Any>)
                .addOnSuccessListener {
                    Toast.makeText(this, "Updated Project Information", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Failed to update :(", Toast.LENGTH_SHORT).show()
                }
        }



    }
}