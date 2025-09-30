package com.wayads.data.model

import com.google.gson.annotations.SerializedName

data class MusicaResponse(
    @SerializedName("content") val content: List<Musica>
)
