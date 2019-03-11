package com.raiffeisen.bejancorneliu.database.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.raiffeisen.bejancorneliu.database.tables.Users

@Dao
interface DaoUsers{
    @Query("select * from users_table where page = 0")
    fun getFirstPage(): LiveData<MutableList<Users>>

    @Query("select * from users_table where page = (:nPage)")
    fun getPage(nPage: Int): List<Users>

    @Transaction
    fun addUsers(nUsers: List<Users>) {
        insertUsers(nUsers)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUsers(nAddresses: List<Users>)

}