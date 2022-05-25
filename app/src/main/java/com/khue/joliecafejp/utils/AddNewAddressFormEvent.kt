package com.khue.joliecafejp.utils

sealed class AddNewAddressFormEvent {
    data class PhoneChanged(val phone: String): AddNewAddressFormEvent()
    data class AddressChanged(val address: String): AddNewAddressFormEvent()
    data class UserNameChanged(val username: String): AddNewAddressFormEvent()
    data class IsDefaultAddressChanged(val isDefault: Boolean): AddNewAddressFormEvent()
    object Submit: AddNewAddressFormEvent()
}
