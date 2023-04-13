package kr.co.bizu.sse.connect.service.impl

import jakarta.annotation.Resource
import kr.co.bizu.sse.connect.service.ConnectService
import kr.co.bizu.sse.store.ConnectionStore
import org.springframework.stereotype.Service
import reactor.core.publisher.Sinks

@Service("sse.connect.DefaultConnectService")
class DefaultConnectService: ConnectService {

    @Resource(name = "sse.store.ConnectionStore")
    private lateinit var connectionStore: ConnectionStore

    override fun connect(id: String): Sinks.Many<Any> {
        // TODO id 유효성 체크 또는 유저별 커넥션 아이디 처리 등등. 현재는 id로 통일 하여 사용
        return connectionStore.getConnection(id)
    }


}