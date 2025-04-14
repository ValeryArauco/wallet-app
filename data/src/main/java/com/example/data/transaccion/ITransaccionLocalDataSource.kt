package com.example.data.transaccion

import com.example.domain.Transaccion

interface ITransaccionLocalDataSource {
    suspend fun saveTransaccion(transaccion: Transaccion): Boolean
    suspend fun getTransacciones():List<Transaccion>
    suspend fun deleteTransaccion(transaccion: Transaccion): Boolean
    suspend fun getBalance(): Double?
}