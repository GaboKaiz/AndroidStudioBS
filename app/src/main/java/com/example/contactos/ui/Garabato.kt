package com.example.contactos.ui

import android.app.AlertDialog
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ListView
import android.widget.SimpleCursorAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.contactos.R
import com.example.contactos.database.DatabaseHelper
import com.example.contactos.editores.EditContactActivity

class Garabato : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var listView: ListView
    private lateinit var cursor: Cursor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.garabato)

        dbHelper = DatabaseHelper(this)
        listView = findViewById(R.id.listViewContacts)
        val btnRetroceder = findViewById<ImageButton>(R.id.btnretroceder)

        loadContacts()

        btnRetroceder.setOnClickListener {
            onBackPressed()
        }
    }

    private fun loadContacts() {
        cursor = dbHelper.getAllContacts() ?: return

        if (cursor.count == 0) {
            Toast.makeText(this, "No hay contactos disponibles", Toast.LENGTH_SHORT).show()
            return
        }

        val adapter = object : SimpleCursorAdapter(
            this,
            R.layout.item_contact,
            cursor,
            arrayOf("fullName", "phone"),
            intArrayOf(R.id.txtFullName, R.id.txtPhone),
            0
        ) {
            override fun bindView(view: View, context: android.content.Context, cursor: Cursor) {
                super.bindView(view, context, cursor)

                val contactId = cursor.getInt(cursor.getColumnIndexOrThrow("_id"))

                val btnEdit = view.findViewById<ImageButton>(R.id.btnEdit)
                val btnDelete = view.findViewById<ImageButton>(R.id.btnDelete)

                btnEdit.setOnClickListener {
                    editContact(contactId)
                }

                btnDelete.setOnClickListener {
                    deleteContact(contactId)
                }
            }
        }

        listView.adapter = adapter
    }

    private fun editContact(contactId: Int) {
        val intent = Intent(this, EditContactActivity::class.java)
        intent.putExtra("CONTACT_ID", contactId)
        startActivity(intent)
    }

    private fun deleteContact(contactId: Int) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Eliminar contacto")
            .setMessage("¿Estás seguro de que deseas eliminar este contacto?")
            .setPositiveButton("Sí") { _, _ ->
                dbHelper.deleteContact(contactId)
                Toast.makeText(this, "Contacto eliminado", Toast.LENGTH_SHORT).show()
                loadContacts()
            }
            .setNegativeButton("No", null)
            .show()
    }
    override fun onResume() {
        super.onResume()
        loadContacts()  // Recargar la lista al volver a esta pantalla
    }

}