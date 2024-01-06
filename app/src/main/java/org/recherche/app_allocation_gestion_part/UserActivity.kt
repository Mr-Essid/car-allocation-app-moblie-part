package org.recherche.app_allocation_gestion_part

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.recherche.app_allocation_gestion_part.models.UserResponse
import org.recherche.app_allocation_gestion_part.ui.theme.Components
import org.recherche.app_allocation_gestion_part.ui.theme.primaryColor
import org.recherche.app_allocation_gestion_part.viewmodels.UserViewModel
import org.recherche.app_allocation_gestion_part.viewmodels.ui.theme.AppallocationgestionpartTheme

class UserActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {

        val viewModel = UserViewModel(application)

        if(checkInternetConnection(this))
            viewModel.getAllUsers()
        else
            viewModel.getUsersFromLocal()
        super.onCreate(savedInstanceState)
        setContent {
            AppallocationgestionpartTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    UserScreen(viewModel)
                }
            }
        }
    }
}

@Composable
fun Greeting4(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview4() {
    AppallocationgestionpartTheme {
        val user = UserResponse(
            created_at = "10/3/2000",
            id_user = 1,
            email_user = "essid",
            ip_user = "localhost",
            name_user = "Amine Essid",
            password_user = "pass",
            is_active = true,
            is_banned = false,
            points = 100,
            updated_at = "10/2/200"

        )


        Box{
            UserItem(userResponse = user)
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserScreen(viewModel: UserViewModel) {
    val userListAsState = viewModel.mutableLiveDataUsers.observeAsState()
    val activity = LocalContext.current as Activity

    Scaffold(
        topBar = {Components.TopAppBar_(title = "Users") { activity.finish() }},
        floatingActionButton = {

            FloatingActionButton(onClick = {  activity.startActivity(Intent(activity, AddUserActivity::class.java)); activity.finish() }) {
            Icon(Icons.Default.Add, contentDescription = "add items")
        }
        }

    ) { paddingValues ->
        if (userListAsState.value != null)
        LazyColumn(
            Modifier
                .padding(paddingValues)
                .padding(16.dp)) {

                items(userListAsState.value!!.size) {
                    Spacer(modifier = Modifier.height(8.dp))
                    UserItem(userResponse = userListAsState.value!![it])
                }
        }
        else
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
    }


}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserItem(userResponse: UserResponse) {
    val active = "Active"
    val context = LocalContext.current as Activity
    val disActive  = "Not Active"
    OutlinedCard(onClick = {
        Log.d("TAG", "UserItem: user clicked ")
                           val intent = Intent(context, UserDetails::class.java)
                            intent.putExtra("id", userResponse.id_user)
                            context.startActivity(intent)
                            context.finish()
    }, modifier = Modifier.fillMaxWidth()) {
        Row (modifier = Modifier.padding(16.dp)){
            Column {
                Text(text = userResponse.name_user)
                Text(text = "state: ${if (userResponse.is_active) active  else disActive}" , style = MaterialTheme.typography.bodySmall, color = if(userResponse.is_active) Color.Green else Color.Red, fontWeight = FontWeight.Bold  )
            }
            Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.End) {
                Icon(painter = painterResource(id = R.drawable.baseline_arrow_forward_24), contentDescription = "Details")
            }
        }
    }
}


