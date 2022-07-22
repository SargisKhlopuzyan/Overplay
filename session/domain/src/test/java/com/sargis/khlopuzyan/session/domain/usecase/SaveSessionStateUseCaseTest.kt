package com.sargis.khlopuzyan.session.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sargis.khlopuzyan.session.domain.MainCoroutineRule
import com.sargis.khlopuzyan.session.domain.repo.SessionRepo
import io.mockk.clearMocks
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Created by Sargis Khlopuzyan on 7/22/2022.
 */
@ExperimentalCoroutinesApi
class SaveSessionStateUseCaseTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get: Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var saveSessionStateUseCase: SaveSessionStateUseCase

    private val sessionRepoMock = mockk<SessionRepo>(relaxed = true)

    @Before
    fun setup() {
        saveSessionStateUseCase = SaveSessionStateUseCaseImpl(
            sessionRepoMock
        )
    }

    @After
    fun teardown() {
        clearMocks(sessionRepoMock)
    }

    @Test
    fun `test for saving session state`() {
        coEvery {
            runBlockingTest {
                sessionRepoMock.saveCurrentDate()
            }
        } returns Unit

        runBlockingTest {
            saveSessionStateUseCase.saveSessionState()
        }

        verify(exactly = 1) {
            runBlockingTest {
                saveSessionStateUseCase.saveSessionState()
            }
        }
    }
}