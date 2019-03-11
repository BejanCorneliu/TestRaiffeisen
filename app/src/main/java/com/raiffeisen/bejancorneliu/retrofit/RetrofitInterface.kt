package com.raiffeisen.bejancorneliu.retrofit

import com.raiffeisen.bejancorneliu.retrofit.model.WsData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitInterface {
    @GET("/api?results=10&seed=abc") fun getData(@Query("page") page : Int): Call<WsData>
}