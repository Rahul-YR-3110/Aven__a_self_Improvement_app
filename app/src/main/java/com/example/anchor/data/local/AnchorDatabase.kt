package com.example.anchor.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.anchor.data.local.converters.Converters
import com.example.anchor.data.local.daos.HabitDao
import com.example.anchor.data.local.daos.JournalDao
import com.example.anchor.data.local.daos.TaskDao
import com.example.anchor.data.local.entities.HabitEntity
import com.example.anchor.data.local.entities.JournalEntity
import com.example.anchor.data.local.entities.TaskEntitiy

@Database(
    entities = [
        JournalEntity::class,
        HabitEntity::class,
        TaskEntitiy::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AnchorDatabase : RoomDatabase() {

    abstract fun journalDao(): JournalDao
    abstract fun habitDao(): HabitDao
    abstract fun taskDao(): TaskDao

    companion object {
        @Volatile
        private var Instance: AnchorDatabase? = null

        fun getDatabase(context: Context): AnchorDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    AnchorDatabase::class.java,
                    "anchor_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}
