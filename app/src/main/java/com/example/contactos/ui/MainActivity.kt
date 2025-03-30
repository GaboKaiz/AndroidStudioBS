package com.example.contactos.ui

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.contactos.R
import com.example.contactos.database.DatabaseHelper
import android.content.Intent
import android.widget.ImageButton
import com.example.contactos.ui.MainActivity

class MainActivity : AppCompatActivity() {
    private lateinit var databaseHelper: DatabaseHelper  // Corregido: primera letra en minúscula

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        databaseHelper = DatabaseHelper(this) // Se mantiene correcto

        val edtFullName = findViewById<EditText>(R.id.edtFullName)
        val edtPhone = findViewById<EditText>(R.id.edtPhone)
        val btnSave = findViewById<Button>(R.id.btnSave)
        val btnViewContacts = findViewById<Button>(R.id.btnViewContacts)

        btnSave.setOnClickListener {
            val fullName = edtFullName.text.toString().trim()
            val phone = edtPhone.text.toString().trim()

            if (fullName.isNotEmpty() && phone.isNotEmpty()) {
                val result = databaseHelper.addContact(fullName, phone)
                if (result != -1L) {
                    Toast.makeText(this, "Contacto guardado", Toast.LENGTH_SHORT).show()
                    edtFullName.text.clear()
                    edtPhone.text.clear() // Limpia los campos después de guardar
                } else {
                    Toast.makeText(this, "Error al guardar", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        btnViewContacts.setOnClickListener {
            startActivity(Intent(this,Garabato::class.java))
        }

    }
}
