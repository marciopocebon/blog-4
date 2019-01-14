package br.com.ms.blog.requests

import br.com.ms.blog.utils.POST_AUTHOR_ID_NULL
import br.com.ms.blog.utils.POST_CATEGORIES_IDS_NULL
import br.com.ms.blog.utils.POST_CONTENT_BLANK
import br.com.ms.blog.utils.POST_TITLE_BLANK
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

class PostRequest(
        @field:NotBlank(message = POST_TITLE_BLANK)
        val title: String,
        @field:NotBlank(message = POST_CONTENT_BLANK)
        val content: String,
        @field:NotNull(message = POST_AUTHOR_ID_NULL)
        val authorId: Long,
        @field:NotNull(message = POST_CATEGORIES_IDS_NULL)
        val categoriesId: List<Long>
)