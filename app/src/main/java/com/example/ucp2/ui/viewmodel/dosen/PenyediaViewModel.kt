package com.example.ucp2.ui.viewmodel.dosen

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.ucp2.DosenMatkulApp

object PenyediaViewModel {
    val Factory = viewModelFactory {
        // Inisialisasi DosenViewModel
        initializer {
            val application = this.DosenMatkulApp()
            val repository = application.containerApp.repositoryDosen
            DosenViewModel(repository)
        }

        // Inisialisasi HomeDosenViewModel
        initializer {
            val application = this.DosenMatkulApp()
            val repository = application.containerApp.repositoryDosen
            HomeDosenViewModel(repository)
        }

        // Inisialisasi DetailDosenViewModel dengan SavedStateHandle
        initializer {
            val application = this.DosenMatkulApp()
            val repository = application.containerApp.repositoryDosen
            DetailDosenViewModel(
                savedStateHandle = this.createSavedStateHandle(),
                repositoryDosen = repository
            )
        }
    }
}

// Extension function untuk mendapatkan instance aplikasi
fun CreationExtras.DosenMatkulApp(): DosenMatkulApp =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as DosenMatkulApp)
