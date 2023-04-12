package kr.co.bizu.sse.token.service

import jakarta.annotation.Resource
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class TokenServiceTest {

    @Resource(name = "sse.token.TokenService")
    lateinit var tokenService: TokenService

    @Test
    fun findAll() {
        tokenService.save()
        tokenService.findAll().subscribe{ println(it) }
    }
}