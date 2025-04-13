package com.example.domain
import kotlinx.serialization.Serializable

@Serializable
data class Transaccion (
    val nombre: String,
    val precio: Double,
    val descripcion: String,
    val fecha: String
)