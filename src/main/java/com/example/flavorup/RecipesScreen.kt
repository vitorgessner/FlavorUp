package com.example.flavorup

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun recipesScreen(
    ingredients: String?,
    level: String?,
    time: String?,
    vegetarian: Boolean,
    vegan: Boolean,
    glutenFree: Boolean,
    navController: NavController
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFFEA)),
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(18.dp, 18.dp, 18.dp, 0.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "FlavorUp",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
            )

            Button(
                onClick = {

                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFF49664)
                ),
                modifier = Modifier.size(35.dp)
            ) {
                Text(
                    text = "",
                )
            }
        }

        Row() {
            Text(
                text = "_________________________________________________________",
                modifier = Modifier.padding(18.dp, 0.dp),
                color = Color(0xFFF49664)
            )
        }

        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 24.dp, 0.dp)
        ) {
            Text(
                text = "Receitas compatíveis",
                fontSize = 20.sp,
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, top = 24.dp, end = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(
                space = 5.dp,
                alignment = Alignment.CenterHorizontally
            ),
        ) {
            Text(
                text = "$level",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.background(Color(0xFFF49664), shape = RoundedCornerShape(10.dp))
                    .padding(vertical = 4.dp, horizontal = 20.dp)
            )

            Text(
                text = "$time min",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.background(Color(0xFFF49664), shape = RoundedCornerShape(10.dp))
                    .padding(vertical = 4.dp, horizontal = 20.dp)
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(
                space = 5.dp,
                alignment = Alignment.CenterHorizontally
            )
        ) {
            if (vegetarian) Text(
                text = "Vegetariano",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.background(Color(0xFFF49664), shape = RoundedCornerShape(10.dp))
                    .padding(vertical = 4.dp, horizontal = 12.dp)
            )

            if (vegan) Text(
                text = "Vegano",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.background(Color(0xFFF49664), shape = RoundedCornerShape(10.dp))
                    .padding(vertical = 4.dp, horizontal = 12.dp)
            )

            if (glutenFree) Text(
                text = "Sem glúten",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.background(Color(0xFFF49664), shape = RoundedCornerShape(10.dp))
                    .padding(vertical = 4.dp, horizontal = 12.dp)
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, top = 0.dp, end = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(
                space = 5.dp,
                alignment = Alignment.CenterHorizontally
            )
        ) {
            Text(
                text = "$ingredients",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.background(Color(0xFFF49664), shape = RoundedCornerShape(10.dp))
                    .padding(vertical = 4.dp, horizontal = 12.dp)
            )
        }

        Row(
            modifier = Modifier
                .padding(top = 24.dp, start = 24.dp, end = 24.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = Color(0xFFFFFFFF),
                        shape = RoundedCornerShape(10.dp)
                    )
                    .size(140.dp)
                    .border(4.dp, Color(0xFFF3F1B5), shape = RoundedCornerShape(10.dp))
            ) {
                Text(
                    text = "Receita 1º",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(12.dp)
                )
            }
        }

        Row(
            modifier = Modifier
                .padding(top = 24.dp, start = 24.dp, end = 24.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = Color(0xFFFFFFFF),
                        shape = RoundedCornerShape(10.dp)
                    )
                    .size(140.dp)
                    .border(4.dp, Color(0xFFF3F1B5), shape = RoundedCornerShape(10.dp))
            ) {
                Text(
                    text = "Receita 2º",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(12.dp)
                )
            }
        }

        Row(
            modifier = Modifier
                .padding(top = 24.dp, start = 24.dp, end = 24.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = Color(0xFFFFFFFF),
                        shape = RoundedCornerShape(10.dp)
                    )
                    .size(140.dp)
                    .border(4.dp, Color(0xFFF3F1B5), shape = RoundedCornerShape(10.dp))
            ) {
                Text(
                    text = "Receita 3º",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(12.dp)
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp, start = 24.dp, end = 24.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {
                    navController.popBackStack()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFF49664)
                )
            ) { 
                Text(
                    text = "Voltar"
                )
            }
        }
    }
}

