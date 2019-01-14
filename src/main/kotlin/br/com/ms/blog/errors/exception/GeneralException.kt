package br.com.ms.blog.errors.exception

import br.com.ms.blog.errors.ErrorObject
import br.com.ms.blog.errors.ErrorResponse
import br.com.ms.blog.utils.GENERAL_EXCEPTION_MESSAGE
import org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR
import org.springframework.http.ResponseEntity

class GeneralException(
        message: String
) : BlogException(message) {

    override fun errorResponse() = ResponseEntity(
            ErrorResponse(GENERAL_EXCEPTION_MESSAGE, listOf(ErrorObject(message))), INTERNAL_SERVER_ERROR
    )
}