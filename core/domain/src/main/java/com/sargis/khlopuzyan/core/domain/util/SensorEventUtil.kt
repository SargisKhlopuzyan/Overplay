package com.sargis.khlopuzyan.core.domain.util

import android.hardware.SensorManager

class SensorEventUtil {

    private val rotationMatrix = FloatArray(16)
    private val remappedRotationMatrix = FloatArray(16)

    fun calculateDeviceZAxisRotationDegree(sensorEventValues: FloatArray): Float {

        SensorManager.getRotationMatrixFromVector(
            rotationMatrix, sensorEventValues
        )

        // Remap coordinate system
        SensorManager.remapCoordinateSystem(
            rotationMatrix,
            SensorManager.AXIS_X,
            SensorManager.AXIS_Z,
            remappedRotationMatrix
        )

        // Convert to orientations
        val orientations = FloatArray(3)
        SensorManager.getOrientation(remappedRotationMatrix, orientations)

        for (i in 0..2) {
            orientations[i] = Math.toDegrees(orientations[i].toDouble()).toFloat()
        }

        return orientations[2]
    }

}