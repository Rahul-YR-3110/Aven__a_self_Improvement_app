package com.example.anchor.data

import android.content.Context
import com.example.anchor.data.local.AnchorDatabase
import com.example.anchor.data.repository.AnchorRepository
import com.example.anchor.data.repository.OfflineAnchorRepository

/**
 * Dependency Injection container at the application level.
 */
interface AppContainer {
    val anchorRepository: AnchorRepository
}

/**
 * [AppContainer] implementation that provides instance of [OfflineAnchorRepository]
 */
class DefaultAppContainer(private val context: Context) : AppContainer {
    /**
     * Implementation for [AnchorRepository]
     */
    override val anchorRepository: AnchorRepository by lazy {
        OfflineAnchorRepository(
            AnchorDatabase.getDatabase(context).journalDao(),
            AnchorDatabase.getDatabase(context).habitDao(),
            AnchorDatabase.getDatabase(context).taskDao()
        )
    }
}
