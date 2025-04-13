package com.example.usecases

import com.example.data.TransaccionRepository
import com.example.domain.Transaccion

class SaveTransaccion(val repository: TransaccionRepository) {
    suspend fun invoke(transaccion: Transaccion){
        repository.saveTransaccion(transaccion)
    }
}

