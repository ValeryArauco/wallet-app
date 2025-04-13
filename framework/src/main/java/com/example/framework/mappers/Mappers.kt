package com.example.framework.mappers

import com.example.domain.Transaccion
import com.example.framework.persistence.TransaccionEntity

fun Transaccion.toEntity(): TransaccionEntity {
    return TransaccionEntity(nombre, precio, descripcion, fecha)
}