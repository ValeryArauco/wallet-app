package com.example.myapplication.wallet

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.Transaccion
import com.example.myapplication.util.Util
import com.example.usecases.GetBalance
import com.example.usecases.SaveTransaccion
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.math.abs
import javax.inject.Inject

@HiltViewModel
class AddTransactionViewModel @Inject constructor(
    private val saveTransaccion: SaveTransaccion,
    private val getBalance: GetBalance,
    @ApplicationContext private val context: Context
): ViewModel() {


    sealed class UiState {
        object Loading : UiState()
        data class Success(val model: String) : UiState()
        data class Error(val message: String) : UiState()
        data class Notification(val message: String) : UiState()
    }
    private val _uiState = MutableStateFlow<UiState>(UiState.Success(""))
    val uiState: StateFlow<UiState> = _uiState


    fun saveTransaction(
        name: String,
        price: String,
        description: String,
        date: String,
        transactionType: String
    ) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading

            Log.d("DEBUG", "Guardando transacción...") // <--- Log de inicio
            Log.d("DEBUG", "Datos recibidos: name=$name, price=$price, description=$description, date=$date, type=$transactionType")

            // Validación básica
            if (name.isBlank() || price.isBlank()) {
                _uiState.value = UiState.Error("Nombre y precio son obligatorios")
                return@launch
            }
            val isIncome = transactionType == "Ingreso"

            val amount = try {
                price.toDouble().let { if (isIncome) it else -it }
            } catch (e: NumberFormatException) {
                _uiState.value = UiState.Error("Precio inválido")
                return@launch
            }

            val balance = getBalance.invoke()
            Log.d("DEBUG", "Balance: balance=$balance")
            // Validar saldo para egresos
            if (!isIncome && balance!! < abs(amount)) {
                _uiState.value = UiState.Notification("Saldo insuficiente")
                Util.sendNotificatión(context)
                return@launch
            }

               saveTransaccion.invoke(
                Transaccion(
                    nombre = name,
                    precio = amount,
                    descripcion = description,
                    fecha = date
                )
            )
            _uiState.value = UiState.Success("Registro guardado")
        }
    }

}
