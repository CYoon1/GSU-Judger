package com.example.group6

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity

class ShowProject: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.show_project)

        var ProjectName = findViewById<TextView>(R.id.ShowProjectName)
        var PersonName = findViewById<TextView>(R.id.PersonName)
        var ProjectDesc = findViewById<TextView>(R.id.ProjectDesc)
        var BackButton = findViewById<Button>(R.id.BackButton)
        var RateButton = findViewById<Button>(R.id.ratingButton)

        var bundle: Bundle? = intent.extras
        var ProjectNameExtra = bundle!!.getString("ShowProjectName")
        var PersonNameExtra = bundle.getString("PersonName")
        var ProjectDescExtra = bundle.getString("ProjectDesc")

        ProjectName.text = ProjectNameExtra
        PersonName.text = PersonNameExtra
        ProjectDesc.text = ProjectDescExtra

        BackButton.setOnClickListener{
            val intent = Intent(applicationContext, EventListener::class.java)
            startActivity(intent)
            finish()
        }
        RateButton.setOnClickListener{

        }
    }
}