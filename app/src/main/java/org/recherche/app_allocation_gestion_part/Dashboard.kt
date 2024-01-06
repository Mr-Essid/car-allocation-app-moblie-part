package org.recherche.app_allocation_gestion_part

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.IdRes
import androidx.annotation.RequiresApi
import androidx.annotation.StyleRes
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.recherche.app_allocation_gestion_part.models.CountResResponse
import org.recherche.app_allocation_gestion_part.ui.theme.AppallocationgestionpartTheme
import org.recherche.app_allocation_gestion_part.ui.theme.primaryColor
import org.recherche.app_allocation_gestion_part.viewmodels.DashboardViewModel

class Dashboard : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dashboardViewModel: DashboardViewModel = DashboardViewModel(application)
        if (checkInternetConnection(this))
            dashboardViewModel.getAdmin()
        else
            dashboardViewModel.getAdminLocal()
        setContent {
            AppallocationgestionpartTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DashBoardView(dashboardViewModel)
                }
            }
        }
    }
}

@Composable
fun Greeting3(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Composable
fun HeaderPre(username: String , listener: () -> Unit) {


    Box {
        Row(modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(bottomEndPercent = 16, bottomStartPercent = 16))
            .background(primaryColor)
            .height(110.dp)
            .padding(16.dp)


            , verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween)
        {

            Text(text ="Welcome, $username" , style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold, color = Color.White)
            IconButton(onClick = listener) {
                Icon(Icons.Default.Settings, contentDescription = "setting", tint = Color.White)
            }
        }
    }
}


@Composable
fun CountResources(countRes: CountResResponse) {

    OutlinedCard {
        Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()) {
            ItemResNumber(text = countRes.number_admins.toString(), id_ =R.drawable.baseline_admin_panel_settings_24 )
            ItemResNumber(text = countRes.number_users.toString(), id_ =R.drawable.baseline_account_circle_24 )
            ItemResNumber(text = countRes.number_cars.toString(), id_ =R.drawable.baseline_directions_car_filled_24 )
        }
    }

}

@Composable
fun ItemResNumber(text: String, id_: Int) {
    Box {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(painter = painterResource(id = id_), contentDescription = "content icon")
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = text, maxLines = 1)
    }
    }
}




@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview3() {
    AppallocationgestionpartTheme {
        Box {
            ItemsOfBean("hi", R.drawable.admin)
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemsOfBean(text: String, id_: Int) {


        val context = LocalContext.current as Activity
        OutlinedCard(modifier = Modifier.fillMaxWidth(),

            onClick = {
                if (text == "Users"){
                    val intent = Intent(context, UserActivity::class.java)
                    context.startActivity(intent)
                }else if (text == "Admins") {


                }else if (text == "Cars") {
                    context.startActivity(Intent(context, CarsActivity::class.java))

                }else if (text == "History") {
                    val intent = Intent(context, HistoryActivity::class.java)
                    context.startActivity(intent)

                }else if (text == "Setting") {
                    val intent = Intent(context, SettingActivity::class.java)
                    context.startActivity(intent)
                }else {
                    Log.d("TAG", "ItemsOfBean: $text")
                }

            }

        ) {
            Row(modifier = Modifier.padding(16.dp)) {
                Image(painter = painterResource(id = id_), contentDescription ="Hello World", modifier = Modifier.size(70.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Column {
                    Text(text = text, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                    Text(text = "active", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.ExtraLight, color = Color.Green)
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        ,
                    contentAlignment = Alignment.CenterEnd
                    ) {
                    Icon(painter = painterResource(id = R.drawable.baseline_format_list_bulleted_24), contentDescription = "text")
                }

            }
        }

}


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashBoardView(viewModel: DashboardViewModel) {


    val context = LocalContext.current as Activity
    val error_ = viewModel.error_.observeAsState()
    val currentUser = viewModel.currentUser.observeAsState()
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val countRes = viewModel.countResResponse.observeAsState()
    val isLogOut = viewModel.isLogOut.observeAsState()





    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) {


        if (isLogOut.value == true) {
            context.finish()
        }

        if (error_.value!!) {
            LaunchedEffect(Unit) {
            scope.launch {
                snackbarHostState.showSnackbar(viewModel.errorMessage)
            }
            }
        }


        Column(modifier = Modifier.padding(it)) {
            if (currentUser.value != null) {
                HeaderPre(username = currentUser.value!!.name_admin) {
                    Log.d("TAG", "DashBoardView: clicked ")
                }
                Column(Modifier.padding(16.dp)) {
                    if(countRes.value != null)
                        CountResources(viewModel.countResResponse.value!!)
                    else
                        CountResources(CountResResponse(0, 0, 0))

                    Spacer(modifier = Modifier.height(16.dp))
                    
                    if (currentUser.value!!.authority == 0) {
                        ItemsOfBean(text = "Admins", id_ = R.drawable.admin )
                    }else {
                        ItemsOfBean(text = "Users", id_ = R.drawable.man)
                    }
                    Spacer(modifier = Modifier.height(16.dp))

                    ItemsOfBean(text = "Cars", id_ = R.drawable.sedan)
                    Spacer(modifier = Modifier.height(16.dp))
                    ItemsOfBean(text = "History", id_ = R.drawable.time_management)
                    Spacer(modifier = Modifier.height(16.dp))
                    ItemsOfBean(text = "Setting", id_ = R.drawable.settings)


                }

            }else
                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(
                        modifier = Modifier.width(40.dp),
                        color = MaterialTheme.colorScheme.secondary,
                    )

                }

        }
    }

}



