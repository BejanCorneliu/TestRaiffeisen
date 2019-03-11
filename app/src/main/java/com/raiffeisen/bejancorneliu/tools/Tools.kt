package com.raiffeisen.bejancorneliu.tools

import com.raiffeisen.bejancorneliu.database.tables.Users
import com.raiffeisen.bejancorneliu.retrofit.model.WsData
import java.text.DecimalFormat
import java.util.ArrayList
import kotlin.random.Random

class Tools{

    fun parseResults(mResponse : WsData,mPage : Int): MutableList<Users> {

        val mResult = mResponse.results
        val mUsersForInsert : MutableList<Users> = ArrayList(mResult.size)

        val mFormatNumber = DecimalFormat("00")

        mResult.forEach{
            mUsersForInsert.add(
                Users(
                    page = mPage,
                    name = it.name.first.toLowerCase().capitalize()+" "+it.name.last.toLowerCase().capitalize(),
                    years = it.dob.age,
                    country = it.nat,
                    hour = mFormatNumber.format((0..24).random())+":"+mFormatNumber.format((0..59).random()),
                    attachment = Random.nextBoolean(),
                    small_pic_url = it.picture.thumbnail,
                    big_pic_url = it.picture.large
                )
            )
        }

        return mUsersForInsert
    }
}