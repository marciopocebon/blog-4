package br.com.ms.blog.utils.page

class PageResponse(
        val size: Int,
        val totalElements: Long,
        val totalPages: Int,
        val number: Int
)