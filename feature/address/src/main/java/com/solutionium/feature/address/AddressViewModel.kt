package com.solutionium.feature.address

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solutionium.data.model.Address
import com.solutionium.domain.user.DeleteAddressUseCase
import com.solutionium.domain.user.LoadAddressesUseCase
import com.solutionium.domain.user.SaveAddressUseCase
import com.solutionium.domain.user.SetDefaultAddressUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AddressViewModel @Inject constructor(

    private val saveAddressUseCase: SaveAddressUseCase,
    private val loadAddressUseCase: LoadAddressesUseCase,
    private val deleteAddressUseCase: DeleteAddressUseCase,
    private val setAsDefaultAddressUseCase: SetDefaultAddressUseCase,
    savedStateHandle: SavedStateHandle // To get addressId if editing


) : ViewModel() {

    private val _state = MutableStateFlow(AddressUiState())
    val state = _state.asStateFlow()

    private val _listState = MutableStateFlow(AddressListUiState())
    val listState = _listState.asStateFlow()



    init {
        val addressIdArg: Int? = savedStateHandle["address_id_or_new"]
        if (addressIdArg != null && addressIdArg != -1) {
            loadAddress(addressIdArg)
        } else {
            // New address
            _state.update { it.copy(addressId = null) }

        }

        loadAddresses()
    }

    private fun loadAddresses() {
        viewModelScope.launch {
            _listState.update { it.copy(isLoading = true) }

            loadAddressUseCase().collect { address ->
                _listState.update {
                    it.copy(
                        addresses = address,
                        isLoading = false
                    )
                }
            }
        }
    }

    private fun loadAddress(addressId: Int) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true)}

            loadAddressUseCase(addressId = addressId).collect { address ->

                if (address != null) {
                    _state.update {
                        it.copy(
                            addressId = address.id,
                            title = address.title,
                            firstName = address.firstName,
                            lastName = address.lastName,
                            state = address.state,
                            city = address.city,
                            addressLine1 = address.address1,
                            addressLine2 = address.address2,
                            postalCode = address.postcode,
                            phoneNumber = address.phone,
                            isLoading = false
                        )
                    }
                } else {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            generalError = "Address not found."
                        )
                    }
                }
            }

        }
    }


    fun onTitleChange(newTitle: String) {
        _state.update { it.copy(title = newTitle, errorMessages = it.errorMessages - AddressField.FIRST_NAME) }
    }

    fun onFirstNameChange(value: String) {
        _state.update { it.copy(firstName = value, errorMessages = it.errorMessages - AddressField.FIRST_NAME)}
    }

    fun onLastNameChange(value: String) {
        _state.update { it.copy(lastName = value, errorMessages = it.errorMessages - AddressField.LAST_NAME)}
    }

    fun onStateChange(value: String) {
        _state.update { it.copy(state = value, errorMessages = it.errorMessages - AddressField.STATE)}
    }
    fun onCityChange(value: String) {
        _state.update { it.copy(city = value, errorMessages = it.errorMessages - AddressField.CITY)}
    }
    fun onAddressLine1Change(value: String) {
        _state.update { it.copy(addressLine1 = value, errorMessages = it.errorMessages - AddressField.ADDRESS_LINE_1)}
    }
    fun onAddressLine2Change(value: String) {
        _state.update { it.copy(addressLine2 = value) }
    }
    fun onPostalCodeChange(value: String) {
        _state.update { it.copy(postalCode = value, errorMessages = it.errorMessages - AddressField.POSTAL_CODE)}
    }
    fun onPhoneNumberChange(value: String) {
        _state.update { it.copy(phoneNumber = value, errorMessages = it.errorMessages - AddressField.PHONE_NUMBER)}
    }

    fun saveAddress(onSuccess: () -> Unit) {
        if (!validateFields()) {
            return
        }
        _state.update { it.copy(isSaving = true, generalError = null)}
        viewModelScope.launch {
            // Simulate saving
            // delay(1000)
            val uiState = _state.value
            val addressToSave = Address(
                id = uiState.addressId, // Generate new ID if adding
                title = uiState.title,
                firstName = uiState.firstName.trim(),
                lastName = uiState.lastName.trim(),
                state = uiState.state.trim(),
                city = uiState.city.trim(),
                address1 = uiState.addressLine1.trim(),
                address2 = uiState.addressLine2?.trim()?.ifBlank { null },
                postcode = uiState.postalCode.trim(),
                phone = uiState.phoneNumber?.trim(),
                company = null,
                country = "", // Hardcoded for simplicity
                email = null,
                isDefault = false
            )

            try {
                saveAddressUseCase(addressToSave)
                _state.update { it.copy(isSaving = false)}
                onSuccess()
            } catch (e: Exception) {
                _state.update { it.copy(isSaving = false, generalError = "Failed to save address: ${e.message}")}
            }
        }
    }

    private fun validateFields(): Boolean {
        val errors = mutableMapOf<String, Int>()
        val uiState = _state.value
        if (uiState.firstName.isBlank()) errors[AddressField.FIRST_NAME] = R.string.first_name_cannot_be_empty
        if (uiState.lastName.isBlank()) errors[AddressField.LAST_NAME] = R.string.last_name_cannot_be_empty
        if (uiState.state.isBlank()) errors[AddressField.STATE] = R.string.state_cannot_be_empty
        if (uiState.city.isBlank()) errors[AddressField.CITY] = R.string.city_cannot_be_empty
        if (uiState.addressLine1.isBlank()) errors[AddressField.ADDRESS_LINE_1] = R.string.address_line_cannot_be_empty

        if (uiState.postalCode.isBlank()) errors[AddressField.POSTAL_CODE] = R.string.postal_code_cannot_be_empty
        // Basic phone validation (can be more complex)
        if (uiState.phoneNumber?.isBlank() == true || (uiState.phoneNumber?.length ?: 0) < 10) errors[AddressField.PHONE_NUMBER] = R.string.enter_a_valid_phone_number

        _state.update { it.copy(errorMessages = errors) }
        return errors.isEmpty()
    }

    fun requestDeleteAddress(address: Address) {
        _listState.update { it.copy(addressToDelete = address, showDeleteConfirmationDialog = true) }
    }

    fun confirmDeleteAddress() {
        viewModelScope.launch {
            //_listState.update { it.copy(isLoading = true) }

            try {
                deleteAddressUseCase(_listState.value.addressToDelete ?: return@launch)
                _listState.update {
                    it.copy(
                        addressToDelete = null,
                        showDeleteConfirmationDialog = false,
                    )
                }
            } catch (e: Exception) {
                _listState.update { it.copy(generalError = "Failed to delete address: ${e.message}")}
            }

        }
    }

    fun cancelDeleteAddress() {
        _listState.update { it.copy(addressToDelete = null, showDeleteConfirmationDialog = false) }

    }

    fun setAsDefaultClicked(id: Int?, isDefault: Boolean) {

        if (isDefault) return // No action needed
        viewModelScope.launch {
            if (id == null) return@launch
            try {
                setAsDefaultAddressUseCase(id)
                // Update the list state to reflect the new default address
//                _listState.update { currentState ->
//                    val updatedAddresses = currentState.addresses.map { address ->
//                        address.copy(isDefault = address.id == id)
//                    }
//                    currentState.copy(addresses = updatedAddresses)
//                }
            } catch (e: Exception) {
                _listState.update { it.copy(generalError = "Failed to set default address: ${e.message}")}
            }
        }

    }




}