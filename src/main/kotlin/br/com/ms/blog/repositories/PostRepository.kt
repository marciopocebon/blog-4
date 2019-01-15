package br.com.ms.blog.repositories

import br.com.ms.blog.models.Post
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.Repository
import javax.transaction.Transactional

interface PostRepository : Repository<Post, Long> {

    fun save(post: Post): Post

    fun findAll(): List<Post>

    fun findById(id: Long): Post?

    fun delete(post: Post)

    @Modifying
    @Transactional
    @Query("DELETE FROM Post p WHERE p.author.id = ?1")
    fun deleteByAuthorId(authorId: Long)

    @Query("SELECT p FROM Post p WHERE p.author.id = ?1")
    fun findByAuthorId(authorId: Long): List<Post>

    @Query("SELECT p FROM Post p JOIN p.categories c ON c.id = ?1")
    fun findByCategory(categoryId: Long): List<Post>
}