package com.example.newsapp.ui.domain.usecases

import com.example.newsapp.ui.domain.manager.LocalUserManager
import kotlinx.coroutines.flow.Flow

class LoadAppEntryUseCase(
    private val localUserManager: LocalUserManager
) {
    suspend operator fun invoke(): Flow<Boolean> {
       return localUserManager.readAppEntry()
    }
}