package com.example.ucp2.ui.view.dosen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp2.data.entity.Dosen
import com.example.ucp2.ui.customwidget.TopAppBar
import com.example.ucp2.ui.theme.PinkDark
import com.example.ucp2.ui.theme.PinkLight
import com.example.ucp2.ui.theme.PinkMedium
import com.example.ucp2.ui.viewmodel.dosen.DetailDosenViewModel
import com.example.ucp2.ui.viewmodel.dosen.DetailUiState
import com.example.ucp2.ui.viewmodel.dosen.PenyediaViewModel
import com.example.ucp2.ui.viewmodel.dosen.toDosenEntity

@Preview(showBackground = true)
@Composable
fun DetailDosenView (
    modifier: Modifier = Modifier,
    viewModel: DetailDosenViewModel = viewModel(factory = PenyediaViewModel.Factory),
    onBack: () -> Unit = { }
){
    Scaffold(
        modifier= Modifier
            .fillMaxSize()
            .padding(16.dp)
            .padding(top = 18.dp),
        topBar = {
            TopAppBar(
                judul = "Detail Dosen",
                showBackButton = true,
                onBack = onBack,
                modifier = modifier
            )
        },
    ) { innerPadding ->
        val detailUiState by viewModel.detailUiState.collectAsState()

        BodyDetailDosen(
            modifier = Modifier.padding(innerPadding),
            detailUiState = detailUiState,
        )
    }
}

@Composable
fun BodyDetailDosen (
    modifier: Modifier = Modifier,
    detailUiState: DetailUiState = DetailUiState()
) {
    when {
        detailUiState.isLoading -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = PinkMedium)
            }
        }

        detailUiState.isUiEventNotEmpty -> {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                ItemDetailDosen(
                    dosen = detailUiState.detailUiEvent.toDosenEntity(),
                    modifier = Modifier
                )
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
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = PinkMedium
                )
            }
        }
    }
}

@Composable
fun ItemDetailDosen (
    modifier: Modifier = Modifier,
    dosen: Dosen
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = PinkLight,
            contentColor = PinkDark
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            ComponentDetailDosen(judul = "NIDN", isinya = dosen.nidn)
            Spacer(modifier = Modifier.padding(4.dp))

            ComponentDetailDosen(judul = "Nama", isinya = dosen.nama)
            Spacer(modifier = Modifier.padding(4.dp))

            ComponentDetailDosen(judul = "Jenis Kelamin", isinya = dosen.jenisKelamin)
        }
    }
}

@Composable
fun ComponentDetailDosen (
    modifier: Modifier = Modifier,
    judul: String,
    isinya: String,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "$judul : ",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = PinkDark,
            fontFamily = FontFamily.Default
        )
        Text(
            text = isinya,
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal,
            color = PinkMedium
        )
    }
}
