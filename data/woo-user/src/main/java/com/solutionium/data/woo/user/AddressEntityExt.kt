package com.solutionium.data.woo.user

import com.solutionium.data.database.entity.AddressEntity
import com.solutionium.data.model.Address

fun AddressEntity.toModel() = Address(
    id = id,
    title = title,
    firstName = firstName,
    lastName = lastName,
    company = company,
    address1 = address1,
    address2 = address2,
    city = city,
    state = state,
    postcode = postcode,
    country = country,
    email = email,
    phone = phone,
    isDefault = isDefault
)

fun Address.toEntity(userId: String) = AddressEntity(
    id = id,
    title = title,
    userId = userId,
    firstName = firstName,
    lastName = lastName,
    company = company,
    address1 = address1,
    address2 = address2,
    city = city,
    state = state,
    postcode = postcode,
    country = country,
    email = email,
    phone = phone,
    isDefault = isDefault
)