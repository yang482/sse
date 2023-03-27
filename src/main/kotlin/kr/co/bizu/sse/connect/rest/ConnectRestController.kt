package kr.co.bizu.sse.connect.rest

import jakarta.annotation.Resource
import kr.co.bizu.sse.connect.service.ConnectService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux

@RestController
@RequestMapping("connect")
class ConnectRestController {

    @Resource(name = "sse.connect.ConnectService")
    private lateinit var connectService: ConnectService

    @GetMapping("/{id}")
    fun connect(
        @PathVariable id: String
    ): Flux<Any> {
        return Flux.merge(Flux.just("OK"), connectService.connect(id).asFlux())
    }


}