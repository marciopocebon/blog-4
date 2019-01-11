package br.com.ms.blog.repositories

import br.com.ms.blog.models.Category
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.Repository

interface CategoryRepository : Repository<Category, Long> {

    fun save(category: Category): Category

    fun findById(id: Long?): Category?

    fun findAll(pageable: Pageable): Page<Category>

    fun delete(category: Category)
}