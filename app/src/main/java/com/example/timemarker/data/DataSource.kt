package com.example.timemarker.data

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/* Handles operations on flowersLiveData and holds details about it. */
class DataSource() {
    //TimeMark(0, "", LocalDateTime.now())
    private val initList = listOf<TimeMark>()
    private val marksLiveData = MutableLiveData(initList)

    /* Adds flower to liveData and posts value. */
    fun addTimeMark(timeMark: TimeMark) {
        val currentList = marksLiveData.value
        if (currentList == null) {
            marksLiveData.postValue(listOf(timeMark))
        } else {
            val updatedList = currentList.toMutableList()
            updatedList.add(0, timeMark)
            marksLiveData.value = updatedList
        }
    }

    /* Removes flower from liveData and posts value. */
    fun removeTimeMark(timeMark: TimeMark) {
        val currentList = marksLiveData.value
        if (currentList != null) {
            val updatedList = currentList.toMutableList()
            updatedList.remove(timeMark)
            marksLiveData.value = (updatedList)
        }
    }

    /* Returns flower given an ID. */
    fun getTimeMarkForId(id: Long): TimeMark? {
        marksLiveData.value?.let { flowers ->
            return flowers.firstOrNull { it.id == id }
        }
        return null
    }

    fun getTimeMarkList(): LiveData<List<TimeMark>> {
        return marksLiveData
    }

    companion object {
        private var INSTANCE: DataSource? = null

        fun getDataSource(): DataSource {
            return synchronized(DataSource::class) {
                val newInstance = INSTANCE ?: DataSource()
                INSTANCE = newInstance
                newInstance
            }
        }
    }
}