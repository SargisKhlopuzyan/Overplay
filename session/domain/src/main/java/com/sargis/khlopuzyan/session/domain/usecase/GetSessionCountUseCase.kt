package com.sargis.khlopuzyan.session.domain.usecase

import com.sargis.khlopuzyan.core.domain.constant.Constants
import com.sargis.khlopuzyan.core.domain.extension.elapsedTimeInMinutes
import com.sargis.khlopuzyan.session.domain.repo.SessionRepo

interface GetSessionCountUseCase {
    suspend fun getSessionCount(): Int
}

class GetSessionCountUseCaseImpl(
    private val sessionRepo: SessionRepo
) : GetSessionCountUseCase {

    override suspend fun getSessionCount(): Int {
        var sessionCount = sessionRepo.getSessionCount()

        // first launch
        if (sessionCount == null) {
            sessionCount = 1
            sessionRepo.saveSessionCount(sessionCount)
        } else {

            val lastSavedDate = sessionRepo.getLastSessionEndDate()

            if (lastSavedDate == null) {
                sessionCount++
                sessionRepo.saveCurrentDate()
                sessionRepo.saveSessionCount(sessionCount)
            } else {
                val diff = lastSavedDate.elapsedTimeInMinutes()
                if (diff >= Constants.App.NEW_SESSION_DURATION) {
                    sessionCount++
                    sessionRepo.saveSessionCount(sessionCount)
                }
            }
        }

        return sessionCount
    }

}