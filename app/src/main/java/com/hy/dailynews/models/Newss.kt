package com.hy.dailynews.models

import java.io.Serializable


data class Newss(
    val url: String?,
    val title: String?,
    val image: String?,
    val siteName: String?,
    val description: String?
) : Serializable {
    constructor() : this(null, null, null, null,null)
}