package kr.co.bizu.sse.common.log

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.lang.invoke.MethodHandles


object InlineLogger {

    inline fun getLogger(): Logger {
        return LoggerFactory.getLogger(MethodHandles.lookup().lookupClass())
    }
}