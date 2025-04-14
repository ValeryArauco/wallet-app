package com.example.framework.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.domain.Transaccion

@Dao
interface ITransaccionEntityDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(transaccionEntity: TransaccionEntity)

    @Query("SELECT * FROM transacciones")
    fun getTransacciones(): List<TransaccionEntity>

    @Query("DELETE FROM transacciones WHERE name = :name AND price = :price AND description = :description AND date = :date")
    suspend fun deleteByProperties(name: String, price: Double, description: String, date: String)

}