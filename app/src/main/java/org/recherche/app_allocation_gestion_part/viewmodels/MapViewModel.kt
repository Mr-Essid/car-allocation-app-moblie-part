package org.recherche.app_allocation_gestion_part.viewmodels

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.AndroidViewModel
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import org.eclipse.paho.client.mqttv3.MqttTopic
import org.recherche.app_allocation_gestion_part.MqttManager

class MapViewModel(application: Application): AndroidViewModel(application) {
    val uri = "ssl://a1bab6f7160c46d9953a7063106f84b9.s1.eu.hivemq.cloud:8883"
    val username = "essid"
    val password = "HelloWorld_1".toCharArray()
    val mqttManager = MqttManager(application, uri)
    val latLon = mutableStateListOf(0.0, 0.0)


    fun connect(options: MqttConnectOptions, topic: String) {
        options.userName = username
        options.password = password
        mqttManager.connect(options, topic)
        latLon[0] = 2.2
        latLon[1] = 1.2
    }
}