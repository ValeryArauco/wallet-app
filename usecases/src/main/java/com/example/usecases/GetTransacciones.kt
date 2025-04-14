package com.example.usecases

import com.example.data.TransaccionRepository
import com.example.domain.Transaccion

class GetTransacciones(val transaccionRepository: TransaccionRepository) {
    suspend fun invoke():List<Transaccion>{
        return transaccionRepository.getTransacciones()
    }
}