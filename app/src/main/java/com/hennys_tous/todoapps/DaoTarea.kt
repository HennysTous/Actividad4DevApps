package com.hennys_tous.todoapps

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DaoTarea {

    @Query("SELECT * FROM tareas")
    suspend fun obtenerTareas():MutableList<Tarea>

    @Insert
    suspend fun agregarTarea(tarea: Tarea)

    @Query("UPDATE tareas set descripcion =:descripcion WHERE titulo =:titulo")
    suspend fun actualizarTarea(descripcion: String, titulo: String)

    @Query("DELETE FROM tareas WHERE titulo =:titulo")
    suspend fun borrarTarea(titulo: String)
}