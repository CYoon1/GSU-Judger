package com.example.group6
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Adapter
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.google.firebase.auth.ktx.auth
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.DocumentChange
import android.util.Log


class EventListener: AppCompatActivity(), RecyclerViewInterface {

    private lateinit var auth: FirebaseAuth
    private lateinit var recyclerView: RecyclerView
    private lateinit var EventArrayList: ArrayList<Event>
    private lateinit var eventAdapter: EventAdapter
    private lateinit var database: FirebaseFirestore

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.event_dashboard)

        auth = Firebase.auth
        var LogOutButton = findViewById<Button>(R.id.LogOutButton)
        var AddEventButton = findViewById<Button>(R.id.AddEvent)

        val currentUser = auth.currentUser
        if (currentUser == null) {
            val intent = Intent(applicationContext, Login::class.java)
            startActivity(intent)
            finish()
        }

        //Goes to Add Event Page
        AddEventButton.setOnClickListener{
            val intent = Intent(applicationContext, AddEvent::class.java)
            startActivity(intent)
            finish()
        }

        //Logout
        LogOutButton.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(applicationContext, Login::class.java)
            startActivity(intent)
            finish()
        }

        recyclerView = findViewById(R.id.EventRecycler)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        EventArrayList = arrayListOf()
        eventAdapter = EventAdapter(EventArrayList, this)
        recyclerView.adapter = eventAdapter

        EventChangeListener()


        database.collection("EventsExample")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d("Document ID: ", "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("Fail", "Error getting documents: ", exception)
            }


    }

    override fun onEventClicked(position: Int) {
        var index = EventArrayList.get(position)
        var getName = index.EventName.toString()
        var getDesc = index.EventDesc
        var getLocation = index.EventLocation
        var getDate = index.EventDate

        val intent = Intent(applicationContext, ShowEvent::class.java)
        intent.putExtra("ShowEventName", getName)
        intent.putExtra("ShowEventDesc", getDesc)
        intent.putExtra("ShowEventDate", getDate)
        intent.putExtra("ShowEventLocation", getLocation)
        startActivity(intent)
        finish()

    }


//Gets Database
    private fun EventChangeListener(){
        database = FirebaseFirestore.getInstance()
        database.collection("EventsExample")
            .addSnapshotListener(object: EventListener<QuerySnapshot>{
                override fun onEvent(
                    value: QuerySnapshot?,
                    error: FirebaseFirestoreException?
                ){
                    if (error != null){
                        Toast.makeText(this@EventListener, "Firebase Error", Toast.LENGTH_SHORT).show()
                        return
                    }

                    for (dc : DocumentChange in value?.documentChanges!!){

                        if(dc.type == DocumentChange.Type.ADDED){

                            EventArrayList.add(dc.document.toObject(Event::class.java))

                        }
                    }

                    eventAdapter.notifyDataSetChanged()
                }
            })
    }



}