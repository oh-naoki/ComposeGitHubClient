package com.example.android.composegithubclient

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.example.UserSearchQuery
import com.example.android.composegithubclient.ui.theme.ComposeGitHubClientTheme
import com.example.fragment.User
import org.koin.androidx.viewmodel.ext.android.getViewModel

class MainActivity : ComponentActivity() {
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeGitHubClientTheme {
                val viewModel = getViewModel<MainViewModel>()

                MainScreen(
                    users = viewModel.users.value,
                    searchText = viewModel.searchText.value,
                    onSearchTextChanged = viewModel::searchUser
                )
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
private fun MainScreen(
    users: List<User>?,
    searchText: String,
    onSearchTextChanged: (String) -> Unit
) {
    Column {
        UserSearchInput(
            text = searchText,
            onTextChanged = onSearchTextChanged
        )
        users?.let {
            UserList(users = it)
        }
    }
}

@Composable
private fun UserSearchInput(
    text: String,
    onTextChanged: (String) -> Unit
) {
    TextField(
        value = text,
        onValueChange = onTextChanged
    )
}

@ExperimentalMaterialApi
@Composable
private fun UserList(
    users: List<User>
) {
    LazyRow {
        items(users) { user ->
            UserRow(user = user)
        }
    }
}

@ExperimentalMaterialApi
@ExperimentalCoilApi
@Composable
private fun UserRow(
    user: User
) {
    Card(
        shape = RoundedCornerShape(4.dp),
        elevation = 2.dp,
        modifier = Modifier
            .padding(8.dp)
            .width(160.dp)
            .height(240.dp),
        onClick = {}
    ) {
        Column {
            Image(
                painter = rememberImagePainter(
                    data = user.avatarUrl,
                    builder = {
                        transformations(CircleCropTransformation())
                    }
                ),
                contentDescription = null,
                modifier = Modifier
                    .padding(8.dp)
                    .size(128.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = user.name.orEmpty(),
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.subtitle1
            )
            Text(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .align(Alignment.CenterHorizontally),
                text = user.bio.orEmpty(),
                style = MaterialTheme.typography.subtitle2
            )
        }
    }
}

@ExperimentalMaterialApi
@Preview
@Composable
fun PreviewUserRow() {
    val user = UserSearchQuery.Data.Search.UserNode(
        __typename = "User",
        name = "oh-naoki",
        avatarUrl = "https://avatars.githubusercontent.com/u/8602846?v=4",
        bio = "Kotlin/Android",
        bioHTML = ""
    )
    UserRow(user = user)
}