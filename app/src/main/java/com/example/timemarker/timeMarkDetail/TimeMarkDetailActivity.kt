package com.example.timemarker.timeMarkDetail

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.timemarker.R
import com.example.timemarker.timeMarkList.TIME_MARK_ID
import com.example.timemarker.timeMarkList.formatter

class TimeMarkDetailActivity : AppCompatActivity() {

    private val timeMarkDetailViewModel by viewModels<TimeMarkDetailViewModel> {
        TimeMarkDetailViewModelFactory(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.time_mark_detail_activity)

        var currentTimeMarkId: Long? = null

        /* Connect variables to UI elements. */
        val timeMarkDetailValue: TextView = findViewById(R.id.mark_detail_value)
        val timeMarkDetailName: TextView = findViewById(R.id.time_nark_detail_name)
        val removeFlowerButton: Button = findViewById(R.id.remove_button)

        val bundle: Bundle? = intent.extras
        if (bundle != null) {
            currentTimeMarkId = bundle.getLong(TIME_MARK_ID)
        }

        /* If currentFlowerId is not null, get corresponding flower and set name, image and
        description */
        currentTimeMarkId?.let {
            val currentTimeMark = timeMarkDetailViewModel.getTimeMarkForId(it)
            timeMarkDetailValue.text = currentTimeMark?.localDateTime?.format(formatter)
            timeMarkDetailName.text = currentTimeMark?.name

            removeFlowerButton.setOnClickListener {
                if (currentTimeMark != null) {
                    timeMarkDetailViewModel.removeTimeMark(currentTimeMark)
                }
                finish()
            }
        }

    }
}