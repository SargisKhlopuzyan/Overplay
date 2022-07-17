package com.sargis.khlopuzyan.session.domain.usecase

import com.sargis.khlopuzyan.session.domain.repo.SessionRepo

interface SaveSessionStateUseCase {
    suspend fun saveSessionState()
}

class SaveSessionStateUseCaseImpl(
    private val sessionRepo: SessionRepo
) : SaveSessionStateUseCase {

    override suspend fun saveSessionState() {
        sessionRepo.saveCurrentDate()
    }

}