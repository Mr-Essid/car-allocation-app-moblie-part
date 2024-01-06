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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.recherche.app_allocation_gestion_part.models.HistoryResponse
import org.recherche.app_allocation_gestion_part.ui.theme.AppallocationgestionpartTheme
import org.recherche.app_allocation_gestion_part.ui.theme.Components
import org.recherche.app_allocation_gestion_part.viewmodels.HistoryViewModel
import java.time.LocalDateTime
import java.time.LocalTime

class HistoryActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {

        val viewModel = HistoryViewModel(application)
        if (checkInternetConnection(this))
            viewModel.getHistoryData()
        else
            viewModel.getFromLocal()
        super.onCreate(savedInstanceState)
        setContent {
            AppallocationgestionpartTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    HistoryScreen(viewModel)
                }
            }
        }
    }
}

@Composable
fun Greeting9(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true,  showSystemUi = true)
@Composable
fun GreetingPreview9() {
    AppallocationgestionpartTheme {
        Box {

            val historyResponse = HistoryResponse(
                LocalDateTime.now().toString(),
                3,
                3,
                2,
                true,
                true,
                32.43,
                LocalDateTime.now().toString()
            )
            HistoryItem(historyResponse)
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HistoryScreen(viewModel: HistoryViewModel) {

    val historyItems = viewModel.listHistory.observeAsState()
    val error = viewModel.error.observeAsState()
    val localContext = LocalContext.current as Activity
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar =  { Components.TopAppBar_(title = "History") {localContext.finish()}},
        snackbarHost = { SnackbarHost(snackbarHostState) },

    ) {paddingValues ->


        if (error.value!!.isNotEmpty()) {
            LaunchedEffect(Unit) {
                scope.launch {
                    snackbarHostState.showSnackbar(error.value!!)
                }
            }
        }

        Surface(modifier= Modifier.padding(paddingValues)) {
            if (historyItems.value != null) {
                LazyColumn(modifier = Modifier.padding(16.dp)) {
                    items(historyItems.value!!.size) {
                        HistoryItem(historyItems.value!![it])
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


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryItem(response: HistoryResponse) {
    val activity = LocalContext.current as Activity
    OutlinedCard(
        onClick = {
            val intent_ = Intent(activity, HistoryDetailsActivity::class.java)
            intent_.putExtra("id", response.id_history)
            activity.startActivity(intent_)
        }, modifier = Modifier.fillMaxWidth()) {

        val textActive = @Composable {
            Text(
                text = "Active",
                style = MaterialTheme.typography.labelSmall,
                color = Color.Green,
                fontWeight = FontWeight.Bold
            )
        }
        val textDisActive = @Composable {
            Text(
                text = "Dis-active",
                style = MaterialTheme.typography.labelSmall,
                color = Color.Red,
                fontWeight = FontWeight.Bold
            )
        }
        val date_ = LocalDateTime.parse(response.ret_date)


        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Column {
                Text(
                    text = "History ${response.id_history}",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold
                )
                if (response.is_active) {
                    textActive()
                } else {
                    textDisActive()

                }
                Text(
                    text = "return at ${date_.year}/${date_.monthValue}/${date_.dayOfMonth}",
                    style = MaterialTheme.typography.labelSmall
                )
            }
            Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.TopEnd) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_format_list_bulleted_24),
                    contentDescription = "list"
                )
            }
        }
    }
}


