package br.com.ms.blog.models

import javax.persistence.*
import javax.persistence.FetchType.*
import javax.persistence.GenerationType.IDENTITY

@Entity
data class Post(
        var title: String,
        var content: String,
        @ManyToOne
        var author: Author,
        @ManyToMany(fetch = EAGER)
        var categories: MutableList<Category>
) {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    val id = 0L
    var views = 0

    fun update(post: Post) {
        title = post.title
        content = post.content
        author = post.author
        categories = post.categories
    }
}
