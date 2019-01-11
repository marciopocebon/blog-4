package br.com.ms.blog.repositories

import br.com.ms.blog.models.Post
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.Repository

interface PostRepository : Repository<Post, Long> {

    fun save(post: Post): Post

    fun findAll(pageable: Pageable): Page<Post>

    fun findById(id: Long): Post?

    fun delete(post: Post)

    @Query("SELECT p FROM Post p WHERE p.author.id = ?1")
    fun findByActorId(actorId: Long, pageable: Pageable): Page<Post>

    @Modifying
    @Query("DELETE FROM Post p WHERE p.author.id = ?1")
    fun deleteByActorId(actorId: Long)

    @Query("SELECT p FROM Post p INNER JOIN p.categories c WHERE c.id = ?1")
    fun findByCategoryId(categoryId: Long, pageable: Pageable): Page<Post>

    @Query("SELECT p FROM Post p INNER JOIN p.categories c WHERE c.id = ?1")
    fun findByCategoryId(categoryId: Long): List<Post>
}