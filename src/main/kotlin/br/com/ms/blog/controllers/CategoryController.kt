package br.com.ms.blog.controllers

import br.com.ms.blog.models.Category
import br.com.ms.blog.resources.CategoryResource
import br.com.ms.blog.services.CategoryService
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/categories")
class CategoryController(
        private val categoryService: CategoryService
) {

    @PostMapping
    fun save(@Valid @RequestBody category: Category): ResponseEntity<CategoryResource> {
        val resource = CategoryResource(categoryService.save(category))

        return ResponseEntity(resource, CREATED)
    }

    @PutMapping("{id}")
    fun update(@PathVariable id: Long, @Valid @RequestBody category: Category): ResponseEntity<CategoryResource> {
        val resource = CategoryResource(categoryService.update(id, category))

        return ResponseEntity(resource, OK)
    }

    @GetMapping
    fun findAll(): ResponseEntity<List<CategoryResource>> {
        val resources = categoryService.findAll().map { CategoryResource(it) }

        return ResponseEntity(resources, OK)
    }

    @GetMapping("{id}")
    fun findById(@PathVariable id: Long): ResponseEntity<CategoryResource> {
        val resource = CategoryResource(categoryService.findById(id))

        return ResponseEntity(resource, OK)
    }

    @DeleteMapping("{id}")
    fun delete(@PathVariable id: Long) = ResponseEntity(categoryService.delete(id), NO_CONTENT)
}