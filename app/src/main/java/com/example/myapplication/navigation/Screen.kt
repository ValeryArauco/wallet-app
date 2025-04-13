package com.example.myapplication.navigation

sealed class Screen(val route: String) {
    object WalletScreen: Screen("wallet")
}