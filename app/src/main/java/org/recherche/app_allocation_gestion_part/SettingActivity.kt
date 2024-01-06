package org.recherche.app_allocation_gestion_part

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.recherche.app_allocation_gestion_part.ui.theme.AppallocationgestionpartTheme
import org.recherche.app_allocation_gestion_part.ui.theme.Components
import org.recherche.app_allocation_gestion_part.ui.theme.primaryColor
import org.recherche.app_allocation_gestion_part.viewmodels.SettingViewModel
import java.time.format.TextStyle

class SettingActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = SettingViewModel(application)
        setContent {
            AppallocationgestionpartTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SettingsScreen(viewModel)
                }
            }
        }
    }
}

@Composable
fun Greeting12(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true,  showSystemUi = true)
@Composable
fun GreetingPreview13() {
    AppallocationgestionpartTheme {
        SettingsScreen()
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(viewModel: SettingViewModel) {


    val context = LocalContext.current as Activity
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        topBar = {Components.TopAppBar_(title = "Settings") {
            context.finish()
        }}
    ) {paddingValues ->



        Surface {


        OutlinedButton(onClick = {
                viewModel.logOut()
            val intent = Intent( context, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)

        }, modifier = Modifier
            .padding(paddingValues)
            .padding(16.dp)
            .fillMaxWidth()) {
            Text(text = "LOGOUT")
        }
        }

    }

}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen() {


//    val context = LocalContext.current as Activity
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    var updatable by remember {
        mutableStateOf(false)
    }

    Scaffold(
        topBar = {
            Components.TopAppBar_(title = "Settings") {
            }
        }


    ) {paddingValues ->

        Surface(modifier = Modifier.padding(paddingValues)) {


            Column(modifier = Modifier.padding(16.dp)) {
                Spacer(modifier =Modifier.height(16.dp) )
                Text(
                    text = "Admin Info",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = primaryColor,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier =Modifier.height(16.dp))
                Text(text = "Email", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
                Spacer(modifier =Modifier.height(8.dp) )
                Text(text = "Usernameutf-8@go.com")
                Spacer(modifier =Modifier.height(16.dp) )
                Text(text = "Full Name", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
                Spacer(modifier =Modifier.height(8.dp) )
                if (updatable)
                    OutlinedTextField(value = "essid", onValueChange = {})
                else
                    Text(text = "essid")

                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Admin IP Address", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
                Spacer(modifier =Modifier.height(8.dp) )
                if (updatable)
                    OutlinedTextField(value ="localhost" , onValueChange = {}, placeholder = {}, modifier = Modifier.fillMaxWidth())
                else
                    Text(text = "localhost")
                Spacer(modifier =Modifier.height(16.dp) )
                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "updatable", style = MaterialTheme.typography.bodySmall)
                    Spacer(modifier = Modifier.width(8.dp))
                    Switch(checked = updatable, onCheckedChange = {updatable = it
                    } )
                }
                OutlinedButton(
                    onClick = {

                    }, modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxWidth()
                ) {
                    Text(text = "LOGOUT")
                }


                TextButton(onClick = {
                }) {
                    Text(text = "update password",  style = MaterialTheme.typography.labelSmall)
                }
            }

    }

    }
}