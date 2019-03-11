package com.raiffeisen.bejancorneliu.screens.first.repository

import com.raiffeisen.bejancorneliu.retrofit.RetrofitService
import com.raiffeisen.bejancorneliu.retrofit.model.WsData
import retrofit2.Response

class FirstRepositoryWS() {

    private var mUsersWS = RetrofitService().getInstance().interfaces

    fun getPage(nNextPage : Int) : Response<WsData> {
        return  mUsersWS.getData(nNextPage).execute()
    }
}