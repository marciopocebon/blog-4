package br.com.ms.blog

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker

@SpringBootApplication
@EnableCircuitBreaker
class BlogApplication

fun main(args: Array<String>) {
	runApplication<BlogApplication>(*args)
}

