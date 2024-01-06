package org.recherche.app_allocation_gestion_part

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import info.mqtt.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.IMqttActionListener
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.IMqttToken
import org.eclipse.paho.client.mqttv3.MqttCallback
import org.eclipse.paho.client.mqttv3.MqttClient
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import org.eclipse.paho.client.mqttv3.MqttMessage
import kotlin.math.log

class MqttManager(application: Application, uri: String) {
    private val mqttAndroidClient = MqttAndroidClient(application, uri, MqttClient.generateClientId())
    val mutableLiveDataOfPosition = MutableLiveData<List<Double>>()

    fun connect(options: MqttConnectOptions, topic: String) {

        mqttAndroidClient.addCallback( object :MqttCallback {
            override fun connectionLost(cause: Throwable?) {
                Log.d("TAG", "connectionLost: connection lost")
            }

            override fun messageArrived(topic: String?, message: MqttMessage?) {

                val lat = message.toString().split("_T_")[0].toDouble()
                val lon = message.toString().split("_T_")[1].toDouble()
                Log.d("TAG", "messageArrived: message $message")

                mutableLiveDataOfPosition.postValue(listOf(lat, lon))
                Log.d("TAG", "messageArrived: message came ${mutableLiveDataOfPosition.value}")

            }

            override fun deliveryComplete(token: IMqttDeliveryToken?) {
                Log.d("TAG", "deliveryComplete: del complete ")
            }

        })
        mqttAndroidClient.connect(options).actionCallback = object : IMqttActionListener {
            override fun onSuccess(asyncActionToken: IMqttToken?) {
                Log.d("TAG", "onSuccess: connected")
                mqttAndroidClient.subscribe(topic, 1).actionCallback = object : IMqttActionListener {
                    override fun onSuccess(asyncActionToken: IMqttToken?) {
                        Log.d("TAG", "onSuccess: subs suc to $topic")
                    }

                    override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                        Log.d("TAG", "onFailure: not good ")
                    }

                }
            }

            override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                Log.d("TAG", "onFailure: failure")
                exception!!.printStackTrace()
            }
        }
    }



    fun disconnect() {
        mqttAndroidClient.disconnect()
    }








}