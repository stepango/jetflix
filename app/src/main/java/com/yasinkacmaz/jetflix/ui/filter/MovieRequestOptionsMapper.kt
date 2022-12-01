package com.yasinkacmaz.jetflix.ui.filter

private const val SORT_BY = "sort_by"
private const val INCLUDE_ADULT = "include_adult"
private const val WITH_GENRES = "with_genres"

fun movieRequestOptionsMapper(filterState: FilterState = FilterState()): Map<String, String> = buildMap {
    val sortBy = "${filterState.sortBy.by}.${filterState.sortOrder.order}"
    put(SORT_BY, sortBy)
    val includeAdult = filterState.includeAdult.toString()
    put(INCLUDE_ADULT, includeAdult)
    if (filterState.selectedGenreIds.isNotEmpty()) {
        val selectedGenreIds = filterState.selectedGenreIds.joinToString("|")
        put(WITH_GENRES, selectedGenreIds)
    }
}
