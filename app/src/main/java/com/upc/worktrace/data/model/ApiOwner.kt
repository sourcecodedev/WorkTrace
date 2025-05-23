package com.upc.worktrace.data.model

enum class ApiOwner(val baseUrl: String) {
    PROD("https://worktrace-api.azurewebsites.net/"),
    DEV("http://10.0.2.2:5087/"),
    LOCAL("http://192.168.1.36:5087/"),
    GENERAL("https://mock-c4159884c5b24c80a095d43fed1b1c16.mock.insomnia.rest/"),
    yhimy("https://20mpw85kof.execute-api.us-east-1.amazonaws.com/");

    companion object {
        fun fromName(name: String): ApiOwner {
            return entries.find { it.name.equals(name, ignoreCase = true) }
                ?: PROD // Por defecto retorna PROD si no encuentra coincidencia
        }
    }
}
