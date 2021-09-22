package com.example.android.composegithubclient.di

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.network.http.BearerTokenInterceptor
import com.apollographql.apollo3.network.http.HttpNetworkTransport
import com.apollographql.apollo3.network.http.TokenProvider
import org.koin.dsl.module

val networkModule = module {
    single {
        ApolloClient(
            networkTransport = HttpNetworkTransport(
                serverUrl = "https://api.github.com/graphql",
                interceptors = listOf(
                    BearerTokenInterceptor(
                        tokenProvider = object : TokenProvider {
                            override suspend fun currentToken(): String {
                                return "Enter your token"
                            }

                            override suspend fun refreshToken(previousToken: String): String {
                                return "Enter you token"
                            }
                        }
                    )
                )
            )
        )
    }
}