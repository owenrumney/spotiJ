package com.github.owenrumney.spotij.services

import com.intellij.openapi.wm.StatusBar
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class StatusUpdater(private var statusBar: StatusBar?) : Runnable {

    override fun run() {
        statusBar?.updateWidget("SpotiJ")
    }
}
