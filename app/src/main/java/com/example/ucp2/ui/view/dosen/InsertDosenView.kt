@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.ucp2.ui.view.dosen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp2.ui.theme.PinkLight
import com.example.ucp2.ui.theme.PinkMedium
import com.example.ucp2.ui.viewmodel.dosen.DosenEvent
import com.example.ucp2.ui.viewmodel.dosen.DosenUIState
import com.example.ucp2.ui.viewmodel.dosen.DosenViewModel
import com.example.ucp2.ui.viewmodel.dosen.FormErrorState
import com.example.ucp2.ui.viewmodel.dosen.PenyediaViewModel
import kotlinx.coroutines.launch

object DestinasiInsertDosen {
    const val route: String = "insert_dosen"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsertDosenView(
    onBack: () -> Unit,
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DosenViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val uiState = viewModel.uiState
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(uiState.snackBarMessage) {
        uiState.snackBarMessage?.let { message ->
            coroutineScope.launch {
                snackbarHostState.showSnackbar(message)
                viewModel.resetSnackBarMessage()
            }
        }
    }

    Scaffold(
        modifier = modifier,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Tambah Dosen",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Kembali",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = PinkMedium
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .background(MaterialTheme.colorScheme.background)
        ) {
            InsertBodyDosen(
                uiState = uiState,
                onValueChange = { updateEvent -> viewModel.updateState(updateEvent) },
                onClick = {
                    viewModel.saveData()
                    onNavigate()
                }
            )
        }
    }
}

@Composable
fun InsertBodyDosen(
    modifier: Modifier = Modifier,
    onValueChange: (DosenEvent) -> Unit,
    uiState: DosenUIState,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = PinkLight) // Light pink background
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Formulir Dosen",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(bottom = 16.dp),
                textAlign = TextAlign.Center,
                color = PinkMedium // Pink color for the title
            )

            FormDosen(
                dosenEvent = uiState.dosenEvent,
                onValueChange = onValueChange,
                errorState = uiState.isEntryValid,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onClick,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = PinkMedium)
            ) {
                Text(text = "Simpan", color = Color.White)
            }
        }
    }
}

@Composable
fun FormDosen(
    dosenEvent: DosenEvent,
    onValueChange: (DosenEvent) -> Unit,
    errorState: FormErrorState,
    modifier: Modifier = Modifier
) {
    val jenisKelamin = listOf("Laki-laki", "Perempuan")

    Column(modifier = modifier.fillMaxWidth()) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = dosenEvent.nama,
            onValueChange = { onValueChange(dosenEvent.copy(nama = it)) },
            label = { Text("Nama") },
            isError = errorState.nama != null,
            placeholder = { Text("Masukkan nama") },
            colors = TextFieldDefaults.outlinedTextFieldColors(focusedBorderColor = PinkMedium) // Pink border
        )
        if (errorState.nama != null) {
            Text(text = errorState.nama ?: "", color = Color.Red, fontSize = 12.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = dosenEvent.nidn,
            onValueChange = { onValueChange(dosenEvent.copy(nidn = it)) },
            label = { Text("NIDN") },
            isError = errorState.nidn != null,
            placeholder = { Text("Masukkan NIDN") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            colors = TextFieldDefaults.outlinedTextFieldColors(focusedBorderColor = PinkMedium) // Pink border
        )
        if (errorState.nidn != null) {
            Text(text = errorState.nidn ?: "", color = Color.Red, fontSize = 12.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Jenis Kelamin", fontWeight = FontWeight.Bold, color = PinkMedium) // Pink color for text
        Row(modifier = Modifier.fillMaxWidth()) {
            jenisKelamin.forEach { jk ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    RadioButton(
                        selected = dosenEvent.jenisKelamin == jk,
                        onClick = { onValueChange(dosenEvent.copy(jenisKelamin = jk)) },
                        colors = RadioButtonDefaults.colors(selectedColor = PinkMedium)
                    )
                    Text(text = jk, color = PinkMedium) // Pink color for the radio button label
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewInsertDosenView() {
    MaterialTheme {
        InsertDosenView(onBack = {}, onNavigate = {})
    }
}

