package com.example.myapplication.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.wallet.AddTransactionUI
import com.example.myapplication.wallet.AddTransactionViewModel
import com.example.myapplication.wallet.Prueba

@Composable
fun AppNavigation(){
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.WalletScreen.route
    ){
        composable(Screen.WalletScreen.route){
            AddTransactionUI()
        }
    }
}