package com.upc.worktrace

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.upc.worktrace.utils.WorkTraceUtils

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

            WorkTraceUtils.configureFullScreenMode(this)
            
            toolbar = findViewById(R.id.toolbar_worktrace)
            setSupportActionBar(toolbar)
            

            supportActionBar?.setDisplayShowTitleEnabled(true)
            

            if (showBackButton) {
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
                supportActionBar?.setDisplayShowHomeEnabled(true)
            }
            

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