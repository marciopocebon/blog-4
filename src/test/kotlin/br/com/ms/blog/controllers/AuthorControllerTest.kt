package br.com.ms.blog.controllers

import br.com.ms.blog.models.Author
import br.com.ms.blog.services.AuthorService
import com.fasterxml.jackson.databind.ObjectMapper
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyLong
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@RunWith(SpringRunner::class)
@WebMvcTest(AuthorController::class)
class AuthorControllerTest {

    @Autowired
    private lateinit var mvc: MockMvc

    @Autowired
    private lateinit var mapper: ObjectMapper

    @MockBean
    private lateinit var authorService: AuthorService

    private val baseUrl = "/authors"

    @Test
    fun `given valid author should save it and return 201`() {
        val author = Author("Author", "Description")

        whenever(authorService.save(any())).thenReturn(author)

        mvc.perform(post(baseUrl)
                .content(mapper.writeValueAsString(author))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated)
    }

    @Test
    fun `given invalid author should not save it and return 400`() {
        val author = Author("", "")

        mvc.perform(post(baseUrl)
                .content(mapper.writeValueAsString(author))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest)
    }

    @Test
    fun `given valid author should update it and return 200`() {
        val authorUpdated = Author("Author UPDATED", "Description UPDATED")

        whenever(authorService.update(anyLong(), any())).thenReturn(authorUpdated)

        mvc.perform(put("$baseUrl/0")
                .content(mapper.writeValueAsString(authorUpdated))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)
    }

    @Test
    fun `given invalid author should not update it and return 400`() {
        val authorUpdated = Author("", "")

        mvc.perform(put("$baseUrl/0")
                .content(mapper.writeValueAsString(authorUpdated))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest)
    }
}