package br.com.ms.blog.models

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType.IDENTITY
import javax.persistence.Id

@Entity
class Category(
        var name: String
) {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    val id = 0L

    fun update(category: Category) {
        name = category.name
    }
}