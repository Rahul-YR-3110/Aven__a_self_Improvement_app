package com.example.anchor.ui.pages.Screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.anchor.data.local.entities.JournalEntity
import com.example.anchor.data.local.entities.Mood
import com.example.anchor.ui.AppViewModelProvider
import com.example.anchor.ui.theme.AnchorTheme
import com.example.anchor.ui.viewmodels.JournalViewModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

val Mood.label: String
    get() = when (this) {
        Mood.SUNNY -> "Sunny"
        Mood.CLOUDY -> "Cloudy"
        Mood.NIGHT -> "Night"
    }

val Mood.icon: ImageVector
    get() = when (this) {
        Mood.SUNNY -> Icons.Default.WbSunny
        Mood.CLOUDY -> Icons.Default.Cloud
        Mood.NIGHT -> Icons.Default.NightsStay
    }

fun LocalDateTime.toRelativeFormattedString(): String {
    val today = LocalDate.now()
    val entryDate = this.toLocalDate()
    val timeFormatter = DateTimeFormatter.ofPattern("h:mm a")
    val timeString = this.format(timeFormatter).lowercase()
    val dateFormatter = DateTimeFormatter.ofPattern("MMM d,yyyy")
    return when {
        entryDate.isEqual(today) -> "Today, $timeString"
        entryDate.isEqual(today.minusDays(1)) -> "Yesterday, $timeString"
        else -> "${this.format(dateFormatter)} $timeString"
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JournalScreen(
    viewModel: JournalViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState by viewModel.journalUiState.collectAsState()
    var showAddSheet by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddSheet = true },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                shape = CircleShape,
                modifier = Modifier.size(64.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Journal Entry",
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Journal",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "${uiState.journalList.size} entries this week",
                fontSize = 15.sp,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                modifier = Modifier.padding(start = 12.dp, bottom = 16.dp)
            )

            if (uiState.journalList.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No entries yet! Tap the + button to add one.",
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                    )
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(bottom = 80.dp)
                ) {
                    itemsIndexed(uiState.journalList, key = { _, entry -> entry.id }) { index, entry ->
                        JournalEntryCard(
                            entry = entry,
                            isHighlighted = index == 0,
                            OnDelete = { viewModel.deleteJournalEntry(entry) }
                        )
                    }
                }
            }
        }

        if (showAddSheet) {
            AddJournalBottomSheet(
                onDismiss = { showAddSheet = false },
                onSave = { notes, selectedMood ->
                    viewModel.saveJournalEntry(notes, selectedMood)
                    showAddSheet = false
                }
            )
        }
    }
}

@Composable
fun JournalEntryCard(
    entry: JournalEntity,
    isHighlighted: Boolean,
    OnDelete: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = entry.mood.icon,
                        contentDescription = entry.mood.label,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = entry.timestamp.toRelativeFormattedString(),
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                Row {
                    IconButton(onClick = OnDelete) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete Journal Entry",
                            modifier = Modifier.size(25.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = entry.notes,
                fontSize = 15.sp,
                lineHeight = 22.sp,
                color = MaterialTheme.colorScheme.onSurface,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddJournalBottomSheet(
    onDismiss: () -> Unit,
    onSave: (notes: String, mood: Mood) -> Unit
) {
    var noteText by remember { mutableStateOf("") }
    var selectedMood by remember { mutableStateOf(Mood.SUNNY) }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface,
        dragHandle = {
            BottomSheetDefaults.DragHandle(
                color = MaterialTheme.colorScheme.primary
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "New Reflection",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = "How are you feeling?",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Mood.entries.forEach { mood ->
                    FilterChip(
                        selected = (selectedMood == mood),
                        onClick = { selectedMood = mood },
                        label = { Text(mood.label) },
                        leadingIcon = {
                            Icon(
                                imageVector = mood.icon,
                                contentDescription = mood.label,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    )
                }
            }

            OutlinedTextField(
                value = noteText,
                onValueChange = { noteText = it },
                placeholder = { Text("Write your thoughts...") },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                    focusedTextColor = MaterialTheme.colorScheme.onSurface,
                    unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                    focusedPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                maxLines = 6
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = onDismiss) {
                    Text("Cancel", color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = {
                        if (noteText.isNotBlank()) {
                            onSave(noteText, selectedMood)
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    enabled = noteText.isNotBlank()
                ) {
                    Text("Save")
                }
            }
        }
    }
}

@Preview
@Composable
fun JournalScreenPreview() {
    AnchorTheme(darkTheme = true) {
        JournalScreen()
    }
}
