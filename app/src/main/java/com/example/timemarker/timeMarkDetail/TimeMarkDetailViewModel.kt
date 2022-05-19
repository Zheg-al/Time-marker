package com.example.timemarker.timeMarkDetail

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.timemarker.data.DataSource
import com.example.timemarker.data.TimeMark

class TimeMarkDetailViewModel(private val datasource: DataSource) : ViewModel() {

    /* Queries datasource to returns a flower that corresponds to an id. */
    fun getTimeMarkForId(id: Long) : TimeMark? {
        return datasource.getTimeMarkForId(id)
    }

    /* Queries datasource to remove a flower. */
    fun removeTimeMark(timeMark: TimeMark) {
        datasource.removeTimeMark(timeMark)
    }
}

class TimeMarkDetailViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TimeMarkDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TimeMarkDetailViewModel(
                datasource = DataSource.getDataSource()
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}