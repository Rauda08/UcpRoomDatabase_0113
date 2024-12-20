package com.example.ucp2.ui.viewmodel.matakuliah

import com.example.ucp2.data.entity.MataKuliah

data class HomeUiState(
    val listMhs: List<MataKuliah> = listOf(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = " "
)