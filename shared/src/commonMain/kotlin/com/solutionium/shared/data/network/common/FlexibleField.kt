package com.solutionium.shared.data.network.common
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
import kotlinx.serialization.json.*

sealed class FlexibleField {
    data class StringValue(val value: String) : FlexibleField()
    data class IntValue(val value: Int) : FlexibleField()
    data class StringListValue(val value: List<String>) : FlexibleField()
    data class IntListValue(val value: List<Int>) : FlexibleField()
    data object UnknownValue : FlexibleField()
}



object FlexibleFieldSerializer : KSerializer<FlexibleField> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("FlexibleField")

    override fun deserialize(decoder: Decoder): FlexibleField {
        val jsonDecoder = decoder as? JsonDecoder ?: error("This serializer only works with JSON")
        val element = jsonDecoder.decodeJsonElement()

        return when (element) {
            is JsonPrimitive -> {
                when {
                    element.isString -> FlexibleField.StringValue(element.content)
                    element.intOrNull != null -> FlexibleField.IntValue(element.int)
                    else -> throw SerializationException("Unexpected primitive type: $element")
                }
            }

            is JsonArray -> {
                // Decide if it's a list of Strings or Ints
                if (element.all { it is JsonPrimitive && it.isString }) {
                    FlexibleField.StringListValue(element.map { it.jsonPrimitive.content })
                } else if (element.all { it is JsonPrimitive && it.intOrNull != null }) {
                    FlexibleField.IntListValue(element.map { it.jsonPrimitive.int })
                } else {
                    throw SerializationException("Unexpected array contents: $element")
                }
            }

            else -> throw SerializationException("Unsupported JSON element: $element")
        }
    }

    override fun serialize(encoder: Encoder, value: FlexibleField) {
        val jsonEncoder = encoder as? JsonEncoder ?: error("This serializer only works with JSON")
        val jsonElement: JsonElement = when (value) {
            is FlexibleField.StringValue -> JsonPrimitive(value.value)
            is FlexibleField.IntValue -> JsonPrimitive(value.value)
            is FlexibleField.StringListValue -> JsonArray(value.value.map { JsonPrimitive(it) })
            is FlexibleField.IntListValue -> JsonArray(value.value.map { JsonPrimitive(it) })
            is FlexibleField.UnknownValue -> JsonNull
        }
        jsonEncoder.encodeJsonElement(jsonElement)
    }
}
