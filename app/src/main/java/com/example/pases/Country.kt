package com.example.pases

import com.google.gson.annotations.SerializedName
import java.util.Objects

data class Country
    (
    @SerializedName("translations") val translations: NameCountry?,
    @SerializedName("capital") val capital: Array<String>?,
    @SerializedName("population") val population: Int?,
    @SerializedName("flags") val flags: FlagsCountry?
)
