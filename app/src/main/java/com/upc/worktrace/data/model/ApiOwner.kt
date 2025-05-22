package com.upc.worktrace.data.model

enum class ApiOwner(val baseUrl: String) {
    JIMI("https://api.jimi.aws/"),
    HAROL("https://api.harol.aws/"),
    YHIMY("https://api.yhimy.aws/"),
    EVER("https://api.ever.aws/"),
    General("https://mock-c4159884c5b24c80a095d43fed1b1c16.mock.insomnia.rest/");

    companion object {
        fun fromName(name: String): ApiOwner? {
            return values().find { it.name.equals(name, ignoreCase = true) }
        }
    }
}
