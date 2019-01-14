package br.com.ms.blog.models

import br.com.ms.blog.utils.AUTHOR_DESCRIPTION_BLANK
import br.com.ms.blog.utils.AUTHOR_NAME_BLANK
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType.IDENTITY
import javax.persistence.Id
import javax.validation.constraints.NotBlank

@Entity
data class Author(
        @field:NotBlank(message = AUTHOR_NAME_BLANK)
        var name: String,
        @field:NotBlank(message = AUTHOR_DESCRIPTION_BLANK)
        var description: String
) {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    val id: Long = 0L

    fun update(author: Author) {
        name = author.name
        description = author.description
    }
}