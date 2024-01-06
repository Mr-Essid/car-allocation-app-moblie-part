package org.recherche.app_allocation_gestion_part

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.CalendarView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import kotlinx.coroutines.launch
import org.recherche.app_allocation_gestion_part.ui.theme.AppallocationgestionpartTheme
import org.recherche.app_allocation_gestion_part.ui.theme.Components
import org.recherche.app_allocation_gestion_part.viewmodels.AllocateViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class CreateAllocationActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val username = intent.getStringExtra("username")
        val userId = intent.getIntExtra("id", -1)

        val carIdentifier = intent.getStringExtra("identi")
        val idCar = intent.getIntExtra("id_car", -1)

        Log.d("TAG", "onCreate: userid $userId ")
        Log.d("TAG", "onCreate: car $idCar ")

        val viewModel = AllocateViewModel(application)
        viewModel.id_user = userId
        viewModel.id_car = idCar

        viewModel.caridentifier = carIdentifier!!
        viewModel.username = username!!


        
        
        setContent {
            AppallocationgestionpartTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ScreenAddHistory(viewModel)
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenAddHistory(viewModel: AllocateViewModel) {
    val context = LocalContext.current as Activity
    var date = ""
    var idShowDialog by remember {
        mutableStateOf(false)
    }

    val isSubmit = viewModel.isAllocated.observeAsState()

    var price by rememberSaveable {
        mutableStateOf("")
    }

    if (isSubmit.value == true) {
        context.finish()
    }


//    Log.d("TAG", "ScreenAddHistory: parse ${LocalDateTime.parse("2023-12-04T22:30:49")}")
    Scaffold(topBar = {
        Components.TopAppBar_(title = "Allocation") {
            context.finish()
        }}) {
        Box(modifier = Modifier
            .padding(it)
            .padding(16.dp)) {

            if (idShowDialog) {
                Popup(onDismissRequest = { idShowDialog = false}, properties = PopupProperties(focusable = true)) {
                    Box(modifier = Modifier.background(Color.White)) {

                    AndroidView(factory = {it_ -> CalendarView(it_)}, update = {dateN->
                        dateN.setOnDateChangeListener { _, year, month, dey ->
                            date = "${year}-${(month + 1).toString().padStart(2, '0')}-${dey.toString().padStart(2, '0')}T00:00:00"
                            viewModel.date = date
                            idShowDialog = false

                        }
                    })
                }
                }
            }


            Column {
                Row(horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                    Text(text = viewModel.username, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(painter = painterResource(id = R.drawable.baseline_arrow_forward_24), contentDescription = "got user", tint = Color.Blue)

                    }
                }

                Divider()

                Row(horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                    Text(text = viewModel.caridentifier, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(painter = painterResource(id = R.drawable.baseline_arrow_forward_24), contentDescription = "got Car", tint = Color.Blue)
                    }
                }
                Divider()
                Spacer(modifier = Modifier.padding(vertical = 8.dp))
                Text(text = "Allocation Meta Data", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
                Row(horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                    Text(text = "return At", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    IconButton(onClick = { idShowDialog = true }) {
                        Icon(painter = painterResource(id = R.drawable.baseline_calendar_month_24), contentDescription = "Cal", tint = Color.Blue)
                    }

                }


                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                    Text(text = "price", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(16.dp))
                    OutlinedTextField(value = price , onValueChange = { newPrice: String -> price = newPrice; viewModel.price = newPrice} , keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal), placeholder = { Text(
                        text = "price",
                    )}, modifier = Modifier.width(100.dp))

                }
                Spacer(modifier = Modifier.height(16.dp))
                Row (horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()){
                    OutlinedButton(
                        onClick = {
                            viewModel.submit()
                        },
                        modifier = Modifier.width(110.dp)) {
                        Text(text = "SUBMIT")
                    }

                    Spacer(modifier = Modifier.width(8.dp))
                    OutlinedButton(onClick = {context.finish() } , modifier = Modifier.width(110.dp)) {
                        Text(text = "CANCEL")
                    }
                }
            }
            

            }

        }
    }

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview12() {
    AppallocationgestionpartTheme {
//        ScreenAddHistory()
    }
}