package com.example.flavorup.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.flavorup.data.model.DietPreference
import com.example.flavorup.data.model.SkillLevel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onSearchClick: (List<String>) -> Unit,
    onFavoritesClick: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "FlavorUp",
                            style = MaterialTheme.typography.headlineMedium,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                        Button(
                            onClick = onFavoritesClick,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFF49664)
                            )
                        ) {
                            Icon(Icons.Default.Favorite, "Favoritos")
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                actions = {
                    IconButton(onClick = { /* Perfil do usuário */ }) {
                        Icon(
                            Icons.Default.AccountCircle,
                            contentDescription = "Perfil",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Título e subtítulo
            Text(
                text = "O que tem na sua cozinha?",
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = "Nos diga o que você tem e traremos receitas perfeitas para você!",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // Ingredientes
            IngredientSection(
                currentIngredient = uiState.currentIngredient,
                ingredients = uiState.filters.ingredients,
                onIngredientChange = viewModel::updateCurrentIngredient,
                onAddIngredient = viewModel::addIngredient,
                onRemoveIngredient = viewModel::removeIngredient
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Nível de habilidade
            SkillLevelSection(
                selectedLevel = uiState.filters.skillLevel,
                onLevelSelect = viewModel::updateSkillLevel
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Tempo disponível
            TimeSection(
                timeAvailable = uiState.filters.timeAvailable,
                onTimeChange = viewModel::updateTimeAvailable
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Preferências de dieta
            DietPreferencesSection(
                selectedPreferences = uiState.filters.dietPreferences,
                onTogglePreference = viewModel::toggleDietPreference
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Botão de pesquisar
            Button(
                onClick = {
                    if (uiState.filters.ingredients.isNotEmpty()) {
                        onSearchClick(uiState.filters.ingredients)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                enabled = uiState.filters.ingredients.isNotEmpty(),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Icon(
                    Icons.Default.Search,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Pesquisar receitas",
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
    }
}

@Composable
fun IngredientSection(
    currentIngredient: String,
    ingredients: List<String>,
    onIngredientChange: (String) -> Unit,
    onAddIngredient: () -> Unit,
    onRemoveIngredient: (String) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Default.RestaurantMenu,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Ingredientes que você possui",
                    style = MaterialTheme.typography.titleLarge
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = currentIngredient,
                onValueChange = onIngredientChange,
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text("Digite todos os ingredientes que você possui\n(exemplo: frango, arroz, cebola, feijão)")
                },
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline
                ),
                minLines = 3,
                trailingIcon = {
                    if (currentIngredient.isNotBlank()) {
                        IconButton(onClick = onAddIngredient) {
                            Icon(
                                Icons.Default.Add,
                                contentDescription = "Adicionar ingredientes",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            )

            if (ingredients.isNotEmpty()) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Tags:",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(8.dp))
                IngredientChips(
                    ingredients = ingredients,
                    onRemove = onRemoveIngredient
                )
            }
        }
    }
}

@Composable
fun IngredientChips(
    ingredients: List<String>,
    onRemove: (String) -> Unit
) {
    Column {
        ingredients.chunked(2).forEach { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                rowItems.forEach { ingredient ->
                    AssistChip(
                        onClick = { onRemove(ingredient) },
                        label = { Text(ingredient) },
                        trailingIcon = {
                            Icon(
                                Icons.Default.Close,
                                contentDescription = "Remover $ingredient",
                                modifier = Modifier.size(18.dp)
                            )
                        },
                        colors = AssistChipDefaults.assistChipColors(
                            containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                        )
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun SkillLevelSection(
    selectedLevel: SkillLevel,
    onLevelSelect: (SkillLevel) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Default.Star,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Nível de habilidade na cozinha",
                    style = MaterialTheme.typography.titleLarge
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                SkillLevelButton(
                    text = "Iniciante",
                    isSelected = selectedLevel == SkillLevel.BEGINNER,
                    onClick = { onLevelSelect(SkillLevel.BEGINNER) },
                    modifier = Modifier.weight(1f)
                )
                SkillLevelButton(
                    text = "Médio",
                    isSelected = selectedLevel == SkillLevel.MEDIUM,
                    onClick = { onLevelSelect(SkillLevel.MEDIUM) },
                    modifier = Modifier.weight(1f)
                )
                SkillLevelButton(
                    text = "Avançado",
                    isSelected = selectedLevel == SkillLevel.ADVANCED,
                    onClick = { onLevelSelect(SkillLevel.ADVANCED) },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
fun SkillLevelButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(48.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) MaterialTheme.colorScheme.primary
            else MaterialTheme.colorScheme.surfaceVariant,
            contentColor = if (isSelected) MaterialTheme.colorScheme.onPrimary
            else MaterialTheme.colorScheme.onSurfaceVariant
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Text(text, style = MaterialTheme.typography.labelLarge)
    }
}

@Composable
fun TimeSection(
    timeAvailable: Int,
    onTimeChange: (Int) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Default.Schedule,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Tempo disponível",
                    style = MaterialTheme.typography.titleLarge
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Minutos",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "$timeAvailable",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Slider(
                value = timeAvailable.toFloat(),
                onValueChange = { onTimeChange(it.toInt()) },
                valueRange = 15f..120f,
                steps = 20,
                colors = SliderDefaults.colors(
                    thumbColor = MaterialTheme.colorScheme.primary,
                    activeTrackColor = MaterialTheme.colorScheme.primary
                )
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("15 min", style = MaterialTheme.typography.bodySmall)
                Text("120 min", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

@Composable
fun DietPreferencesSection(
    selectedPreferences: List<DietPreference>,
    onTogglePreference: (DietPreference) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Default.LocalDining,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Preferências de dieta (opcional)",
                    style = MaterialTheme.typography.titleLarge
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = selectedPreferences.contains(DietPreference.VEGETARIAN),
                    onClick = { onTogglePreference(DietPreference.VEGETARIAN) },
                    label = { Text("Vegetariano") },
                    modifier = Modifier.weight(1f)
                )
                FilterChip(
                    selected = selectedPreferences.contains(DietPreference.VEGAN),
                    onClick = { onTogglePreference(DietPreference.VEGAN) },
                    label = { Text("Vegano") },
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            FilterChip(
                selected = selectedPreferences.contains(DietPreference.GLUTEN_FREE),
                onClick = { onTogglePreference(DietPreference.GLUTEN_FREE) },
                label = { Text("Sem glúten") },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}