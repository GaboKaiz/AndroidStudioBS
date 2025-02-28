package com.example.contactos.ui

import android.database.Cursor
import android.os.Bundle
import android.widget.ListView
import android.widget.SimpleCursorAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.contactos.R
import com.example.contactos.database.DatabaseHelper

class ContactListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_list)

        val dbHelper = DatabaseHelper(this)
        val cursor: Cursor = dbHelper.getAllContacts()

        val listView = findViewById<ListView>(R.id.listViewContacts)
        val adapter = SimpleCursorAdapter(
            this,
            android.R.layout.simple_list_item_2,
            cursor,
            arrayOf("name", "phone"),
            intArrayOf(android.R.id.text1, android.R.id.text2),
            0
        )

        listView.adapter = adapter
    }
}
