package com.example.group6

import android.content.Intent
import android.os.Bundle
import android.util.Log
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


    private val database = Firebase.firestore

    //FOR GOING BACK TO PROJECT PAGE
    var projectIDBack: String? = null
    var personNameBack: String? = null
    var projectNameBack: String? = null
    var projectDescBack: String? = null

    //FOR GOING BACK TO EVENT PAGE
    private var EventNameBack: String? = null
    private var EventDescBack: String? = null
    private var EventDateBack: String? = null
    private var EventLocationBack: String? = null
    private var EventIDBack: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_project_page)
        var bundle: Bundle? = intent.extras
        var projIdent = bundle?.getString("projectID")
        var personName = bundle?.getString("studName")
        var projectName = bundle?.getString("projName")
        var projectDescr = bundle?.getString("projDesc")
        var projEvent = bundle?.getString("evID")
        var projUser = bundle?.getString("studID")
        var projID = projIdent.toString()
        editStudName = findViewById<EditText>(R.id.editName)
        editProjName = findViewById<EditText>(R.id.editProj)
        editProjDesc = findViewById<EditText>(R.id.editDesc)
        editStudName.setText(personName)
        editProjName.setText(projectName)
        editProjDesc.setText(projectDescr)

        //FOR GOING BACK TO PROJECT PAGE
        projectIDBack = bundle?.getString("projectID")
        personNameBack = bundle?.getString("studName")
        projectNameBack = bundle?.getString("projName")
        projectDescBack = bundle?.getString("projDesc")

        Log.e("projectNameBack", "IN EDITPROJECT project Name is $projectNameBack")

        //FOR GOING BACK TO EVENT PAGE
        EventNameBack = bundle!!.getString("ShowEventName")
        EventDescBack = bundle.getString("ShowEventDesc")
        EventDateBack = bundle.getString("ShowEventDate")
        EventLocationBack = bundle.getString("ShowEventLocation")
        EventIDBack = bundle.getString("ShowEventID")

        Log.e("projectNameBack", "IN EDITPROJECT Event Name is $EventNameBack")


        var BackButton = findViewById<Button>(R.id.BackButton)
        updateBtn = findViewById(R.id.submitProj)

        BackButton.setOnClickListener{
            val intent = Intent(applicationContext, ShowProject::class.java)

            intent.putExtra("projID", projectIDBack)
            intent.putExtra("PersonName", personNameBack)
            intent.putExtra("ShowProjectName", projectNameBack)
            intent.putExtra("ProjectDesc", projectDescBack)

            intent.putExtra("ShowEventName", EventNameBack)
            intent.putExtra("ShowEventDesc", EventDescBack)
            intent.putExtra("ShowEventDate", EventDateBack)
            intent.putExtra("ShowEventLocation", EventLocationBack)
            intent.putExtra("ShowEventID", EventIDBack)
            intent.putExtra("userID", projUser)


            Log.e("projectNameBack", "EDITPROJECT TO SHOWPROJECT, project Name is $projectNameBack")
            Log.e("GetEventName", "FROM EDITPROJECT TO SHOWPROJECT Event Name is $EventNameBack")


            startActivity(intent)
            finish()

        }

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
            val intent = Intent(applicationContext, ShowProject::class.java)

            database.collection("Projects").document(projID).update(projUpdate as Map<String, Any>)
                .addOnSuccessListener {
                    Toast.makeText(this, "Updated Project Information", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Failed to update", Toast.LENGTH_SHORT).show()
                }

            projectIDBack = projID
            personNameBack = editedName
            projectNameBack = editedProjName
            projectDescBack = editedProjDesc

            intent.putExtra("projID", projectIDBack)
            intent.putExtra("PersonName", personNameBack)
            intent.putExtra("ShowProjectName", projectNameBack)
            intent.putExtra("ProjectDesc", projectDescBack)

            intent.putExtra("ShowEventName", EventNameBack)
            intent.putExtra("ShowEventDesc", EventDescBack)
            intent.putExtra("ShowEventDate", EventDateBack)
            intent.putExtra("ShowEventLocation", EventLocationBack)
            intent.putExtra("ShowEventID", EventIDBack)
            intent.putExtra("userID", projUser)

            Log.e("projectNameBack", "UPDATED PROJECT, FROM EDITPROJECT TO SHOWPROJECT, project Name is $projectNameBack")
            Log.e("GetEventName", "UPDATED PROJECT, FROM EDITPROJECT TO SHOWPROJECT Event Name is $EventNameBack")

            startActivity(intent)
            finish()
        }



    }
}