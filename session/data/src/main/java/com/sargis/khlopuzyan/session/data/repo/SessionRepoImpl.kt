package com.sargis.khlopuzyan.session.data.repo

import com.sargis.khlopuzyan.core.data.util.DataStoreUtil
import com.sargis.khlopuzyan.core.domain.constant.Constants
import com.sargis.khlopuzyan.core.domain.util.SensorEventUtil
import com.sargis.khlopuzyan.session.domain.repo.SessionRepo
import java.util.*

class SessionRepoImpl(
    private val dataStoreUtil: DataStoreUtil,
    private val sensorEventUtil: SensorEventUtil
) : SessionRepo {

    override suspend fun getSessionCount(): Int? {
        return dataStoreUtil.readIntFromDataStore(Constants.DataStore.SESSION_COUNT)
    }

    override suspend fun saveSessionCount(count: Int) = dataStoreUtil.saveIntInDataStore(
        Constants.DataStore.SESSION_COUNT,
        count
    )

    override suspend fun getLastSessionEndDate(): Date? {
        val datetimeInMillis =
            dataStoreUtil.readLongFromDataStore(Constants.DataStore.SESSION_END_DATE)
        return if (datetimeInMillis != null) {
            Date(datetimeInMillis)
        } else {
            null
        }
    }

    override suspend fun saveCurrentDate() {
        dataStoreUtil.saveLongInDataStore(
            Constants.DataStore.SESSION_END_DATE,
            Date().time
        )
    }

    override fun calculateDeviceZAxisRotationDegree(sensorEventValues: FloatArray): Float {
        return sensorEventUtil.calculateDeviceZAxisRotationDegree(sensorEventValues)
    }

}