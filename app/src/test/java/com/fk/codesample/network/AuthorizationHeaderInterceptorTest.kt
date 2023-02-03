package com.fk.codesample.network

import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import junit.framework.Assert.assertEquals
import okhttp3.Interceptor.Chain
import okhttp3.Request
import okhttp3.Response
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class AuthorizationHeaderInterceptorTest {

    @Test
    fun `request has expected header added`() {
        val request = Request.Builder().url("https://google.com")
            .build()

        val response = mockk<Response>()
        val chain = mockk<Chain>()
        val slot = slot<Request>()

        every { chain.request() } returns request

        every { chain.proceed(capture(slot)) } returns response

        val headerInterceptor = AuthorizationHeaderInterceptor("thisIsApiKey")

        headerInterceptor.intercept(chain)
        assertEquals("thisIsApiKey", slot.captured.headers()["x-api-key"])
    }
}