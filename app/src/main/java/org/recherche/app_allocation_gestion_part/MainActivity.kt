package org.recherche.app_allocation_gestion_part

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment
import org.recherche.app_allocation_gestion_part.ui.theme.AppallocationgestionpartTheme
import org.recherche.app_allocation_gestion_part.ui.theme.Components
import org.recherche.app_allocation_gestion_part.viewmodels.DashboardViewModel
import org.recherche.app_allocation_gestion_part.viewmodels.LoginViewModel
import java.time.LocalDate

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val loginViewModel = LoginViewModel(application)
        if (SessionManagement(application).getToken().isNotEmpty()) {
            startActivity(Intent(this, Dashboard::class.java))
        }

        setContent {
            AppallocationgestionpartTheme {
               // A surface container using the 'background' color from the theme
                LoginScreen(viewModel = loginViewModel)
                }
            }
        }
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showSystemUi = true)
@Composable
fun GreetingPreview() {
    AppallocationgestionpartTheme {
        LoginScreenPre()
    }
}


@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(viewModel: LoginViewModel) {

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    var username by rememberSaveable {
        mutableStateOf("")
    }


    val showPassword = rememberSaveable {
        mutableStateOf(false)
    }
    var password by rememberSaveable {
        mutableStateOf("")
    }

    var isShowAlert by remember {
        mutableStateOf(false)
    }



    @Composable
    fun alert(title: String, message: String) {
        AlertDialog(
            dismissButton = {
                TextButton(onClick = { isShowAlert = false }) {
                    Text(text = "cancel")
                }
            }
            , confirmButton = { TextButton(onClick = {isShowAlert = false}) {
                Text(text = "confirm")
            }}, title = { Text(
                text = title
            )}, text = { Text(text = message)}, onDismissRequest = {}

        )

    }






    var error = viewModel.error.observeAsState()
    var gotToken = viewModel.isGotTheToken.observeAsState()


    Scaffold(
        snackbarHost = {SnackbarHost(snackbarHostState)}

    ) { padding ->


        if (isShowAlert) {
            Log.d("TAG", "LoginScreen: Alert ")

            alert(title = "Connection", message = "Check Your Connection" )
        }

        if (gotToken.value!!) {
            context.startActivity(Intent(context.applicationContext, Dashboard::class.java))
        }

        if (error.value!!.isNotEmpty()) {
            scope.launch {
                snackbarHostState.showSnackbar(error.value!!)
            }
            viewModel.error.postValue("")
        }



        Box(
            Modifier
                .padding(padding)
                .padding(16.dp)) {
            Column(modifier = Modifier
                .fillMaxSize(),
                verticalArrangement = Arrangement.Center) {
                Spacer(modifier = Modifier.height(16.dp))
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {

                    Image(painter = painterResource(id = R.drawable.image_latst), contentDescription = "Hello", modifier = Modifier.width(150.dp))
                }
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = username,
                    onValueChange = { username = it },
                    placeholder = {Text("email")}
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = password,
                    visualTransformation = if(showPassword.value)  VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                                   IconButton(onClick = {showPassword.value != showPassword.value}) {
                                       Icon(painter = if(showPassword.value) painterResource(id = R.drawable.baseline_visibility_off_24) else painterResource(id = R.drawable.baseline_visibility_24), contentDescription = "password of" )
                                   }
                    },
                    onValueChange = { password = it },
                    placeholder = { Text("password") }
                )

                Spacer(modifier = Modifier.height(16.dp))
                Row (horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()){
                    OutlinedButton(
                        onClick = {
                            if (checkInternetConnection(context)) {
                                val res = viewModel.login(username, password)
                                if (res == -1) {
                                    scope.launch {
                                        snackbarHostState.showSnackbar("ERROR USERNAME INVALID")
                                    }
                                }else if (res == -2) {
                                    scope.launch {
                                        snackbarHostState.showSnackbar("ERROR PASSWORD INVALID")
                                    }
                                }
                                }else {
                                    isShowAlert = true
                            }

                        },
                        modifier = Modifier.width(100.dp)) {
                        Text(text = "SUBMIT")
                    }

                    Spacer(modifier = Modifier.width(8.dp))
                    OutlinedButton(onClick = { username = ""; password = ""  } , modifier = Modifier.width(100.dp)) {
                        Text(text = "RESET")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
                    Text(text = "or")
                }
                Divider(modifier = Modifier.height(1.dp), thickness = 2.dp, color = Color.Black)

                TextButton(onClick = {  context.startActivity(Intent(context, SignUpActivity::class.java))}, modifier = Modifier.align(Alignment.CenterHorizontally)) {
                    Text(text = "you haven't account?, sign in")
                }

            }

        }
    }
























}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreenPre() {


    var username by rememberSaveable {
        mutableStateOf("")
    }
    var password by rememberSaveable {
        mutableStateOf("")
    }

    Box(Modifier.padding(16.dp)) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {


            Spacer(modifier = Modifier.height(16.dp))
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {

                Image(
                    painter = painterResource(id = R.drawable.image_latst),
                    contentDescription = "Hello",
                    modifier = Modifier.width(150.dp)
                )
            }
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = username,
                onValueChange = { username = it },
                placeholder = { Text("eg. root@go.com") }
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = password,
                onValueChange = { password = it },
                placeholder = { Text("password") }
            )

            Spacer(modifier = Modifier.height(16.dp))
            Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
                OutlinedButton(onClick = {}, modifier = Modifier.width(100.dp)) {
                    Text(text = "SUBMIT")
                }

                Spacer(modifier = Modifier.width(8.dp))
                OutlinedButton(onClick = { username = ""; password = "" },
                    modifier = Modifier.width(100.dp)) {
                    Text(text = "RESET")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
                Text(text = "or")
            }
            Divider(modifier = Modifier.height(1.dp), thickness = 2.dp, color = Color.Black)

            TextButton(onClick = { }, modifier = Modifier.align(Alignment.CenterHorizontally)) {
                Text(text = "you haven't account?, sign in")
            }

        }

    }

}



