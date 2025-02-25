package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.functions.FirebaseFunctions
import com.google.zxing.integration.android.IntentIntegrator

@Suppress("OVERRIDE_DEPRECATION")
class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var functions: FirebaseFunctions

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Aplicar colores al tema de la aplicación
        window.statusBarColor = getColor(R.color.azul_oscuro)
        supportActionBar?.setBackgroundDrawable(resources.getDrawable(R.color.azul_claro))

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        functions = FirebaseFunctions.getInstance()

        val btnScanQR: Button = findViewById(R.id.btnScanQR)
        val btnLogout: Button = findViewById(R.id.btnLogout)

        btnScanQR.setBackgroundColor(getColor(R.color.azul_claro))
        btnScanQR.setTextColor(getColor(R.color.blanco))

        btnLogout.setBackgroundColor(getColor(R.color.azul_oscuro))
        btnLogout.setTextColor(getColor(R.color.blanco))

        btnScanQR.setOnClickListener { scanQRCode() }
        btnLogout.setOnClickListener { logout() }
    }

    private fun scanQRCode() {
        val scanner = IntentIntegrator(this)
        scanner.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
        scanner.setPrompt("Escanea tu código QR")
        scanner.setCameraId(0)
        scanner.setBeepEnabled(false)
        scanner.setBarcodeImageEnabled(true)
        scanner.initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents != null) {
                registrarAsistencia(result.contents)
            } else {
                Toast.makeText(this, "Escaneo cancelado", Toast.LENGTH_SHORT).show()
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun registrarAsistencia(usuarioId: String) {
        val asistencia = hashMapOf(
            "usuarioId" to usuarioId,
            "fecha" to Timestamp.now()
        )
        db.collection("asistencias")
            .add(asistencia)
            .addOnSuccessListener {
                Toast.makeText(this, "Asistencia registrada", Toast.LENGTH_SHORT).show()
                enviarNotificacion(usuarioId)
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error al registrar asistencia", Toast.LENGTH_SHORT).show()
            }
    }

    private fun enviarNotificacion(usuarioId: String) {
        val data = hashMapOf(
            "usuarioId" to usuarioId
        )
        functions.getHttpsCallable("enviarNotificacion")
            .call(data)
            .addOnSuccessListener {
                Toast.makeText(this, "Notificación enviada", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error al enviar notificación", Toast.LENGTH_SHORT).show()
            }
    }

    private fun logout() {
        auth.signOut()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}

annotation class LoginActivity
