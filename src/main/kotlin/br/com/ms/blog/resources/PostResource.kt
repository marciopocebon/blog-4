package br.com.ms.blog.resources

import br.com.ms.blog.controllers.PostController
import br.com.ms.blog.models.Post
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import org.springframework.data.domain.Pageable.unpaged
import org.springframework.hateoas.ResourceSupport
import org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo
import org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn

@JsonPropertyOrder("id", "title", "views", "content", "author", "categories")
class PostResource(
        post: Post
): ResourceSupport(){

    @JsonProperty("id")
    val identification = post.id
    val title = post.title
    val content = post.content
    val views = post.views
    val author = AuthorResource(post.author)
    val categories = post.categories.map { CategoryResource(it) }

    init {
        add(postLinks(identification))
    }

     private fun postLinks(id: Long) = listOf(
            linkTo(methodOn(PostController::class.java).findById(id)).withSelfRel().withType("GET"),
            linkTo(methodOn(PostController::class.java).findAll()).withRel("posts").withType("GET"),
            linkTo(methodOn(PostController::class.java).delete(id)).withSelfRel().withType("GET")
    )
}
