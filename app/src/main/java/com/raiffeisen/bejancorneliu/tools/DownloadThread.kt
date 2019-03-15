package com.raiffeisen.bejancorneliu.tools

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import com.raiffeisen.bejancorneliu.database.AppDataBase
import com.raiffeisen.bejancorneliu.retrofit.model.WsData
import com.raiffeisen.bejancorneliu.screens.first.FirstViewModel
import com.raiffeisen.bejancorneliu.screens.first.repository.FirstRepositoryDB
import com.raiffeisen.bejancorneliu.screens.first.repository.FirstRepositoryWS
import java.lang.Exception

class DownloadThread(var mContext: Context) : Thread() {

    lateinit var handler: Handler
    private val tag = DownloadThread::class.java.simpleName
    private var totalCompleted: Int = 0
    private var totalQueued: Int = 0

    private var mDB = FirstRepositoryDB(mContext)
    private var mWS = FirstRepositoryWS()

    override fun run() {

        try {
            Looper.prepare()
            Log.i(tag, "DownloadThread entering the loop")
            handler = Handler()

            Looper.loop()
            Log.i(tag, "DownloadThread exiting gracefully")
        } catch (t: Exception) {
            Log.e(tag, "DownloadThread halted due to an error", t)
        }

    }

    @Synchronized
    fun requestStop() {
        // using the handler, post a Runnable that will quit()
        // the Looper attached to our DownloadThread
        // obviously, all previously queued tasks will be executed
        // before the loop gets the quit Runnable
        handler.post {
            // This is guaranteed to run on the DownloadThread
            // so we can use myLooper() to get its looper
            Log.i(tag, "DownloadThread loop quitting by request")
            Looper.myLooper()!!.quit()
        }
    }

    @Synchronized
    fun enqueueDownload(nNextPage: Int, mViewModel: FirstViewModel) {
        // Wrap DownloadTask into another Runnable to track the statistics
        handler.post {
            try {

                casdass(nNextPage,mViewModel)

            } finally {
                // register task completion
                synchronized(this@DownloadThread) {
                    totalCompleted++
                }
                // tell the listener something has happened
               // signalUpdate()
            }
        }

        totalQueued++
        // tell the listeners the queue is now longer
        //signalUpdate()
    }

    @Synchronized
    fun getTotalQueued(): Int {
        return totalQueued
    }

    @Synchronized
    fun getTotalCompleted(): Int {
        return totalCompleted
    }

    private fun casdass(nNextPage: Int, mViewModel: FirstViewModel) {
        val mNextResults = mDB.getPage(nNextPage)
    Log.d("dsadsadadas","ceva1")

        if (mNextResults.isEmpty()) {
            // next page not found in DB; Initiate WS interrogation for next page
            val mResponse = mWS.getPage(nNextPage)

            if (mResponse.code() == 200 && mResponse.body() != null && mResponse.body() is WsData) {
                //response received; save to local DB
                AppDataBase.getDataBase(mContext).daoUser().addUsers(Tools().parseResults(mResponse.body() as WsData, nNextPage))
                mViewModel.resetLoadingNextPage()
            } else {
                //something is wrong : ws can not be reached or internet connection problem
                sendHandlerMessageData(mViewModel,mViewModel.mUpdateMessageCode,"Connection problem")
            }
        } else {
            //next page is founded in local DB ; send results to UI throw handler
            sendHandlerMessageData(mViewModel,mViewModel.mUpdateListCode,mNextResults)
        }
        Log.d("dsadsadadas","ceva2")
    }

    private fun sendHandlerMessageData(mViewModel: FirstViewModel, nWhat : Int, nResults : Any) {
        val message = Message()
        message.what = nWhat
        message.obj = nResults
        mViewModel.mHandlerThread.sendMessage(message)
    }

    // thread.
    // Thus, it is up for the listener to deal with that (in case it is a UI
    // component,
    // it has to execute the signal handling code in the UI thread using Handler
    // - see
    // DownloadQueueActivity for example).
//    private fun signalUpdate() {
//        if (listener != null) {
//            listener.handleDownloadThreadUpdate()
//        }
//    }
}