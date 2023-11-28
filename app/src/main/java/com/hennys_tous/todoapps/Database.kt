package com.hennys_tous.todoapps

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Tarea::class],
    version = 2

)


abstract class Database: RoomDatabase() {

    abstract fun daoTarea(): DaoTarea
}