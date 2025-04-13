package com.example.data.transaccion

import com.example.domain.Transaccion

interface ITransaccionLocalDataSource {
    suspend fun saveTransaccion(transaccion: Transaccion): Boolean
}