package com.yasinkacmaz.jetflix.service

import com.yasinkacmaz.jetflix.data.*
import com.yasinkacmaz.jetflix.util.parseJson

class TestMovieService: MovieService {

    val moviesResponse = MoviesResponse(
        page = 1,
        totalPages = 1,
        totalResults = 1,
        movies = listOf(
            MovieResponse(
                id = 111,
                name = "Scarface",
                originalLanguage = "en",
                originalTitle = "Scarface",
                overview = "Lorum ipsum",
                voteAverage = 0.0,
                voteCount = 0,
            )
        )
    )

    override suspend fun fetchMovies(pageNumber: Int, options: Map<String, String>): MoviesResponse {
        return moviesResponse
    }

    override suspend fun search(pageNumber: Int, searchQuery: String, includeAdult: Boolean): MoviesResponse {
        return moviesResponse
    }

    override suspend fun fetchGenres(): GenresResponse {
        return GenresResponse(listOf())
    }

    override suspend fun fetchMovieDetail(movieId: Int): MovieDetailResponse {
        return parseJson("movie_detail.json")
    }

    override suspend fun fetchMovieCredits(movieId: Int): CreditsResponse {
        return parseJson("credits.json")
    }

    override suspend fun fetchMovieImages(movieId: Int): ImagesResponse {
        return parseJson("images.json")
    }
}