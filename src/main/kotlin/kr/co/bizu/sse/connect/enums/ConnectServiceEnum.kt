package kr.co.bizu.sse.connect.enums

import kr.co.bizu.sse.connect.service.impl.DefaultConnectService

enum class ConnectServiceEnum(
    val javaType: Class<*>
) {
    STANDALONE(DefaultConnectService::class.java),
    DEFAULT(STANDALONE.javaType),
}