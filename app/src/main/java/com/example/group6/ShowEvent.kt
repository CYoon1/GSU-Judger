package com.example.group6
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.ComponentActivity
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot

class ShowEvent: ComponentActivity(), RecyclerViewInterface {

    private lateinit var recyclerView: RecyclerView
    private lateinit var projectArrayList : ArrayList<Project>
    private lateinit var ProjectAdapter: ProjectAdapter
    private lateinit var database : FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.show_event)

        var EventName = findViewById<TextView>(R.id.ShowEventName)
        var EventDesc = findViewById<TextView>(R.id.ShowEventDesc)
        var EventDate = findViewById<TextView>(R.id.ShowEventDate)
        var EventLocation = findViewById<TextView>(R.id.ShowEventLocation)
        var BackButton = findViewById<Button>(R.id.BackButton)
        var AddProj = findViewById<Button>(R.id.AddProject)

        var bundle: Bundle? = intent.extras
        var EventNameExtra = bundle!!.getString("ShowEventName")
        var EventDescExtra = bundle.getString("ShowEventDesc")
        var EventDateExtra = bundle.getString("ShowEventDate")
        var EventLocationExtra = bundle.getString("ShowEventLocation")
        var EventIDExtra = bundle.getString("ShowEventID")


        recyclerView = findViewById(R.id.ProjectRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        projectArrayList = arrayListOf()
        ProjectAdapter = ProjectAdapter(projectArrayList, this)
        recyclerView.adapter = ProjectAdapter

        ProjectChangeListener()


        EventName.text = EventNameExtra
        EventDesc.text = EventDescExtra
        EventDate.text = EventDateExtra
        EventLocation.text = EventLocationExtra


        BackButton.setOnClickListener{
            val intent = Intent(applicationContext, EventListener::class.java)
            startActivity(intent)
            finish()
        }

        AddProj.setOnClickListener{
            val intent = Intent(applicationContext, AddProject::class.java)
            intent.putExtra("GetEventID", EventIDExtra)
            startActivity(intent)
            finish()
        }

    }

    override fun onEventClicked(position: Int) {
        Toast.makeText(this@ShowEvent, "hi", Toast.LENGTH_SHORT).show()

        var index = projectArrayList.get(position)

//        Log.e("Index", "Index is $index")

        var getProjectName = index.name.toString()
        var getDesc = index.description
        var getUsername = index.userName

        Log.e("GET", "Proj name is $getProjectName, Desc is $getDesc, Username is $getUsername")


        val intent = Intent(applicationContext, ShowProject::class.java)
        intent.putExtra("ShowProjectName", getProjectName)
        intent.putExtra("ProjectDesc", getDesc)
        intent.putExtra("PersonName", getUsername)
        startActivity(intent)
        finish()
    }

    private fun ProjectChangeListener() {
        database = FirebaseFirestore.getInstance()
        database.collection("Projects").addSnapshotListener(object :
            com.google.firebase.firestore.EventListener<QuerySnapshot> {
            override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                if(error != null){
                    Log.e("Firestore error", error.message.toString())
                    return
                }
                for(dc : DocumentChange in value?.documentChanges!!){
                    if(dc.type == DocumentChange.Type.ADDED){
                        projectArrayList.add(dc.document.toObject(Project::class.java))
                    }
                }
                ProjectAdapter.notifyDataSetChanged()
            }

        })
    }

}