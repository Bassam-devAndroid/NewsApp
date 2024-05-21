package com.example.newsapp.ui.domain.usecases.app_entry

import com.example.newsapp.ui.domain.manager.LocalUserManager

class SaveAppEntryUseCase(
    private val localUserManager: LocalUserManager
) {
    suspend operator fun invoke(){
        localUserManager.saveAppEntry()
    }
}