package com.example.demo

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

@Serializable
data class CharacterList (
    val docs: List<Doc>,

)

@Serializable
data class Doc (
    @SerialName("_id")
    val id: String,
    var miUsuario: String? = null,
    var vivo: Boolean = true,
    val height: String,
    val race: String,
    val gender: String? = null,
    val birth: String,
    val spouse: String,
    val death: String,
    val realm: String,
    val hair: String,
    val name: String,

    @SerialName("wikiUrl")
    val wikiURL: String? = null
)



