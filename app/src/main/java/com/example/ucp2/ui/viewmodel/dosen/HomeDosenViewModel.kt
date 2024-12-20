package com.example.ucp2.ui.viewmodel.dosen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2.data.entity.Dosen
import com.example.ucp2.repository.RepositoryDosen
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeDosenViewModel(private val repositoryDosen: RepositoryDosen) : ViewModel() {

    private val _homeUiState = MutableStateFlow(DosenUIState())
    val homeUiState: StateFlow<DosenUIState> = _homeUiState

    init {
        loadDosenData()
    }

    private fun loadDosenData() {
        _homeUiState.value = _homeUiState.value.copy(isLoading = true)
        viewModelScope.launch {
            try {
                val dosenList = repositoryDosen.getAllDosen() // Pastikan data dosenList valid
                _homeUiState.value = _homeUiState.value.copy(
                    dosenList = dosenList,   // Menyimpan data dosen ke dalam state
                    isLoading = false
                )
            } catch (e: Exception) {
                _homeUiState.value = _homeUiState.value.copy(
                    isError = true,
                    isLoading = false,
                    errorMessage = "Gagal memuat data"
                )
            }
        }
    }

    fun saveData() {
        val currentEvent = _homeUiState.value.dosenEvent
        if (validateFields()) {
            viewModelScope.launch {
                try {
                    repositoryDosen.insertDosen(currentEvent.toDosenEntity())
                    _homeUiState.value = _homeUiState.value.copy(
                        snackBarMessage = "Data Berhasil Disimpan",
                        dosenEvent = DosenEvent(), // reset input form
                        isEntryValid = FormErrorState() // reset error state
                    )
                    loadDosenData() // Reload list setelah data disimpan
                } catch (e: Exception) {
                    _homeUiState.value = _homeUiState.value.copy(
                        snackBarMessage = "Data Gagal Disimpan"
                    )
                }
            }
        } else {
            _homeUiState.value = _homeUiState.value.copy(
                snackBarMessage = "Data tidak valid. Periksa kembali data anda"
            )
        }
    }

    private fun validateFields(): Boolean {
        val event = _homeUiState.value.dosenEvent
        val errorState = FormErrorState(
            nidn = if (event.nidn.isNotEmpty()) null else "NIDN tidak boleh kosong",
            nama = if (event.nama.isNotEmpty()) null else "Nama tidak boleh kosong",
            jenisKelamin = if (event.jenisKelamin.isNotEmpty()) null else "Jenis Kelamin tidak boleh kosong"
        )
        _homeUiState.value = _homeUiState.value.copy(isEntryValid = errorState)
        return errorState.isValid()
    }
    fun updateState(newEvent: DosenEvent) {
        _homeUiState.value = _homeUiState.value.copy(dosenEvent = newEvent)
    }

    fun resetSnackBarMessage() {
        _homeUiState.value = _homeUiState.value.copy(
            snackBarMessage = null
        )
    }
}

data class DosenUIState(
    val dosenEvent: DosenEvent = DosenEvent(),
    val isEntryValid: FormErrorState = FormErrorState(),
    val snackBarMessage: String? = null,
    val isLoading: Boolean = false,
    val dosenList: List<Dosen> = emptyList(),
    val isError: Boolean = false,
    val errorMessage: String? = null
)

data class FormErrorState(
    val nidn: String? = null,
    val nama: String? = null,
    val jenisKelamin: String? = null
) {
    fun isValid(): Boolean {
        return nidn == null && nama == null && jenisKelamin == null
    }
}

fun DosenEvent.toDosenEntity(): Dosen = Dosen(
    nidn = nidn,
    nama = nama,
    jenisKelamin = jenisKelamin
)

data class DosenEvent(
    val nidn: String = "",
    val nama: String = "",
    val jenisKelamin: String = ""
)

