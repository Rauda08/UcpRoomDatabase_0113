package com.example.ucp2.ui.viewmodel.matakuliah

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.ucp2.DosenMatkulApp

object PenyediaViewModel{
    val Factory = viewModelFactory {
        initializer {
            MataKuliahViewModel(
                DosenMatkulApp().containerApp.repositoryMataKuliah
            )
        }
        initializer{
            HomeMataKuliahViewModel(
                DosenMatkulApp().containerApp.repositoryMataKuliah
            )
        }

        initializer{
            DetailMataKuliahViewModel(
                createSavedStateHandle(),
                DosenMatkulApp().containerApp.repositoryMataKuliah,
            )
        }

        initializer{
            UpdateMataKuliahViewModel(
                createSavedStateHandle(),
                DosenMatkulApp().containerApp.repositoryMataKuliah,
            )
        }
    }
}
fun CreationExtras.DosenMatkulApp(): DosenMatkulApp =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as DosenMatkulApp)