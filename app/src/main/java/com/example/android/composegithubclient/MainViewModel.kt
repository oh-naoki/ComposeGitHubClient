package com.example.android.composegithubclient

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo3.ApolloClient
import com.example.UserSearchQuery
import com.example.UserSearchQuery.Data.Search.Node.Companion.user
import com.example.fragment.User
import kotlinx.coroutines.launch

class MainViewModel(
    private val apolloClient: ApolloClient
) : ViewModel() {

    val users = mutableStateOf<List<User>?>(emptyList())
    val searchText = mutableStateOf("")
    val errors = mutableStateOf(false)

    init {
        fetchUsers()
    }

    fun searchUser(userName: String) {
        searchText.value = userName
        fetchUsers(userName = userName)
    }

    private fun fetchUsers(userName: String = "oh-naoki") {
        viewModelScope.launch {
            val result = apolloClient.query(UserSearchQuery(userName = userName))

            users.value = result.data?.search?.nodes?.mapNotNull { it?.user() }
            errors.value = result.hasErrors()
        }
    }
}