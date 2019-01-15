package br.com.ms.blog.controllers

import br.com.ms.blog.errors.exception.EntityNotFoundException
import br.com.ms.blog.models.Author
import br.com.ms.blog.services.AuthorService
import br.com.ms.blog.utils.AUTHOR_NOT_EXISTS
import com.fasterxml.jackson.databind.ObjectMapper
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyLong
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.core.io.ClassPathResource
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
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
        val expectedJson = ClassPathResource("json/author/author.json").file.reader().readText()

        whenever(authorService.save(any())).thenReturn(author)

        mvc.perform(post(baseUrl)
                .content(mapper.writeValueAsString(author))
                .contentType(APPLICATION_JSON))
                .andExpect(status().isCreated)
                .andExpect(content().json(expectedJson))
    }

    @Test
    fun `given invalid author should not save it and return 400`() {
        val author = Author("", "")
        val expectedJson = ClassPathResource("json/author/invalid_author.json").file.reader().readText()

        mvc.perform(post(baseUrl)
                .content(mapper.writeValueAsString(author))
                .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest)
                .andExpect(content().json(expectedJson))
    }

    @Test
    fun `given valid author should update it and return 200`() {
        val authorUpdated = Author("Author UPDATED", "Description UPDATED")
        val expectedJson = ClassPathResource("json/author/updated_author.json").file.reader().readText()

        whenever(authorService.update(anyLong(), any())).thenReturn(authorUpdated)

        mvc.perform(put("$baseUrl/0")
                .content(mapper.writeValueAsString(authorUpdated))
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk)
                .andExpect(content().json(expectedJson))
    }

    @Test
    fun `given invalid author should not update it and return 400`() {
        val authorUpdated = Author("", "")
        val expectedJson = ClassPathResource("json/author/invalid_author.json").file.reader().readText()

        mvc.perform(put("$baseUrl/0")
                .content(mapper.writeValueAsString(authorUpdated))
                .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest)
                .andExpect(content().json(expectedJson))
    }

    @Test
    fun `should find all authors and return 200`(){
        val author1 = Author("Author 1", "Description 1")
        val author2 = Author("Author 2", "Description 2")
        val expectedJson = ClassPathResource("json/author/authors.json").file.reader().readText()

        whenever(authorService.findAll()).thenReturn(listOf(author1, author2))

        mvc.perform(get(baseUrl))
                .andExpect(status().isOk)
                .andExpect(content().json(expectedJson))
    }

    @Test
    fun `given ID of existent author should return it and 200`(){
        val author = Author("Author", "Description")
        val expectedJson = ClassPathResource("json/author/author.json").file.reader().readText()

        whenever(authorService.findById(anyLong())).thenReturn(author)

        mvc.perform(get("$baseUrl/0"))
                .andExpect(status().isOk)
                .andExpect(content().json(expectedJson))
    }

    @Test
    fun `given ID of non existent author should not find it, throw exception and return 404`(){
        val expectedJson = ClassPathResource("json/author/author_not_found.json").file.reader().readText()

        whenever(authorService.findById(anyLong())).thenThrow(EntityNotFoundException(AUTHOR_NOT_EXISTS, "id", 0))

        mvc.perform(get("$baseUrl/0"))
                .andExpect(status().isNotFound)
                .andExpect(content().json(expectedJson))
    }

    @Test
    fun `given ID of existent author should delete it and return it and 204`(){
        mvc.perform(delete("$baseUrl/0"))
                .andExpect(status().isNoContent)
    }
}