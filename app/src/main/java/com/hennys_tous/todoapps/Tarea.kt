package com.hennys_tous.todoapps

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tareas")
data class Tarea(
    @PrimaryKey var titulo: String ,
    @ColumnInfo (name = "descripcion") var descripcion : String
)

