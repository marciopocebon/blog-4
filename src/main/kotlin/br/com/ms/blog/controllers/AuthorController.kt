package br.com.ms.blog.controllers

import br.com.ms.blog.annotations.PageableMethod
import br.com.ms.blog.models.Author
import br.com.ms.blog.resources.AuthorResource
import br.com.ms.blog.services.ActorService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/actors")
class AuthorController(
        private val actorService: ActorService
) {

    @PostMapping
    fun save(@Valid @RequestBody author: Author): ResponseEntity<AuthorResource> {
        val resource = AuthorResource(actorService.save(author))

        return ResponseEntity(resource, CREATED)
    }

    @PutMapping("{id}")
    fun update(@PathVariable id: Long, @Valid @RequestBody author: Author): ResponseEntity<AuthorResource> {
        val resource = AuthorResource(actorService.update(id, author))

        return ResponseEntity(resource, OK)
    }

    @GetMapping
    @PageableMethod
    fun findAll(pageable: Pageable): ResponseEntity<Page<AuthorResource>> {
        val pageableResource = actorService.findAll(pageable).map { AuthorResource(it) }

       return ResponseEntity(pageableResource, OK)
    }

    @GetMapping("{id}")
    fun findById(@PathVariable id: Long): ResponseEntity<AuthorResource> {
        val resource = AuthorResource(actorService.findById(id))

        return ResponseEntity(resource, OK)
    }

    @DeleteMapping("{id}")
    fun delete(@PathVariable id: Long) = ResponseEntity(actorService.delete(id), NO_CONTENT)
}