package com.solutionium.shared.data.network.common

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonPrimitive

@Serializable
data class MetaDatum (
    val id: Int? = null,
    val key: String? = null,

    //@Serializable(with = FlexibleFieldSerializer::class)
    val value: JsonElement? = null
)

fun MetaDatum.valueString() : String? {
    return this.value?.let {
        if (it is JsonPrimitive) {
            it.content
        } else {
            it.toString()
        }
    }
}

// The fix is to delegate the serializer resolution
//object MetaDatumSerializer : KSerializer<MetaDatum> {
//    // Delegate the actual serialization to a transforming serializer
//    private val transformingSerializer =
//        object : JsonTransformingSerializer<MetaDatum>(MetaDatum.serializer()) {
//            override fun transformDeserialize(element: JsonElement): JsonElement {
//                val jsonObject = element.jsonObject
//                val valueElement = jsonObject["value"]
//
//                if (valueElement !is JsonPrimitive) {
//                    val newJsonObject = JsonObject(jsonObject.toMutableMap().apply {
//                        this["value"] = JsonPrimitive(valueElement.toString())
//                    })
//                    return newJsonObject
//                }
//                return element
//            }
//        }
//
//    // Explicitly override the required KSerializer members
//    override val descriptor: SerialDescriptor
//        get() = transformingSerializer.descriptor
//
//    override fun serialize(encoder: Encoder, value: MetaDatum) {
//        transformingSerializer.serialize(encoder, value)
//    }
//
//    override fun deserialize(decoder: Decoder): MetaDatum {
//        return transformingSerializer.deserialize(decoder)
//    }
//}

