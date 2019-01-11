package br.com.ms.blog.utils.page

import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.hateoas.Link
import org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo
import org.springframework.http.HttpMethod.GET
import java.lang.reflect.Method

class PageLinks(
        private val page: Page<*>,
        method: Method
) {
    private val builder = linkTo(method).toUriComponentsBuilder()

    fun buildLinks(): List<Link>? {
        val links = mutableListOf<Link>()
        if (!page.isFirst) {
            links.add(createLink(page.pageable.first(), "first"))
        }
        if (page.hasPrevious() && page.previousPageable().pageNumber != 0) {
            links.add(createLink(page.previousPageable(), "previous"))
        }
        if (page.hasNext() && page.nextPageable().pageNumber != page.totalPages - 1) {
            links.add(createLink(page.nextPageable(), "next"))
        }
        if (!page.isLast) {
            links.add(createLink(PageRequest.of(page.totalPages - 1, page.size), "last"))
        }
        return if (links.isNotEmpty()) links else null
    }

    private fun createLink(pageable: Pageable, rel: String): Link {
        builder.replaceQueryParam("page", pageable.pageNumber)
        builder.replaceQueryParam("size", pageable.pageSize)
        return Link(builder.toUriString(), rel).withType(GET.name)
    }
}