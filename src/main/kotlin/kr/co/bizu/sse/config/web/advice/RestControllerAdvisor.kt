package kr.co.bizu.sse.config.web.advice

import kr.co.bizu.sse.common.exception.ServiceException
import kr.co.bizu.sse.common.log.InlineLogger.getLogger
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.server.ServerWebInputException

@RestControllerAdvice
class RestControllerAdvisor {

    companion object {
        val logger = getLogger()

        const val DEFAULT_EXCEPTION_CODE = "E"
        const val DEFAULT_EXCEPTION_MESSAGE = "ERROR"
    }

    @ExceptionHandler(ServiceException::class)
    suspend fun serviceExceptionHandler(e: ServiceException): ResponseEntity<Map<String, Any>> {

        logger.error("", e)

        return ResponseEntity.ok().body(
            mapOf("code" to e.code.ifBlank { DEFAULT_EXCEPTION_CODE }, "msg" to e.message)
        )
    }

    @ExceptionHandler(ServerWebInputException::class)
    suspend fun serverWebInputExceptionHandler(e: ServerWebInputException): ResponseEntity<Map<String, Any>> {
        return ResponseEntity.ok().body(
            mapOf("code" to DEFAULT_EXCEPTION_CODE, "msg" to "BAD_REQUEST")
        )
    }
    @ExceptionHandler(Exception::class)
    suspend fun exceptionHandler(e: Exception): ResponseEntity<Map<String, Any>> {
        return ResponseEntity.ok().body(
            mapOf("code" to DEFAULT_EXCEPTION_CODE, "msg" to DEFAULT_EXCEPTION_MESSAGE)
        )
    }
}