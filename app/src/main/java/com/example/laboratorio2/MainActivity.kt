package com.example.laboratorio2

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var preferences: SharedPreferences

    private val PREFS_NAME = "user_preferences"

    private lateinit var etUsername: EditText
    private lateinit var swDarkMode: Switch
    private lateinit var swRemember: Switch
    private lateinit var tvLaunchCount: TextView
    private lateinit var btnSave: Button
    private lateinit var btnReset: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        // Obtener SharedPreferences
        preferences = getSharedPreferences(
            PREFS_NAME,
            Context.MODE_PRIVATE
        )

        // Obtener referencias de los controles
        etUsername = findViewById(R.id.et_username)
        swDarkMode = findViewById(R.id.sw_dark_mode)
        swRemember = findViewById(R.id.sw_remember)
        tvLaunchCount = findViewById(R.id.tv_launch_count)
        btnSave = findViewById(R.id.btn_save)
        btnReset = findViewById(R.id.btn_reset)

        // Contador de aperturas
        val currentLaunches =
            preferences.getInt("launch_count", 0) + 1

        preferences.edit()
            .putInt("launch_count", currentLaunches)
            .apply()

        tvLaunchCount.text = "Veces abierta: $currentLaunches"

        // Recuperar configuraciones anteriores
        etUsername.setText(
            preferences.getString("username", "")
        )

        swDarkMode.isChecked =
            preferences.getBoolean("dark_mode", false)

        swRemember.isChecked =
            preferences.getBoolean("remember_session", false)

        // Botón Guardar
        btnSave.setOnClickListener {

            val editor = preferences.edit()

            editor.putString(
                "username",
                etUsername.text.toString().trim()
            )

            editor.putBoolean(
                "dark_mode",
                swDarkMode.isChecked
            )

            editor.putBoolean(
                "remember_session",
                swRemember.isChecked
            )

            editor.apply()

            Toast.makeText(
                this,
                "Preferencias guardadas exitosamente",
                Toast.LENGTH_SHORT
            ).show()
        }

        // Botón Restablecer
        btnReset.setOnClickListener {

            preferences.edit()
                .clear()
                .apply()

            etUsername.text.clear()

            swDarkMode.isChecked = false

            swRemember.isChecked = false

            tvLaunchCount.text = "Veces abierta: 0"

            Toast.makeText(
                this,
                "Datos eliminados",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}