package br.com.ms.blog.models

import br.com.ms.blog.utils.CATEGORY_NAME_BLANK
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType.IDENTITY
import javax.persistence.Id
import javax.validation.constraints.NotBlank

@Entity
data class Category(
        @field:NotBlank(message = CATEGORY_NAME_BLANK)
        var name: String
) {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    val id = 0L

    fun update(category: Category) {
        name = category.name
    }
}