package com.ishmael.spotit

import com.adamratzman.spotify.spotifyAppApi
import kotlin.system.exitProcess

fun main() {
    val api = spotifyAppApi(System.getenv("CLIENT_ID"), System.getenv("CLIENT_SECRET")).build()

    println("Enter Spotify username")
    val userName = readLine() as String

    try {
        val playlistSearch = api.playlists.getUserPlaylists(userName).complete().map { it!!.name }
        println(playlistSearch)
    }
    catch (e: com.adamratzman.spotify.models.SpotifyUriException) {
        println("The following error has occurred: " + e.message)
    }

    exitProcess(0)
}
