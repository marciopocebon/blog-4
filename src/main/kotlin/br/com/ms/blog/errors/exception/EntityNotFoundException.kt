package br.com.ms.blog.errors.exception

import br.com.ms.blog.errors.ErrorObject
import br.com.ms.blog.errors.ErrorResponse
import br.com.ms.blog.utils.Messenger.message
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.http.ResponseEntity

class EntityNotFoundException(
        message: String,
        field: String,
        parameter: Any
) : YellowChallengeException(message, field, parameter) {

    override fun errorResponse() = ResponseEntity(
            ErrorResponse(message("not.found.general"), listOf(ErrorObject(message, field, parameter))), NOT_FOUND
    )
}