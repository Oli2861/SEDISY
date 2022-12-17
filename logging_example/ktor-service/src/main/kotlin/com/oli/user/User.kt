package com.oli.user

@kotlinx.serialization.Serializable
data class User(
    val id: String,
    val userName: String,
    val password: String
)