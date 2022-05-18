package com.myiss.isstracker89

data class ISSBeans(
    val iss_position: IssPosition,
    val message: String,
    val timestamp: Int
)

data class IssPosition(
    val latitude: String,
    val longitude: String
)