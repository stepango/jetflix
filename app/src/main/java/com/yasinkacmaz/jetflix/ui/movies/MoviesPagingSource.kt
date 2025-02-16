package com.yasinkacmaz.jetflix.ui.movies

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.yasinkacmaz.jetflix.service.MovieService
import com.yasinkacmaz.jetflix.ui.filter.FilterState
import com.yasinkacmaz.jetflix.ui.filter.movieRequestOptionsMapper
import com.yasinkacmaz.jetflix.ui.movies.movie.Movie
import com.yasinkacmaz.jetflix.ui.movies.movie.movieMapper

class MoviesPagingSource(
    private val movieService: MovieService,
    filterState: FilterState = FilterState(),
    private val searchQuery: String = ""
) : PagingSource<Int, Movie>() {
    private val options = movieRequestOptionsMapper(filterState)

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val page = params.key ?: 1
            val moviesResponse = if (searchQuery.isNotBlank()) {
                movieService.search(page, searchQuery)
            } else {
                movieService.fetchMovies(page, options)
            }
            val movies = moviesResponse.movies.map(::movieMapper)
            LoadResult.Page(
                data = movies,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (page >= moviesResponse.totalPages) null else moviesResponse.page + 1
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int = 1
}
