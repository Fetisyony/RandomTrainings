package com.ba.randomtraining.data.model

import com.google.gson.annotations.SerializedName

data class JasonResponse(
    val results: List<JasonSearchResultItem>,
    val next: String?
)

data class JasonSearchResultItem(
    val id: String,
    @SerializedName("media_formats")  val mediaFormats: MediaFormats,
    val url: String
)

data class MediaFormats(
    @SerializedName("tinygif") val gif: Gif
)

data class Gif(
    val url: String,
    val dims: List<Int>
) {
    fun getRatio(): Float {
        val w = dims[0].toFloat()
        val h = dims[1].toFloat()
        return w / h
    }
    fun getHeight(): Int {
        return dims[1]
    }
}
