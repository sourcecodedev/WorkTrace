package com.upc.worktrace

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

/**
 * Actividad base para todas las actividades de la aplicación.
 * Configura automáticamente la barra superior con el título "WorkTrace".
 */
abstract class BaseActivity : AppCompatActivity() {
    
    lateinit var toolbar: Toolbar
    private val TAG = "BaseActivity"
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        try {
            // La configuración de pantalla completa se realizará después de setContentView en setupToolbar
            // para evitar problemas con la inicialización de la vista
        } catch (e: Exception) {
            Log.e(TAG, "Error en onCreate de BaseActivity: ${e.message}")
            e.printStackTrace()
        }
    }
    
    /**
     * Configura la barra superior personalizada.
     * Debe llamarse después de setContentView().
     *
     * @param showBackButton Si se debe mostrar el botón para volver atrás
     * @param title Título opcional para mostrar en la barra (si es nulo, se mostrará "WorkTrace")
     */
    protected fun setupToolbar(showBackButton: Boolean = false, title: String? = null) {
        try {
            // Configurar la visualización en pantalla completa
            WorkTraceUtils.configureFullScreenMode(this)
            
            toolbar = findViewById(R.id.toolbar_worktrace)
            setSupportActionBar(toolbar)
            
            // Mostrar el título en la ActionBar
            supportActionBar?.setDisplayShowTitleEnabled(true)
            
            // Configurar el botón para volver atrás si es necesario
            if (showBackButton) {
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
                supportActionBar?.setDisplayShowHomeEnabled(true)
            }
            
            // Configurar el título personalizado si es necesario
            if (title != null) {
                supportActionBar?.title = title
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error en setupToolbar: ${e.message}")
            e.printStackTrace()
        }
    }
    
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
} 