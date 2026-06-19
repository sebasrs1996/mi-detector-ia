package com.tuusuario.detectoria

import android.app.Service
import android.content.Intent
import android.graphics.PixelFormat
import android.os.IBinder
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import java.util.Random

class FloatingService : Service() {

    private lateinitinit var windowManager: WindowManager
    private lateinitinit var floatingView: View

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()

        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
        
        // Crear un botón simple de manera dinámica para la simulación
        val button = Button(this).apply {
            text = "🔍 Analizar IA"
            textSize = 14f
            setBackgroundColor(android.graphics.Color.parseColor("#6200EE"))
            setTextColor(android.graphics.Color.WHITE)
            setPadding(30, 20, 30, 20)
        }
        floatingView = button

        // Configuración de la ventana flotante (Overlay)
        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        ).apply {
            gravity = Gravity.TOP or Gravity.START
            x = 100
            y = 300
        }

        // Lógica al presionar el botón flotante
        button.setOnClickListener {
            button.text = "⏳ Escaneando..."
            button.isEnabled = false

            // Simular un retraso de procesamiento de 2 segundos
            button.postDelayed({
                val random = Random()
                val esIA = random.nextBoolean()
                
                if (esIA) {
                    val porcentaje = random.nextInt(31) + 70 // Entre 70% y 100%
                    Toast.makeText(this, "⚠️ ALERTA: Probabilidad de IA del $porcentaje%", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, "✅ Video Seguro: Real / Original", Toast.LENGTH_LONG).show()
                }
                
                button.text = "🔍 Analizar IA"
                button.isEnabled = true
            }, 2000)
        }

        windowManager.addView(floatingView, params)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::floatingView.isInitialized) {
            windowManager.removeView(floatingView)
        }
    }
}
