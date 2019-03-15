package com.raiffeisen.bejancorneliu.screens.first.repository

import android.arch.lifecycle.LiveData
import android.content.Context
import android.os.Handler
import android.os.Message
import com.raiffeisen.bejancorneliu.database.tables.Users
import com.raiffeisen.bejancorneliu.screens.first.FirstViewModel
import android.os.Looper
import android.util.Log
import java.lang.Exception


class FirstRepository(mContext: Context) {

    private var mDB = FirstRepositoryDB(mContext)
    private var mWS = FirstRepositoryWS()

    fun getFirstPage(): LiveData<MutableList<Users>> {
        return mDB.getFirstPage()
    }

    /**
     * Get next page on scroll : first check local DB for results
     */
    fun getNextPage(nNextPage: Int, mViewModel: FirstViewModel) {

//        val thread = Thread {
//            //check local DB for next page
//            val mNextResults = mDB.getPage(nNextPage)
//
//            if (mNextResults.isEmpty()) {
//                // next page not found in DB; Initiate WS interrogation for next page
//                val mResponse = mWS.getPage(nNextPage)
//
//                if (mResponse.code() == 200 && mResponse.body() != null && mResponse.body() is WsData) {
//                    //response received; save to local DB
//                   AppDataBase.getDataBase(mContext).daoUser().addUsers(Tools().parseResults(mResponse.body() as WsData, nNextPage))
//                   mViewModel.resetLoadingNextPage()
//                } else {
//                    //something is wrong : ws can not be reached or internet connection problem
//                    sendHandlerMessageData(mViewModel,mViewModel.mUpdateMessageCode,"Connection problem")
//                }
//            } else {
//                //next page is founded in local DB ; send results to UI throw handler
//                sendHandlerMessageData(mViewModel,mViewModel.mUpdateListCode,mNextResults)
//            }
//        }.start()

        mViewModel.downloadThread.enqueueDownload(nNextPage,mViewModel)
        Log.d("download_thread123",""+mViewModel.downloadThread.getTotalQueued()+"/"+mViewModel.downloadThread.getTotalCompleted())
    }

    private fun sendHandlerMessageData(mViewModel: FirstViewModel, nWhat : Int, nResults : Any) {
        val message = Message()
        message.what = nWhat
        message.obj = nResults
        mViewModel.mHandlerThread.sendMessage(message)
    }
}

internal class LooperThread : Thread() {
    lateinit var mHandler: Handler

    override fun run() {

        try {
            Looper.prepare()

            mHandler = Handler()

            Looper.loop()
        } catch (e : Exception) {

        }

    }
}