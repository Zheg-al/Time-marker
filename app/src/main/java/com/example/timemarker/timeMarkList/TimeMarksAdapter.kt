package com.example.timemarker.timeMarkList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.timemarker.R
import com.example.timemarker.data.TimeMark

class TimeMarksAdapter(private val onClick: (TimeMark) -> Unit) :
    ListAdapter<TimeMark, TimeMarksAdapter.TimeMarkViewHolder>(TimeMarkDiffCallback) {

    /* ViewHolder for Flower, takes in the inflated view and the onClick behavior. */
    class TimeMarkViewHolder(itemView: View, val onClick: (TimeMark) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        private val timeMarkTextView: TextView = itemView.findViewById(R.id.time_mark_text)
        private val timeMarkTextViewValue: TextView = itemView.findViewById(R.id.time_mark_value)
        private var currentTimeMark: TimeMark? = null

        init {
            itemView.setOnClickListener {
                currentTimeMark?.let {
                    onClick(it)
                }
            }
        }

        /* Bind flower name and image. */
        fun bind(timeMark: TimeMark) {
            currentTimeMark = timeMark

            timeMarkTextViewValue.text = timeMark.localDateTime.format(formatter)
            timeMarkTextView.text = timeMark.name
        }
    }

    /* Creates and inflates view and return FlowerViewHolder. */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeMarkViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.time_mark_item, parent, false)
        return TimeMarkViewHolder(view, onClick)
    }

    /* Gets current flower and uses it to bind view. */
    override fun onBindViewHolder(holder: TimeMarkViewHolder, position: Int) {
        val flower = getItem(position)
        holder.bind(flower)

    }
}

object TimeMarkDiffCallback : DiffUtil.ItemCallback<TimeMark>() {
    override fun areItemsTheSame(oldItem: TimeMark, newItem: TimeMark): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: TimeMark, newItem: TimeMark): Boolean {
        return oldItem.id == newItem.id
    }
}