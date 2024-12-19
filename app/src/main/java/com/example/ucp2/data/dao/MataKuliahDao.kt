package com.example.ucp2.data.dao

import androidx.room.Dao
import androidx.room.Insert
import com.example.ucp2.data.entity.MataKuliah


@Dao
interface MataKuliahDao {
    @Insert
    suspend fun insertMataKuliah(
        mataKuliah: MataKuliah
    )


}