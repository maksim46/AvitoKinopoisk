import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FullMoviesDTO(
    @SerialName("internalNames") val internalNames: List<String>? = null,
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("alternativeName") val alternativeName: String? = null,
    @SerialName("enName") val enName: String? = null,
    @SerialName("type") val type: String?,
    @SerialName("year") val year: Int,
    @SerialName("description") val description: String? = null,
    @SerialName("shortDescription") val shortDescription: String?,
    @SerialName("movieLength") val movieLength: Int? = null,
    @SerialName("isSeries") val isSeries: Boolean? = null,
    @SerialName("ticketsOnSale") val ticketsOnSale: Boolean? = null,
    @SerialName("totalSeriesLength") val totalSeriesLength: Int? = null,
    @SerialName("seriesLength") val seriesLength: Int? = null,
    @SerialName("ratingMpaa") val ratingMpaa: String? = null,
    @SerialName("ageRating") val ageRating: Int? = null,
    @SerialName("top10") val top10: Int? = null,
    @SerialName("top250") val top250: Int? = null,
    @SerialName("typeNumber") val typeNumber: Int? = null,
    @SerialName("status") val status: String? = null,
    @SerialName("names") val names: List<Name>,
    @SerialName("externalId") val externalId: ExternalId?,
    @SerialName("logo") val logo: Logo? = null,
    @SerialName("poster") val poster: PosterFullDTO?,
    @SerialName("backdrop") val backdrop: Backdrop?,
    @SerialName("rating") val rating: Rating?,
    @SerialName("votes") val votes: Votes?,
    @SerialName("genres") val genres: List<Genre>?,
    @SerialName("countries") val countries: List<Country>?,
    @SerialName("releaseYears") val releaseYears: List<ReleaseYear>? = null,
    @SerialName("internalRating") val internalRating: Double? = null,
    @SerialName("internalVotes") val internalVotes: Int? = null,
)

@Serializable
data class Name(
    @SerialName("name") val name: String? = null,
    @SerialName("language") val language: String? = null,
    @SerialName("type") val type: String? = null
)

@Serializable
data class ExternalId(
    @SerialName("imdb") val imdb: String? = null,
    @SerialName("tmdb") val tmdb: Int? = null,
    @SerialName("kpHD") val kpHD: String? = null
)

@Serializable
data class Logo(
    @SerialName("url") val url: String? = null,
    @SerialName("previewUrl") val previewUrl: String? = null,
)

@Serializable
data class Rating(
    @SerialName("kp") val kp: Double? = null,
    @SerialName("imdb") val imdb: Double? = null,
    @SerialName("filmCritics") val filmCritics: Double? = null,
    @SerialName("russianFilmCritics") val russianFilmCritics: Double? = null,
    @SerialName("await") val await: Double? = null
)

@Serializable
data class Votes(
    @SerialName("kp") val kp: Int? = null,
    @SerialName("imdb") val imdb: Int? = null,
    @SerialName("filmCritics") val filmCritics: Int? = null,
    @SerialName("russianFilmCritics") val russianFilmCritics: Int? = null,
    @SerialName("await") val await: Int? = null
)

@Serializable
data class PosterFullDTO(
    @SerialName("url") val url: String? = null,
    @SerialName("previewUrl") val previewUrl: String? = null
)

@Serializable
data class Backdrop(
    @SerialName("url") val url: String? = null,
    @SerialName("previewUrl") val previewUrl: String? = null
)

@Serializable
data class Genre(
    @SerialName("name") val name: String? = null
)

@Serializable
data class Country(
    @SerialName("name") val name: String? = null
)

@Serializable
data class ReleaseYear(
    @SerialName("start") val start: Int? = null,
    @SerialName("end") val end: Int? = null
)

@Serializable
data class NetworkContainer(
    val items: List<Network>?
)

@Serializable
data class Network(
    val name: String?
)