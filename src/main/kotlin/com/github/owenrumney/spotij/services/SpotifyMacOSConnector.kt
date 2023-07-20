package com.github.owenrumney.spotij.services

import com.intellij.openapi.util.IconLoader
import com.jetbrains.rd.util.string.printToString
import io.ktor.utils.io.streams.*
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStream


object SpotifyMacOSConnector {


    private fun isPlaying(): Boolean {
        var command = """tell application "Spotify"
            player state
        end tell
        """.trimMargin()

        val processBuilder = ProcessBuilder("osascript", "-e", command)
        val process = processBuilder.start()

        return readStreamToString(process.inputStream) == "playing"
    }

    fun nextTrack() {
        var command = """tell application "Spotify"
            next track
        end tell
        """.trimMargin()
        ProcessBuilder("osascript", "-e", command).start()
    }

    fun previousTrack() {
        var command = """tell application "Spotify"
            previous track
        end tell
        """.trimMargin()
        ProcessBuilder("osascript", "-e", command).start()
    }

    fun togglePlay() {
        var command = """tell application "Spotify"
            playpause
        end tell
        """.trimMargin()
        ProcessBuilder("osascript", "-e", command).start()
    }

    fun getTrackDetails(): String {
        val playing = isPlaying()
        SpotifyConnector.setIcon(playing)
        if (playing) {
            var command = """tell application "Spotify" 
            return (get artist of current track) & " - " &(get name of current track) 
            end tell""".trimMargin()

            val processBuilder = ProcessBuilder("osascript", "-e", command)

            val process = processBuilder.start()

            return readStreamToString(process.inputStream)
        }
        return "Paused"
    }

    private fun readStreamToString(inputStream: java.io.InputStream): String {
        val reader = BufferedReader(InputStreamReader(inputStream))
        val stringBuilder = StringBuilder()
        var line: String?
        while (reader.readLine().also { line = it } != null) {
            stringBuilder.append(line).append(System.lineSeparator())
        }
        return stringBuilder.toString().trim()
    }
}