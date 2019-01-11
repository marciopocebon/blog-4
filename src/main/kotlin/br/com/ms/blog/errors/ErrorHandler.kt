package br.com.ms.blog.errors

import br.com.ms.blog.errors.exception.YellowChallengeException
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ErrorHandler {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValid(ex: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
        val errors = ex.bindingResult.fieldErrors.map {
            ErrorObject(it.defaultMessage ?: "", it.field, it.rejectedValue ?: "")
        }
        return ResponseEntity(ErrorResponse("Invalid data", errors), BAD_REQUEST)
    }

    @ExceptionHandler(YellowChallengeException::class)
    fun handleYellowChallengeException(ex: YellowChallengeException) = ex.errorResponse()
}
