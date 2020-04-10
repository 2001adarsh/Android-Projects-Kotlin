package com.adarsh.todolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.adarsh.todolist.adapters.TodoAdapter
import com.adarsh.todolist.models.Todo
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var todos: ArrayList<Todo> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        todos.add(Todo("First Task!", false))



        rev.layoutManager = LinearLayoutManager(this)
        val todoAdapter: TodoAdapter = TodoAdapter(todos)
        rev.adapter = todoAdapter

        addBut.setOnClickListener {
            val i: String = edit.text.toString()
            Toast.makeText(this, i,Toast.LENGTH_SHORT).show()
            todos.add( Todo ( i ,false))

            todoAdapter.notifyDataSetChanged()
        }

    }
}