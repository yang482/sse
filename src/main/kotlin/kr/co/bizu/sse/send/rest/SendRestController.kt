package kr.co.bizu.sse.send.rest

import jakarta.annotation.Resource
import kr.co.bizu.sse.store.ConnectionStore
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import reactor.core.publisher.Sinks

@RestController
@RequestMapping("/send")
class SendRestController {

    @Resource(name = "sse.store.ConnectionStore")
    private lateinit var connectionStore: ConnectionStore

    @PostMapping("")
    fun sendAll(): Mono<String> {
        val con: Map<String, Sinks.Many<Any>> = connectionStore.getConnections()

        con.entries.stream().forEach {
            it.value.tryEmitNext(mapOf(
                "to" to it.key,
                "message" to "Send message to all"
            ))
        }

        return Mono.just("OK")
    }

    @PostMapping("/{id}")
    fun send(
        @PathVariable id: String
    ): Mono<String> {
        val con: Sinks.Many<Any> = connectionStore.getConnection(id)

        val result: Sinks.EmitResult = con.tryEmitNext(mapOf(
            "to" to id,
            "message" to "Send message to $id"
        ))

        if(result.isFailure) {
            return Mono.just("FAIL " + result.ordinal);
        }

        return Mono.just("OK " + result.ordinal)
    }
}