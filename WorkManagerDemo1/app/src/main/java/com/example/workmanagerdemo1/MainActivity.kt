package com.example.workmanagerdemo1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.work.*
import com.example.workmanagerdemo1.Constants.KEY_COUNT_VALUE
import com.example.workmanagerdemo1.Constants.KEY_WORKER
import com.example.workmanagerdemo1.databinding.ActivityMainBinding
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.startBtn.setOnClickListener {
//            setOneTimeWorkerRequest()
            setPeriodicRequest()
        }
    }

    private fun setOneTimeWorkerRequest() {
        val workerManager = WorkManager.getInstance(this)

        val data: Data = Data.Builder()
            .putInt(KEY_COUNT_VALUE, 125)
            .build()

        val constraints = Constraints.Builder()
            .setRequiresCharging(true)
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val uploadRequest = OneTimeWorkRequest.Builder(UploadWorker::class.java)
            .setInputData(data)
            .setConstraints(constraints)
            .build()

        /*val filteringRequest = OneTimeWorkRequest.Builder(FilteringWorker::class.java).build()
        val compressingRequest = OneTimeWorkRequest.Builder(CompressingWorker::class.java).build()
        val downloadRequest = OneTimeWorkRequest.Builder(DownloadWorker::class.java).build()*/

        val filteringRequest = getRequest(FilteringWorker::class.java)
        val compressingRequest = getRequest(CompressingWorker::class.java)
        val downloadRequest = getRequest(DownloadWorker::class.java)

        val parallelWorkers = mutableListOf<OneTimeWorkRequest>()
        parallelWorkers.add(downloadRequest)
        parallelWorkers.add(filteringRequest)

        workerManager
            .beginWith(parallelWorkers)
            .then(compressingRequest)
            .then(uploadRequest)
            .enqueue()
        workerManager.getWorkInfoByIdLiveData(uploadRequest.id)
            .observe(this) {
                binding.textView.text = it.state.name
                if (it.state.isFinished) {
                    it.outputData.getString(KEY_WORKER)?.let { ret ->
                        Toast.makeText(this, ret, Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }

    private fun setPeriodicRequest() {
        val worker = PeriodicWorkRequest
            .Builder(DownloadWorker::class.java, 16, TimeUnit.MINUTES)
            .build()
        WorkManager.getInstance(this)
            .enqueue(worker)
    }

    private fun getRequest(clazz: Class<out Worker>): OneTimeWorkRequest {
        return OneTimeWorkRequest.Builder(clazz).build()
    }
}