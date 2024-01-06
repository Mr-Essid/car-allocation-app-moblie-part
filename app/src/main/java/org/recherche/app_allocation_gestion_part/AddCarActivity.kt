package org.recherche.app_allocation_gestion_part

import android.app.Activity
import android.os.Build
import android.os.Bundle
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
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import org.recherche.app_allocation_gestion_part.viewmodels.AddCarViewModel
import org.recherche.app_allocation_gestion_part.viewmodels.AddUserViewModel

class AddCarActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = AddCarViewModel(application)
        setContent {
            AppallocationgestionpartTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    addCarScreen(viewModel)
                }
            }
        }
    }
}

@Composable
fun Greeting13(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun GreetingPreview14() {
    AppallocationgestionpartTheme {
    }
}





@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun addCarScreen(viewModel: AddCarViewModel) {

    val context = LocalContext.current as Activity
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }




    val error = viewModel.error.observeAsState()
    val added = viewModel.isSubmit.observeAsState()
    Scaffold(snackbarHost = { SnackbarHost(snackbarHostState) }, topBar = {
        Components.TopAppBar_(
            title = "Add Car"
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
                    Image(painter = painterResource(id = R.drawable.sedan), contentDescription = "users image", modifier = Modifier.size(80.dp))
                }



                Spacer(modifier =Modifier.height(16.dp) )
                Text(
                    text = "CAR INFOS",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = primaryColor,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier =Modifier.height(16.dp) )
                OutlinedTextField(value =viewModel.caridentifier.value , onValueChange = {viewModel.caridentifier.value = it}, placeholder = { Text(
                    text = "identifier"
                )}, modifier = Modifier.fillMaxWidth())
                Spacer(modifier =Modifier.height(16.dp) )
                OutlinedTextField(value =viewModel.carModel.value , onValueChange = {viewModel.carModel.value = it}, placeholder = { Text(
                    text = "car model"
                )}, modifier = Modifier.fillMaxWidth())

                Spacer(modifier =Modifier.height(16.dp) )
                OutlinedTextField(value =viewModel.carColor.value , onValueChange = {viewModel.carColor.value = it }, placeholder = { Text(
                    text = "Color"
                )}, modifier = Modifier.fillMaxWidth())

                Spacer(modifier =Modifier.height(16.dp) )
                OutlinedTextField(value =viewModel.carPrice.value , onValueChange = {viewModel.carPrice.value = it}, placeholder = { Text(
                    text = "price"
                )}, modifier = Modifier.fillMaxWidth(), keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal))
                Spacer(modifier =Modifier.height(16.dp) )

                Spacer(modifier =Modifier.height(16.dp) )

                Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
                    OutlinedButton(
                        onClick = {
                            val res = viewModel.addCar()
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