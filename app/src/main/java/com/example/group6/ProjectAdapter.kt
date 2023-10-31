package com.example.group6
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView

class ProjectAdapter(private val projectList : ArrayList<Project>) : RecyclerView.Adapter<ProjectAdapter.ProjectViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProjectAdapter.ProjectViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.project_card, parent, false)
        return ProjectViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ProjectAdapter.ProjectViewHolder, position: Int) {
        val project : Project = projectList[position]
        holder.name.text = project.name
        holder.description.text = project.description
    }

    override fun getItemCount(): Int {
        return projectList.size
    }

    public class ProjectViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val name : TextView = itemView.findViewById(R.id.projectName)
        val description : TextView = itemView.findViewById(R.id.projectDescription)
    }
}