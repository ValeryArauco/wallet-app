package com.example.framework.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy

@Dao
interface ITransaccionEntityDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(transaccionEntity: TransaccionEntity)
}