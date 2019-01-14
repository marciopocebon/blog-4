package br.com.ms.blog.services

import br.com.ms.blog.errors.exception.EntityNotFoundException
import br.com.ms.blog.errors.exception.GeneralException
import br.com.ms.blog.models.Author
import br.com.ms.blog.repositories.AuthorRepository
import br.com.ms.blog.utils.AUTHOR_NOT_EXISTS
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Lazy
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class AuthorService(
        private val authorRepository: AuthorRepository,
        @Lazy private val postService: PostService
) {

    private val logger = LoggerFactory.getLogger(AuthorService::class.java)

    @HystrixCommand(fallbackMethod = "saveFallback")
    fun save(author: Author) = authorRepository.save(author)

    fun saveFallback(author: Author, throwable: Throwable): Author {
        logger.error("Persisting $author to database fails. Running saveFallback method", throwable)

        throw GeneralException(throwable.localizedMessage)
    }

    @HystrixCommand(fallbackMethod = "updateFallback")
    fun update(id: Long, author: Author) = findById(id)
            .apply { update(author) }
            .let { authorRepository.save(it) }

    fun updateFallback(id: Long, author: Author, throwable: Throwable): Author {
        logger.error("Persisting $author to database fails. Running updateFallback method", throwable)

        throw GeneralException(throwable.localizedMessage)
    }

    @HystrixCommand(fallbackMethod = "findAllFallback")
    fun findAll() = authorRepository.findAll()

    fun findAllFallback(throwable: Throwable): List<Author> {
        logger.error("Searching all authors in database fails. Running findAllFallback method", throwable)

        throw GeneralException(throwable.localizedMessage)
    }

    @HystrixCommand(fallbackMethod = "findByIdFallback", ignoreExceptions = [EntityNotFoundException::class])
    fun findById(id: Long): Author = authorRepository.findById(id) ?: throw EntityNotFoundException(AUTHOR_NOT_EXISTS, "id", id)

    fun findByIdFallback(id: Long, throwable: Throwable): Author{
        logger.error("Finding author by ID $id in database fails. Running findByIdFallback method", throwable)

        throw GeneralException(throwable.localizedMessage)
    }

    @HystrixCommand(fallbackMethod = "deleteFallback")
    fun delete(id: Long) {
        val actor = findById(id)
        postService.deleteByAuthorId(actor.id)
        authorRepository.delete(actor)
    }

    fun deleteFallback(id: Long, throwable: Throwable) {
        logger.error("Deleting author by ID $id in database fails. Running deleteFallback method", throwable)

        throw GeneralException(throwable.localizedMessage)
    }
}