package com.example.framework.persistence

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity( tableName = "transacciones")
class TransaccionEntity(
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "price")
    var price: Double,
    @ColumnInfo(name = "description")
    var description: String,
    @ColumnInfo(name = "date")
    var date: String
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0
}