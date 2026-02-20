package com.solutionium.shared.data.network.adapter

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object IntOrStringSerializer : KSerializer<Int> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("IntOrString", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): Int {
        return try {
            val value = decoder.decodeString()
            if (value.isEmpty()) 0 else value.toInt()
        } catch (e: Exception) {
            0
        }
    }

    override fun serialize(encoder: Encoder, value: Int) {
        encoder.encodeInt(value)
    }
}