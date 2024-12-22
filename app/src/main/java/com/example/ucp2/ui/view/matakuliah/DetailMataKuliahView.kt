package com.example.ucp2.ui.view.matakuliah

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp2.data.entity.MataKuliah
import com.example.ucp2.ui.theme.PinkBackground
import com.example.ucp2.ui.theme.PinkDark
import com.example.ucp2.ui.theme.PinkMedium
import com.example.ucp2.ui.theme.PinkPrimary
import com.example.ucp2.ui.viewmodel.matakuliah.DetailMataKuliahViewModel
import com.example.ucp2.ui.viewmodel.matakuliah.DetailUiState
import com.example.ucp2.ui.viewmodel.matakuliah.PenyediaViewModel
import com.example.ucp2.ui.viewmodel.matakuliah.toMatkulEntity

@Composable
fun DetailMataKuliahView(
    modifier: Modifier = Modifier,
    viewModel: DetailMataKuliahViewModel = viewModel(factory = PenyediaViewModel.Factory),
    onBack: () -> Unit = {},
    onEditClick: (String) -> Unit = {},
    onDeleteClick: () -> Unit = {}
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onEditClick(viewModel.detailUiState.value.detailUiEvent.kode)
                },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Mata Kuliah"
                )
            }
        }
    ) { innerPadding ->
        val detailUiState by viewModel.detailUiState.collectAsState()

        Column(modifier = Modifier.fillMaxSize()) {
            TopAppBar(
                showBackButton = true,
                onBack = onBack,
                modifier = modifier
            )

            Spacer(modifier = Modifier.height(16.dp))

            BodyDetailMataKuliah(
                modifier = Modifier.padding(innerPadding),
                detailUiState = detailUiState,
                onDeleteClick = {
                    viewModel.deleteMataKuliah()
                    onDeleteClick()
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    showBackButton: Boolean = true,
    onBack: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text("Detail Mata Kuliah", color = PinkPrimary) },
        navigationIcon = {
            if (showBackButton) {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = PinkPrimary
                    )
                }
            }
        },
        modifier = modifier,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = PinkBackground,
            titleContentColor = PinkPrimary
        )
    )
}


@Composable
fun BodyDetailMataKuliah(
    modifier: Modifier = Modifier,
    detailUiState: DetailUiState = DetailUiState(),
    onDeleteClick: () -> Unit = {}
) {
    var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false) }

    when {
        detailUiState.isLoading -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = PinkPrimary)
            }
        }

        detailUiState.isUiEventNotEmpty -> {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                ItemDetailMataKuliah(
                    mataKuliah = detailUiState.detailUiEvent.toMatkulEntity(),
                    modifier = Modifier
                )
                Spacer(modifier = Modifier.height(10.dp))
                Button(
                    onClick = {
                        deleteConfirmationRequired = true
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PinkDark
                    )
                ) {
                    Text(text = "Delete", color = Color.White, fontSize = 18.sp)
                }
                if (deleteConfirmationRequired) {
                    DeleteConfirmationDialog(
                        onDeleteConfirm = {
                            deleteConfirmationRequired = false
                            onDeleteClick()
                        },
                        onDeleteCancel = { deleteConfirmationRequired = false },
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }

        detailUiState.isUiEventEmpty -> {
            Box(
                modifier = modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Data tidak ditemukan",
                    modifier = Modifier.padding(16.dp),
                    color = PinkPrimary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}


@Composable
fun ItemDetailMataKuliah (
    modifier: Modifier = Modifier,
    mataKuliah: MataKuliah
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .border(2.dp, color = PinkPrimary)
            .padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = PinkBackground,
            contentColor = PinkPrimary
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, color = PinkPrimary)
                    .padding(8.dp)
            ) {
                Text(
                    text = "Detail Mata Kuliah",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = PinkPrimary,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            ComponentDetailMataKuliah(judul = "KODE", isinya = mataKuliah.kode)
            Spacer(modifier = Modifier.padding(4.dp))

            ComponentDetailMataKuliah(judul = "Nama", isinya = mataKuliah.nama)
            Spacer(modifier = Modifier.padding(4.dp))

            ComponentDetailMataKuliah(judul = "SKS", isinya = mataKuliah.sks)
            Spacer(modifier = Modifier.padding(4.dp))

            ComponentDetailMataKuliah(judul = "Semester", isinya = mataKuliah.semester)
            Spacer(modifier = Modifier.padding(4.dp))

            ComponentDetailMataKuliah(judul = "Jenis", isinya = mataKuliah.jenis)
            Spacer(modifier = Modifier.padding(4.dp))

            ComponentDetailMataKuliah(judul = "Dosen Pengampu", isinya = mataKuliah.dosenPengampu)
        }
    }
}

@Composable
fun ComponentDetailMataKuliah(
    modifier: Modifier = Modifier,
    judul: String,
    isinya: String
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "$judul : ",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = PinkPrimary
        )
        Text(
            text = isinya,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            color = PinkDark
        )
    }
}

@Composable
private fun DeleteConfirmationDialog(
    onDeleteConfirm: () -> Unit,
    onDeleteCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = { /* Do nothing */ },
        title = { Text("Delete Data", color = PinkPrimary) },
        text = { Text("Apakah anda yakin ingin menghapus data?", color = PinkDark) },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text(text = "Cancel", color = PinkMedium)
            }
        },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) {
                Text(text = "Yes", color = PinkPrimary)
            }
        }
    )
}


