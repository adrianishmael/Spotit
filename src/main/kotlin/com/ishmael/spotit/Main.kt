package com.ishmael.spotit

import com.adamratzman.spotify.spotifyAppApi
import kotlin.system.exitProcess

fun main() {
    val api = spotifyAppApi(
            System.getenv("CLIENT_ID"),
            System.getenv("CLIENT_SECRET"))
            .build()

    val fobID = api.search.searchArtist("Blink-182").complete()[0].id
    println(api.artists.getArtistTopTracks(fobID).complete().joinToString { it.name })
    println("Hello World")
    exitProcess(0)
}
