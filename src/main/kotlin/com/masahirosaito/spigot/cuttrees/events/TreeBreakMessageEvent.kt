package com.masahirosaito.spigot.cuttrees.events

import com.masahirosaito.spigot.cuttrees.BaseCancellableEvent

class TreeBreakMessageEvent(event: TreeBreakEvent) : BaseCancellableEvent() {
    private val configs = event.configs
    val player = event.player
    val durability = event.durability
    val damage = event.damage
    val blocksNum = event.blocks.size

    fun sendBlockBreakMessage() {
        if (configs.onBlockBreakMessage) {
            val newDurability = if (durability - damage >= 0) durability - damage else 0
            player.sendMessage("耐久値: $durability -> $newDurability, ブロック数: $blocksNum")
        }
    }
}