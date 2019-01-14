package br.com.ms.blog.services

import br.com.ms.blog.errors.exception.EntityNotFoundException
import br.com.ms.blog.models.Author
import br.com.ms.blog.repositories.AuthorRepository
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyLong
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class AuthorServiceTest {

    @InjectMocks
    private lateinit var authorService: AuthorService

    @Mock
    private lateinit var authorRepository: AuthorRepository

    @Mock
    private lateinit var postService: PostService

    @Test
    fun `given valid Author should update an existent author`(){
        val author = Author("Author", "Description")
        val updatedAuthor = Author("Author Updated", "Description Updated")

        whenever(authorRepository.findById(anyLong())).thenReturn(author)
        whenever(authorRepository.save(any())).thenReturn(any())

        authorService.update(0, updatedAuthor)

        assertThat(author).isEqualToComparingFieldByField(updatedAuthor)
    }

    @Test(expected = EntityNotFoundException::class)
    fun `given ID of non existent author should throw EntityNotFoundException`(){
        whenever(authorRepository.findById(anyLong())).thenReturn(null)

        authorService.findById(0)
    }
}