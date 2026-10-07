package com.example.anchor.data.repository

import com.example.anchor.data.local.daos.HabitDao
import com.example.anchor.data.local.daos.JournalDao
import com.example.anchor.data.local.daos.TaskDao
import com.example.anchor.data.local.daos.WaterIntakeDao
import com.example.anchor.data.local.entities.HabitEntity
import com.example.anchor.data.local.entities.JournalEntity
import com.example.anchor.data.local.entities.TaskEntitiy
import com.example.anchor.data.local.entities.WaterIntakeEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.time.ZoneId

class OfflineAnchorRepository(
    private val journalDao: JournalDao,
    private val habitDao: HabitDao,
    private val waterIntakeDao: WaterIntakeDao,
    private val taskDao: TaskDao
) : AnchorRepository {
    
    // Journal
    override fun getAllJournalsStream(): Flow<List<JournalEntity>> = journalDao.getAllJournals()
    override suspend fun insertJournal(journal: JournalEntity) = journalDao.insertJournal(journal)
    override suspend fun deleteJournal(journal: JournalEntity) = journalDao.deleteJournal(journal)

    // Habit
    override fun getAllHabitsStream(): Flow<List<HabitEntity>> = habitDao.getAllHabits()
    override suspend fun insertHabit(habit: HabitEntity) = habitDao.insertHabit(habit)
    override suspend fun deleteHabit(habit: HabitEntity) = habitDao.deleteHabit(habit)
    override suspend fun incrementStreak(habitId: String) {
        habitDao.incrementStreak(habitId)
    }

    //Task
    override fun getAllTasks(): Flow<List<TaskEntitiy>> = taskDao.getAllTasks()
    override suspend fun insertTask(task: TaskEntitiy) = taskDao.insertTask(task)
    override suspend fun deleteTask(task: TaskEntitiy) = taskDao.deleteTask(task)

    // Water
    override fun getWaterIntakeStream(date: LocalDate): Flow<WaterIntakeEntity?> {
        val millis = date.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
        return waterIntakeDao.getWaterIntakeForDate(millis)
    }

    override suspend fun upsertWaterIntake(waterIntake: WaterIntakeEntity) = 
        waterIntakeDao.upsertWaterIntake(waterIntake)
}
