package com.github.owenrumney.spotij.mpris

import org.freedesktop.dbus.DBusPath
import org.freedesktop.dbus.annotations.DBusInterfaceName
import org.freedesktop.dbus.interfaces.DBusInterface
import org.freedesktop.dbus.messages.DBusSignal

@DBusInterfaceName("org.mpris.MediaPlayer2.Player")
interface Player: DBusInterface {
    class Seeked(path: DBusPath, position: Long): DBusSignal(path.path, position)

    fun Next()
    fun Previous()
    fun Pause()
    fun PlayPause()
    fun Play()
    fun Stop()
    fun Seek(x: Long)
    fun OpenUri(uri: String)
}