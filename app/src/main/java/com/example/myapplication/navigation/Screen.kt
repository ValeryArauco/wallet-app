package com.example.myapplication.navigation

sealed class Screen(val route: String) {
    object AddTransactionScreen: Screen("addTransaction")
    object TransactionListScreen: Screen("list")
}