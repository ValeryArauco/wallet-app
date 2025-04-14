package com.example.myapplication.wallet

import android.content.Context
import com.example.domain.Transaccion
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.usecases.DeleteTransaccion
import com.example.usecases.GetTransacciones
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionListViewModel @Inject constructor(private val getTransacciones: GetTransacciones, private val deleteTransaccion: DeleteTransaccion, @ApplicationContext private val context: Context): ViewModel() {
    sealed class TransactionUIState {
        object Loading: TransactionUIState()
        class Loaded(val list: List<Transaccion>): TransactionUIState()
        class Error(val message: String): TransactionUIState()
    }
    private val _state = MutableStateFlow<TransactionUIState>(TransactionUIState.Loading)
    val state : StateFlow<TransactionUIState> = _state

    fun loadTransacciones() {
        viewModelScope.launch(Dispatchers.IO){
            try {
                _state.value = TransactionUIState.Loading
                val transactions = getTransacciones.invoke()
                _state.value = TransactionUIState.Loaded(transactions)
            } catch (e: Exception) {
                _state.value = TransactionUIState.Error("Error al cargar transacciones: ${e.message}")
            }
        }
    }


    fun deleteTransaction(transaccion: Transaccion) {
        viewModelScope.launch {
            try {
                deleteTransaccion.invoke(transaccion)
                // Recargar la lista después de eliminar
                loadTransacciones()
            } catch (e: Exception) {
                _state.value = TransactionUIState.Error("Error al eliminar la transacción: ${e.message}")
            }
        }
    }
}