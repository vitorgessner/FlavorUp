package com.example.flavorup

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()

            NavHost(
                navController = navController,
                startDestination = "home"
            ) {
                composable("home") {
                    Home(navController)
                }

                composable(
                    route = "receitas/{ingredients}/{level}/{time}/{vegetarian}/{vegan}/{glutenFree}"
                ) { backStackEntry ->

                    val ingredients = backStackEntry.arguments?.getString("ingredients")
                    val level = backStackEntry.arguments?.getString("level")
                    val time = backStackEntry.arguments?.getString("time")
                    val vegetarian = backStackEntry.arguments?.getString("vegetarian").toBoolean()
                    val vegan = backStackEntry.arguments?.getString("vegan").toBoolean()
                    val glutenFree = backStackEntry.arguments?.getString("glutenFree").toBoolean()

                    recipesScreen(
                        ingredients,
                        level,
                        time,
                        vegetarian,
                        vegan,
                        glutenFree,
                        navController
                    )
                }
            }
        }
    }
}

@Composable
fun Home(navController: NavController){
    var ingredients by remember { mutableStateOf("") }
    val selectedLevelOption = remember { mutableStateOf("Iniciante")}
    var sliderPosition by remember { mutableStateOf(30f) }
    val vegetarianChecked = remember { mutableStateOf(false) }
    val veganChecked = remember { mutableStateOf(false) }
    val glutenFreeChecked = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFFEA)),
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.padding(18.dp, 18.dp, 18.dp, 0.dp)
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
            modifier = Modifier.fillMaxWidth()
                .padding(0.dp, 24.dp, 0.dp)
        ) {
            Text(
                text = "O que tem na sua cozinha?",
                fontSize = 20.sp,
            )
        }

        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
                .padding(58.dp, 24.dp, 58.dp)
        ) {
            Text(
                text = "Nos diga o que você tem e traremos receitas perfeitas para você!",
                fontSize = 14.sp,
                color = Color(0xFFF49664),
                textAlign = TextAlign.Center
            )
        }

        Row(
            modifier = Modifier
                .padding(30.dp, 30.dp, 0.dp, 10.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .background(Color(0xFFF49664), shape = CircleShape)
            )

            Text(
                text = "Ingredientes que você possui",
                fontSize = 16.sp,
                modifier = Modifier.padding(12.dp, 2.dp)
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
        ){
            OutlinedTextField(
                value = ingredients,
                onValueChange = { newIngredients -> ingredients = newIngredients },
                placeholder = { Text("Insira ingredientes separados por vírgulas (exemplo, frango, arroz, cebola, shoyu)")},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(30.dp, 14.dp),
            )
        }

        Row(
            modifier = Modifier
                .padding(30.dp, 16.dp, 0.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .background(Color(0xFFF49664), shape = CircleShape)
            ) {
                Text(
                    text = "?",
                    color = Color(0xFFFFFFFF),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(8.dp, 1.dp)
                )
            }

            Text(
                text = "Nível de habilidade na cozinha",
                fontSize = 16.sp,
                modifier = Modifier.padding(12.dp, 2.dp)
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(24.dp, 10.dp)
                .fillMaxWidth()
        ) {
            RadioButton(
                selected = selectedLevelOption.value == "Iniciante",
                onClick = { selectedLevelOption.value = "Iniciante" },
                colors = RadioButtonColors(
                    selectedColor = Color(0xFFF49664),
                    unselectedColor = Color(0xFF673AB7),
                    disabledSelectedColor = Color(0xFF673AB7),
                    disabledUnselectedColor = Color(0xFF673AB7)
                )
            )
            Text("Iniciante")

            RadioButton(
                selected = selectedLevelOption.value == "Médio",
                onClick = { selectedLevelOption.value = "Médio" },
                modifier = Modifier.padding(10.dp, 0.dp, 0.dp, 0.dp),
                colors = RadioButtonColors(
                    selectedColor = Color(0xFFF49664),
                    unselectedColor = Color(0xFF673AB7),
                    disabledSelectedColor = Color(0xFF673AB7),
                    disabledUnselectedColor = Color(0xFF673AB7)
                )
            )
            Text("Médio")

            RadioButton(
                selected = selectedLevelOption.value == "Avançado",
                onClick = { selectedLevelOption.value = "Avançado" },
                modifier = Modifier.padding(10.dp, 0.dp, 0.dp, 0.dp),
                colors = RadioButtonColors(
                    selectedColor = Color(0xFFF49664),
                    unselectedColor = Color(0xFF673AB7),
                    disabledSelectedColor = Color(0xFF673AB7),
                    disabledUnselectedColor = Color(0xFF673AB7)
                )
            )
            Text("Avançado")
        }

        Row(
            modifier = Modifier
                .padding(30.dp, 24.dp, 0.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .background(Color(0xFFF49664), shape = CircleShape)
            )

            Text(
                text = "Tempo disponível",
                fontSize = 16.sp,
                modifier = Modifier.padding(12.dp, 2.dp)
            )
        }

        Row(
            modifier = Modifier.padding(18.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Slider(
                value = sliderPosition,
                onValueChange = { sliderPosition = it },
                steps = 4,
                valueRange = 30f..180f,
                modifier = Modifier.weight(1f),
                colors = SliderDefaults.colors(
                    thumbColor = Color(0xFFF49664),
                    activeTrackColor = Color(0xFFF3B598),
                    inactiveTrackColor = Color(0x77C7C7C7),
                    activeTickColor = Color.White,
                    inactiveTickColor = Color(0xFFFFFFFF)
                )
            )
            Text(
                text = "${sliderPosition.toString()} min",
                fontSize = 16.sp,
                modifier = Modifier.padding(start = 12.dp)
                )
        }

        Row(
            modifier = Modifier
                .padding(30.dp, 24.dp, 0.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .background(Color(0xFFF49664), shape = CircleShape)
            )

            Text(
                text = "Preferências de dieta (opcional)",
                fontSize = 16.sp,
                modifier = Modifier.padding(12.dp, 2.dp)
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(20.dp, 10.dp)
                .fillMaxWidth()
        ) {
            Checkbox(
                checked = vegetarianChecked.value,
                onCheckedChange = { isChecked -> vegetarianChecked.value = isChecked },
                colors = CheckboxDefaults.colors(
                    checkedColor = Color(0xFFF49964),
                    uncheckedColor = Color(0xFF2A2828)
                )
            )
            Text("Vegetariano")

            Checkbox(
                checked = veganChecked.value,
                onCheckedChange = { isChecked -> veganChecked.value = isChecked },
                colors = CheckboxDefaults.colors(
                    checkedColor = Color(0xFFF49964),
                    uncheckedColor = Color(0xFF2A2828)
                )
            )
            Text("Vegano")

            Checkbox(
                checked = glutenFreeChecked.value,
                onCheckedChange = { isChecked -> glutenFreeChecked.value = isChecked },
                colors = CheckboxDefaults.colors(
                    checkedColor = Color(0xFFF49964),
                    uncheckedColor = Color(0xFF2A2828)
                )
            )

            Text("Sem glúten")
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {
                    val route = "receitas/" +
                            "${ingredients.ifBlank { "nenhum" }}/" +
                            "${selectedLevelOption.value}/" +
                            "${sliderPosition.toInt()}/" +
                            "${vegetarianChecked.value}/" +
                            "${veganChecked.value}/" +
                            "${glutenFreeChecked.value}"

                    navController.navigate(route)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFF49664)
                )
            ) {
                Text(
                    text = "Pesquisar receitas"
                )
            }
        }
    }
}