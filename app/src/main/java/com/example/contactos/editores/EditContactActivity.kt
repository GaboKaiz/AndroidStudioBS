package com.example.contactos.editores

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.contactos.R
import com.example.contactos.database.DatabaseHelper

class EditContactActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper
    private var contactId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_contact)

        dbHelper = DatabaseHelper(this)

        val edtFullName = findViewById<EditText>(R.id.edtFullName)
        val edtPhone = findViewById<EditText>(R.id.edtPhone)
        val btnSave = findViewById<Button>(R.id.btnSave)

        contactId = intent.getIntExtra("CONTACT_ID", -1)

        val cursor = dbHelper.getContactById(contactId)
        cursor?.use {
            if (it.moveToFirst()) {
                edtFullName.setText(it.getString(it.getColumnIndexOrThrow("fullName")))
                edtPhone.setText(it.getString(it.getColumnIndexOrThrow("phone")))
            }
        }

        btnSave.setOnClickListener {
            val fullName = edtFullName.text.toString().trim()
            val phone = edtPhone.text.toString().trim()

            if (fullName.isNotEmpty() && phone.isNotEmpty()) {
                dbHelper.updateContact(contactId, fullName, phone)
                Toast.makeText(this, "Contacto actualizado", Toast.LENGTH_SHORT).show()
                val intent = Intent()
                setResult(RESULT_OK, intent)
                finish()
            } else {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
