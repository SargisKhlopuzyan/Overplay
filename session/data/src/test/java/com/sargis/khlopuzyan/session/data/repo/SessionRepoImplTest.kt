package com.sargis.khlopuzyan.session.data.repo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.sargis.khlopuzyan.core.data.util.DataStoreUtil
import com.sargis.khlopuzyan.core.domain.constant.Constants
import com.sargis.khlopuzyan.core.domain.util.SensorEventUtil
import com.sargis.khlopuzyan.session.data.MainCoroutineRule
import com.sargis.khlopuzyan.session.domain.repo.SessionRepo
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.*

/**
 * Created by Sargis Khlopuzyan on 7/22/2022.
 */
@ExperimentalCoroutinesApi
class SessionRepoImplTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get: Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var sessionRepo: SessionRepo

    private val dataStoreUtilMock = mockk<DataStoreUtil>(relaxed = true)
    private val sensorEventUtilMock = mockk<SensorEventUtil>(relaxed = true)

    @Before
    fun setup() {
        sessionRepo = SessionRepoImpl(
            dataStoreUtilMock,
            sensorEventUtilMock
        )
    }

    @After
    fun teardown() {
        clearMocks(dataStoreUtilMock)
        clearMocks(sensorEventUtilMock)
    }

    @Test
    fun `checks session count when first launch`() {

        coEvery {
            dataStoreUtilMock.readIntFromDataStore(Constants.DataStore.SESSION_COUNT)
        } returns null

        runBlockingTest {
            val sessionCount = sessionRepo.getSessionCount()
            assertThat(sessionCount).isEqualTo(null)
        }

        verify(exactly = 1) {
            runBlockingTest {
                dataStoreUtilMock.readIntFromDataStore(Constants.DataStore.SESSION_COUNT)
            }
        }

    }

    @Test
    fun `checks session count when session is saved`() {

        val sessionCount = 5

        coEvery {
            dataStoreUtilMock.readIntFromDataStore(Constants.DataStore.SESSION_COUNT)
        } returns sessionCount

        runBlockingTest {
            val count = sessionRepo.getSessionCount()
            assertThat(count).isEqualTo(sessionCount)
        }

        verify(exactly = 1) {
            runBlockingTest {
                dataStoreUtilMock.readIntFromDataStore(Constants.DataStore.SESSION_COUNT)
            }
        }

    }

    @Test
    fun `test session count saving`() {

        val sessionCount = 5

        coEvery {
            dataStoreUtilMock.saveIntInDataStore(Constants.DataStore.SESSION_COUNT, sessionCount)
        } returns Unit

        coEvery {
            dataStoreUtilMock.readIntFromDataStore(Constants.DataStore.SESSION_COUNT)
        } returns sessionCount

        runBlockingTest {
            sessionRepo.saveSessionCount(sessionCount)
            val count = sessionRepo.getSessionCount()
            assertThat(count).isEqualTo(sessionCount)
        }

        verify(exactly = 1) {
            runBlockingTest {
                dataStoreUtilMock.saveIntInDataStore(
                    Constants.DataStore.SESSION_COUNT,
                    sessionCount
                )
            }
        }

        verify(exactly = 1) {
            runBlockingTest {
                dataStoreUtilMock.readIntFromDataStore(Constants.DataStore.SESSION_COUNT)
            }
        }

    }

    @Test
    fun `getLastSessionEndDate`() {

        val cal = Calendar.getInstance()
        cal.add(Calendar.MINUTE, -15)
        val lastSessionEndDate = cal.time

        coEvery {
            dataStoreUtilMock.readLongFromDataStore(Constants.DataStore.SESSION_END_DATE)
        } returns lastSessionEndDate.time

        runBlockingTest {
            val lastSessionDate = sessionRepo.getLastSessionEndDate()
            assertThat(lastSessionDate).isEqualTo(lastSessionDate)
        }

        verify(exactly = 1) {
            runBlockingTest {
                dataStoreUtilMock.readLongFromDataStore(Constants.DataStore.SESSION_END_DATE)
            }
        }
    }

    @Test
    fun `test current date saving`() {

        val cal = Calendar.getInstance()

        coEvery {
            dataStoreUtilMock.saveLongInDataStore(
                Constants.DataStore.SESSION_END_DATE,
                any()
            )
        } returns Unit

        coEvery {
            dataStoreUtilMock.readLongFromDataStore(
                Constants.DataStore.SESSION_END_DATE,
            )
        } returns cal.time.time

        runBlockingTest {
            sessionRepo.saveCurrentDate()

            val lastSessionEndDate = sessionRepo.getLastSessionEndDate()

            assertThat(lastSessionEndDate).isEqualTo(cal.time)
        }

        verify(exactly = 1) {
            runBlockingTest {
                dataStoreUtilMock.saveLongInDataStore(Constants.DataStore.SESSION_END_DATE, any())
            }
        }

        verify(exactly = 1) {
            runBlockingTest {
                dataStoreUtilMock.readLongFromDataStore(Constants.DataStore.SESSION_END_DATE)
            }
        }
    }

    @Test
    fun `test device Z axis rotation degree calculation when device rotated more than 30 degrees to the user left`() {

        val deviceRotationDegree = -40f

        val sessionEventValues = floatArrayOf(0f, 0f, 0f, 0f)

        every {
            sensorEventUtilMock.calculateDeviceZAxisRotationDegree(sessionEventValues)
        } returns deviceRotationDegree

        val degree = sessionRepo.calculateDeviceZAxisRotationDegree(sessionEventValues)

        assertThat(degree).isEqualTo(deviceRotationDegree)

        verify(exactly = 1) {
            sensorEventUtilMock.calculateDeviceZAxisRotationDegree(sessionEventValues)
        }
    }

    @Test
    fun `test device Z axis rotation degree calculation when device rotated more than 30 degrees to the user right`() {

        val deviceRotationDegree = 40f

        val sessionEventValues = floatArrayOf(0f, 0f, 0f, 0f)

        every {
            sensorEventUtilMock.calculateDeviceZAxisRotationDegree(sessionEventValues)
        } returns deviceRotationDegree

        val degree = sessionRepo.calculateDeviceZAxisRotationDegree(sessionEventValues)

        assertThat(degree).isEqualTo(deviceRotationDegree)

        verify(exactly = 1) {
            sensorEventUtilMock.calculateDeviceZAxisRotationDegree(sessionEventValues)
        }
    }

    @Test
    fun `test device Z axis rotation degree calculation when device rotated less than 30 degrees to the user left and less than 30 degrees to the user right`() {

        val deviceRotationDegree = 20f

        val sessionEventValues = floatArrayOf(0f, 0f, 0f, 0f)

        every {
            sensorEventUtilMock.calculateDeviceZAxisRotationDegree(sessionEventValues)
        } returns deviceRotationDegree

        val degree = sessionRepo.calculateDeviceZAxisRotationDegree(sessionEventValues)

        assertThat(degree).isEqualTo(deviceRotationDegree)

        verify(exactly = 1) {
            sensorEventUtilMock.calculateDeviceZAxisRotationDegree(sessionEventValues)
        }
    }
}