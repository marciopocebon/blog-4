package br.com.ms.blog.controllers

import br.com.ms.blog.utils.annotations.PageableMethod
import br.com.ms.blog.requests.PostRequest
import br.com.ms.blog.resources.PostResource
import br.com.ms.blog.services.PostService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
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

    @PageableMethod
    @GetMapping
    fun findAll(pageable: Pageable): ResponseEntity<Page<PostResource>> {
        val pageableResource = postService.findAll(pageable).map { PostResource(it) }

        return ResponseEntity(pageableResource, OK)
    }

    @GetMapping("{id}")
    fun findById(@PathVariable id: Long): ResponseEntity<PostResource> {
        val resource = PostResource(postService.findById(id))

        return ResponseEntity(resource, OK)
    }

    @DeleteMapping("{id}")
    fun delete(@PathVariable id: Long) = ResponseEntity(postService.delete(id), NO_CONTENT)

    @PageableMethod
    @GetMapping(params = ["authorId"])
    fun findByActorId(@RequestParam actorId: Long, pageable: Pageable): ResponseEntity<Page<PostResource>> {
        val pageableResource = postService.findByAuthorId(actorId, pageable).map { PostResource(it) }

        return ResponseEntity(pageableResource, OK)
    }

    @PageableMethod
    @GetMapping(params = ["categoryId"])
    fun findByCategoryId(@RequestParam categoryId: Long, pageable: Pageable): ResponseEntity<Page<PostResource>> {
        val pageableResource = postService.findByCategoryId(categoryId, pageable).map { PostResource(it) }

        return ResponseEntity(pageableResource, OK)
    }
}