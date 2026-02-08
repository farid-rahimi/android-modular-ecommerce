package com.solutionium.data.network.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Image (
    val id: Int? = null,

    @SerialName("date_created")
    val dateCreated: String? = null,

    @SerialName("date_created_gmt")
    val dateCreatedGmt: String? = null,

    @SerialName("date_modified")
    val dateModified: String? = null,

    @SerialName("date_modified_gmt")
    val dateModifiedGmt: String? = null,

    val src: String? = null,
    val name: String? = null,
    val alt: String? = null,
    val thumbnail: String? = null,
)