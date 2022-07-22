package io.github.alirzaev.movies.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Actor(
    val id: Int,
    val name: String,
    val image: String
) : Parcelable
