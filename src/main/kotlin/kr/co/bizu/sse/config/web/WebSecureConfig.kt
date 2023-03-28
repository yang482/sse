package kr.co.bizu.sse.config.web

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.config.CorsRegistry
import org.springframework.web.reactive.config.WebFluxConfigurer

@Configuration
@ConfigurationProperties(prefix = "sse.config.web.secure")
class WebSecureConfig: WebFluxConfigurer {

    var allowedOrigins: Array<String> = arrayOf("*")
    var allowedHeaders: Array<String> = arrayOf("*")
    var allowedMethods: Array<String> = arrayOf("*")
    var exposedHeaders: Array<String> = arrayOf()

    override fun addCorsMappings(registry: CorsRegistry) {

        // CORS 관련 하여 이곳에서 설정한다. ex) allows host 등
        registry.addMapping("/**")
            .allowedOrigins(*allowedOrigins)
            .allowedHeaders(*allowedHeaders)
            .allowedMethods(*allowedMethods)
            .exposedHeaders(*exposedHeaders)
            .maxAge(3600L)
    }
}