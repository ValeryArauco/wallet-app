package com.example.myapplication.wallet

import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.myapplication.util.Util
import java.time.Instant
import java.time.ZoneId



@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTransactionUI(navController: NavController, viewModel: AddTransactionViewModel = hiltViewModel()) {
    var name by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }

    // Date picker state
    val datePickerState = rememberDatePickerState()
    var showDatePicker by remember { mutableStateOf(false) }


    // Transaction type state
    var expanded by remember { mutableStateOf(false) }
    val transactionTypes = listOf("Egreso", "Ingreso")
    var selectedType by remember { mutableStateOf(transactionTypes[0]) }


    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nombre") },
            placeholder = { Text(" ") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        OutlinedTextField(
            value = price,
            onValueChange = {
                if (it.isEmpty() || it.matches(Regex("^?\\d*\\.?\\d*$"))) {
                    price = it
                }
            },
            label = { Text("Precio") },
            placeholder = { Text(" ") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Next
            )
        )



        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Descripcion") },
            placeholder = { Text(" ") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        // Date input with date picker
        OutlinedTextField(
            value = date,
            onValueChange = { date = it },
            label = { Text("Fecha") },
            placeholder = { Text(" ") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { showDatePicker = true }) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Select date"
                    )
                }
            }
        )


        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            OutlinedTextField(
                value = selectedType,
                onValueChange = {},
                readOnly = true,
                label = { Text("Tipo de Transacción") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                transactionTypes.forEach { type ->
                    DropdownMenuItem(
                        text = { Text(text = type) },
                        onClick = {
                            selectedType = type
                            expanded = false
                        }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.weight(1f))

        // Save button
        Button(
            onClick = {
                Toast.makeText(context, "Guardado", Toast.LENGTH_SHORT).show()

                viewModel.saveTransaction(name, price, description, date, selectedType)
                navController.popBackStack()
                //Log.d("DEBUG", "Datos recibidos: name=$name, price=$price, description=$description, date=$date, type=$selectedType")
            },
            modifier = Modifier
                .padding(vertical = 16.dp)
                .height(48.dp)
                .width(120.dp),
            shape = RoundedCornerShape(24.dp)
        ) {
            Text("Guardar", color = Color.White)
        }
    }

    // Date picker dialog
    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let { millis ->
                            val localDate = Instant.ofEpochMilli(millis)
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate()
                            date = "${localDate.dayOfMonth}/${localDate.monthValue}/${localDate.year.toString().takeLast(2)}"
                        }
                        showDatePicker = false
                    }
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDatePicker = false }
                ) {
                    Text("Cancel")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}