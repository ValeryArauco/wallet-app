package com.example.usecases

import com.example.data.TransaccionRepository
import com.example.domain.Transaccion

class GetBalance(val transaccionRepository: TransaccionRepository) {
    suspend fun invoke():Double?{
        return transaccionRepository.getBalance()
    }
}