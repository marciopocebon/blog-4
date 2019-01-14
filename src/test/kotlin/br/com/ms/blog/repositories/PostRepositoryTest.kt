package br.com.ms.blog.repositories

import br.com.ms.blog.models.Author
import br.com.ms.blog.models.Category
import br.com.ms.blog.models.Post
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Pageable.*
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@DataJpaTest
class PostRepositoryTest {

    @Autowired
    private lateinit var postRepository: PostRepository

    @Autowired
    private lateinit var authorRepository: AuthorRepository

    @Autowired
    private lateinit var categoryRepository: CategoryRepository

    private lateinit var author: Author
    @Before
    fun setup(){
        author = authorRepository.save(Author("Author", "Author Description"))
    }

    @Test
    fun `given ID from author with one post should return a page with size 1`() {
        postRepository.save(Post("Title", "Content", author, mutableListOf()))

        val posts = postRepository.findByAuthorId(author.id)

        assertThat(posts.size).isEqualTo(1)
    }

    @Test
    fun `given ID from category with one post should return a list with size 1`() {
        val category = categoryRepository.save(Category("Category"))
        postRepository.save(Post(" Title", " Content", author, mutableListOf(category)))

        val posts = postRepository.findByCategoryId(author.id)

        assertThat(posts.size).isEqualTo(1)
    }
}