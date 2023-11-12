package com.example.group6

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.icu.text.CaseMap.Title
import android.os.Bundle
import android.text.TextUtils
import android.util.EventLogTags.Description
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import android.util.Log
import android.content.ContentValues.TAG
import android.widget.DatePicker
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.Date
class AddEvent: ComponentActivity(), DatePickerDialog.OnDateSetListener {
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseFirestore
    private val calendar = Calendar.getInstance()
    private val calendarFormat = SimpleDateFormat("MM/dd/yyyy", Locale.US)


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.add_event)

        auth = Firebase.auth

        var AddEvent = findViewById<TextView>(R.id.AddEvent)
        var TitleName = findViewById<EditText>(R.id.TitleName)
        var Desc = findViewById<EditText>(R.id.Description)
        var LastDay = findViewById<TextView>(R.id.LastDay)
        var Date = findViewById<TextView>(R.id.Date)
        var Location = findViewById<EditText>(R.id.Location)
        var AddEventButton = findViewById<Button>(R.id.AddEventButton)
        var BackButton = findViewById<Button>(R.id.BackButton)

        val currentUser = auth.currentUser
        if (currentUser == null) {
            val intent = Intent(applicationContext, Login::class.java)
            startActivity(intent)
            finish()
        }

        BackButton.setOnClickListener{
            val intent = Intent(applicationContext, EventListener::class.java)
            startActivity(intent)
            finish()
        }

        Date.setOnClickListener{
            DatePickerDialog(
                this,
                this,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
                ).show()
        }

        AddEventButton.setOnClickListener{
            var EventName: String?=null
            var EventDesc: String?=null
            var EventDate: String?=null
            var EventLocation: String?=null
            database = FirebaseFirestore.getInstance()

            EventName = TitleName.text.toString()
            EventDesc = Desc.text.toString()
//            CHANGE BELOW LINE TO TIMESTAMP
            EventDate = Date.text.toString()
            EventLocation = Location.text.toString()

            val event = hashMapOf(
                "EventName" to EventName,
                "EventDate" to EventDate,
                "EventLocation" to EventLocation,
                "EventDesc" to EventDesc
            )
            database.collection("EventsExample")
                .add(event)
                .addOnSuccessListener { documentReference ->
                    Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                    val intent = Intent(applicationContext, EventListener::class.java)
                    startActivity(intent)
                    finish()
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error adding document", e)
                }

            if (TextUtils.isEmpty(EventName)){
                Toast.makeText(this@AddEvent, "Title is empty", Toast.LENGTH_SHORT).show()
            }

            if (TextUtils.isEmpty(EventDate)){
                Toast.makeText(this@AddEvent, "Date is empty", Toast.LENGTH_SHORT).show()
            }
        }

    }

    //NOT FINISHED FIX THIS RN
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val currentDate = calendarFormat.format(System.currentTimeMillis())
        Log.e("Current Date", "$currentDate" )
        Log.e("Calendar", "$month/$dayOfMonth/$year")

        if(calendarFormat.format(calendar.timeInMillis) < currentDate){
            Toast.makeText(this@AddEvent, "Date is invalid", Toast.LENGTH_SHORT).show()
            Log.e("Invalid Date:", "$month/$dayOfMonth/$year is before $currentDate")
        }else {

            calendar.set(year, month, dayOfMonth)
            displayFormattedDate(calendar.timeInMillis)
        }
    }

    private fun displayFormattedDate(timestamp: Long){
        findViewById<TextView>(R.id.Date).text = calendarFormat.format(timestamp)
        Log.i("Format time to epoch: ", timestamp.toString())
    }
}