package br.com.ms.blog.services

import br.com.ms.blog.errors.exception.EntityNotFoundException
import br.com.ms.blog.models.Category
import br.com.ms.blog.repositories.CategoryRepository
import br.com.ms.blog.repositories.PostRepository
import br.com.ms.blog.utils.Messenger.message
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class CategoryService(
        private val categoryRepository: CategoryRepository,
        private val postRepository: PostRepository
) {

    fun save(category: Category) = categoryRepository.save(category)

    fun update(id: Long, category: Category) = findById(id)
            .apply { update(category) }
            .let { categoryRepository.save(it) }

    fun findAll(pageable: Pageable) = categoryRepository.findAll(pageable)

    fun findById(id: Long) = categoryRepository.findById(id)
            ?: throw EntityNotFoundException(message("category.not.exists"), "id", id)

    fun delete(id: Long) = findById(id).let {
        deleteCategoryFromPost(it)
        categoryRepository.delete(it)
    }

    private fun deleteCategoryFromPost(category: Category) = postRepository.findByCategoryId(category.id)
            .filter { it.categories.contains(category) }
            .apply { forEach { it.categories.remove(category) } }
            .forEach { postRepository.save(it) }
}