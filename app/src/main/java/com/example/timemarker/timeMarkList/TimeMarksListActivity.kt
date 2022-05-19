package com.example.timemarker.timeMarkList

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.timemarker.MARK_NAME
import com.example.timemarker.AddTimeMarkActivity
import com.example.timemarker.R
import com.example.timemarker.data.TimeMark
import com.example.timemarker.timeMarkDetail.TimeMarkDetailActivity
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

const val TIME_MARK_ID = "time_mark_id"
const val TIME_MARK_COUNT = "time_mark_count"
const val TIME_MARK_VALUE = "time_mark_value"
const val TIME_MARK_NAME = "time_mark_name"
val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
const val PREF_NAME = "TIME_MARK_PREF"

class TimeMarksListActivity : AppCompatActivity() {
    private val newFlowerActivityRequestCode = 1
    private val timeMarksListViewModel by viewModels<TimeMarksListViewModel> {
        FlowersListViewModelFactory(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val headerAdapter = HeaderAdapter()
        val timeMarksAdapter = TimeMarksAdapter { timeMark -> adapterOnClick(timeMark) }
        val concatAdapter = ConcatAdapter(headerAdapter, timeMarksAdapter)

        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        recyclerView.adapter = concatAdapter

        timeMarksListViewModel.timeMarksLiveData.observe(this) {
            it?.let {
                timeMarksAdapter.submitList(it)
                headerAdapter.updateTimeMarkCount(it.size)
            }
        }

        load()

        val fab: View = findViewById(R.id.fab)
        fab.setOnClickListener {
            fabOnClick()
        }
    }

    private fun load() {
        val sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE)
        val timeMarkCount = sharedPreferences.getString(TIME_MARK_COUNT, "0")!!.toInt()
        repeat(timeMarkCount) {
            try {
                val index = timeMarkCount - it - 1
                val id = sharedPreferences.getString(TIME_MARK_ID + index, "")!!.toLong()
                val name = sharedPreferences.getString(TIME_MARK_NAME + index, "")!!
                val value = LocalDateTime.parse(
                    sharedPreferences.getString(TIME_MARK_VALUE + index, ""),
                    formatter
                )
                timeMarksListViewModel.insertTimeMark(TimeMark(id, name, value))
            } catch (e: Exception) {
                // ignore this time mark :(
            }
        }
    }

    private fun save() {
        val sharedPref: SharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE)
        val editor = sharedPref.edit()

        timeMarksListViewModel.timeMarksLiveData.value?.let {
            editor.putString(TIME_MARK_COUNT, (it.size).toString())
            it.forEachIndexed() { index, timeMark ->
                editor.putString(TIME_MARK_ID + index, (timeMark.id).toString())
                editor.putString(TIME_MARK_NAME + index, (timeMark.name))
                editor.putString(
                    TIME_MARK_VALUE + index,
                    (formatter.format(timeMark.localDateTime))
                )
            }
        }

        editor.apply()
    }

    override fun onPause() {
        super.onPause()

        save()
    }

    /* Opens FlowerDetailActivity when RecyclerView item is clicked. */
    private fun adapterOnClick(timeMark: TimeMark) {
        val intent = Intent(this, TimeMarkDetailActivity()::class.java)
        intent.putExtra(TIME_MARK_ID, timeMark.id)
        startActivity(intent)
    }

    /* Adds timeMark to timeMarkList when FAB is clicked. */
    private fun fabOnClick() {
        val intent = Intent(this, AddTimeMarkActivity::class.java)
        startActivityForResult(intent, newFlowerActivityRequestCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)

        /* Inserts timeMark into viewModel. */
        if (requestCode == newFlowerActivityRequestCode && resultCode == Activity.RESULT_OK) {
            intentData?.let { data ->
                val timeMarkName = data.getStringExtra(MARK_NAME)

                timeMarksListViewModel.insertTimeMark(timeMarkName)
            }
        }
    }
}