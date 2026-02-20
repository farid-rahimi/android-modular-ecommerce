package com.solutionium.shared.data.network.response

import kotlinx.serialization.*

typealias WooCategoryListResponse = List<WooCategoryResponse>

@Serializable
data class WooCategoryResponse (
    val id: Int? = null,
    val name: String? = null,
    val slug: String? = null,
    val parent: Int? = null,
    val description: String? = null,
    val display: String? = null,
    val image: CategoryImage? = null,

    @SerialName("menu_order")
    val menuOrder: Int? = null,

    val count: Int? = null,

    )


@Serializable
data class CategoryImage (
    val id: Long? = null,

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
    val alt: String? = null
)
