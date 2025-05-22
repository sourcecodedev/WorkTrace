package com.upc.worktrace.utils

import android.R
import android.app.Activity
import android.util.Log
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat

/**
 * Clase de utilidad para gestionar aspectos visuales y de configuración de la aplicación.
 */
object WorkTraceUtils {
    private const val TAG = "WorkTraceUtils"

    /**
     * Configura la interfaz para ocupar el área completa de la pantalla,
     * incluyendo el área debajo del notch y la barra de estado.
     *
     * @param activity La actividad donde se aplicará la configuración
     * @param lightStatusBar Si se deben usar iconos oscuros en la barra de estado (true para fondos claros)
     */
    fun configureFullScreenMode(activity: Activity, lightStatusBar: Boolean = true) {
        try {
            // Hacer que el contenido de la actividad ocupe toda la pantalla
            WindowCompat.setDecorFitsSystemWindows(activity.window, false)

            // Configurar el controlador de insets para gestionar la barra de navegación y de estado
            val windowInsetsController =
                WindowInsetsControllerCompat(activity.window, activity.window.decorView)

            // Configurar el color de los iconos de la barra de estado
            windowInsetsController.isAppearanceLightStatusBars = lightStatusBar

            // Hacer que la barra de estado sea transparente
            activity.window.statusBarColor = activity.resources.getColor(R.color.transparent, activity.theme)
        } catch (e: Exception) {
            Log.e(TAG, "Error al configurar modo de pantalla completa: ${e.message}")
            e.printStackTrace()

            // Fallback básico para evitar errores fatales
            try {
                activity.window.statusBarColor = activity.resources.getColor(R.color.transparent, activity.theme)
            } catch (e2: Exception) {
                Log.e(TAG, "Error en fallback de configuración: ${e2.message}")
            }
        }
    }
}