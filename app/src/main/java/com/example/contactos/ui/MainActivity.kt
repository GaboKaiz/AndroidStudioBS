package com.example.contactos.ui

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.contactos.R
import com.example.contactos.database.DatabaseHelper
import android.content.Intent

class MainActivity : AppCompatActivity() {
    private lateinit var DatabaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        DatabaseHelper = DatabaseHelper(this)

        val edtFullName = findViewById<EditText>(R.id.edtFullName)  // Campo único para nombre completo
        val edtPhone = findViewById<EditText>(R.id.edtPhone)
        val btnSave = findViewById<Button>(R.id.btnSave)
        val btnViewContacts = findViewById<Button>(R.id.btnViewContacts)

        btnSave.setOnClickListener {
            val fullName = edtFullName.text.toString()
            val phone = edtPhone.text.toString()

            if (fullName.isNotEmpty() && phone.isNotEmpty()) {
                val result = DatabaseHelper.addContact(fullName, phone)
                if (result != -1L) {
                    Toast.makeText(this, "Contacto guardado", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Error al guardar", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        btnViewContacts.setOnClickListener {
            startActivity(Intent(this, ContactListActivity::class.java))
        }
    }
}
