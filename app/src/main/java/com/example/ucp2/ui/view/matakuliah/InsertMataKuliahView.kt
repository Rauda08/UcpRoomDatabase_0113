package com.example.ucp2.ui.view.matakuliah

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp2.ui.customwidget.TopAppBar
import com.example.ucp2.ui.navigation.AlamatNavigasiMatkul
import com.example.ucp2.ui.viewmodel.matakuliah.FormErrorState
import com.example.ucp2.ui.viewmodel.matakuliah.MataKuliahViewModel
import com.example.ucp2.ui.viewmodel.matakuliah.MatkulEvent
import com.example.ucp2.ui.viewmodel.matakuliah.MatkulUIState
import com.example.ucp2.ui.viewmodel.matakuliah.PenyediaViewModel
import kotlinx.coroutines.launch

object DestinasiInsertMatkul : AlamatNavigasiMatkul {
    override val route: String = "insert_matkul"
}
@Composable
fun InsertMataKuliahView(
    onBack: () -> Unit,
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MataKuliahViewModel = viewModel(factory = PenyediaViewModel.Factory)
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
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            TopAppBar(
                onBack = onBack,
                showBackButton = true,
                judul = "Tambah Mata Kuliah",
                modifier = Modifier
            )
            InsertBodyMataKuliah(
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
fun InsertBodyMataKuliah(
    modifier: Modifier = Modifier,
    onValueChange: (MatkulEvent) -> Unit,
    uiState: MatkulUIState,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FormMataKuliah(
            matkulEvent = uiState.matkulEvent,
            onValueChange = onValueChange,
            errorState = uiState.isEntryValid,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Simpan")
        }
    }
}

@Composable
fun FormMataKuliah(
    matkulEvent: MatkulEvent = MatkulEvent(),
    onValueChange: (MatkulEvent) -> Unit = {},
    errorState: FormErrorState = FormErrorState(),
    modifier: Modifier = Modifier
) {
    val listDosen = listOf("Kurniawan", "Eliza", "Hermawan")
    val semesterOptions = listOf("Genap", "Ganjil")
    val jenisOptions = listOf("Wajib", "Tidak")

    Column(modifier = modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = matkulEvent.kode,
            onValueChange = { onValueChange(matkulEvent.copy(kode = it)) },
            label = { Text("KODE") },
            isError = errorState.kode != null,
            placeholder = { Text("Masukan KODE") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = matkulEvent.nama,
            onValueChange = { onValueChange(matkulEvent.copy(nama = it)) },
            label = { Text("Nama") },
            isError = errorState.nama != null,
            placeholder = { Text("Masukan nama") },
            modifier = Modifier.fillMaxWidth()
        )
        if (errorState.nama != null) {
            Text(errorState.nama, color = Color.Red)
        }

        OutlinedTextField(
            value = matkulEvent.sks,
            onValueChange = { onValueChange(matkulEvent.copy(sks = it)) },
            label = { Text("SKS") },
            isError = errorState.sks != null,
            placeholder = { Text("Masukan SKS") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        if (errorState.sks != null) {
            Text(errorState.sks, color = Color.Red)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Semester")
        semesterOptions.forEach { option ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = matkulEvent.semester == option,
                    onClick = { onValueChange(matkulEvent.copy(semester = option)) }
                )
                Text(option)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Jenis")
        jenisOptions.forEach { option ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = matkulEvent.jenis == option,
                    onClick = { onValueChange(matkulEvent.copy(jenis = option)) }
                )
                Text(option)
            }
        }

        DropdownMenuField(
            label = "Nama Dosen Pengampu",
            options = listDosen,
            selectedOption = matkulEvent.dosenPengampu,
            onOptionSelected = { onValueChange(matkulEvent.copy(dosenPengampu = it)) },
            isError = errorState.dosenPengampu != null,
            errorMessage = errorState.dosenPengampu
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownMenuField(
    label: String,
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
    isError: Boolean = false,
    errorMessage: String? = null
) {
    var expanded by remember { mutableStateOf(false) }
    var currentSelection by remember { mutableStateOf(selectedOption) }

    Column {
        OutlinedTextField(
            value = currentSelection,
            onValueChange = {},
            readOnly = true,
            label = { Text(label) },
            trailingIcon = {
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "Dropdown Icon")
                }
            },
            isError = isError,
            modifier = Modifier.fillMaxWidth()
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        currentSelection = option
                        onOptionSelected(option)
                        expanded = false
                    }
                )
            }
        }

        if (isError && errorMessage != null) {
            Text(errorMessage, color = Color.Red, style = MaterialTheme.typography.bodySmall)
        }
    }
}
