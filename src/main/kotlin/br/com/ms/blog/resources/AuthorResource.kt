package br.com.ms.blog.resources

import br.com.ms.blog.controllers.AuthorController
import br.com.ms.blog.controllers.PostController
import br.com.ms.blog.models.Author
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import org.springframework.hateoas.ResourceSupport
import org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo
import org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn

@JsonPropertyOrder("id", "name", "description")
class AuthorResource(
         author: Author
) : ResourceSupport() {

    @JsonProperty("id")
    val identification = author.id
    val name = author.name
    val description = author.description

    init {
        add(authorLinks(identification))
    }

    private fun authorLinks(id: Long) = listOf(
            linkTo(methodOn(AuthorController::class.java).findById(id)).withSelfRel().withType("GET"),
            linkTo(methodOn(AuthorController::class.java).delete(id)).withSelfRel().withType("DELETE"),
            linkTo(methodOn(AuthorController::class.java).findAll()).withRel("authors").withType("GET"),
            linkTo(methodOn(PostController::class.java).findByAuthorId(id)).withRel("posts").withType("GET")
    )
}