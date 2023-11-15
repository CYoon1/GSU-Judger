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

        var bundle: Bundle? = intent.extras
        var ProjectNameExtra = bundle!!.getString("ShowProjectName")
        var PersonNameExtra = bundle.getString("PersonName")
        var ProjectDescExtra = bundle.getString("ProjectDesc")
        var projectIDextra = bundle.getString("projID")
        val projID = projectIDextra.toString()
        ProjectName.text = ProjectNameExtra
        PersonName.text = PersonNameExtra
        ProjectDesc.text = ProjectDescExtra

        BackButton.setOnClickListener{
            val intent = Intent(applicationContext, EventListener::class.java)
            startActivity(intent)
            finish()
        }
        RateButton.setOnClickListener{
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
                    Toast.makeText(this, "Successfully added your rating", Toast.LENGTH_SHORT).show()
                    val intent = Intent(applicationContext, EventListener::class.java)
                    startActivity(intent)
                    finish()
                    //projName.text.clear()
                    //projDesc.text.clear()
                }
                .addOnFailureListener{
                    Toast.makeText(this, "Failed to added your rating", Toast.LENGTH_SHORT).show()
                }
//            var counter = 0
//            val query = database.collection("Projects").document(projID).collection("ratings")
//            val countQuery = query.count()
//            countQuery.get(AggregateSource.SERVER).addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    // Count fetched successfully
//                    val snapshot = task.result
//                    Log.d("BANANA", "Count: ${snapshot.count}")
//                    var counter = snapshot.count.toInt()
//                } else {
//                    Log.d("whomp whomp", "Count failed: ", task.getException())
//                }
//            }
//            Log.d("work", "Number of docus: $counter")
        }
    }
}