package com.github.owenrumney.spotij.services

import org.freedesktop.dbus.connections.impl.DBusConnectionBuilder



object SpotifyDBusConnector {

    const val BUS_NAME = "org.mpris.MediaPlayer2.spotify"
    const val OBJECT_PATH = "/org/mpris/MediaPlayer2"
    const val MPRIS_PROPERTY = "org.mpris.MediaPlayer2.Player"

    val dbusConn = DBusConnectionBuilder.forSessionBus("test").build()
    val spotifyService = dbusConn.getRemoteObject(
        BUS_NAME,
        OBJECT_PATH,
        org.freedesktop.dbus.interfaces.Properties::class.java
    )
    val controlsProxy = dbusConn.getRemoteObject(
        BUS_NAME,
        OBJECT_PATH,
        com.github.owenrumney.spotij.mpris.Player::class.java)

    private fun isPlaying(): Boolean {
        val status = spotifyService.Get(MPRIS_PROPERTY, "PlaybackStatus") as String
        return status == "Playing"
    }

    fun nextTrack() {
        controlsProxy.Next()
    }

    fun previousTrack() {
        controlsProxy.Previous()
    }

    fun togglePlay() {
        controlsProxy.PlayPause()
    }

    fun getTrackDetails(): String {
        val playing = isPlaying()
        SpotifyConnector.setIcon(playing)
        if (playing) {
            val metadata = spotifyService.Get("org.mpris.MediaPlayer2.Player", "Metadata") as Map<String, Any>
            val artist = metadata["xesam:artist"] as List<String>?
            val title = metadata["xesam:title"] as String?
            return "${artist?.joinToString()} - $title"
        }
        return "Paused"
    }
}