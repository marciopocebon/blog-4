package br.com.ms.blog.repositories

import br.com.ms.blog.models.Category
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.Repository
import org.springframework.transaction.annotation.Transactional

interface CategoryRepository : Repository<Category, Long> {

    fun save(category: Category): Category

    fun findById(id: Long?): Category?

    fun findAll(): List<Category>

    @Transactional
    fun delete(category: Category)
}