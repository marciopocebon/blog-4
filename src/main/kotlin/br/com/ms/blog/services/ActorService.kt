package br.com.ms.blog.services

import br.com.ms.blog.errors.exception.EntityNotFoundException
import br.com.ms.blog.models.Author
import br.com.ms.blog.repositories.AuthorRepository
import br.com.ms.blog.repositories.PostRepository
import br.com.ms.blog.utils.Messenger.message
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class ActorService(
        private val authorRepository: AuthorRepository,
        private val postRepository: PostRepository
) {

    fun save(author: Author) = authorRepository.save(author)

    fun update(id: Long, author: Author) = findById(id)
            .apply { update(author) }
            .let { authorRepository.save(it) }

    fun findAll(pageable: Pageable) = authorRepository.findAll(pageable)

    fun findById(id: Long): Author = authorRepository.findById(id)
            ?: throw EntityNotFoundException(message("author.not.exists"), "id", id)

    fun delete(id: Long) {
        val actor = findById(id)
        postRepository.deleteByActorId(actor.id)
        authorRepository.delete(actor)
    }
}