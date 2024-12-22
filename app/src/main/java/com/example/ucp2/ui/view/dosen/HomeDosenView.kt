package com.example.ucp2.ui.view.dosen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp2.data.entity.Dosen
import com.example.ucp2.ui.customwidget.TopAppBar
import com.example.ucp2.ui.viewmodel.dosen.HomeDosenViewModel
import com.example.ucp2.ui.viewmodel.dosen.HomeUiState
import com.example.ucp2.ui.viewmodel.dosen.PenyediaViewModel


@Composable
fun HomeDosenView(
    viewModel: HomeDosenViewModel = viewModel(factory = PenyediaViewModel.Factory),
    onAddDosen: () -> Unit = {},
    onDetailClick: (String) -> Unit = {},
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        topBar = {
            Column {
                TopAppBar(
                    judul = "Daftar Dosen",
                    showBackButton = true,
                    onBack = onBack,
                    modifier = modifier
                )
                Text(
                    text = "Berikut adalah list dosen:",
                    color = Color(0xFFEE427C),
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .background(Color(0xFFFFEBEE))
                        .padding(8.dp)
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddDosen,
                containerColor = Color(0xFFF66596), // Pink
                contentColor = Color.White,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Tambah Dosen"
                )
            }
        }
    ) { innerPadding ->
        val homeUiState by viewModel.homeUiState.collectAsState()

        BodyHomeDosenView(
            homeUiState = homeUiState,
            onClick = {
                onDetailClick(it)
            },
            modifier = Modifier.padding(innerPadding)
        )
    }
}
@Composable
fun BodyHomeDosenView(
    homeUiState: HomeUiState,
    onClick: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    when {
        homeUiState.isLoading -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color(0xFFE91E63))
            }
        }

        homeUiState.listDosen.isEmpty() -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Tidak ada data dosen.",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFE91E63),
                    modifier = Modifier.padding(16.dp)
                )
            }
        }

        else -> {
            Column(modifier = modifier) {
                Spacer(modifier = Modifier.height(16.dp))
                ListDosen(
                    listDosen = homeUiState.listDosen,
                    onClick = { onClick(it) },
                    modifier = Modifier
                )
            }
        }
    }
}


@Composable
fun ListDosen(
    listDosen: List<Dosen>,
    modifier: Modifier = Modifier,
    onClick: (String) -> Unit = {}
) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        items(items = listDosen) { dosen ->
            CardDosen(
                dosen = dosen,
                onClick = { onClick(dosen.nidn) }
            )
        }
    }
}

@Composable
fun CardDosen(
    dosen: Dosen,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Card(
        onClick = onClick,
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFCE4EC),
            contentColor = Color(0xFFF33072)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .border(2.dp, Color(0xFFE91E63), MaterialTheme.shapes.medium) // Border di pinggir card
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            // Nama Dosen
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = null,
                    tint = Color(0xFFF33475)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = dosen.nama,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color(0xFFE91E63)
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            // NIDN Dosen
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.DateRange,
                    contentDescription = null,
                    tint = Color(0xFFE91E63)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = dosen.nidn,
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                    color = Color(0xFF880E4F)
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Jenis Kelamin
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = "Jenis Kelamin",
                    tint = Color(0xFFE91E63)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (dosen.jenisKelamin == "L") "Laki-Laki" else "Perempuan",
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                    color = Color(0xFF880E4F)
                )
            }
        }
    }
}

