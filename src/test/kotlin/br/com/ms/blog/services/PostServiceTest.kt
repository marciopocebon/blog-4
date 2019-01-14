package br.com.ms.blog.services

import br.com.ms.blog.errors.exception.EntityNotFoundException
import br.com.ms.blog.models.Author
import br.com.ms.blog.models.Category
import br.com.ms.blog.models.Post
import br.com.ms.blog.repositories.PostRepository
import br.com.ms.blog.requests.PostRequest
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.whenever
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyLong
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Spy
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class PostServiceTest {

    @InjectMocks
    private lateinit var postService: PostService

    @Mock
    private lateinit var postRepository: PostRepository

    @Mock
    private lateinit var categoryService: CategoryService

    @Mock
    private lateinit var authorService: AuthorService

    @Test
    fun `given valid Post should update an existent Post`(){
        val category = Category("Category")
        val author = Author("Author", "Description")
        val post = Post("Post", "Content", author, mutableListOf())
        val updatedPostRequest = PostRequest("Post UPDATED", "Content UPDATED", 1, listOf(1))
        val updatedPost = Post("Post UPDATED", "Content UPDATED", author, mutableListOf(category)).apply { views++ }

        whenever(postRepository.findById(any())).thenReturn(post)
        whenever(postRepository.save(any())).thenReturn(post)
        whenever(authorService.findById(anyLong())).thenReturn(author)
        whenever(categoryService.findById(anyLong())).thenReturn(category)

        postService.update(0, updatedPostRequest)

        assertThat(post).isEqualToComparingFieldByField(updatedPost)
    }

    @Test(expected = EntityNotFoundException::class)
    fun `given ID of non existent post should throw EntityNotFoundException`(){
        whenever(postRepository.findById(anyLong())).thenReturn(null)

        postService.findById(0)
    }

    @Test
    fun `given category should delete it from posts that have it`(){
        val category = Category("Category")
        val author = Author("Author", "Description")
        val post = Post("Post", "Content", author, mutableListOf(category))

        whenever(postRepository.findByCategoryId(anyLong())).thenReturn(listOf(post))

        postService.deleteCategoryFromPosts(category)

        assertThat(post.categories).isEmpty()
    }
}