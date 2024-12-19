package com.example.ucp2.dependenciesinjection

import android.content.Context
import com.example.ucp2.data.database.KrsDatabase
import com.example.ucp2.repository.LocalRepositoryDosen
import com.example.ucp2.repository.LocalRepositoryMK
import com.example.ucp2.repository.RepositoryDosen
import com.example.ucp2.repository.RepositoryMataKuliah

interface InterfaceContainerApp {
    val repositoryDosen: RepositoryDosen
    val repositoryMataKuliah: RepositoryMataKuliah
}

class ContainerApp(private val context: Context) : InterfaceContainerApp {
    override val repositoryDosen: RepositoryDosen by lazy {
        LocalRepositoryDosen(KrsDatabase.getDatabase(context).dosenDao())
    }
    override val repositoryMataKuliah: RepositoryMataKuliah by lazy {
        LocalRepositoryMK(KrsDatabase.getDatabase(context).mataKuliahDao())
    }
}