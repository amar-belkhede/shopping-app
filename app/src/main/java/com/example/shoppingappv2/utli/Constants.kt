package com.example.shoppingappv2.utli

class Constants {
    companion object{
        const val BASE_URL = "https://5b598356-d19a-4ed2-a16f-c7bad359c48d.mock.pstmn.io"


        //Room database api
        const val DATABASE_NAME = "shopping_database_api"
        const val PRODUCT_LIST_TABLE = "product_list_table_api"
        const val PRODUCT_TABLE = "product_table_api"
        const val CART_TABLE = "cart_table"

        // Bottom Sheet and Preferences
        const val DEFAULT_PRODUCT_NUMBER = "50"
        const val DEFAULT_SIZE_TYPE = "S"
        const val DEFAULT_PRICE_RANGE_TYPE = "below 200"

        const val PREFERENCES_NAME = "product_preferences"
        const val PREFERENCES_SIZE_TYPE = "sizeType"
        const val PREFERENCES_SIZE_TYPE_ID = "sizeTypeId"
        const val PREFERENCES_PRICE_RANGE_TYPE = "priceRangeType"
        const val PREFERENCES_PRICE_RANGE_TYPE_ID = "priceRangeTypeId"
    }
}