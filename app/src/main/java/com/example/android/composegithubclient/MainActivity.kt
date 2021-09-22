package com.example.android.composegithubclient

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.rememberCoroutineScope
import com.apollographql.apollo3.ApolloClient
import com.example.UserSearchQuery
import com.example.android.composegithubclient.ui.theme.ComposeGitHubClientTheme
import kotlinx.coroutines.launch
import org.koin.androidx.compose.get

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeGitHubClientTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    val apolloClient = get<ApolloClient>()
                    val scope = rememberCoroutineScope()

                    Button(onClick = {
                        scope.launch {
                            val result = apolloClient.query(UserSearchQuery())
                            Log.d("hogehoge", result.data?.search?.nodes?.first().toString())
                        }
                    }) {
                        Text(text = "Button")
                    }

                }
            }
        }
    }
}