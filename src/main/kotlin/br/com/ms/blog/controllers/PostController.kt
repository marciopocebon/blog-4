package br.com.ms.blog.controllers

import br.com.ms.blog.requests.PostRequest
import br.com.ms.blog.resources.PostResource
import br.com.ms.blog.services.PostService
import org.springframework.http.HttpStatus.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/posts")
class PostController(
        private val postService: PostService
) {

    @PostMapping
    fun save(@Valid @RequestBody postRequest: PostRequest): ResponseEntity<PostResource> {
        val resource = PostResource(postService.save(postRequest))

        return ResponseEntity(resource, CREATED)
    }

    @PutMapping("{id}")
    fun update(@PathVariable id: Long, @Valid @RequestBody postRequest: PostRequest): ResponseEntity<PostResource> {
        val resource = PostResource(postService.update(id, postRequest))

        return ResponseEntity(resource, OK)
    }

    @GetMapping
    fun findAll(): ResponseEntity<List<PostResource>> {
        val resources = postService.findAll().map { PostResource(it) }

        return ResponseEntity(resources, OK)
    }

    @GetMapping("{id}")
    fun findById(@PathVariable id: Long): ResponseEntity<PostResource> {
        val resource = PostResource(postService.findById(id))

        return ResponseEntity(resource, OK)
    }

    @DeleteMapping("{id}")
    fun delete(@PathVariable id: Long) = ResponseEntity(postService.delete(id), NO_CONTENT)

    @GetMapping(params = ["authorId"])
    fun findByAuthorId(@RequestParam authorId: Long): ResponseEntity<List<PostResource>> {
        val resources = postService.findByAuthorId(authorId).map { PostResource(it) }

        return ResponseEntity(resources, OK)
    }

    @GetMapping(params = ["categoryId"])
    fun findByCategoryId(@RequestParam categoryId: Long): ResponseEntity<List<PostResource>> {
        val resources = postService.findByCategoryId(categoryId).map { PostResource(it) }

        return ResponseEntity(resources, OK)
    }
}