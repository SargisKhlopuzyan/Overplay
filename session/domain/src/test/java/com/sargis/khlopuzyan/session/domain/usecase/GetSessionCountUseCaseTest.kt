package com.sargis.khlopuzyan.session.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
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
import java.util.*

/**
 * Created by Sargis Khlopuzyan on 7/22/2022.
 */
@ExperimentalCoroutinesApi
class GetSessionCountUseCaseTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get: Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var getSessionCountUseCase: GetSessionCountUseCase

    private val sessionRepoMock = mockk<SessionRepo>(relaxed = true)

    @Before
    fun setup() {
        getSessionCountUseCase = GetSessionCountUseCaseImpl(
            sessionRepoMock
        )
    }

    @After
    fun teardown() {
        clearMocks(sessionRepoMock)
    }

    @Test
    fun `checks session count is one when session has just started`() {

        coEvery {
            sessionRepoMock.getSessionCount()
        } returns null

        coEvery {
            sessionRepoMock.getLastSessionEndDate()
        } returns null

        coEvery {
            sessionRepoMock.saveCurrentDate()
        } returns Unit

        coEvery {
            sessionRepoMock.saveSessionCount(any())
        } returns Unit

        runBlockingTest {
            val sessionCount = getSessionCountUseCase.getSessionCount()
            assertThat(sessionCount).isEqualTo(1)
        }

        verify(exactly = 1) {
            runBlockingTest {
                sessionRepoMock.getSessionCount()
            }
        }

        verify(exactly = 0) {
            runBlockingTest {
                sessionRepoMock.getLastSessionEndDate()
            }
        }

        verify(exactly = 1) {
            runBlockingTest {
                sessionRepoMock.saveSessionCount(any())
            }
        }

        verify(exactly = 0) {
            runBlockingTest {
                sessionRepoMock.saveCurrentDate()
            }
        }
    }

    @Test
    fun `checks session count increment when last session finished more then 10 minutes`() {

        val sessionCount = 1

        coEvery {
            sessionRepoMock.getSessionCount()
        } returns sessionCount

        val cal = Calendar.getInstance()
        cal.add(Calendar.MINUTE, -15)
        val lastSessionEndDate = cal.time

        coEvery {
            sessionRepoMock.getLastSessionEndDate()
        } returns lastSessionEndDate

        coEvery {
            sessionRepoMock.saveCurrentDate()
        } returns Unit

        coEvery {
            sessionRepoMock.saveSessionCount(any())
        } returns Unit

        runBlockingTest {
            val newSessionCount = getSessionCountUseCase.getSessionCount()
            assertThat(newSessionCount).isEqualTo(sessionCount + 1)
        }

        verify(exactly = 1) {
            runBlockingTest {
                sessionRepoMock.getSessionCount()
            }
        }

        verify(exactly = 1) {
            runBlockingTest {
                sessionRepoMock.getLastSessionEndDate()
            }
        }

        verify(exactly = 1) {
            runBlockingTest {
                sessionRepoMock.saveSessionCount(any())
            }
        }

        verify(exactly = 0) {
            runBlockingTest {
                sessionRepoMock.saveCurrentDate()
            }
        }
    }

    @Test
    fun `checks session count is the same when last session finished less then 10 minutes`() {

        val sessionCount = 1

        coEvery {
            sessionRepoMock.getSessionCount()
        } returns sessionCount

        val cal = Calendar.getInstance()
        cal.add(Calendar.MINUTE, -5)
        val lastSessionEndDate = cal.time

        coEvery {
            sessionRepoMock.getLastSessionEndDate()
        } returns lastSessionEndDate

        coEvery {
            sessionRepoMock.saveCurrentDate()
        } returns Unit

        coEvery {
            sessionRepoMock.saveSessionCount(any())
        } returns Unit

        runBlockingTest {
            val newSessionCount = getSessionCountUseCase.getSessionCount()
            assertThat(newSessionCount).isEqualTo(sessionCount)
        }

        verify(exactly = 1) {
            runBlockingTest {
                sessionRepoMock.getSessionCount()
            }
        }

        verify(exactly = 1) {
            runBlockingTest {
                sessionRepoMock.getLastSessionEndDate()
            }
        }

        verify(exactly = 0) {
            runBlockingTest {
                sessionRepoMock.saveSessionCount(any())
            }
        }

        verify(exactly = 0) {
            runBlockingTest {
                sessionRepoMock.saveCurrentDate()
            }
        }
    }
}