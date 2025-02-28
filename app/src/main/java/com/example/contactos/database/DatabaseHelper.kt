package com.example.contactos.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE_CONTACTS)  // Crea la tabla al iniciar la base de datos
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_CONTACTS")  // Borra la tabla si ya existe
        onCreate(db)
    }

    fun addContact(fullName: String, phone: String): Long {
        val db = writableDatabase
        return try {
            val values = ContentValues().apply {
                put(COLUMN_FULL_NAME, fullName)
                put(COLUMN_PHONE, phone)
            }
            val result = db.insertOrThrow(TABLE_CONTACTS, null, values)
            Log.d("DatabaseHelper", "Contacto guardado correctamente con ID: $result")
            result
        } catch (e: Exception) {
            Log.e("DatabaseError", "Error al insertar contacto", e)
            -1L
        }
    }


    fun getAllContacts(): Cursor {
        val db = readableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_CONTACTS", null)
    }

    companion object {
        private const val DATABASE_NAME = "contacts.db"
        private const val DATABASE_VERSION = 1

        private const val TABLE_CONTACTS = "contacts"
        private const val COLUMN_ID = "id"
        private const val COLUMN_FULL_NAME = "fullName"  // Ahora coincide con addContact()
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
