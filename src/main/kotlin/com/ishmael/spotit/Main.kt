package com.ishmael.spotit

import com.adamratzman.spotify.models.PagingObject
import com.adamratzman.spotify.models.SimplePlaylist
import com.adamratzman.spotify.spotifyAppApi
import java.io.File
import kotlin.system.exitProcess

// Instance of Spotify API lib
private val api = spotifyAppApi(System.getenv("CLIENT_ID"), System.getenv("CLIENT_SECRET")).build()
private val username = getUsername()    // Declared here to scope to all functions

fun main() {
    // Current test code

    // gets all users public playlist, prints names and ids
    val userPlaylistIds = getUserPlaylistsIds()

    println("Users playlist")
    for(p in userPlaylistIds) {
        println(api.playlists.getPlaylist(p).complete()!!.name + " id: " + p)
    }

    exitProcess(0)
}

/**
 *  Gets user's Spotify username from saved data or user input if it is already stored
 *
 *  @return String
 */
fun getUsername(): String {
    val pathname = "data/username.dat"

    return if(File(pathname).exists()) {
        File(pathname).readText()
    }
    else {
        File("data").mkdir() // make the data directory so we don't crash

        println("Enter Spotify username")
        val username = readLine()!!.trim()

        println("Save username for later use? (y/n)")
        when(readLine()) {
            "y" -> File(pathname).writeText(username)
        }

        username
    }
}

/**
 *  Requests all of the data from user's public playlists
 *
 *  @return PagingObject of playlist objects and fields
 */
fun getUserPlaylists(): PagingObject<SimplePlaylist> {
    try {
        return api.playlists.getUserPlaylists(username).complete()
    }
    catch(e: Exception) {
        if(File("data/username.dat").exists())
            File("data/username.dat").delete()
            // TODO: Delete here so we can get a correct username on next run. To change later when commands are added

        println("An error has occurred. This is most likely because an incorrect username was provided")
        println("Double check your username is correct on next run")
        exitProcess(1)
    }
}


/**
 *  Gets the ids of each of the users public playlist
 *
 *  @return ArrayList containing each playlist id
 */
fun getUserPlaylistsIds(): ArrayList<String> {
    val playlists = getUserPlaylists()
    val userPlaylistIds = ArrayList<String>()

    for(p in playlists) {
        userPlaylistIds.add(p!!.id)
    }

    return userPlaylistIds
}
