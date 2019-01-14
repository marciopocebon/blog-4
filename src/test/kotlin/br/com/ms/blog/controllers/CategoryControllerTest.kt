package br.com.ms.blog.controllers

import br.com.ms.blog.models.Category
import br.com.ms.blog.services.CategoryService
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
@WebMvcTest(CategoryController::class)
class CategoryControllerTest {

    @Autowired
    private lateinit var mvc: MockMvc

    @Autowired
    private lateinit var mapper: ObjectMapper

    @MockBean
    private lateinit var categoryService: CategoryService

    private val baseUrl = "/categories"

    @Test
    fun `given valid category should save it and return 201`() {
        val category = Category("Category")

        whenever(categoryService.save(any())).thenReturn(category)

        mvc.perform(post(baseUrl)
                .content(mapper.writeValueAsString(category))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated)
    }

    @Test
    fun `given invalid category should not save it and return 400`() {
        val category = Category("")

        mvc.perform(post(baseUrl)
                .content(mapper.writeValueAsString(category))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest)
    }

    @Test
    fun `given valid category should update it and return 200`() {
        val categoryUpdated = Category("Category UPDATED")

        whenever(categoryService.update(anyLong(), any())).thenReturn(categoryUpdated)

        mvc.perform(put("$baseUrl/0")
                .content(mapper.writeValueAsString(categoryUpdated))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)
    }

    @Test
    fun `given invalid category should not update it and return 400`() {
        val categoryUpdated = Category("")

        mvc.perform(put("$baseUrl/0")
                .content(mapper.writeValueAsString(categoryUpdated))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest)
    }
}