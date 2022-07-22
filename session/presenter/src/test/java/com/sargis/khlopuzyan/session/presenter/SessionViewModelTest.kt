package com.sargis.khlopuzyan.session.presenter

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.sargis.khlopuzyan.session.domain.usecase.CalculateFontSizeForSensorEventUseCase
import com.sargis.khlopuzyan.session.domain.usecase.GetSessionCountUseCase
import com.sargis.khlopuzyan.session.domain.usecase.SaveSessionStateUseCase
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
 * Created by Sargis Khlopuzyan on 7/20/2022.
 */
@ExperimentalCoroutinesApi
class SessionViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get: Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: SessionViewModel

    private val getSessionCountUseCaseMock = mockk<GetSessionCountUseCase>(relaxed = true)
    private val saveSessionStateUseCaseMock = mockk<SaveSessionStateUseCase>(relaxed = true)
    private val calculateFontSizeForSensorEventUseCaseMock =
        mockk<CalculateFontSizeForSensorEventUseCase>(relaxed = true)

    @Before
    fun setup() {
        viewModel = SessionViewModel(
            getSessionCountUseCaseMock,
            saveSessionStateUseCaseMock,
            calculateFontSizeForSensorEventUseCaseMock
        )
    }

    @After
    fun teardown() {
        clearMocks(getSessionCountUseCaseMock)
        clearMocks(saveSessionStateUseCaseMock)
        clearMocks(calculateFontSizeForSensorEventUseCaseMock)
    }

    @Test
    fun `in case rotation degree is smaller then -30, the text size should be 12`() {
        val fontSize = 12f
        val sessionEventValues = floatArrayOf(0f, 0f, 0f, 0f)

        every {
            calculateFontSizeForSensorEventUseCaseMock.calculateFontSizeForSensorEvent(
                sessionEventValues
            )
        } returns fontSize

        viewModel.deviceSensorChanged(sessionEventValues)
        val textSize = viewModel.stateFlowTextSize.value
        assertThat(textSize).isEqualTo(fontSize)

        verify(exactly = 1) {
            calculateFontSizeForSensorEventUseCaseMock.calculateFontSizeForSensorEvent(
                sessionEventValues
            )
        }
    }

    @Test
    fun `in case rotation degree is between -30 and 30, the text size should be 16`() {
        val fontSize = 16f
        val sessionEventValues = floatArrayOf(0f, 0f, 0f, 0f)

        every {
            calculateFontSizeForSensorEventUseCaseMock.calculateFontSizeForSensorEvent(
                sessionEventValues
            )
        } returns fontSize

        viewModel.deviceSensorChanged(sessionEventValues)
        val textSize = viewModel.stateFlowTextSize.value
        assertThat(textSize).isEqualTo(fontSize)

        verify(exactly = 1) {
            calculateFontSizeForSensorEventUseCaseMock.calculateFontSizeForSensorEvent(
                sessionEventValues
            )
        }
    }

    @Test
    fun `in case rotation degree is greater then 30, the text size should be 20`() {
        val fontSize = 20f
        val sessionEventValues = floatArrayOf(0f, 0f, 0f, 0f)

        every {
            calculateFontSizeForSensorEventUseCaseMock.calculateFontSizeForSensorEvent(
                sessionEventValues
            )
        } returns fontSize

        viewModel.deviceSensorChanged(sessionEventValues)
        val textSize = viewModel.stateFlowTextSize.value
        assertThat(textSize).isEqualTo(fontSize)

        verify(exactly = 1) {
            calculateFontSizeForSensorEventUseCaseMock.calculateFontSizeForSensorEvent(
                sessionEventValues
            )
        }
    }

}