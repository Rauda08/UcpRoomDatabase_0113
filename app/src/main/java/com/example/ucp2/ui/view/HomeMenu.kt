package com.example.ucp2.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview(showBackground = true)
@Composable
fun HomeMenuViewPreview() {
    HomeMenuView(
        onDosenClick = { },
        onMatkulClick = { }
    )
}

@Composable
fun HomeMenuView(
    onDosenClick: () -> Unit,
    onMatkulClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color(0xFDEAABEF)) // Soft pink background
        ,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Welcome Text
        Text(
            text = "Selamat Datang",
            fontSize = 40.sp, // Larger size for the welcome text
            fontWeight = FontWeight.Bold,
            color = Color(0xFFB75C8E), // Deep pink color
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = "di aplikasi dosen dan matakuliah",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFB75C8E), // Deep pink color
            modifier = Modifier.padding(bottom = 40.dp)
        )

        // Dosen Menu Button
        Button(
            onClick = onDosenClick,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(0.8f),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD05E99)) // Light pink for button
        ) {
            Text(
                text = "Menu Dosen",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        // Mata Kuliah Menu Button
        Button(
            onClick = onMatkulClick,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(0.8f),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD05E99)) // Light pink for button
        ) {
            Text(
                text = "Menu Mata Kuliah",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}
