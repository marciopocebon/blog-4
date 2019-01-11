package br.com.ms.blog.services

import br.com.ms.blog.errors.exception.EntityNotFoundException
import br.com.ms.blog.models.Post
import br.com.ms.blog.repositories.PostRepository
import br.com.ms.blog.requests.PostRequest
import br.com.ms.blog.utils.Messenger.message
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class PostService(
        private val postRepository: PostRepository,
        private val actorService: ActorService,
        private val categoryService: CategoryService
) {

    fun save(postRequest: PostRequest) = postRepository.save(createPost(postRequest))

    fun update(id: Long, postRequest: PostRequest) = findById(id)
            .apply { update(createPost(postRequest)) }
            .let { postRepository.save(it) }

    fun findAll(pageable: Pageable) = postRepository.findAll(pageable)

    fun findById(id: Long) = postRepository.findById(id)
            ?.apply { views++ }
            ?.let { postRepository.save(it) }
            ?: throw EntityNotFoundException(message("author.not.exists"), "id", id)

    fun delete(id: Long) = postRepository.delete(findById(id))

    fun findByActorId(actorId: Long, pageable: Pageable) = postRepository.findByActorId(actorId, pageable)

    fun findByCategoryId(categoryId: Long, pageable: Pageable) = postRepository.findByCategoryId(categoryId, pageable)

    private fun createPost(postRequest: PostRequest) = Post(
            title = postRequest.title,
            content = postRequest.content,
            author = actorService.findById(postRequest.actorId),
            categories = postRequest.categoriesId.map { categoryService.findById(it) }.toMutableList()
    )
}