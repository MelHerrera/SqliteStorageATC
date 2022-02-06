package com.example.sqlitestorage

import android.content.ContentValues
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.sqlitestorage.databinding.ActivityMainBinding
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {
    lateinit var binding :ActivityMainBinding
    var mUserData: EventDataSQLHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mUserData = EventDataSQLHelper(this)

        binding.save.setOnClickListener {
            enterData(binding.enterData.text.toString())
            binding.show.isEnabled = true
        }
        binding.show.setOnClickListener {
            val cursor: Cursor? = getEvents()
            if(cursor != null)
                binding.loadData.setText(showData(cursor))
        }
        binding.show.isEnabled = false

        val cursor = getEvents()
        if(cursor != null){
            val data = showData(cursor)
            if(data.isNotEmpty())
                binding.show.isEnabled = true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mUserData?.close()
    }

    private fun enterData(userName:String){
        val db = mUserData?.writableDatabase
        val values = ContentValues()

        values.put(EventDataSQLHelper.USERNAME, userName)
        db?.insert(EventDataSQLHelper.TABLE, null, values)
    }

    private fun getEvents():Cursor?{
        val db = mUserData?.writableDatabase
        val cursor = db?.query(EventDataSQLHelper.TABLE, null,null, null, null, null, null)
        startManagingCursor(cursor)
        return cursor
    }

    private fun showData(cursor:Cursor): String{
        val ret = StringBuilder("")

        while (cursor.moveToNext()){
            val id = cursor.getLong(0)
            val name = cursor.getString(1)
            ret.append(id).append(": ").append(name).append("\n")
        }
        return ret.toString()
    }
}