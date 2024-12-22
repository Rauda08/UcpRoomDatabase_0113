package com.example.ucp2.ui.view.matakuliah

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
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
import com.example.ucp2.data.entity.MataKuliah
import com.example.ucp2.ui.viewmodel.matakuliah.HomeMataKuliahViewModel
import com.example.ucp2.ui.viewmodel.matakuliah.HomeUiState
import com.example.ucp2.ui.viewmodel.matakuliah.PenyediaViewModel
import kotlinx.coroutines.launch

val PinkPrimary = Color(0xFFE876C2)
val PinkSecondary = Color(0xFFF8B7D3)
val PinkOnPrimary = Color.White
val PinkText = Color(0xFFAF5486)

@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun HomeMataKuliahView(
    viewModel: HomeMataKuliahViewModel = viewModel(factory = PenyediaViewModel.Factory),
    onAddMatkul: () -> Unit = { },
    onBack: () -> Unit,
    onDetailClick: (String) -> Unit = { },
    modifier: Modifier = Modifier
) {

    val customColorScheme = lightColorScheme(
        primary = PinkPrimary,
        secondary = PinkSecondary,
        onPrimary = PinkOnPrimary,
        onSecondary = Color.Black,
        background = Color.White,
        onBackground = Color.Black,
        surface = Color.White,
        onSurface = Color.Black
    )


    MaterialTheme(colorScheme = customColorScheme) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(top = 18.dp),
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Daftar Mata Kuliah",
                            color = PinkOnPrimary // Title color
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = onBack) {
                            Icon(
                                imageVector = Icons.Filled.Home,
                                contentDescription = "Back",
                                tint = PinkOnPrimary // Icon color
                            )
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = PinkPrimary, // Background color
                        titleContentColor = PinkOnPrimary // Title text color
                    ),
                    modifier = modifier
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = onAddMatkul,
                    shape = MaterialTheme.shapes.medium,
                    containerColor = PinkPrimary, // Floating action button background color
                    modifier = Modifier.padding(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Tambah Mata Kuliah",
                        tint = PinkOnPrimary
                    )
                }
            }
        ) { innerPadding ->
            val homeUiState by viewModel.homeUiState.collectAsState()

            BodyHomeMataKuliahView(
                homeUiState = homeUiState,
                onClick = {
                    onDetailClick(it)
                },
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}



@Composable
fun BodyHomeMataKuliahView(
    homeUiState: HomeUiState,
    onClick: (String) -> Unit = { },
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    when {
        homeUiState.isLoading -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        homeUiState.isError -> {
            LaunchedEffect(homeUiState.errorMessage) {
                homeUiState.errorMessage?.let { message ->
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(message)
                    }
                }
            }
        }

        homeUiState.listMataKuliah.isEmpty() -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Tidak ada data Mata Kuliah.",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = PinkText,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }

        else -> {
            ListMataKuliah(
                listMataKuliah = homeUiState.listMataKuliah,
                onClick = { onClick(it) },
                modifier = modifier
            )
        }
    }
}

@Composable
fun ListMataKuliah(
    listMataKuliah: List<MataKuliah>,
    modifier: Modifier = Modifier,
    onClick: (String) -> Unit = { }
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(
            items = listMataKuliah,
            itemContent = { matkul ->
                CardMataKuliah(
                    matkul = matkul,
                    onClick = { onClick(matkul.kode) }
                )
            }
        )
    }
}

@Composable
fun CardMataKuliah(
    matkul: MataKuliah,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = { }
) {
    Card(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = "",
                    tint = PinkPrimary
                )
                Spacer(modifier = Modifier.padding(4.dp))
                Text(
                    text = matkul.nama,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = PinkText
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.DateRange,
                    contentDescription = "",
                    tint = PinkPrimary
                )
                Spacer(modifier = Modifier.padding(4.dp))
                Text(
                    text = matkul.kode,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = PinkText
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.Home,
                    contentDescription = "",
                    tint = PinkPrimary
                )
                Spacer(modifier = Modifier.padding(4.dp))
                Text(
                    text = matkul.sks,
                    fontWeight = FontWeight.Bold,
                    color = PinkText
                )
            }
        }
    }
}
