package com.khue.joliecafejp.utils

import androidx.compose.ui.unit.dp

class Constants {
    companion object {
        const val CATEGORY = "category"
        const val SEARCH = "search"
        const val IS_FAV = "isFav"
        const val PRODUCT_ID = "productId"
        const val CARTS = "carts"
        const val WEBCLIENT_ID =
            "819161778616-o1p3eai92m7ir256h6uvgteo09acd0oe.apps.googleusercontent.com"
        const val PREFERENCES_NAME = "jolie_preferences"
        const val PREFERENCES_USER_TOKEN = "userToken"
        const val PAGE_SIZE = 20
        const val BASE_URL = "https://joliecafe.herokuapp.com"
        const val API_GATEWAY = "/api/v1/jolie-cafe"

        const val SNACK_BAR_STATUS_SUCCESS = 1
        const val SNACK_BAR_STATUS_DISABLE = 0
        const val SNACK_BAR_STATUS_ERROR = -1

        val listNotificationTab = listOf(
            "All",
            "For you"
        )

        const val UTC_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
        const val LOCAL_TIME_FORMAT = "dd/MM/yyyy hh:mm:ss a"
    }
}