package br.com.ms.blog.resources

import org.springframework.hateoas.Link
import org.springframework.hateoas.ResourceSupport
import org.springframework.web.servlet.mvc.method.RequestMappingInfo

class ResourceComposer(
        baseUrl: StringBuffer,
        requestMappingInfo: Set<RequestMappingInfo>
) : ResourceSupport() {

    init {
        add(requestMappingInfo.map { createResourceLink(baseUrl, it) })
    }

    private fun createResourceLink(baseUrl: StringBuffer, info: RequestMappingInfo) = getResourceName(info)
            .let { Link("$baseUrl$it", it) }

    private fun getResourceName(info: RequestMappingInfo) =
            info.patternsCondition.patterns.elementAt(0).replace(nonLetters(), "")

    private fun nonLetters() = "[^A-Za-z]".toRegex()
}
