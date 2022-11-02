package com.instagram.src.main.home.models

import com.google.gson.annotations.SerializedName

data class StoryData(
    @SerializedName("state") var state : Boolean,
    @SerializedName("profileimage") var profile : String,
    @SerializedName("nickName") var nickName : String

)
