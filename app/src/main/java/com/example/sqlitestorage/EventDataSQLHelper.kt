package com.example.sqlitestorage

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

class EventDataSQLHelper(context: Context)
    :SQLiteOpenHelper(context, EventDataSQLHelper.DATABASE_NAME, null,EventDataSQLHelper.DATABASE_VERSION) {

    companion object{
        private val DATABASE_NAME = "events.db"
        private val DATABASE_VERSION = 1
        val TABLE = "User"
        val USERNAME = "username"
    }

    override fun onCreate(_db: SQLiteDatabase?) {
     val query = "CREATE TABLE $TABLE(${BaseColumns._ID} integer primary key autoincrement, " +
             "$USERNAME text not null);"
        _db?.execSQL(query)
    }

    override fun onUpgrade(_db: SQLiteDatabase?, _oldVersion: Int, _newVersion: Int) {
        if(_oldVersion >= _newVersion)
            return

        var sql:String? = null

        if(_oldVersion == 1)
            sql = "ALTER TABLE $TABLE add note text;"

        if(_oldVersion == 2)
            sql =""
        if(sql != null)
            _db?.execSQL(sql)
    }
}