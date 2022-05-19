package com.example.timemarker.timeMarkList

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.timemarker.data.DataSource
import com.example.timemarker.data.TimeMark
import java.time.LocalDateTime
import kotlin.random.Random

class TimeMarksListViewModel(val dataSource: DataSource) : ViewModel() {

    val timeMarksLiveData = dataSource.getTimeMarkList()

    /* If the name and description are present, create new Flower and add it to the datasource */
    fun insertTimeMark(timeMarkName: String?) {
        if (timeMarkName == null) {
            return
        }

        val newTimeMark = TimeMark(
            Random.nextLong(),
            timeMarkName,
            LocalDateTime.now()
        )

        insertTimeMark(newTimeMark)
    }

    fun insertTimeMark(timeMark: TimeMark) {
        if (dataSource.getTimeMarkForId(timeMark.id) == null)
            dataSource.addTimeMark(timeMark)
    }
}

class FlowersListViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TimeMarksListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TimeMarksListViewModel(
                dataSource = DataSource.getDataSource()
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}