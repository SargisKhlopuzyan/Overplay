package com.sargis.khlopuzyan.session.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.sargis.khlopuzyan.core.domain.constant.Constants
import com.sargis.khlopuzyan.session.domain.MainCoroutineRule
import com.sargis.khlopuzyan.session.domain.repo.SessionRepo
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Created by Sargis Khlopuzyan on 7/21/2022.
 */
@ExperimentalCoroutinesApi
class CalculateFontSizeForSensorEventUseCaseTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get: Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var calculateFontSizeForSensorEventUseCase: CalculateFontSizeForSensorEventUseCase

    private val sessionRepoMock = mockk<SessionRepo>(relaxed = true)

    @Before
    fun setup() {
        calculateFontSizeForSensorEventUseCase = CalculateFontSizeForSensorEventUseCaseImpl(
            sessionRepoMock
        )
    }

    @After
    fun teardown() {
        clearMocks(sessionRepoMock)
    }

    @Test
    fun `if the device rotated more than 30 degrees to the user left, text size should be ROTATION_LEFT_FONT_SIZE`() {
        val deviceRotationDegree = -40f
        val sessionEventValues = floatArrayOf(0f, 0f, 0f, 0f)

        every {
            sessionRepoMock.calculateDeviceZAxisRotationDegree(sessionEventValues)
        } returns deviceRotationDegree

        val textSize = calculateFontSizeForSensorEventUseCase.calculateFontSizeForSensorEvent(
            sessionEventValues
        )

        assertThat(textSize).isEqualTo(Constants.App.ROTATION_LEFT_FONT_SIZE)

        verify(exactly = 1) {
            sessionRepoMock.calculateDeviceZAxisRotationDegree(sessionEventValues)
        }
    }

    @Test
    fun `if the device rotated more than 30 degrees to the user left, text size should be ROTATION_RIGHT_FONT_SIZE`() {
        val deviceRotationDegree = 40f
        val sessionEventValues = floatArrayOf(0f, 0f, 0f, 0f)

        every {
            sessionRepoMock.calculateDeviceZAxisRotationDegree(sessionEventValues)
        } returns deviceRotationDegree

        val textSize = calculateFontSizeForSensorEventUseCase.calculateFontSizeForSensorEvent(
            sessionEventValues
        )

        assertThat(textSize).isEqualTo(Constants.App.ROTATION_RIGHT_FONT_SIZE)

        verify(exactly = 1) {
            sessionRepoMock.calculateDeviceZAxisRotationDegree(sessionEventValues)
        }
    }

    @Test
    fun `if the device rotated less than 30 degrees to the user left and less than 30 degrees to the user left, text size should be ROTATION_DEFAULT_FONT_SIZE`() {
        val deviceRotationDegree = 20f
        val sessionEventValues = floatArrayOf(0f, 0f, 0f, 0f)

        every {
            sessionRepoMock.calculateDeviceZAxisRotationDegree(sessionEventValues)
        } returns deviceRotationDegree

        val textSize = calculateFontSizeForSensorEventUseCase.calculateFontSizeForSensorEvent(
            sessionEventValues
        )

        assertThat(textSize).isEqualTo(Constants.App.ROTATION_DEFAULT_FONT_SIZE)

        verify(exactly = 1) {
            sessionRepoMock.calculateDeviceZAxisRotationDegree(sessionEventValues)
        }
    }

}