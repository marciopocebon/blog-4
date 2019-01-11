package br.com.ms.blog.errors

class ErrorObject(
        val message: String,
        val field: String? = null,
        val parameter: Any? = null
)
