package org.recherche.app_allocation_gestion_part

import android.app.Activity
import android.os.Build
import android.os.Bundle
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import org.recherche.app_allocation_gestion_part.ui.theme.AppallocationgestionpartTheme
import org.recherche.app_allocation_gestion_part.ui.theme.Components
import org.recherche.app_allocation_gestion_part.ui.theme.primaryColor
import org.recherche.app_allocation_gestion_part.viewmodels.AddUserViewModel

class AddUserActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = AddUserViewModel(application)
        setContent {
            AppallocationgestionpartTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    addUserScreen(viewModel)
                }
            }
        }
    }
}

@Composable
fun Greeting5(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview5() {

    AppallocationgestionpartTheme {
        //addUserScreen(viewModel)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun addUserScreen(viewModel: AddUserViewModel) {

    val context = LocalContext.current as Activity
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }


 var username by rememberSaveable {
    mutableStateOf("")
 }

    var email by rememberSaveable {
        mutableStateOf("")
    }

    var password by rememberSaveable {
        mutableStateOf("")
    }

    var password_confirmation by rememberSaveable {
        mutableStateOf("")
    }
    val error = viewModel.error.observeAsState()
    val added = viewModel.userAdded.observeAsState()
    Scaffold(snackbarHost = { SnackbarHost(snackbarHostState) }, topBar = {
        Components.TopAppBar_(
        title = "Add User"
    ) {context.finish()}
    }) { paddingValues ->

        if (error.value!!.isNotEmpty()) {
            LaunchedEffect(Unit) {
               scope.launch {
                   snackbarHostState.showSnackbar(error.value!!)
                   viewModel.error.value = ""
               }
            }
        }

        if(added.value!!) {
            context.finish()
        }

        Box(modifier = Modifier
            .padding(paddingValues)
            .padding(16.dp)
            .fillMaxSize(), contentAlignment = Alignment.Center){

            Column(Modifier.fillMaxWidth()) {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
                    Image(painter = painterResource(id = R.drawable.man), contentDescription = "users image", modifier = Modifier.size(80.dp))
                }

                Spacer(modifier =Modifier.height(16.dp) )
                Text(text = "USER INFO", textAlign = TextAlign.Center , style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = primaryColor, modifier = Modifier.fillMaxWidth())
                Spacer(modifier =Modifier.height(16.dp) )
                OutlinedTextField(value =username , onValueChange = {username = it; viewModel.userName = it }, placeholder = { Text(
                    text = "username"
                )}, modifier = Modifier.fillMaxWidth())

                Spacer(modifier =Modifier.height(16.dp) )
                OutlinedTextField(value =email , onValueChange = {email = it; viewModel.email = it }, placeholder = { Text(
                    text = "email"
                )}, modifier = Modifier.fillMaxWidth())

                Spacer(modifier =Modifier.height(16.dp) )
                OutlinedTextField(value =password , onValueChange = {password = it; viewModel.password = it }, placeholder = { Text(
                    text = "passworld"
                )}, modifier = Modifier.fillMaxWidth(),visualTransformation = PasswordVisualTransformation())
                Spacer(modifier =Modifier.height(16.dp) )
                OutlinedTextField(value =password_confirmation , onValueChange = {password_confirmation = it; viewModel.passwordConfirmation= it}, placeholder = { Text(
                    text = "confirmation"
                )}, modifier = Modifier.fillMaxWidth() , visualTransformation = PasswordVisualTransformation())
                Spacer(modifier =Modifier.height(16.dp) )

                Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
                    OutlinedButton(
                        onClick = {
                                  val res = viewModel.addUser()
                            if (res < 0) {
                                val message = if(res == -1) "Username Invalid" else if(res == -2) "Email Invalid" else if(res == -3) "Password Invalid" else "Confiramtion Error"
                                scope.launch {
                                    snackbarHostState.showSnackbar("Error $message")
                                }
                            }
                    }, Modifier.width(100.dp)) {
                        Text(text = "SUBMIT")
                    }

                    Spacer(modifier =Modifier.width(4.dp) )

                        OutlinedButton(onClick = { }, Modifier.width(100.dp)) {
                            Text(text = "RESET")
                        }
                }


            }

        }


    }

}