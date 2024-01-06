package org.recherche.app_allocation_gestion_part.ui.theme

import android.app.AlertDialog
import android.util.Log
import android.view.View.OnClickListener
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import org.recherche.app_allocation_gestion_part.R

class Components {

    companion object {
        @Composable
        fun MainButton(text: String, listener: () -> Unit) {
            OutlinedButton(onClick = listener) {
                Text(text = text)
            }
        }

        @OptIn(ExperimentalMaterial3Api::class)
        @Composable
        fun TopAppBar_(title: String, navigationIconButton: () -> Unit) {
             TopAppBar(title = { Text(title)}, navigationIcon = { IconButton(onClick = navigationIconButton ) {
                Icon(painter = painterResource(id = R.drawable.baseline_arrow_back_24), contentDescription ="return" )
            }
            }, colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = primaryColor, titleContentColor = Color.White, navigationIconContentColor = Color.White) )
            }

        @Composable
        fun FloatingActionButton_(listener: () -> Unit) {
            FloatingActionButton(onClick = { listener }) {
                Icon(Icons.Default.Add, contentDescription = "add items")
            }
        }

        @Composable
        fun AlertDialog_(title: String, description: String, listener: () -> Unit) {
            Dialog(onDismissRequest = listener ) {
                Column {
                    Text(text = title, style = MaterialTheme.typography.titleSmall)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = description)
                }
            }
        }
    }
}


@Preview
@Composable
fun getAllComponents() {
    Components.MainButton("Submit") {}
}