package com.raiffeisen.bejancorneliu.screens.first

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import android.os.Handler
import android.util.Log
import android.widget.Toast
import com.raiffeisen.bejancorneliu.database.tables.Users
import com.raiffeisen.bejancorneliu.screens.first.repository.FirstRepository
import com.raiffeisen.bejancorneliu.tools.DownloadThread



class FirstViewModel(mContext: Context) : ViewModel() {

    val downloadThread: DownloadThread = DownloadThread(mContext)


    // handler used to communicate back into UI from separate thread
    val mHandlerThread = Handler(Handler.Callback {
        if (it.what == mUpdateListCode){
            Log.d("dsadsadadas","ceva4")
            mAddNewDataFlag.value=false
            mNewData.addAll(it.obj as Collection<Users>)
            mAddNewDataFlag.value=true
        } else if (it.what == mUpdateMessageCode) {
            Toast.makeText(mContext,(it.obj as String),Toast.LENGTH_SHORT).show()
        }

        true
    })

    init {
        downloadThread.name = "SKODA OCTAVIA"
        downloadThread.start()
    }

    val mUpdateListCode = 1234
    val mUpdateMessageCode = 5678

    var mNextPage = 1
    var mIsLoading = false

    var mRepository = FirstRepository(mContext)
    var mUsersList = mRepository.getFirstPage()

    val mAddNewDataFlag = MutableLiveData<Boolean>()
    var mNewData = ArrayList<Users>(0)

    fun getNextPage() {
        mRepository.getNextPage(mNextPage,this)

    }

    //afert next page is served
    fun resetLoadingNextPage() {
        mNewData.clear()
        mNextPage += 1
        mIsLoading = false
    }
}