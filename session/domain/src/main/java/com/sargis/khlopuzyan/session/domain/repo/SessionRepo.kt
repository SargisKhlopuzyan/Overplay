package com.sargis.khlopuzyan.session.domain.repo

import java.util.*

interface SessionRepo {
    suspend fun getSessionCount(): Int?
    suspend fun saveSessionCount(count: Int)
    suspend fun getLastSessionEndDate(): Date?
    suspend fun saveCurrentDate()
    fun calculateDeviceZAxisRotationDegree(sensorEventValues: FloatArray): Float
}