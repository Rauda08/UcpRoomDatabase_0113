package com.example.ucp2.ui.viewmodel.matakuliah

import com.example.ucp2.data.entity.MataKuliah


data class MatkulUIState(
    val MatkulEvent: MatkulEvent = MatkulEvent(),
    val isEntryValid: FormErrorState = FormErrorState(),
    val snackBarMessage:String? = null,
)

data class FormErrorState(
    val kode: String? = null,
    val nama: String? = null,
    val sks: String? = null,
    val semester: String? = null,
    val jenis: String? = null,
    val dosenPengampu: String? = null
){
    fun isValid(): Boolean{
        return kode == null && nama == null && jenisKelamin == null && alamat == null && kelas == null && angkatan == null
    }
}

//menyimpan input form ke dalam entity
fun MatkulEvent.toMatkulEntity(): MataKuliah = MataKuliah(
    kode = kode,
    nama = nama,
    sks = sks,
    semester = semester,
    jenis = jenis,
    dosenPengampu = dosenPengampu
)

data class MatkulEvent(
    val kode: String,
    val nama: String,
    val sks: String = "",
    val semester: String = "",
    val jenis: String = "",
    val dosenPengampu: String = ""
)