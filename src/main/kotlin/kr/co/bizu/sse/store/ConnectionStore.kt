package kr.co.bizu.sse.store

import org.springframework.stereotype.Component
import reactor.core.publisher.Sinks

@Component("sse.store.ConnectionStore")
class ConnectionStore {

    private val connections: MutableMap<String, Sinks.Many<Any>> = mutableMapOf()

    private fun createConnection(): Sinks.Many<Any> = Sinks.many().multicast().directAllOrNothing()

    /**
     * 해당 ID로 커넥션 정보를 가져온다.
     *
     * 커넥션 정보가 없으면 새로운 커넥션을 생성해서 반환한다.
     */
    fun getConnection(connectionId: String): Sinks.Many<Any> {
        return connections.computeIfAbsent(connectionId) { createConnection() }
    }

    /**
     * Store 에서 해당 아이디의 커넥션 정보를 삭제한다.
     */
    fun delConnection(connectionId: String) {
        connections.remove(connectionId)
    }

    /**
     * 모든 커넥션 정보를 반환한다.
     */
    fun getConnections(): MutableMap<String, Sinks.Many<Any>> {
        return connections
    }
}