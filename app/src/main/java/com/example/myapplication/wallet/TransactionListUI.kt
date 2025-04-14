package com.example.myapplication.wallet

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.domain.Transaccion
import com.example.myapplication.navigation.Screen

@Composable
fun TransactionListUI(navController: NavController, transactionListViewModel: TransactionListViewModel = hiltViewModel()) {

    LaunchedEffect(Unit) {
        transactionListViewModel.loadTransacciones()
    }

    val uiState by transactionListViewModel.state.collectAsState()

    Scaffold(
        floatingActionButton = {
            FAB(navController)
        }
    )
    {
        innerPadding ->
        when (val ui = uiState) {
            is TransactionListViewModel.TransactionUIState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is TransactionListViewModel.TransactionUIState.Loaded ->{
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(16.dp)
                ) {

                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Nombre",
                                fontWeight = FontWeight.Medium,
                                fontSize = 14.sp
                            )
                            Text(
                                text = "Precio",
                                fontWeight = FontWeight.Medium,
                                fontSize = 14.sp
                            )
                            Text(
                                text = "Descripcion",
                                fontWeight = FontWeight.Medium,
                                fontSize = 14.sp
                            )
                            Text(
                                text = "Fecha",
                                fontWeight = FontWeight.Medium,
                                fontSize = 14.sp
                            )
                            // Space for delete icon
                            Spacer(modifier = Modifier.width(24.dp))
                        }
                    }

                    items(ui.list) { transaccion ->
                        TransactionItem(
                            transaccion = transaccion,
                            onDelete = {
                                transactionListViewModel.deleteTransaction(transaccion)
                            }
                        )
                    }

                }
            }
            is TransactionListViewModel.TransactionUIState.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    Text(ui.message)
                }
            }
        }


    }
}


@Composable
fun TransactionItem(transaccion: Transaccion, onDelete: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = transaccion.nombre,
            fontSize = 14.sp
        )
        Text(
            text = transaccion.precio.toString(),
            fontSize = 14.sp,
            color = if (transaccion.precio < 0) Color.Red else Color.Green
        )
        Text(
            text = transaccion.descripcion,
            fontSize = 14.sp
        )
        Text(
            text = transaccion.fecha,
            fontSize = 14.sp
        )
        IconButton(
            onClick = onDelete,
            modifier = Modifier.size(24.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Clear,
                contentDescription = "Delete transaction",
                tint = Color.Gray
            )
        }
    }
}



@Composable
fun FAB(navController: NavController) {
    val context = LocalContext.current
    FloatingActionButton(
        onClick = {
            navController.navigate(Screen.AddTransactionScreen.route)
        },
        containerColor = Color(0xFFE6E0EB),
        contentColor = Color.Black
    ) {
        Icon(
            imageVector = Icons.Default.Edit,
            contentDescription = "Add transaction"
        )
    }
}