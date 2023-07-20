package com.github.owenrumney.spotij.actions

import com.github.owenrumney.spotij.services.SpotifyConnector
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class TogglePlayPauseAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
            SpotifyConnector.togglePlay()
    }
}

class PrevTrackAction: AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        SpotifyConnector.previousTrack()
    }
}

class NextTrackAction: AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        SpotifyConnector.nextTrack()
    }
}