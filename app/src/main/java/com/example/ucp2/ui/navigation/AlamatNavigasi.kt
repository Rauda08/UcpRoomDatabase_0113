package com.example.ucp2.ui.navigation

// Interface untuk semua navigasi
interface AlamatNavigasiDosen {
    val route: String
}

// Navigasi ke Home Dosen
object DestinasiHomeDosen : AlamatNavigasiDosen {
    override val route = "home_dosen"
}

// Navigasi ke Home Menu
object DestinasiHomeMenu : AlamatNavigasiDosen {
    override val route = "home_menu"
}

// Navigasi ke Detail Dosen
object DestinasiDetail : AlamatNavigasiDosen {
    override val route = "detail"
    const val NIDN = "nidn"
    val routesWithArg = "$route/{$NIDN}"
}

// Navigasi untuk Insert Dosen
object DestinasiInsert : AlamatNavigasiDosen {
    override val route = "insert_dosen"
}

interface AlamatNavigasiMatkul {
    val route: String
}
// Navigasi untuk Home Mata Kuliah
object DestinasiHomeMatkul : AlamatNavigasiDosen {
    override val route = "home_mahasiswa"
}

// Navigasi ke Detail Mata Kuliah
object DestinasiDetailMatkul : AlamatNavigasiDosen {
    override val route = "detail_matkul"
    const val KODE = "kode"
    val routesWithArg = "$route/{$KODE}"
}

// Navigasi untuk Update Mata Kuliah
object DestinasiUpdateMatkul : AlamatNavigasiDosen {
    override val route = "update_matkul"
    const val KODE = "kode"
    val routesWithArg = "$route/{$KODE}"
}

// Navigasi untuk Insert Mata Kuliah
object DestinasiInsertMatkul : AlamatNavigasiDosen { // Menggunakan interface yang benar
    override val route = "insert_matkul"
}
