package br.com.ms.blog.errors

class ErrorResponse(
        val message: String,
        val errors: List<ErrorObject>
)