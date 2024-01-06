package org.recherche.app_allocation_gestion_part

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import org.recherche.app_allocation_gestion_part.ui.theme.AppallocationgestionpartTheme
import org.recherche.app_allocation_gestion_part.ui.theme.Components
import org.recherche.app_allocation_gestion_part.viewmodels.MapViewModel
import java.security.KeyStore
import java.security.cert.CertificateFactory
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory

class HistoryMapActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {

        val id = intent.getIntExtra("id", -1)


        super.onCreate(savedInstanceState)
        val cf = CertificateFactory.getInstance("X.509")
        val cert = resources?.openRawResource(R.raw.server1)



        val ca = cf.generateCertificate(cert)
        val keyStoreType = KeyStore.getDefaultType()
        val keyStore = KeyStore.getInstance(keyStoreType)
        keyStore.load(null, null)
        keyStore.setCertificateEntry("ca", ca)

        val tmfAlgo = TrustManagerFactory.getDefaultAlgorithm()
        val tmf = TrustManagerFactory.getInstance(tmfAlgo)
        tmf.init(keyStore)

        val sslContext = SSLContext.getInstance("TLS")
        sslContext.init(null, tmf.trustManagers, null)

        val mqttConnectOptions = MqttConnectOptions()
        mqttConnectOptions.userName = "YOUR-BROKER-USERNAME"
        mqttConnectOptions.password = "YOUR-BROKER-PASSWORD".toCharArray()
        mqttConnectOptions.socketFactory = sslContext.socketFactory

        val mqttMattManager = MqttManager(application, "YOUR-BROKER-CLOUD/LOCAL-URI")
        val viewModel = MapViewModel(application)
        viewModel.connect(mqttConnectOptions, "/car/info/${id}")

        mqttMattManager.connect(mqttConnectOptions, "/hi")
        setContent {
            val context = LocalContext.current as Activity

            AppallocationgestionpartTheme {
                val singapore = LatLng(1.35, 103.87)
                val cameraPositionState = rememberCameraPositionState {
                    position = CameraPosition.fromLatLngZoom(singapore, 0f)
                }
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    Scaffold(
                        topBar = {Components.TopAppBar_(title = "Map") { context.finish() } }
                    ) {

                        Box(Modifier.padding(it)) {

                    val positionState = viewModel.mqttManager.mutableLiveDataOfPosition.observeAsState()


                    val newPostion = CameraPosition.fromLatLngZoom(LatLng(positionState.value?.get(0) ?: 0.0,
                        positionState.value?.get(1) ?: 0.0
                    ), 18f)

                    Log.d("TAG", "onCreate:  ${positionState.value.toString()}")

                    cameraPositionState.position = newPostion


                    GoogleMap(
                        modifier = Modifier.fillMaxSize(),
                        cameraPositionState = cameraPositionState,
                        onMapLoaded = {
                        }
                    ) {
                        Marker(
                            state = MarkerState(position = newPostion.target),
                            title = "Singapore",
                            snippet = "Marker in Singapore"
                        )
                    }
                    }
                }
                }
            }
        }
    }
}

@Composable
fun Greeting11(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview11() {
    val singapore = LatLng(1.35, 103.87)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(singapore, 10f)
    }

    AppallocationgestionpartTheme {


        Scaffold {
            Box(Modifier.padding(it)) {


            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState
            ) {
                Marker(
                    state = MarkerState(position = singapore),
                    title = "Singapore",
                    snippet = "Marker in Singapore"
                )
            }
        }
    }
    }

}