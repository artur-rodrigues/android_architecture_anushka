package com.example.workmanagerdemo1

import android.content.Context
import android.util.Log.i
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.workmanagerdemo1.Constants.KEY_COUNT_VALUE
import com.example.workmanagerdemo1.Constants.KEY_WORKER
import java.text.SimpleDateFormat
import java.util.*

class UploadWorker(context: Context, parameters: WorkerParameters) : Worker(context, parameters) {

    override fun doWork(): Result {
        return try {
            val count = inputData.getInt(KEY_COUNT_VALUE, 0)
            for (i in 0..count) {
                i("MY_TAG", "Uploading $i")
            }

            val time = SimpleDateFormat("dd/MM/yyyy hh:mm:ss", Locale.getDefault())
            val currentDate = time.format(Date())
            val outputData = Data.Builder()
                .putString(KEY_WORKER, currentDate)
                .build()

            Result.success(outputData)
        } catch (e: Exception) {
            Result.failure()
        }
    }
}