package com.github.owenrumney.spotij.services

import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.popup.JBPopupFactory
import com.intellij.openapi.ui.popup.ListPopup
import com.intellij.openapi.util.IconLoader
import com.intellij.openapi.wm.StatusBar
import com.intellij.openapi.wm.StatusBarWidget
import com.intellij.openapi.wm.StatusBarWidgetFactory
import com.intellij.ui.awt.RelativePoint
import com.intellij.util.Consumer
import java.awt.*
import java.awt.event.MouseEvent
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import javax.swing.*

class SpotijStatusBarWidgetFactory : StatusBarWidgetFactory {
    private var statusUpdaterThread: Thread? = null

    private lateinit var spotiJWidget: StatusBarWidget
    private val name = "SpotiJ"

    override fun getId(): String {
        return name
    }

    override fun getDisplayName(): String {
        return name
    }

    override fun isAvailable(project: Project): Boolean {
        return true
    }

    override fun createWidget(project: Project): StatusBarWidget {
        spotiJWidget = object : StatusBarWidget {
            val scheduler = Executors.newScheduledThreadPool(1)
            override fun dispose() {
                scheduler.shutdown()
            }

            override fun ID(): String {
                return name
            }

            override fun install(statusBar: StatusBar) {
                val statusUpdate = StatusUpdater(statusBar)
                scheduler.scheduleAtFixedRate(statusUpdate, 0, 1, TimeUnit.SECONDS)
            }

            override fun getPresentation(): StatusBarWidget.WidgetPresentation? {
                return object : StatusBarWidget.MultipleTextValuesPresentation {
                    override fun getTooltipText(): String? {
                        return "SpotiJ"
                    }

                    override fun getSelectedValue(): String? {
                        return " %s".format(SpotifyConnector.getTrackDetails())
                    }

                    override fun getIcon(): Icon? {
                        return SpotifyConnector.currentIcon
                    }
                }
            }
        }
        return spotiJWidget
    }

    override fun disposeWidget(widget: StatusBarWidget) {
//        spotifyStatusUpdater!!.stop()
        statusUpdaterThread!!.interrupt()
    }

    override fun canBeEnabledOn(statusBar: StatusBar): Boolean {
        return true
    }
}