package com.fk.codesample.network

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AuthorizationHeaderInterceptor(private val apiKey: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder: Request.Builder = chain
            .request()
            .newBuilder()
            .addAuthorizationHeader()
        return chain.proceed(requestBuilder.build())
    }

    private fun Request.Builder.addAuthorizationHeader(): Request.Builder =
        addHeader(AUTHORIZATION_HEADER_NAME, apiKey)


    companion object {
        const val AUTHORIZATION_HEADER_NAME = "x-api-key"
    }
}