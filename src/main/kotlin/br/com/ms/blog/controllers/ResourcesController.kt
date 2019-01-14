package br.com.ms.blog.controllers

import br.com.ms.blog.resources.ResourceComposer
import br.com.ms.blog.services.ResourcesService
import org.springframework.http.HttpStatus.OK
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.context.request.RequestContextHolder.*
import org.springframework.web.context.request.ServletRequestAttributes

@RestController
@RequestMapping("/")
class ResourcesController(
  private val resourcesService: ResourcesService
) {

    @GetMapping
    fun home(): ResponseEntity<ResourceComposer> {
        val baseUrl = (currentRequestAttributes() as ServletRequestAttributes).request.requestURL

        return ResponseEntity(ResourceComposer(baseUrl, resourcesService.getResourcesInfo()), OK)
    }
}