package kr.co.bizu.sse.common.exception

import kr.co.bizu.sse.config.web.advice.RestControllerAdvisor

open class ServiceException(
    val code: String = "E",
    override var message: String = RestControllerAdvisor.DEFAULT_EXCEPTION_MESSAGE
) : RuntimeException() {

    init {}
}