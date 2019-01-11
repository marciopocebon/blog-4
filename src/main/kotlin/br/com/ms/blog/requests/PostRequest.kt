package br.com.ms.blog.requests

import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

class PostRequest(
        @NotBlank(message = "{post.title.blank}")
        val title: String,
        @NotBlank(message = "{post.content.blank}")
        val content: String,
        @NotNull(message = "{post.author.id.null}")
        val actorId: Long,
        @NotNull(message = "{post.categories.id.null}")
        val categoriesId: List<Long>
)