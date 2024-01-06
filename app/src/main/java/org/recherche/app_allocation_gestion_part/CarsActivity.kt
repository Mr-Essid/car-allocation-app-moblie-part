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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.recherche.app_allocation_gestion_part.models.CarResponse
import org.recherche.app_allocation_gestion_part.ui.theme.AppallocationgestionpartTheme
import org.recherche.app_allocation_gestion_part.ui.theme.Components
import org.recherche.app_allocation_gestion_part.viewmodels.CarsViewModel

class CarsActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val carsViewModel = CarsViewModel(application)
        if (checkInternetConnection(this))
            carsViewModel.getAllCars()
        else
            carsViewModel.getCarsLocal()

        val authority = SessionManagement(application).getAuthority()

        setContent {
            AppallocationgestionpartTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CarsScreen(viewModel = carsViewModel, authority)
                }
            }
        }
    }
}

@Composable
fun Greeting7(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview7() {
    AppallocationgestionpartTheme {

        val carResponse = CarResponse(
            color_car = "Black",
            id_car = 3,
            identifyer_car = "back01",
            is_active_car = 1,
            is_allocated_car = 1,
            is_mapped_car = 1,
            model = "BMW",
            price_k_dinar = 992.32
        )
        Box {
//            CarItem(carResponse = carResponse)
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarsScreen(viewModel: CarsViewModel, authority: Int) {


    val localContext = LocalContext.current as Activity

    val listCars = viewModel.listCars.observeAsState()
    val error = viewModel.errorMessage.observeAsState()
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }


    Scaffold(
        topBar =  {Components.TopAppBar_(title = "Cars") {localContext.finish()}},
        snackbarHost = {SnackbarHost(snackbarHostState)},
        floatingActionButton = { if (authority < 1)
            FloatingActionButton(onClick = { Log.d("TAG", "CarsScreen: add ") }) {
                FloatingActionButton(onClick = { localContext.startActivity(Intent(localContext, AddCarActivity::class.java))}) {
                    Icon(Icons.Default.Add, contentDescription = "add items")
                }
            } else {}
        }
    ) {paddingValues ->


        if (error.value!!.isNotEmpty()) {
            LaunchedEffect(Unit) {
                scope.launch {
                    snackbarHostState.showSnackbar(error.value!!)
                }
            }
        }

        Surface(modifier= Modifier.padding(paddingValues)) {
            if (listCars.value != null) {
                LazyColumn(modifier = Modifier.padding(16.dp)) {
                    items(listCars.value!!.size) {
                        CarItem(listCars.value!![it], authority)
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }else {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
        }

    }


}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarItem(carResponse: CarResponse, authority: Int) {
    val textActive  =  @Composable {Text(text = "Active", style = MaterialTheme.typography.labelSmall , color = Color.Green , fontWeight = FontWeight.Bold)}
    val textDisActive = @Composable {Text(text = "Dis-active", style = MaterialTheme.typography.labelSmall , color = Color.Red , fontWeight = FontWeight.Bold)}
    OutlinedCard(onClick = {if(authority < 1) Log.d("TAG", "CarItem: start activity") }) {
        Row(Modifier.padding(16.dp)) {
            Column {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(text = carResponse.identifyer_car , style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                    Icon(painter = painterResource(id = R.drawable.baseline_format_list_bulleted_24), contentDescription = "Icon")
                }
                if (carResponse.is_active_car == 1)
                    textActive()
                else
                    textDisActive()
                Divider()
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "Model ${carResponse.model}", style = MaterialTheme.typography.labelSmall, )
                Text(text = "Color ${carResponse.color_car}", style = MaterialTheme.typography.labelSmall, )
            }
        }
    }
}
