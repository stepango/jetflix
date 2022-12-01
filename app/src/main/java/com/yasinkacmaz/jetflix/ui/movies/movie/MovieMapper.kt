package com.yasinkacmaz.jetflix.ui.movies.movie

import com.yasinkacmaz.jetflix.data.MovieResponse
import com.yasinkacmaz.jetflix.util.toPosterUrl

fun movieMapper(input: MovieResponse) = Movie(
    id = input.id,
    name = input.name,
    releaseDate = input.firstAirDate.orEmpty(),
    posterPath = input.posterPath.orEmpty().toPosterUrl(),
    voteAverage = input.voteAverage,
    voteCount = input.voteCount
)
