package io.github.alirzaev.movies.data.source.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieCreditsResponse(
    val id: Int,
    val cast: List<Member>,
    val crew: List<Member>,
) {
    @Serializable
    data class Member(
        val id: Int,
        val name: String,
        @SerialName("profile_path")
        val profilePath: String?
    )
}
