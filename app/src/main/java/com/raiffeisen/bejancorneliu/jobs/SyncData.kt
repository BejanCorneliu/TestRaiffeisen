package com.raiffeisen.bejancorneliu.jobs

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.raiffeisen.bejancorneliu.database.AppDataBase
import com.raiffeisen.bejancorneliu.retrofit.RetrofitService
import com.raiffeisen.bejancorneliu.retrofit.model.WsData
import com.raiffeisen.bejancorneliu.tools.Tools


/**
 * Load first page using work manager
 */
class SyncData(nContext : Context, nWorkerParameters: WorkerParameters) : Worker(nContext,nWorkerParameters) {

    private val mContext = nContext
    private val mDaoUsers = AppDataBase.getDataBase(mContext).daoUser()

    override fun doWork(): Result {

        return try {

                if (mDaoUsers.getPage(0).isEmpty()) {
                    //first page is not found in local DB ; initiate WS interrogation
                    val mResponse = RetrofitService().getInstance().interfaces.getData(0).execute()

                    if (mResponse.code() == 200 && mResponse.body() != null && mResponse.body() is WsData) {
                        AppDataBase.getDataBase(mContext).daoUser().addUsers(Tools().parseResults(mResponse.body() as WsData,0))
                        Result.success()
                    } else {
                        Result.retry()
                    }
                } else {
                    //first page is found in local DB ; close with success
                    Result.success()
                }
        } catch (e: Exception) {
           Result.retry()
        }
    }
}