package kr.co.bizu.sse.connect.service

import reactor.core.publisher.Sinks

interface ConnectService {

    fun connect(id: String): Sinks.Many<Any>
}