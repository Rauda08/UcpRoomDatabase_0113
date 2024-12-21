package com.example.ucp2.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ucp2.ui.view.HomeMenuView
import com.example.ucp2.ui.view.dosen.DetailDosenView
import com.example.ucp2.ui.view.dosen.HomeDosenView
import com.example.ucp2.ui.view.dosen.InsertDosenView
import com.example.ucp2.ui.view.matakuliah.InsertMataKuliahView

@Composable
fun PengelolaHalaman(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = DestinasiDetailView.route
    ) {
        composable(
            route = DestinasiHomeMenu.route
        ) {
            HomeMenuView(
                onDosenClick = { nidn ->
                    navController.navigate(DestinasiHomeDosen.route)
                },
                onMatkulClick = {
                    navController.navigate(DestinasiHomeMatkul.route)
                }
            )
        }
        composable(
            route = DestinasiHomeDosen.route
        ) {
            HomeDosenView(
                onDetailClick = { nidn ->
                    navController.navigate("${DestinasiDetailDosen.route}/$nidn")
                },
                onAddDosen = {
                    navController.navigate(DestinasiInsertDosen.route)
                },
                modifier = modifier
            )
        }

        composable(
            route = DestinasiHomeMatkul.route
            DestinasiDetailDosen.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiDetailDosen.NIDN) {
                    type = NavType.StringType
                }
            )
        ) {
            val nidn = it.arguments?.getString(DestinasiDetailDosen.NIDN)

            nidn?.let { nidn ->
                DetailDosenView(
                    onBack = {
                        navController.popBackStack()
                    },
                    onEditClick = {
                        navController.navigate("${DestinasiUpdateDosen.route}/$nidn")
                    }
                )
            }
        }
    }
}
