package com.example.simpletodo

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * A bridge that tells the recyclerView how to display the data we give it
 */
class TaskItemAdapter(val listofItem: List<String>, val longClickListener: OnLongClickListener):
    RecyclerView.Adapter<TaskItemAdapter.ViewHolder>() {

    interface OnLongClickListener {
        fun onItemLongClicked(position: Int)
    }
    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    // Usually involves inflating a layout from XML and returning the holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskItemAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val contactView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false)
        // Return a new holder instance
        return ViewHolder(contactView)
    }

    override fun onBindViewHolder(holder: TaskItemAdapter.ViewHolder, position: Int) {

        // Get the data model based on position
        val item = listofItem.get(position)
        holder.textView.text = item
    }

    override fun getItemCount(): Int {
        return  listofItem.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //TODO
        val textView: TextView
        init{
            textView = itemView.findViewById(android.R.id.text1)
            itemView.setOnClickListener{
                longClickListener.onItemLongClicked(adapterPosition)
                true
            }
        }
    }


}