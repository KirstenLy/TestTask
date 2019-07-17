package com.example.testtask.domain.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ResponseResult(
    @SerializedName("response")
    @Expose
    val items: List<Employee>
)