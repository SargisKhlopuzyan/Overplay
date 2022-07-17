package com.sargis.khlopuzyan.core.domain.constant

interface Constants {

    interface DataStore {
        companion object {
            const val OVERPLAY_PREFERENCES = "OVERPLAY_PREFERENCES"
            const val SESSION_END_DATE = "SESSION_END_DATE"
            const val SESSION_COUNT = "SESSION_COUNT"
        }
    }

    interface App {
        companion object {
            const val NEW_SESSION_DURATION = 10 // in minutes
            const val ROTATION_LEFT_ANGLE = 30 // degree
            const val ROTATION_RIGHT_ANGLE = -30 // degree

            // font sizes
            const val ROTATION_LEFT_FONT_SIZE = 12f
            const val ROTATION_RIGHT_FONT_SIZE = 20f
            const val ROTATION_DEFAULT_FONT_SIZE = 16f
        }
    }
}