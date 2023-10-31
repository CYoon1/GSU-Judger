package com.example.group6

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import android.content.Context

class EventAdapter(private val EventList: ArrayList<Event>,  private val recyclerViewListener: RecyclerViewInterface) : RecyclerView.Adapter<EventAdapter.EventViewHolder>(){

    lateinit var context: Context
    lateinit var EventArrayList: ArrayList<Event>


//    fun EventAdapter(context: Context, EventArrayList: ArrayList<Event>, RecyclerViewInterface: RecyclerViewInterface){
//        this.context = context
//        this.EventArrayList = EventArrayList
//        this.listener = RecyclerViewInterface
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventAdapter.EventViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.event_card, parent, false)

        return EventViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: EventAdapter.EventViewHolder, position: Int) {

        val event: Event = EventList[position]
        holder.EventName.text = event.EventName
        holder.EventDate.text = event.EventDate
        holder.EventLocation.text = event.EventLocation
    }

    override fun getItemCount(): Int {

        return EventList.size

    }

    inner class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener{
        var EventName: TextView = itemView.findViewById<TextView>(R.id.EventName)
        var EventDate: TextView = itemView.findViewById<TextView>(R.id.EventDate)
        var EventLocation: TextView = itemView.findViewById<TextView>(R.id.EventLocation)

        init{
            itemView.setOnClickListener(this)
        }
        override fun onClick(v: View?) {
            val position = adapterPosition
            if(position !=RecyclerView.NO_POSITION){
                recyclerViewListener.onEventClicked(position)

            }
        }


    }
}