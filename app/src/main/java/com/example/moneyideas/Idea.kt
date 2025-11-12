package com.example.moneyideas

import java.io.Serializable

data class Idea(
    var text: String,
    var tried: Boolean = false
) : Serializable
