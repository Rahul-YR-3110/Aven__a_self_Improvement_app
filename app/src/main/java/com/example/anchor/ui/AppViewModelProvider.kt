package com.example.anchor.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.anchor.AnchorApplication
import com.example.anchor.data.local.entities.HabitEntity
import com.example.anchor.data.local.entities.JournalEntity
import com.example.anchor.data.local.entities.TaskEntitiy
import com.example.anchor.data.local.entities.WaterIntakeEntity
import com.example.anchor.data.repository.AnchorRepository
import com.example.anchor.ui.viewmodels.HabitViewModel
import com.example.anchor.ui.viewmodels.JournalViewModel
import com.example.anchor.ui.viewmodels.TaskViewModel
import com.example.anchor.ui.viewmodels.WaterViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.time.LocalDate

/**
 * Provides Factory to create instance of ViewModel for the entire Anchor app
 */
object AppViewModelProvider {
    val Factory = viewModelFactory {
        // Initializer for JournalViewModel
        initializer {
            JournalViewModel(anchorApplication()?.container?.anchorRepository ?: PreviewAnchorRepository)
        }
        // Initializer for HabitViewModel
        initializer {
            HabitViewModel(anchorApplication()?.container?.anchorRepository ?: PreviewAnchorRepository)
        }
        // Initializer for WaterViewModel
        initializer {
            WaterViewModel(anchorApplication()?.container?.anchorRepository ?: PreviewAnchorRepository)
        }
        // Initializer for TaskViewModel
        initializer {
            TaskViewModel(anchorApplication()?.container?.anchorRepository ?: PreviewAnchorRepository)
        }
    }
}

private object PreviewAnchorRepository : AnchorRepository {
    override fun getAllJournalsStream(): Flow<List<JournalEntity>> = flowOf(emptyList())
    override suspend fun insertJournal(journal: JournalEntity) {}
    override suspend fun deleteJournal(journal: JournalEntity) {}

    override fun getAllHabitsStream(): Flow<List<HabitEntity>> = flowOf(emptyList())
    override suspend fun insertHabit(habit: HabitEntity) {}
    override suspend fun deleteHabit(habit: HabitEntity) {}

    override fun getWaterIntakeStream(date: LocalDate): Flow<WaterIntakeEntity?> = flowOf(null)
    override suspend fun upsertWaterIntake(waterIntake: WaterIntakeEntity) {}
    override suspend fun incrementStreak(habitId: String) {}

    override fun getAllTasks(): Flow<List<TaskEntitiy>> = flowOf(emptyList())
    override suspend fun insertTask(task: TaskEntitiy) {}
    override suspend fun deleteTask(task: TaskEntitiy) {}
}

/**
 * Extension function that queries for [android.app.Application] object and returns an instance of
 * [AnchorApplication].
 */
fun CreationExtras.anchorApplication(): AnchorApplication? =
    this[AndroidViewModelFactory.APPLICATION_KEY] as? AnchorApplication
