package com.example.ucp2.ui.viewmodel.matakuliah

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2.data.entity.Dosen
import com.example.ucp2.repository.RepositoryDosen
import com.example.ucp2.repository.RepositoryMataKuliah
import com.example.ucp2.ui.viewmodel.dosen.DosenEvent
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

class DetailMhsViewModel (
    savedStateHandle: SavedStateHandle,
    private val repositoryMataKuliah: RepositoryMataKuliah,

    ) : ViewModel() {
    private val kode: String = checkNotNull(savedStateHandle[DestinasiDetail.KODE])

    val detailUiState: StateFlow<DetailUiState> = repositoryMataKuliah.getMataKuliah(kode)
        .filterNotNull()
        .map {
            DetailUiState(
                detailUiEvent = it.toDetailUiEvent(),
                isLoading = false,
            )
        }
        .onStart {
            emit(DetailUiState(isLoading = true))
            delay(600)
        }
        .catch {
            emit(
                DetailUiState(
                    isLoading = false,
                    isError = true,
                    errorMessage = it.message ?: "Terjadi kesalahan",
                )
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(2000),
            initialValue = DetailUiState(
                isLoading = true,
            ),
        )

data class DetailUiState(
    val detailUiEvent: MataKuliahEvent = DosenEvent (),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
) {
    val isUiEventEmpty: Boolean
        get() = detailUiEvent == DosenEvent()

    val isUiEventNotEmpty: Boolean
        get() = detailUiEvent != DosenEvent ()
}

fun Dosen.toDetailUiEvent () : DosenEvent {
    return DosenEvent(
        nidn = nidn,
        nama = nama,
        jenisKelamin = jenisKelamin
    )
}