package com.example.anchor.data.repository

import com.example.anchor.data.local.entities.HabitEntity
import com.example.anchor.data.local.entities.JournalEntity
import com.example.anchor.data.local.entities.TaskEntitiy
import com.example.anchor.data.local.entities.WaterIntakeEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

/**
 * Repository that provides insert, update, delete, and retrieve of [Anchor] data from a given data source.
 */
interface AnchorRepository {
    // Journal
    fun getAllJournalsStream(): Flow<List<JournalEntity>>
    suspend fun insertJournal(journal: JournalEntity)
    suspend fun deleteJournal(journal: JournalEntity)

    // Habit
    fun getAllHabitsStream(): Flow<List<HabitEntity>>
    suspend fun insertHabit(habit: HabitEntity)
    suspend fun deleteHabit(habit: HabitEntity)

    // Water
    fun getWaterIntakeStream(date: LocalDate): Flow<WaterIntakeEntity?>
    suspend fun upsertWaterIntake(waterIntake: WaterIntakeEntity)
    suspend fun incrementStreak(habitId: String)

    //Task
    fun getAllTasks(): Flow<List<TaskEntitiy>>
    suspend fun insertTask(task: TaskEntitiy)
    suspend fun deleteTask(task: TaskEntitiy)

}
