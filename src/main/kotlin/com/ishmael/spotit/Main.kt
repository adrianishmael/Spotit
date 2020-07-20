package com.ishmael.spotit

import com.adamratzman.spotify.spotifyAppApi
import java.io.File
import kotlin.system.exitProcess

private val api = spotifyAppApi(System.getenv("CLIENT_ID"), System.getenv("CLIENT_SECRET")).build()

fun main() {
    val username = getUsername()
    val playlistSearch = getUserPlaylists(username)
    println("User's playlists: ")
    println(playlistSearch)

    exitProcess(0)
}

fun getUsername(): String {
    val pathname = "src/main/resources/data.txt"

    return if(File(pathname).exists()) {
        File(pathname).readText()
    }
    else {
        println("Enter Spotify username")
        val username = readLine()!!.trim()

        println("Save username for later use? (y/n)")
        when(readLine()) {
            "y" -> File(pathname).writeText(username)
        }

        username
    }
}

fun getUserPlaylists(username: String): List<String> {
    try {
        return api.playlists.getUserPlaylists(username).complete().map { it!!.name }
    }
    catch(e: Exception) {
        if(File("src/main/resources/data.txt").exists())
            File("src/main/resources/data.txt").delete()

        println("An error has occurred. This is most likely because an incorrect username was provided")
        println("Double check your username is correct on next run")
        exitProcess(1)
    }
}
