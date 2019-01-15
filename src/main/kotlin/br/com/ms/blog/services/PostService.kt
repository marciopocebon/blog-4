package br.com.ms.blog.services

import br.com.ms.blog.errors.exception.EntityNotFoundException
import br.com.ms.blog.errors.exception.GeneralException
import br.com.ms.blog.models.Category
import br.com.ms.blog.models.Post
import br.com.ms.blog.repositories.PostRepository
import br.com.ms.blog.requests.PostRequest
import br.com.ms.blog.utils.POST_NOT_EXISTS
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class PostService(
        private val postRepository: PostRepository,
        private val categoryService: CategoryService,
        private val authorService: AuthorService
) {

    private val logger = LoggerFactory.getLogger(PostService::class.java)

    @HystrixCommand(fallbackMethod = "saveFallback")
    fun save(postRequest: PostRequest) = postRepository.save(createPost(postRequest))

    fun saveFallback(postRequest: PostRequest, throwable: Throwable): Post {
        logger.error("Persisting ${createPost(postRequest)} to database fails. Running saveFallback method", throwable)

        throw GeneralException(throwable.localizedMessage)
    }

    @HystrixCommand(fallbackMethod = "updateFallback")
    fun update(id: Long, postRequest: PostRequest) = findById(id)
            .apply { update(createPost(postRequest)) }
            .let { postRepository.save(it) }

    fun updateFallback(id: Long, postRequest: PostRequest, throwable: Throwable): Post {
        logger.error("Persisting ${createPost(postRequest)} to database fails. Running updateFallback method", throwable)

        throw GeneralException(throwable.localizedMessage)
    }

    @HystrixCommand(fallbackMethod = "findAllFallback")
    fun findAll() = postRepository.findAll()

    fun findAllFallback(throwable: Throwable): List<Post> {
        logger.error("Searching all posts in database fails. Running findAllFallback method", throwable)

        throw GeneralException(throwable.localizedMessage)
    }

    @HystrixCommand(fallbackMethod = "findByIdFallback", ignoreExceptions = [EntityNotFoundException::class])
    fun findById(id: Long) = postRepository.findById(id)
            ?.apply { views++ }
            ?.let { postRepository.save(it) }
            ?: throw EntityNotFoundException(POST_NOT_EXISTS, "id", id)

    fun findByIdFallback(id: Long, throwable: Throwable): Post {
        logger.error("Finding post by ID $id in database fails. Running findByIdFallback method", throwable)

        throw GeneralException(throwable.localizedMessage)
    }

    @HystrixCommand(fallbackMethod = "deleteFallback")
    fun delete(id: Long) = postRepository.delete(findById(id))

    fun deleteFallback(id: Long, throwable: Throwable) {
        logger.error("Deleting post by ID $id in database fails. Running deleteFallback method", throwable)

        throw GeneralException(throwable.localizedMessage)
    }

    @HystrixCommand(fallbackMethod = "findByAuthorIdFallback")
    fun findByAuthorId(authorId: Long) = postRepository.findByAuthorId(authorId)

    fun findByAuthorIdFallback(authorId: Long, throwable: Throwable): List<Post> {
        logger.error("Finding posts by author ID $authorId in database fails. Running findByAuthorIdFallback method", throwable)

        throw GeneralException(throwable.localizedMessage)
    }

    @HystrixCommand(fallbackMethod = "findByCategoryIdFallback")
    fun findByCategoryId(categoryId: Long) = postRepository.findByCategory(categoryId)

    fun findByCategoryIdFallback(categoryId: Long, throwable: Throwable): List<Post> {
        logger.error("Finding posts by category ID $categoryId in database fails. Running findByCategoryIdFallback method", throwable)

        throw GeneralException(throwable.localizedMessage)
    }

    @HystrixCommand(fallbackMethod = "deleteCategoryFromPostsFallback")
    fun deleteCategoryFromPosts(category: Category) = postRepository.findByCategory(category.id)
            .apply { forEach { it.categories.remove(category) } }
            .forEach { postRepository.save(it) }

    fun deleteCategoryFromPostsFallback(category: Category, throwable: Throwable) {
        logger.error("Deleting $category from posts fails. Running deleteCategoryFromPostsFallback method", throwable)

        throw GeneralException(throwable.localizedMessage)
    }

    @HystrixCommand(fallbackMethod = "deleteByAuthorIdFallback")
    fun deleteByAuthorId(authorId: Long) = postRepository.deleteByAuthorId(authorId)

    fun deleteByAuthorIdFallback(authorId: Long, throwable: Throwable) {
        logger.error("Deleting posts by author ID $authorId fails. Running deleteByAuthorIdFallback method", throwable)

        throw GeneralException(throwable.localizedMessage)
    }

    private fun createPost(postRequest: PostRequest) = Post(
            title = postRequest.title,
            content = postRequest.content,
            author = authorService.findById(postRequest.authorId),
            categories = postRequest.categoriesId.map { categoryService.findById(it) }.toMutableList()
    )
}