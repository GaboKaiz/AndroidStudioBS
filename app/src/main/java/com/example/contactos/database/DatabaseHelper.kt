package com.example.contactos.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    init {
        writableDatabase // Esto fuerza la creación de la base de datos al instanciar la clase.
    }

    override fun onCreate(db: SQLiteDatabase) {
        try {
            db.execSQL(CREATE_TABLE_CONTACTS)
            Log.d("DatabaseHelper", "Tabla $TABLE_CONTACTS creada correctamente.")
        } catch (e: SQLException) {
            Log.e("DatabaseHelper", "Error al crear la tabla", e)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        try {
            db.execSQL("DROP TABLE IF EXISTS $TABLE_CONTACTS")
            onCreate(db)
            Log.d("DatabaseHelper", "Tabla $TABLE_CONTACTS actualizada a la versión $newVersion.")
        } catch (e: SQLException) {
            Log.e("DatabaseHelper", "Error al actualizar la base de datos", e)
        }
    }

    fun addContact(fullName: String, phone: String): Long {
        val db = writableDatabase
        return try {
            val values = ContentValues().apply {
                put(COLUMN_FULL_NAME, fullName)
                put(COLUMN_PHONE, phone)
            }
            val result = db.insert(TABLE_CONTACTS, null, values)
            Log.d("DatabaseHelper", "Contacto guardado con ID: $result")
            result
        } catch (e: Exception) {
            Log.e("DatabaseError", "Error al insertar contacto", e)
            -1L
        }
    }

    fun getAllContacts(): Cursor? {
        val db = readableDatabase
        return db.rawQuery("SELECT id as _id, fullName, phone FROM contacts", null)
    }
    fun updateContact(id: Int, fullName: String, phone: String): Int {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_FULL_NAME, fullName)
            put(COLUMN_PHONE, phone)
        }
        return db.update(TABLE_CONTACTS, values, "$COLUMN_ID=?", arrayOf(id.toString()))
    }

    fun deleteContact(id: Int): Int {
        val db = writableDatabase
        return db.delete(TABLE_CONTACTS, "$COLUMN_ID=?", arrayOf(id.toString()))
    }
    fun getContactById(id: Int): Cursor? {
        val db = readableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_CONTACTS WHERE id = ?", arrayOf(id.toString()))
    }




    companion object {
        private const val DATABASE_NAME = "contacts.db"
        private const val DATABASE_VERSION = 1

        private const val TABLE_CONTACTS = "contacts"
        private const val COLUMN_ID = "id"
        private const val COLUMN_FULL_NAME = "fullName"
        private const val COLUMN_PHONE = "phone"

        private const val CREATE_TABLE_CONTACTS = """
            CREATE TABLE $TABLE_CONTACTS (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_FULL_NAME TEXT NOT NULL,
                $COLUMN_PHONE TEXT NOT NULL
            )
        """
    }
}
