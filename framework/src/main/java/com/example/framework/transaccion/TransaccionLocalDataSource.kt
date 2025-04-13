package com.example.framework.transaccion

import android.content.Context
import com.example.data.transaccion.ITransaccionLocalDataSource
import com.example.domain.Transaccion
import com.example.framework.mappers.toEntity
import com.example.framework.persistence.AppRoomDatabase
import com.example.framework.persistence.ITransaccionEntityDAO

class TransaccionLocalDataSource(val context: Context) : ITransaccionLocalDataSource {
    val transaccionEntityDAO:ITransaccionEntityDAO = AppRoomDatabase.getDatabase(context).transaccionDAO()
    override suspend fun saveTransaccion(transaccion: Transaccion): Boolean {
        transaccionEntityDAO.insert(transaccion.toEntity())
        return true
    }
}
