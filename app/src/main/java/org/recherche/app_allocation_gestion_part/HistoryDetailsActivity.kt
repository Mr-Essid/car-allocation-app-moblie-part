package org.recherche.app_allocation_gestion_part

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.icu.text.CaseMap.Title
import android.os.Build
import android.os.Bundle
import android.os.Message
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.recherche.app_allocation_gestion_part.ui.theme.AppallocationgestionpartTheme
import org.recherche.app_allocation_gestion_part.ui.theme.Components
import org.recherche.app_allocation_gestion_part.viewmodels.HistoryDetailsViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeParseException

class HistoryDetailsActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val id_ = intent.getIntExtra("id", -1)
        if (id_ == -1) {
            finish()
        }

        val viewModel = HistoryDetailsViewModel(application)
        viewModel.getHistoryFullData(id_)

        setContent {
            AppallocationgestionpartTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    HistoryDetailsScreenC(viewModel)
                }
            }
        }
    }
}

@Composable
fun Greeting10(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview10() {
    AppallocationgestionpartTheme {
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryDetailsScreen(historyViewModel: HistoryDetailsViewModel) {


    val localContext = LocalContext.current as Activity
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()


    var isAlertDialogShown by remember {
        mutableStateOf(false)
    }


    Scaffold(
        topBar =  { Components.TopAppBar_(title = "History") {localContext.finish()}},
        snackbarHost = { SnackbarHost(snackbarHostState) },

        ) {paddingValues ->


        Box(modifier = Modifier.padding(paddingValues)) {

        }

    }

}


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryDetailsScreenC(historyViewModel: HistoryDetailsViewModel) {



    val localContext = LocalContext.current as Activity
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val isToggled = historyViewModel.toggledTrack.observeAsState()

    var isMapped by rememberSaveable {
        mutableStateOf(false)
    }

    val historyModel = historyViewModel.historyFullData.observeAsState()
    val error = historyViewModel.error.observeAsState()


    val isDeAlloc = historyViewModel.isDeAllocate.observeAsState()

    var showAlertDialog by remember {
        mutableStateOf(false)
    }

    @Composable
    fun alert(title: String, message: String) {
        AlertDialog(
            dismissButton = {
                TextButton(onClick = { showAlertDialog = false }) {
                    Text(text = "cancel")
                }
            }
            , confirmButton = { TextButton(
                onClick = {
                        historyViewModel.addToggleTrack(historyViewModel.historyFullData.value!!.id_history)
                }

            ) {
                Text(text = "confirm")

            }}, title = { Text(
                text = title
            )}, text = { Text(text = message)}, onDismissRequest = {}

        )

    }



    if (isToggled.value == true || isDeAlloc.value == true)
        localContext.finish()


    if (error.value!!.isNotEmpty()) {

        LaunchedEffect(Unit) {
            scope.launch {
                snackbarHostState.showSnackbar(error.value.toString())
                historyViewModel.error.value = ""
            }
        }
    }




    Scaffold(
        topBar =  { Components.TopAppBar_(title = "History Details") {localContext.finish()}},
        snackbarHost = { SnackbarHost(snackbarHostState) },
        ) {paddingValues ->


        if (historyModel.value != null){

            Box(modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)) {




                if (showAlertDialog) {
                    alert(title = "Map State Change", message = if (historyModel.value!!.is_dup) "Remove Map To History" else "Add Map To History" )
                }


                var retTime: LocalDateTime? = null
                var getTime: LocalDateTime? = null
                try {
                    retTime = LocalDateTime.parse(historyModel.value!!.ret_date)
                    getTime = LocalDateTime.parse(historyModel.value!!.get_date)
                } catch (e: DateTimeParseException) {
                    e.printStackTrace()
                }

                Column {

                    Row(horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                        Text(text = "User Info", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(painter = painterResource(id = R.drawable.baseline_arrow_forward_24), contentDescription = "got user", tint = Color.Blue)

                        }
                }

                Divider()

                Row(horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                    Text(text = "Car Info", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(painter = painterResource(id = R.drawable.baseline_arrow_forward_24), contentDescription = "got Car", tint = Color.Blue)
                    }
                }
                Divider()

                Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Date get", style = MaterialTheme.typography.labelSmall)
                    Text(text = "${retTime!!.year}/${retTime.monthValue}/${retTime.dayOfMonth}")
                }

                Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Date return", style = MaterialTheme.typography.labelSmall)
                    Text(text = "${getTime!!.year}/${getTime.monthValue}/${getTime.dayOfMonth}")
                }

                Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "price", style = MaterialTheme.typography.labelSmall)
                    Text(text = "${historyViewModel.historyFullData.value!!.price_} dt")

                }
                    
                Divider()
                Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "End Allocation", style = MaterialTheme.typography.labelSmall)
                    IconButton(onClick = { historyViewModel.disAllocateCar(historyModel.value!!.id_history) }) {
                        Icon(painter = painterResource(id = R.drawable.baseline_do_not_disturb_on_24,)   , contentDescription = "Dis Active", tint = Color.Blue)
                    }
                }
                Divider()


                Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "enable mapping", style = MaterialTheme.typography.labelSmall,)
                    Switch(checked = historyViewModel.historyFullData.value!!.is_dup, onCheckedChange = { showAlertDialog = true})
                }


                if (historyViewModel.historyFullData.value!!.is_dup) {

                    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "google maps: id  /car/info/${historyViewModel.historyFullData.value!!.id_history}", style = MaterialTheme.typography.labelSmall)
                        IconButton(onClick = {
                            val id = Intent(localContext, HistoryMapActivity::class.java)
                            id.putExtra("id", historyViewModel.historyFullData.value!!.id_history )
                            localContext.startActivity(id)
                        }) {
                            Icon(painter = painterResource(id = R.drawable.baseline_fmd_good_24), contentDescription = "map enable" , tint = Color.Blue)
                        }
                    }

                }
            }
        }}else {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
    }
}


