package kr.co.bizu.sse.connect.rest

import jakarta.annotation.PostConstruct
import kr.co.bizu.sse.config.web.provider.ApplicationContextProvider
import kr.co.bizu.sse.connect.enums.ConnectServiceEnum
import kr.co.bizu.sse.connect.service.ConnectService
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux

@RestController
@RequestMapping("connect")
class ConnectRestController {

    // @Resource(name = "sse.connect.DefaultConnectService")
    private lateinit var connectService: ConnectService

    @Value("\${sse.mode:standalone}")
    private lateinit var mode: String

    @PostConstruct
    fun init() {
        connectService = ApplicationContextProvider.applicationContext
            ?.getBean(ConnectServiceEnum.valueOf(mode.uppercase()).javaType) as ConnectService
    }

    @GetMapping("/{id}", produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun connect(
        @PathVariable id: String
    ): Flux<Any> {
        return Flux.merge(Flux.just("OK"), connectService.connect(id).asFlux())
    }


}