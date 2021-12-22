package com.example.simpletodo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.apache.commons.io.FileUtils;
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File
import java.io.IOException
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {
    var listoftasks = mutableListOf<String>()
    lateinit var adapter : TaskItemAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val onLongClickListener = object : TaskItemAdapter.OnLongClickListener{
            override fun onItemLongClicked(position: Int) {
                //1. Remove the item from the list
                listoftasks.removeAt(position)
                //2. Notify the adapter that our dataset has change
                adapter.notifyDataSetChanged()

                saveItems()
            }

        }
        // 1. Detect when the user clicks on the add button
//        findViewById<Button>(R.id.button).setOnClickListener{
//            // Code here is going to be executed when the user clicks on a button
//            Log.i("Caren", "User clicked on button")
//        }

        loadItems()

        // Look up recyclerView in layout
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        // Create adapter passing in the sample user data
        adapter = TaskItemAdapter(listoftasks, onLongClickListener )
        // Attach the adapter to the recyclerview to populate items
        recyclerView.adapter = adapter
        // Set layout manager to position the items
        recyclerView.layoutManager = LinearLayoutManager(this)

        //Set up the button and input field so that the user can enter a task and add it to the list
        val inputTextField = findViewById<EditText>(R.id.addTaskField)
        findViewById<Button>(R.id.button).setOnClickListener{
            // 1. Grab the text the user has input in
            val userInputtedTask = inputTextField.text.toString()

            // 2. Add the string to our list tasks
            listoftasks.add(userInputtedTask)

            // Notify the adapter our data has been updated
            adapter.notifyItemInserted(listoftasks.size-1)

            // 3. Reset text fields
            inputTextField.setText("")

            saveItems()
        }
    }

    // Save the data the user has input
    // Save data by writing and reading from a file
    // Get the file we need
    fun getDataFile(): File{
        // Every line is going to represent a specific task in our list of tasks
        return File(filesDir, "data.txt")
    }

    // Load the items by reading every line in the data file
    fun loadItems(){
        try {
            listoftasks = FileUtils.readLines(getDataFile(), Charset.defaultCharset())
        }catch (ioException: IOException){
            ioException.printStackTrace()
        }

    }
    // Save items by writin them into our data file
    fun saveItems(){
        try {
            FileUtils.writeLines(getDataFile(), listoftasks)
        } catch (ioException: IOException) {
            ioException.printStackTrace()
        }
    }

}