package br.com.ms.blog.models

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType.IDENTITY
import javax.persistence.Id
import javax.validation.constraints.NotBlank

@Entity
class Author(
        @NotBlank(message = "{author.name.blank}")
        var name: String,
        @NotBlank(message = "{author.description.blank}")
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