package br.com.ms.blog.errors.exception

import br.com.ms.blog.errors.ErrorObject
import br.com.ms.blog.errors.ErrorResponse
import br.com.ms.blog.utils.NOT_FOUND_EXCEPTION_MESSAGE
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.http.ResponseEntity

class EntityNotFoundException(
        message: String,
        field: String,
        parameter: Any
) : BlogException(message, field, parameter) {

    override fun errorResponse() = ResponseEntity(
            ErrorResponse(NOT_FOUND_EXCEPTION_MESSAGE, listOf(ErrorObject(message, field, parameter))), NOT_FOUND
    )
}