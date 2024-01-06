package org.recherche.app_allocation_gestion_part

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.recherche.app_allocation_gestion_part.ui.theme.AppallocationgestionpartTheme
import org.recherche.app_allocation_gestion_part.ui.theme.Components
import org.recherche.app_allocation_gestion_part.viewmodels.UserDetailsViewModel

class UserDetails : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        val userDetailsViewModel = UserDetailsViewModel(application)
        val id = intent.getIntExtra("id", 0)
        if (id == 0) {
            Log.d("TAG", "onCreate: There is Problem")
            this.finish()
        }
        userDetailsViewModel.getUser(id)


        super.onCreate(savedInstanceState)
        setContent {
            AppallocationgestionpartTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DetailsScreenV(userDetailsViewModel)
                }
            }
        }
    }
}

@Composable
fun Greeting6(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview6() {
    AppallocationgestionpartTheme {
    }
}








@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreenV(userDetailsViewModel: UserDetailsViewModel) {

    val scope = rememberCoroutineScope()
    val snackbarHostState = SnackbarHostState()
    val user = userDetailsViewModel.currentUser.observeAsState()
    val isUpdated = userDetailsViewModel.isUserUpdated.observeAsState()
    val messageError = userDetailsViewModel.messageError.observeAsState()
    val isBanned_viewmodel = userDetailsViewModel.isBanned.observeAsState()

    var updatable by rememberSaveable {
        mutableStateOf(false)
    }

    var isDialogShown by remember {
        mutableStateOf(false)
    }

    var newUsername by remember {
        mutableStateOf(userDetailsViewModel.current_username_to_update)
    }

    var newpoints by remember {
        mutableStateOf(userDetailsViewModel.current_points_to_update_str)
    }


    var isBanned  = userDetailsViewModel.isBanned.observeAsState()
    val context = LocalContext.current as Activity
    Scaffold (
        snackbarHost = {SnackbarHost(snackbarHostState)},
        topBar = {Components.TopAppBar_(title = "User Details") { context.finish() } }
    ){ it__ ->


        if (isUpdated.value!!) {
            context.finish()
        }


        if (isDialogShown) {
            AlertDialog(
                dismissButton = {
                                TextButton(onClick = { isDialogShown = false }) {
                                    Text(text = "cancel")
                                }
                }
                , confirmButton = { TextButton(
                onClick = { userDetailsViewModel.banneUser(); context.finish() }) {
                Text(text = "confirm")

            }}, title = { Text(
                text = "Confirmation"
            )}, text = { Text(text = "Are your Sure You wanna banne this user")}, onDismissRequest = {})
        }

        if (messageError.value!!.isNotEmpty()) {
            LaunchedEffect(Unit) {
                scope.launch {
                    snackbarHostState.showSnackbar(messageError.value!!)
                }
            }

        }




        if (user.value != null)
        Box(modifier = Modifier
            .padding(it__)
            .padding(16.dp)) {


            Column(modifier = Modifier.fillMaxWidth()) {
                Text(text = "Username", style= MaterialTheme.typography.labelSmall)
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(value = userDetailsViewModel.newUsername.value,
                    onValueChange = {userDetailsViewModel.newUsername.value = it} ,
                    colors =TextFieldDefaults.outlinedTextFieldColors( disabledBorderColor = Color.White, disabledTextColor = Color.Black), enabled = updatable)

                Spacer(modifier = Modifier.height(8.dp))

                Text(text = "email", style= MaterialTheme.typography.labelSmall)
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(value = userDetailsViewModel.currentUser.value!!.email_user, onValueChange = {} , colors =TextFieldDefaults.outlinedTextFieldColors( disabledBorderColor = Color.White, disabledTextColor = Color.Black ), enabled = false)
                if(updatable)
                    Text(text = "mail not updatable", style = MaterialTheme.typography.labelSmall)
                Spacer(modifier = Modifier.height(8.dp))




                Text(text = "points", style= MaterialTheme.typography.labelSmall)
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(value = userDetailsViewModel.newPoints.value, onValueChange = {userDetailsViewModel.newPoints.value = it}, colors =TextFieldDefaults.outlinedTextFieldColors( disabledBorderColor = Color.White, disabledTextColor = Color.Black ), enabled = updatable)


                Spacer(modifier = Modifier.height(8.dp))


                Text(text = "status", style= MaterialTheme.typography.labelSmall)
                Spacer(modifier = Modifier.height(8.dp))





                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Banned", style= MaterialTheme.typography.labelSmall, modifier = Modifier.width(100.dp))
                    Spacer(modifier = Modifier.width(16.dp))
                    isBanned.value?.let {
                        Switch(
                            checked = userDetailsViewModel.currentUser.value!!.is_banned,
                            onCheckedChange = {it_ ->
                                if (it_) {
                                    isDialogShown = true
                                }else {
                                    userDetailsViewModel.deBanneUser()
                                    context.finish()
                                }
                            }
                        )
                    }
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Activated", style= MaterialTheme.typography.labelSmall, modifier = Modifier.width(100.dp))
                    Spacer(modifier = Modifier.width(16.dp))
                    Switch(
                        checked = true,
                        onCheckedChange = {}
                    )
                }

                Text(text = "update", style= MaterialTheme.typography.labelSmall)
                Spacer(modifier = Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("updatable", style= MaterialTheme.typography.labelSmall, modifier = Modifier.width(100.dp))
                    Spacer(modifier = Modifier.width(16.dp))
                    Switch(
                        checked = updatable,
                        onCheckedChange = {updatable = !updatable}
                    )
                }



                Spacer(modifier = Modifier.height(8.dp))

                Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {

                    OutlinedButton(
                        onClick = {
                            val intent = Intent(context, AvailableCarsActivity::class.java)
                            intent.putExtra("id", userDetailsViewModel.currentUser.value!!.id_user)
                            intent.putExtra("username", userDetailsViewModel.currentUser.value!!.name_user)
                                    context.startActivity(intent)
                                  }, modifier = Modifier.width(120.dp), enabled = !(userDetailsViewModel.currentUser.value!!.is_banned)) {
                        Text(text = "Allocate")
                    }

                    if(updatable) {

                        Spacer(modifier = Modifier.width(8.dp))

                        OutlinedButton(onClick =
                        { val res = userDetailsViewModel.updateUser()
                            Log.d("TAG", "DetailsScreenV: $res")
                        }, modifier = Modifier.width(120.dp)) {
                            Text(text = "Update")
                        }
                    }

                }

            }

        }else {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }


        }

    }








