package com.sargis.khlopuzyan.session.domain.usecase

import com.sargis.khlopuzyan.core.domain.constant.Constants
import com.sargis.khlopuzyan.session.domain.repo.SessionRepo

interface CalculateFontSizeForSensorEventUseCase {
    fun calculateFontSizeForSensorEvent(sensorEventValues: FloatArray): Float
}

class CalculateFontSizeForSensorEventUseCaseImpl(
    private val sessionRepo: SessionRepo
) : CalculateFontSizeForSensorEventUseCase {

    override fun calculateFontSizeForSensorEvent(sensorEventValues: FloatArray): Float {
        val degree = sessionRepo.calculateDeviceZAxisRotationDegree(sensorEventValues)

        return when {
            degree > Constants.App.ROTATION_LEFT_ANGLE -> {
                Constants.App.ROTATION_RIGHT_FONT_SIZE
            }
            degree < Constants.App.ROTATION_RIGHT_ANGLE -> {
                Constants.App.ROTATION_LEFT_FONT_SIZE
            }
            else -> {
                Constants.App.ROTATION_DEFAULT_FONT_SIZE
            }
        }

    }

}