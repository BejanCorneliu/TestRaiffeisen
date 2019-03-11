package com.raiffeisen.bejancorneliu.screens.first.repository

import android.arch.lifecycle.LiveData
import android.content.Context
import com.raiffeisen.bejancorneliu.database.AppDataBase
import com.raiffeisen.bejancorneliu.database.tables.Users

class FirstRepositoryDB(mContext : Context) {

    private var mUsersDB = AppDataBase.getDataBase(mContext).daoUser()

    fun getFirstPage(): LiveData<MutableList<Users>> {
        return mUsersDB.getFirstPage()
    }

    fun getPage(nNextPage : Int) : List<Users> {
        return mUsersDB.getPage(nNextPage)
    }
}