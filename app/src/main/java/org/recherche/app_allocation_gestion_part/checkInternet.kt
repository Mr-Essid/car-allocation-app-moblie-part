package org.recherche.app_allocation_gestion_part

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

fun checkInternetConnection(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    val net  = connectivityManager.activeNetwork ?: return false


    val activityNetWork = connectivityManager.getNetworkCapabilities(net)?: return false
    return when {
        // Indicates this network uses a Wi-Fi transport,
        // or WiFi has network connectivity
        activityNetWork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true

        // Indicates this network uses a Cellular transport. or
        // Cellular has network connectivity
        activityNetWork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true

        // else return false
        else -> false
    }


}