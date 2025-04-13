package com.example.myapplication.wallet

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.navigation.Screen

@Composable
fun TransactionListUI(/*innerPadding: PaddingValues*/ navController: NavController) {
    // Sample data - you'll connect this with your backend later
    val transactions = remember { mutableStateListOf(
        Transaction("Fulano", 0.0,"Chicharitos", "12/04/25")
    ) }

    Scaffold(
        floatingActionButton = {
            FAB(navController)
        }
    )
    {
        innerPadding ->
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

            items(transactions) { transaction ->
                TransactionItem(
                    transaction = transaction,
                    onDelete = {
                        transactions.remove(transaction)
                    }
                )
            }

        }
    }
}


@Composable
fun TransactionItem(transaction: Transaction, onDelete: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = transaction.name,
            fontSize = 14.sp
        )
        Text(
            text = transaction.amount.toString(),
            fontSize = 14.sp,
            color = if (transaction.amount < 0) Color.Red else Color.Green
        )
        Text(
            text = transaction.description,
            fontSize = 14.sp
        )
        Text(
            text = transaction.date,
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


data class Transaction(
    val name: String,
    var amount: Double,
    val description: String,
    val date: String
)

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