package com.hy.dailynews.models

import androidx.room.Entity
import java.io.Serializable

@Entity
data class News(
    var itemType: Int?,
    val url: String?,
    val title: String?,
    val image: String?,
    val siteName: String?,
    val description: String?
): Serializable {
    constructor() : this(0, null, null,null,null,null,)
}