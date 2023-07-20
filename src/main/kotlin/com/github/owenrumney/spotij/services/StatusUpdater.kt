package com.github.owenrumney.spotij.services

import com.intellij.openapi.wm.StatusBar

class StatusUpdater(private var statusBar: StatusBar?) : Runnable {
    private var stop = false

    override fun run() {
        while (!stop) {
            statusBar?.updateWidget("SpotiJ")
            Thread.sleep(1000L)
        }
    }
}
