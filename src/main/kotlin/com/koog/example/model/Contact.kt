package com.koog.example.model

import kotlinx.serialization.Serializable

@Serializable
data class Contact(
    val id: Int,
    val name: String,
    val lastName: String,
    val phone: String,
)

