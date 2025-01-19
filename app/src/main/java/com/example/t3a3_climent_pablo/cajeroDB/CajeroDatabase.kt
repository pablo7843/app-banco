package com.example.t3a3_climent_pablo.cajeroDB

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.t3a3_climent_pablo.dao.CajeroDAO
import com.example.t3a3_climent_pablo.entitites.CajeroEntity

@Database(entities = [CajeroEntity::class], version =1)
abstract class CajeroDatabase : RoomDatabase() {
    abstract fun cajeroDAO(): CajeroDAO

}