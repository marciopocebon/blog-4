package br.com.ms.blog.services

import br.com.ms.blog.errors.exception.EntityNotFoundException
import br.com.ms.blog.errors.exception.GeneralException
import br.com.ms.blog.models.Category
import br.com.ms.blog.repositories.CategoryRepository
import br.com.ms.blog.utils.CATEGORY_NOT_EXISTS
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Lazy
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class CategoryService(
        private val categoryRepository: CategoryRepository,
        @Lazy private val postService: PostService
) {

    private val logger = LoggerFactory.getLogger(CategoryService::class.java)

    @HystrixCommand(fallbackMethod = "saveFallback")
    fun save(category: Category) = categoryRepository.save(category)

    fun saveFallback(category: Category, throwable: Throwable): Category {
        logger.error("Persisting $category to database fails. Running saveFallback method", throwable)

        throw GeneralException(throwable.localizedMessage)
    }

    @HystrixCommand(fallbackMethod = "updateFallback")
    fun update(id: Long, category: Category) = findById(id)
            .apply { update(category) }
            .let { categoryRepository.save(it) }

    fun updateFallback(id: Long, category: Category, throwable: Throwable): Category {
        logger.error("Persisting $category to database fails. Running updateFallback method", throwable)

        throw GeneralException(throwable.localizedMessage)
    }

    @HystrixCommand(fallbackMethod = "findAllFallback")
    fun findAll(pageable: Pageable) = categoryRepository.findAll(pageable)

    fun findAllFallback(pageable: Pageable, throwable: Throwable): Page<Category> {
        logger.error("Searching all categories in database fails. Running findAllFallback method", throwable)

        throw GeneralException(throwable.localizedMessage)
    }

    @HystrixCommand(fallbackMethod = "findByIdFallback", ignoreExceptions = [EntityNotFoundException::class])
    fun findById(id: Long) = categoryRepository.findById(id) ?: throw EntityNotFoundException(CATEGORY_NOT_EXISTS, "id", id)

    fun findByIdFallback(id: Long, throwable: Throwable): Category{
        logger.error("Finding category by ID $id in database fails. Running findByIdFallback method", throwable)

        throw GeneralException(throwable.localizedMessage)
    }

    @HystrixCommand(fallbackMethod = "deleteFallback")
    fun delete(id: Long) = findById(id).let {
        postService.deleteCategoryFromPosts(it)
        categoryRepository.delete(it)
    }

    fun deleteFallback(id: Long, throwable: Throwable) {
        logger.error("Deleting category by ID $id in database fails. Running deleteFallback method", throwable)

        throw GeneralException(throwable.localizedMessage)
    }
}