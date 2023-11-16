package com.example.group6
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.activity.ComponentActivity
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import android.widget.TextView
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth

class AddProject : ComponentActivity(){
    private lateinit var projName: EditText
    private lateinit var projDesc: EditText
    private lateinit var projUser: EditText
    private lateinit var subBtn: Button
    private lateinit var backBtn: Button
    private var database = Firebase.firestore


    //FOR GOING BACK TO EVENT PAGE
    private var EventNameBack: String? = null
    private var EventDescBack: String? = null
    private var EventDateBack: String? = null
    private var EventLocationBack: String? = null
    private var EventIDBack: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.project_page)
        projName = findViewById(R.id.enterProj)
        projDesc = findViewById(R.id.enterDesc)
        projUser = findViewById(R.id.enterName)
        subBtn = findViewById(R.id.submitProj)
        backBtn = findViewById(R.id.BackButton)
        val curUser = FirebaseAuth.getInstance().currentUser
        val uuid = curUser?.uid


        //FOR GOING BACK TO EVENT PAGE
        var bundle: Bundle? = intent.extras
        EventNameBack = bundle!!.getString("ShowEventName")
        EventDescBack = bundle.getString("ShowEventDesc")
        EventDateBack = bundle.getString("ShowEventDate")
        EventLocationBack = bundle.getString("ShowEventLocation")
        EventIDBack = bundle.getString("ShowEventID")

        Log.e("GetEventName", "IN ADDPROJECT Event Name is $EventNameBack")


        subBtn.setOnClickListener{
            var bundle: Bundle? = intent.extras
            var idEvent = bundle?.getString("GetEventID")
            val projectName = projName.text.toString()
            val projectDesc = projDesc.text.toString()
            val projectUser = projUser.text.toString()
            val userUid = uuid.toString()
            val projID = database.collection("Projects").document()
            val projMap = hashMapOf(
                "name" to projectName,
                "description" to projectDesc,
                "userName" to projectUser,
                "userID" to userUid,
                "projID" to projID.id,
                "eventID" to idEvent
            )

            projID.set(projMap)
                .addOnSuccessListener {
                    Toast.makeText(this, "Successfully added your project", Toast.LENGTH_SHORT).show()
                    val intent = Intent(applicationContext, ShowEvent::class.java)

                    intent.putExtra("ShowEventName", EventNameBack)
                    intent.putExtra("ShowEventDesc", EventDescBack)
                    intent.putExtra("ShowEventDate", EventDateBack)
                    intent.putExtra("ShowEventLocation", EventLocationBack)
                    intent.putExtra("ShowEventID", EventIDBack)

                    Log.e("GetEventName", "SUBMITTED PROJECT, FROM ADDPROJECT TO SHOWEVENT Event Name is $EventNameBack")


                    startActivity(intent)
                    finish()

                }
                .addOnFailureListener{
                    Toast.makeText(this, "Failed to added your project", Toast.LENGTH_SHORT).show()
                }
        }

        backBtn.setOnClickListener{
            val intent = Intent(applicationContext, ShowEvent::class.java)

            intent.putExtra("ShowEventName", EventNameBack)
            intent.putExtra("ShowEventDesc", EventDescBack)
            intent.putExtra("ShowEventDate", EventDateBack)
            intent.putExtra("ShowEventLocation", EventLocationBack)
            intent.putExtra("ShowEventID", EventIDBack)

            Log.e("GetEventName", "FROM ADDPROJECT TO SHOWEVENT Event Name is $EventNameBack")
            startActivity(intent)
            finish()
        }


    }
}