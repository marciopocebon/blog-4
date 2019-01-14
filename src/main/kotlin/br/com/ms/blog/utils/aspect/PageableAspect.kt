package br.com.ms.blog.utils.aspect

import br.com.ms.blog.utils.annotations.PageableMethod
import br.com.ms.blog.utils.page.PageLinks
import br.com.ms.blog.utils.page.PageResponse
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.data.domain.Page
import org.springframework.hateoas.Link
import org.springframework.http.HttpStatus.OK
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import java.lang.reflect.Method

@Aspect
@Component
class PageableAspect {

    @Around("execution(* br.com.ms.yellowchallenge.controllers.*.*(..))")
    fun aroundMonitorMethods(point: ProceedingJoinPoint): Any? {
        val entity = point.proceed() as ResponseEntity<*>
        val method = (point.signature as MethodSignature).method
        return if (method.isAnnotationPresent(PageableMethod::class.java))
            buildPageableResponse(entity, method)
        else
            build(entity.body)

    }

    private fun buildPageableResponse(entity: ResponseEntity<*>, method: Method): ResponseEntity<Map<String, Any?>> {
        val page = entity.body as Page<*>
        val pageResponse = PageResponse(page.size, page.totalElements, page.totalPages, page.number)
        return build(page.content, PageLinks(page, method).buildLinks(), pageResponse)
    }

    fun build(content: Any?, links: List<Link>? = null, page: PageResponse? = null) =
            ResponseEntity(mapOf(
                    "_embedded" to content,
                    "_links" to links,
                    "page" to page
            ), OK)
}