package com.raiffeisen.bejancorneliu

import android.app.Application
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.raiffeisen.bejancorneliu.jobs.SyncData

class First : Application() {
    override fun onCreate() {
        super.onCreate()

        WorkManager.getInstance().beginWith(
            OneTimeWorkRequest.Builder(SyncData::class.java).setConstraints(
                Constraints.Builder().setRequiredNetworkType(NetworkType.NOT_REQUIRED).build()).addTag("general_sync") .build()).enqueue()

    }
}