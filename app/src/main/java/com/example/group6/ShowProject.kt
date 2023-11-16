package com.example.group6

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.AggregateSource
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.awaitAll

class ShowProject: ComponentActivity() {

    //FOR GOING BACK TO EVENT PAGE
    private var EventNameBack: String? = null
    private var EventDescBack: String? = null
    private var EventDateBack: String? = null
    private var EventLocationBack: String? = null
    private var EventIDBack: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.show_project)

        val database = Firebase.firestore

        var ProjectName = findViewById<TextView>(R.id.ShowProjectName)
        var PersonName = findViewById<TextView>(R.id.PersonName)
        var ProjectDesc = findViewById<TextView>(R.id.ProjectDesc)
        var BackButton = findViewById<Button>(R.id.BackButton)
        var RateButton = findViewById<Button>(R.id.ratingButton)
        var Ratebar = findViewById<RatingBar>(R.id.ratingBar)
        var editButton = findViewById<Button>(R.id.EditProject)

        var bundle: Bundle? = intent.extras
        var ProjectNameExtra = bundle!!.getString("ShowProjectName")
        var PersonNameExtra = bundle.getString("PersonName")
        var ProjectDescExtra = bundle.getString("ProjectDesc")
        var projectIDextra = bundle.getString("projID")
        var projectUserExtra = bundle.getString("userID")
        val projID = projectIDextra.toString()
        ProjectName.text = ProjectNameExtra
        PersonName.text = PersonNameExtra
        ProjectDesc.text = ProjectDescExtra


        //FOR GOING BACK TO EVENT PAGE
        EventNameBack = bundle!!.getString("ShowEventName")
        EventDescBack = bundle.getString("ShowEventDesc")
        EventDateBack = bundle.getString("ShowEventDate")
        EventLocationBack = bundle.getString("ShowEventLocation")
        EventIDBack = bundle.getString("ShowEventID")


        Log.e("GetEventName", "IN SHOWPROJECT Event Name is $EventNameBack")


        BackButton.setOnClickListener {
            val intent = Intent(applicationContext, ShowEvent::class.java)

            intent.putExtra("ShowEventName", EventNameBack)
            intent.putExtra("ShowEventDesc", EventDescBack)
            intent.putExtra("ShowEventDate", EventDateBack)
            intent.putExtra("ShowEventLocation", EventLocationBack)
            intent.putExtra("ShowEventID", EventIDBack)

            Log.e("GetEventName", "FROM SHOWPROJECT TO SHOWEVENT Event Name is $EventNameBack")


            startActivity(intent)
            finish()
        }
        RateButton.setOnClickListener {
            val rateRef = database.collection("Ratings").document()
            val rating = Ratebar.rating.toInt()
            val curUser = FirebaseAuth.getInstance().currentUser
            val uuid = curUser?.uid
            val userUid = uuid.toString()
            val rater = hashMapOf(
                "userID" to userUid,
                "projID" to projID,
                "stars" to rating
            )
            rateRef.set(rater)
                .addOnSuccessListener {
                    Toast.makeText(this, "Successfully added your rating", Toast.LENGTH_SHORT)
                        .show()
                    val intent = Intent(applicationContext, ShowEvent::class.java)

                    intent.putExtra("ShowEventName", EventNameBack)
                    intent.putExtra("ShowEventDesc", EventDescBack)
                    intent.putExtra("ShowEventDate", EventDateBack)
                    intent.putExtra("ShowEventLocation", EventLocationBack)
                    intent.putExtra("ShowEventID", EventIDBack)

                    Log.e(
                        "GetEventName",
                        "RATED PROJECT, SHOWPROJECT TO SHOWEVENT Event Name is $EventNameBack"
                    )
                    startActivity(intent)
                    finish()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Failed to added your rating", Toast.LENGTH_SHORT).show()
                }
        }
        editButton.setOnClickListener {
            val projID = projectIDextra.toString()
            val userID = projectUserExtra.toString()
            val curUser = FirebaseAuth.getInstance().currentUser
            val uuid = curUser?.uid
            if (uuid == userID) {
                val intent = Intent(applicationContext, EditProject::class.java)
                intent.putExtra("projID", projID)
                startActivity(intent)
                finish()
            }
        }
    }
}