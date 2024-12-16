package com.example.ucp2.data.dao

import androidx.room.Insert
import com.example.ucp2.data.entity.Dosen

interface DosenDao {
    @Insert
    suspend fun insertDosen(
        dosen: Dosen
    )
}
