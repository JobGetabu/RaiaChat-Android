package com.raiachat.model

import com.google.firebase.firestore.ServerTimestamp
import java.util.*


data class User(
    val username: String = "",
    val status: String = "",
    val gender: String = "",
    val photourl: String? = null,
    var userid: String = ""
)

data class UserAuth(
    val createdby: String = "",
    @ServerTimestamp val createdon: Date,
    val phone: String? = null,
    val email: String? = null,
    val fb: Boolean = false,
    val google: Boolean = false,
    val role: String = ""               //Basic | - Official - | Provider| Editor | Admin
)