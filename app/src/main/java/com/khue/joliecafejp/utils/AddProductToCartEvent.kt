package com.khue.joliecafejp.utils

import com.khue.joliecafejp.domain.model.ProductTopping

sealed class AddProductToCartEvent {
    data class SizeChanged(val size: String): AddProductToCartEvent()
    data class SugarChanged(val sugar: Int): AddProductToCartEvent()
    data class IceChanged(val ice: Int): AddProductToCartEvent()
    data class AddTopping(val topping: ProductTopping): AddProductToCartEvent()
    data class RemoveTopping(val topping: ProductTopping): AddProductToCartEvent()
    data class OnNoteChanged(val note: String): AddProductToCartEvent()
    object AddToCart: AddProductToCartEvent()
    object Purchase: AddProductToCartEvent()
}
