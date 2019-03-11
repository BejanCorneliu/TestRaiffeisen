package com.raiffeisen.bejancorneliu.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.raiffeisen.bejancorneliu.database.dao.DaoUsers
import com.raiffeisen.bejancorneliu.database.tables.Users

@Database(entities = [(Users::class)], version = 1, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {
    companion object {
        private var INSTANCE: AppDataBase? = null
        fun getDataBase(context: Context): AppDataBase {
            if (INSTANCE == null) {

                INSTANCE = Room.databaseBuilder(context.applicationContext, AppDataBase::class.java, "data-db").build()
            }
            return INSTANCE as AppDataBase
        }
    }

    abstract fun daoUser(): DaoUsers
}