package com.adarsh.todolist.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.adarsh.todolist.R
import com.adarsh.todolist.models.Todo
import kotlinx.android.synthetic.main.listadapter.view.*

class TodoAdapter( var todos:ArrayList<Todo> ) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val li = parent.context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val itemView = li.inflate(R.layout.listadapter, parent , false )
        return TodoViewHolder(itemView)
    }


    override fun getItemCount(): Int {
       return todos.size
  }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.tex.text  = todos[position].task
    }


    class TodoViewHolder(val item: View) : RecyclerView.ViewHolder(item){
        val tex = item.findViewById<TextView>(R.id.tex)
    }


}