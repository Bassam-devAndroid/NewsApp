package com.example.newsapp.domain.usecases.app_entry

import com.example.newsapp.domain.manager.LocalUserManager
import kotlinx.coroutines.flow.Flow

class LoadAppEntryUseCase(
    private val localUserManager: LocalUserManager
) {
    operator fun invoke(): Flow<Boolean> {
       return localUserManager.readAppEntry()
    }
}