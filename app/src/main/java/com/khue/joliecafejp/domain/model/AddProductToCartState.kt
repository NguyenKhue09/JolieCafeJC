package com.khue.joliecafejp.domain.model

import androidx.compose.runtime.mutableStateListOf

data class AddProductToCartState(
    val size: String = "S",
    val sugar: Int = 25,
    val ice: Int = 25,
    val note: String = ""
) {
    val toppings = mutableStateListOf<ProductTopping>()

    fun addTopping(productTopping: ProductTopping) {
        toppings.add(productTopping)
    }

    fun removeTopping(productTopping: ProductTopping) {
        toppings.remove(productTopping)
    }
}
