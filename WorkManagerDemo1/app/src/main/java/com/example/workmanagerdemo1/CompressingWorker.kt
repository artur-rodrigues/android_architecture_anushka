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

class CompressingWorker(context: Context, parameters: WorkerParameters) : Worker(context, parameters) {

    override fun doWork(): Result {
        return try {
            for (i in 0..300) {
                i("MY_TAG", "Compressing $i")
            }
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }
}