package com.ezatpanah.workmanager_youtube

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.work.*
import com.ezatpanah.workmanager_youtube.databinding.ActivityMainBinding
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            btnOneTime.setOnClickListener {
                myOneTimeWork()
            }
            btnPeriodic.setOnClickListener {
                myPeriodicWork()
            }
        }
    }

    private fun myPeriodicWork() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
            .build()

        val myRequest = PeriodicWorkRequest.Builder(
            MyWorker::class.java,
            15,
            TimeUnit.MINUTES
        ).setConstraints(constraints)
            .addTag("my_id")
            .build()

        //minimum interval is 15min, just wait 15 min,
        // I will cut this.. to show you
        //quickly

        //now is 0:15 let's wait until 0:30min

        WorkManager.getInstance(this)
            .enqueueUniquePeriodicWork(
                "my_id",
                ExistingPeriodicWorkPolicy.KEEP,
                myRequest
            )
    }


    private fun myOneTimeWork() {

        val constraints: Constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .setRequiresCharging(true)
            .build()


        val myWorkRequest: WorkRequest = OneTimeWorkRequest.Builder(MyWorker::class.java)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(this).enqueue(myWorkRequest)

    }
}