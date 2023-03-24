package ru.spliterash.httpExecutor

import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.permissions.Permission
import org.bukkit.permissions.PermissionAttachment
import org.bukkit.permissions.PermissionAttachmentInfo
import org.bukkit.plugin.Plugin
import java.util.*

private val regex = Regex("§[0-9A-FK-OR]", setOf(RegexOption.MULTILINE, RegexOption.IGNORE_CASE))

class CollectingCommandSender : CommandSender {
    private val messages = arrayListOf<String>()

    fun response(): String = messages
        .joinToString("\n")
        .replace(regex, "")//.let { pattern.matcher(it).replaceAll("") }

    override fun sendMessage(message: String) {
        messages.add(message)
    }

    override fun sendMessage(vararg messages: String) {
        this.messages.addAll(messages)
    }

    override fun sendMessage(sender: UUID?, message: String) {
        messages.add(message)
    }

    override fun sendMessage(sender: UUID?, vararg messages: String) {
        this.messages.addAll(messages)
    }

    override fun isOp() = true

    override fun setOp(value: Boolean) {
    }

    override fun isPermissionSet(name: String) = true

    override fun isPermissionSet(perm: Permission) = true

    override fun hasPermission(name: String) = true

    override fun hasPermission(perm: Permission) = true

    override fun addAttachment(plugin: Plugin, name: String, value: Boolean): PermissionAttachment {
        throw UnsupportedOperationException()
    }

    override fun addAttachment(plugin: Plugin): PermissionAttachment {
        throw UnsupportedOperationException()
    }

    override fun addAttachment(plugin: Plugin, name: String, value: Boolean, ticks: Int): PermissionAttachment? {
        throw UnsupportedOperationException()
    }

    override fun addAttachment(plugin: Plugin, ticks: Int): PermissionAttachment? {
        throw UnsupportedOperationException()
    }

    override fun removeAttachment(attachment: PermissionAttachment) {
    }

    override fun recalculatePermissions() {
    }

    override fun getEffectivePermissions(): Set<PermissionAttachmentInfo> = setOf()

    override fun getServer() = Bukkit.getServer()


    override fun getName() = "HttpCommandSender"

    override fun spigot(): CommandSender.Spigot {
        return CommandSender.Spigot()
    }

    override fun name(): Component {
        return Component.text(name)
    }
}