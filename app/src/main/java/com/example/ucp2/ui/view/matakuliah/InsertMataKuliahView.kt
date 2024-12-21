package com.example.ucp2.ui.view.matakuliah

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp2.ui.customwidget.TopAppBar
import com.example.ucp2.ui.navigation.AlamatNavigasiMatkul
import com.example.ucp2.ui.viewmodel.dosen.DosenViewModel
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
    onBack:() -> Unit,
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MataKuliahViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val uiState=viewModel.uiState
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(uiState.snackBarMessage) {
        uiState.snackBarMessage?.let { message ->
            coroutineScope.launch {
                snackbarHostState.showSnackbar(message) //tampilansnackbar
                viewModel.resetSnackBarMessage()
            }
        }
    }

    Scaffold (
        modifier = modifier,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ){ padding ->
        Column (
            modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp)
        ){
            TopAppBar(
                onBack = onBack,
                showBackButton = true,
                judul = "Tambah Mata Kuliah",
                modifier = Modifier

            )

            InsertBodyMataKuliah (
                uiState = uiState,
                onValueChange = { updateEvent ->
                    viewModel.updateState(updateEvent) //update state di viewmodel
                },
                onClick = {
                    viewModel.saveData() //simpan data
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
    Column (
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
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Simpan")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FormMataKuliah(
    matkulEvent: MatkulEvent = MatkulEvent(),
    onValueChange: (MatkulEvent) -> Unit = {},
    errorState: FormErrorState = FormErrorState(),
    modifier: Modifier = Modifier,
    DosenViewModel: DosenViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    // Mengambil data dosen dengan benar
    val DosenUIState by DosenViewModel.dosenUIState.collectAsState()
    val listDosen = DosenUIState.listDosen.map { it.nama }
    val jenis = listOf("Wajib", "Tidak Wajib")
    val semester = listOf("Genap", "Ganjil")

    Column(modifier = modifier.fillMaxWidth()) {
        // Kode Matkul
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = matkulEvent.kode,
            onValueChange = { onValueChange(matkulEvent.copy(kode = it)) },
            label = { Text("KODE Matkul") },
            isError = errorState.kode != null,
            placeholder = { Text("Masukkan Kode Matkul") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        errorState.kode?.let {
            Text(text = it, color = Color.Red)
        }

        // Nama Matkul
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = matkulEvent.nama,
            onValueChange = { onValueChange(matkulEvent.copy(nama = it)) },
            label = { Text("Nama Matkul") },
            isError = errorState.nama != null,
            placeholder = { Text("Masukkan Nama Matkul") }
        )
        errorState.nama?.let {
            Text(text = it, color = Color.Red)
        }

        // Jumlah SKS
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = matkulEvent.sks,
            onValueChange = { onValueChange(matkulEvent.copy(sks = it)) },
            label = { Text("SKS") },
            isError = errorState.sks != null,
            placeholder = { Text("Masukkan Jumlah SKS") }
        )
        errorState.sks?.let {
            Text(text = it, color = Color.Red)
        }

        // Semester
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Semester")
        Row(modifier = Modifier.fillMaxWidth()) {
            semester.forEach { semesterOption ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    RadioButton(
                        selected = matkulEvent.semester == semesterOption,
                        onClick = { onValueChange(matkulEvent.copy(semester = semesterOption)) },
                    )
                    Text(text = semesterOption)
                }
            }
        }

        // Jenis
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Jenis")
        Row(modifier = Modifier.fillMaxWidth()) {
            jenis.forEach { jenisOption ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    RadioButton(
                        selected = matkulEvent.jenis == jenisOption,
                        onClick = { onValueChange(matkulEvent.copy(jenis = jenisOption)) },
                    )
                    Text(text = jenisOption)
                }
            }
        }

        // Dropdown Dosen Pengampu
        DropdownMenuField(
            label = "Nama Dosen Pengampu",
            options = listDosen,
            selectedOption = matkulEvent.dosenPengampu,
            onOptionSelected = { selectedDosen ->
                onValueChange(matkulEvent.copy(dosenPengampu = selectedDosen))
            },
            isError = errorState.dosenPengampu != null,
            errorMessage = errorState.dosenPengampu
        )
    }
}

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
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Dropdown Icon"
                    )
                }
            },
            modifier = Modifier.fillMaxWidth(),
            isError = isError
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
            Text(
                text = errorMessage,
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

