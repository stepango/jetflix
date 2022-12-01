package com.yasinkacmaz.jetflix.ui.moviedetail.image

import com.yasinkacmaz.jetflix.data.ImagesResponse
import com.yasinkacmaz.jetflix.util.toOriginalUrl

fun imageMapper(input: ImagesResponse): List<Image> = buildList {
    addAll(input.backdrops.map { Image(it.filePath.toOriginalUrl(), it.voteCount) })
    addAll(input.posters.map { Image(it.filePath.toOriginalUrl(), it.voteCount) })
    sortByDescending { it.voteCount }
}
