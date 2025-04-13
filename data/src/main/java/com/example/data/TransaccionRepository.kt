package com.example.data

import com.example.data.transaccion.ITransaccionLocalDataSource
import com.example.domain.Transaccion

class TransaccionRepository(private val localDataSource: ITransaccionLocalDataSource) {
    suspend fun saveTransaccion(transaccion: Transaccion):Boolean{
        this.localDataSource.saveTransaccion(transaccion)
        return true
    }
}
