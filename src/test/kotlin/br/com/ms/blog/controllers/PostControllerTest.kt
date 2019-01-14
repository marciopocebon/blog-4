package br.com.ms.blog.controllers

import br.com.ms.blog.models.Author
import br.com.ms.blog.models.Post
import br.com.ms.blog.requests.PostRequest
import br.com.ms.blog.services.PostService
import com.fasterxml.jackson.databind.ObjectMapper
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Before
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
@WebMvcTest(PostController::class)
class PostControllerTest {

    @Autowired
    private lateinit var mvc: MockMvc

    @Autowired
    private lateinit var mapper: ObjectMapper

    @MockBean
    private lateinit var postService: PostService

    private val baseUrl = "/posts"

    private lateinit var post: Post

    @Before
    fun setup(){
        val author = Author("Author", "Description")
        post = Post("Post", "Content", author, mutableListOf())
    }

    @Test
    fun `given valid post request should save it and return 201`() {
        val postRequest = PostRequest("Post", "Content", 1, mutableListOf())

        whenever(postService.save(any())).thenReturn(post)

        mvc.perform(post(baseUrl)
                .content(mapper.writeValueAsString(postRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated)
    }

    @Test
    fun `given invalid post request should not save it and return 400`() {
        val postRequest = PostRequest("", "", 1, mutableListOf())

        mvc.perform(post(baseUrl)
                .content(mapper.writeValueAsString(postRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest)
    }

    @Test
    fun `given valid post request should update it and return 200`() {
        val postRequest = PostRequest("Post UPDATED", "Content UPDATED", 1, mutableListOf())
        val author = Author("Author", "Description")
        val postUpdated = Post(postRequest.title, postRequest.content, author, mutableListOf())

        whenever(postService.update(anyLong(), any())).thenReturn(postUpdated)

        mvc.perform(put("$baseUrl/0")
                .content(mapper.writeValueAsString(postRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)
    }

    @Test
    fun `given invalid post should not update it and return 400`() {
        val postRequest = PostRequest("", "", 0, emptyList())

        mvc.perform(put("$baseUrl/0")
                .content(mapper.writeValueAsString(postRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest)
    }
}