package com.sargis.khlopuzyan.connector.session

import com.sargis.khlopuzyan.core.data.util.DataStoreUtil
import com.sargis.khlopuzyan.core.domain.util.SensorEventUtil
import com.sargis.khlopuzyan.session.data.repo.SessionRepoImpl
import com.sargis.khlopuzyan.session.domain.repo.SessionRepo
import com.sargis.khlopuzyan.session.domain.usecase.*
import com.sargis.khlopuzyan.session.presenter.SessionViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

fun sessionModule() = module {

    // DataStoreUtil
    single {
        DataStoreUtil(androidContext())
    }

    // SensorEventUtil
    single {
        SensorEventUtil()
    }

    // Repositories
    factory<SessionRepo> {
        SessionRepoImpl(get(), get())
    }

    // UseCases
    factory<SaveSessionStateUseCase> {
        SaveSessionStateUseCaseImpl(get())
    }

    factory<GetSessionCountUseCase> {
        GetSessionCountUseCaseImpl(get())
    }

    factory<CalculateFontSizeForSensorEventUseCase> {
        CalculateFontSizeForSensorEventUseCaseImpl(get())
    }

    // ViewModel
    viewModel {
        SessionViewModel(get(), get(), get())
    }

}