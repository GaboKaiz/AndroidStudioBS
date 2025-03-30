package com.example.contactos.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.contactos.R
import com.example.contactos.ui.MainActivity  // Asegúrate de cambiarlo por la actividad a la que quieres redirigir

class PresentacionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_presentacion) // Crea un layout XML para esta pantalla

        // Espera 1 segundo y redirige a otra actividad
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java)) // Redirige a MainActivity
            finish() // Cierra esta pantalla
        }, 1000) // 1000 milisegundos = 1 segundo
    }
}
