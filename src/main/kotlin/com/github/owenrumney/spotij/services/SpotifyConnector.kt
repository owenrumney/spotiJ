package com.github.owenrumney.spotij.services

import com.intellij.openapi.util.IconLoader
import com.intellij.openapi.vcs.ex.createInnerRanges
import com.jetbrains.rd.util.string.printToString
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStream


object SpotifyConnector {
    private val spotifyIcon = IconLoader.getIcon("/icons/spotify.svg", this::class.java)
    private val inactiveIcon = IconLoader.getIcon("/icons/spotify-inactive.svg", this::class.java)
    var currentIcon = spotifyIcon

    val osName = System.getProperty("os.name").toLowerCase()

    val isMacOS =  (osName.contains("mac") || osName.contains("darwin"))

    fun setIcon(playing :Boolean) {
        if (playing) {
            currentIcon = spotifyIcon
        } else {
            currentIcon = inactiveIcon
        }
    }

    fun nextTrack() {
        if (isMacOS) {
            SpotifyMacOSConnector.nextTrack()
        } else {
            SpotifyDBusConnector.nextTrack()
        }
    }

    fun previousTrack() {
        if (isMacOS) {
            SpotifyMacOSConnector.previousTrack()
        } else {
            SpotifyDBusConnector.previousTrack()
        }
    }

    fun togglePlay() {
        if (isMacOS) {
            SpotifyMacOSConnector.togglePlay()
        } else {
            SpotifyDBusConnector.togglePlay()
        }
    }

    fun getTrackDetails(): String {
        if (isMacOS) {
            return SpotifyMacOSConnector.getTrackDetails()
        } else {
            return SpotifyDBusConnector.getTrackDetails()
        }
    }
}