package com.example.myapplication

import android.annotation.SuppressLint
import android.content.ClipData.Item
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*

import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import com.example.myapplication.navigation.AppNavigation
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.wallet.AddTransactionUI
import com.example.myapplication.wallet.AddTransactionViewModel
import dagger.hilt.android.AndroidEntryPoint

import java.time.Instant
import java.time.ZoneId
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation()
                }
            }
        }
    }
}


@Composable
fun Content(innerPadding: PaddingValues) {
    // Sample data - you'll connect this with your backend later
    val transactions = remember { mutableStateListOf(
        Transaction("Fulano", 0.0,"Chicharitos", "12/04/25")
    ) }


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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToolBar() {
    TopAppBar (title = {
        Text(
            text = "My Wallet App",
            fontSize = 20.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Medium
        )
    })

}
@Composable
fun FAB(){
    val context = LocalContext.current
    FloatingActionButton(
        onClick = { Toast.makeText(context, "TO DO", Toast.LENGTH_SHORT).show() },
        containerColor = Color(0xFFE6E0EB),
        contentColor = Color.Black
    ) {
        Icon(
            imageVector = Icons.Default.Edit,
            contentDescription = "Add transaction"
        )
    }
}
