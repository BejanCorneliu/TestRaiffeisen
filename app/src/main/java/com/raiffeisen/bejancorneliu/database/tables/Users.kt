package com.raiffeisen.bejancorneliu.database.tables

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "users_table")
data class Users(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "uid")
    var uid: Int = 0,

    @ColumnInfo(name = "page")
    var page: Int = 0,

    @ColumnInfo(name = "name")
    var name: String = "",

    @ColumnInfo(name = "years")
    var years: String = "",

    @ColumnInfo(name = "country")
    var country: String = "",

    @ColumnInfo(name = "hour")
    var hour: String = "",

    @ColumnInfo(name = "attachment")
    var attachment: Boolean = false,

    @ColumnInfo(name = "small_pic_url")
    var small_pic_url: String = "",

    @ColumnInfo(name = "big_pic_url")
    var big_pic_url: String = ""
)