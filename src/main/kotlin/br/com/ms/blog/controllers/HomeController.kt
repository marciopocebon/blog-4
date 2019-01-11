package br.com.ms.blog.controllers

import org.springframework.hateoas.Link
import org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo
import org.springframework.http.HttpMethod.GET
import org.springframework.http.HttpStatus.OK
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping

@RestController
@RequestMapping("/")
class HomeController(
        private val requestMappingHandlerMapping: RequestMappingHandlerMapping
) {

    @GetMapping
    fun home(): ResponseEntity<List<Link>> {
        val (baseUrl, links) = getResourceLinks()
        links.add(Link("$baseUrl/actuator/health").withRel("health").withType("GET"))
        return ResponseEntity(links, OK)
    }

    private fun getResourceLinks() = requestMappingHandlerMapping.handlerMethods
            .filter { it.value.toString().endsWith("findAll()") }
            .map {
                val uriComponent = linkTo(it.value.method).toUriComponentsBuilder().build()
                linkTo(it.value.method).withRel(uriComponent.pathSegments[0]).withType("GET")
            }
            .let {
                val firstResource = it[0]
                val href = firstResource.href.replace("/${firstResource.rel}", "")
                href to it.toMutableList()
            }

}