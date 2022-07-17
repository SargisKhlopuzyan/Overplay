package com.sargis.khlopuzyan.session.presenter

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.sargis.khlopuzyan.core.presenter.runOnBackground
import com.sargis.khlopuzyan.session.domain.usecase.CalculateFontSizeForSensorEventUseCase
import com.sargis.khlopuzyan.session.domain.usecase.GetSessionCountUseCase
import com.sargis.khlopuzyan.session.domain.usecase.SaveSessionStateUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SessionViewModel constructor(
    private val getSessionCountUseCase: GetSessionCountUseCase,
    private val saveSessionStateUseCase: SaveSessionStateUseCase,
    private val calculateFontSizeForSensorEventUseCase: CalculateFontSizeForSensorEventUseCase
) : ViewModel(), DefaultLifecycleObserver {

    private val _stateFlowTextSize = MutableStateFlow(0.0f)
    val stateFlowTextSize: StateFlow<Float> = _stateFlowTextSize

    private val _stateFlowSessionCount = MutableStateFlow(0)
    val stateFlowSessionCount: StateFlow<Int> = _stateFlowSessionCount

    override fun onStart(owner: LifecycleOwner) {
        runOnBackground {
            _stateFlowSessionCount.value = getSessionCountUseCase.getSessionCount()
        }
        super.onStart(owner)
    }

    override fun onStop(owner: LifecycleOwner) {
        runOnBackground {
            saveSessionStateUseCase.saveSessionState()
        }
        super.onStop(owner)
    }

    fun deviceSensorChanged(sensorEventValues: FloatArray) {
        _stateFlowTextSize.value =
            calculateFontSizeForSensorEventUseCase.calculateFontSizeForSensorEvent(sensorEventValues)
    }

}