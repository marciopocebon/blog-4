package br.com.ms.blog.repositories

import br.com.ms.blog.models.Author
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.Repository

interface AuthorRepository : Repository<Author, Long> {

    fun save(author: Author): Author

    fun findAll(pageable: Pageable): Page<Author>

    fun findById(id: Long): Author?

    fun delete(author: Author)
}