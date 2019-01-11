package br.com.ms.blog.errors.exception

import br.com.ms.blog.errors.ErrorResponse
import org.springframework.http.ResponseEntity

abstract class YellowChallengeException(
        override val message: String,
        val field: String? = null,
        val parameter: Any? = null) : RuntimeException() {

    abstract fun errorResponse(): ResponseEntity<ErrorResponse>
}