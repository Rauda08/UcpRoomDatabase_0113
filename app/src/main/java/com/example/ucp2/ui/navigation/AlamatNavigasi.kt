package com.example.ucp2.ui.navigation

interface AlamatNavigasiHome {
    val route: String
}
object DestinasiHomeMenu : AlamatNavigasiHome {
    override val route = "home_menu"
}
interface AlamatNavigasiDosen {
    val route: String
}

object DestinasiHomeDosen : AlamatNavigasiDosen {
    override val route = "home_dosen"
}

object DestinasiDetailDosen : AlamatNavigasiDosen {
    override val route = "detail"
    const val NIDN = "nidn"
    val routesWithArg = "$route/{$NIDN}"
}


interface AlamatNavigasiMatkul {
    val route: String
}

object DestinasiHomeMatkul : AlamatNavigasiMatkul {
    override val route = "home_matkul"
}

object DestinasiDetailMatkul : AlamatNavigasiMatkul {
    override val route = "detail_matkul"
    const val KODE = "kode"
    val routesWithArg = "$route/{$KODE}"
}


object DestinasiUpdateMatkul : AlamatNavigasiMatkul {
    override val route = "update_matkul"
    const val KODE = "kode"
    val routesWithArg = "$route/{$KODE}"
}

