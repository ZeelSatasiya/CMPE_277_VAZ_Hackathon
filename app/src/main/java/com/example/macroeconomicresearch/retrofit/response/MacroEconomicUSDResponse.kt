package com.example.macroeconomicresearch.retrofit.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


typealias Root = Pair<Root2, List<Root3>>;


@JsonClass(generateAdapter = true)
data class Root2(
    var page: Long? = null,
    var pages: Long? = null,
    @Json(name = "per_page")
    var perPage: Long? = null,
    var total: Long? = null,
    var sourceid: String? = null,
    var sourcename: String? = null,
    var lastupdated: String? = null,
)
@JsonClass(generateAdapter = true)
data class Root3(
    var indicator: Indicator? = null,
    var country: Country? = null,
    var countryiso3code: String?=null,
    var date: String,
    var value: Double,
    var unit: String?="",
    @Json(name = "obs_status")
    var obsStatus: String? = null,
    var decimal: Long? = null,
)
@JsonClass(generateAdapter = true)
data class Indicator(
    val id: String? = null,
    val value: String? = null,
)
@JsonClass(generateAdapter = true)
data class Country(
    val id: String? = null,
    val value: String? = null,
)
