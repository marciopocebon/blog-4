package br.com.ms.blog.resources

import br.com.ms.blog.controllers.CategoryController
import br.com.ms.blog.controllers.PostController
import br.com.ms.blog.models.Category
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.data.domain.Pageable.unpaged
import org.springframework.hateoas.ResourceSupport
import org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo
import org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn

class CategoryResource(
        category: Category
) : ResourceSupport(){

    @JsonProperty("id")
    val identification = category.id
    val name = category.name

    init {
        add(categoryLinks(identification))
    }

    private fun categoryLinks(id: Long) = listOf(
            linkTo(methodOn(CategoryController::class.java).findById(id)).withSelfRel().withType("GET"),
            linkTo(methodOn(CategoryController::class.java).delete(id)).withSelfRel().withType("DELETE"),
            linkTo(methodOn(CategoryController::class.java).findAll()).withRel("categories").withType("GET"),
            linkTo(methodOn(PostController::class.java).findByCategoryId(id, unpaged())).withRel("posts").withType("GET")
    )
}
