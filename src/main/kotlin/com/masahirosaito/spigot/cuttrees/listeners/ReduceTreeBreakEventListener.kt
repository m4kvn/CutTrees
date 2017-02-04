package com.masahirosaito.spigot.cuttrees.listeners

import com.masahirosaito.spigot.cuttrees.CutTrees
import com.masahirosaito.spigot.cuttrees.events.ReduceTreeBreakEvent
import com.masahirosaito.spigot.cuttrees.utils.damage
import com.masahirosaito.spigot.cuttrees.utils.isBreak
import com.masahirosaito.spigot.cuttrees.utils.onBreakItemInMainHand
import org.bukkit.block.Block
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.inventory.ItemStack
import java.util.*

class ReduceTreeBreakEventListener(plugin: CutTrees) : TreeBreakEventListener(plugin) {

    @EventHandler(priority = EventPriority.MONITOR)
    fun onReduceTreeBreak(event: ReduceTreeBreakEvent) {
        onTreeBreakEvent(event)
    }

    private fun isReduce(tool: ItemStack): Boolean {
        return Random().nextInt(tool.getEnchantmentLevel(Enchantment.DURABILITY)) == 0
    }

    override fun calcToolDamage(tool: ItemStack, blocksSize: Int): Int {
        var damage = blocksSize
        if (tool.containsEnchantment(Enchantment.DURABILITY)) {
            for (i in 1..blocksSize) damage -= if (isReduce(tool)) 0 else 1
        }
        return damage
    }

    override fun calcTakeBlocksSize(blocks: MutableList<Block>, damage: Int, tool: ItemStack): Int {
        return if (tool.isBreak(damage)) tool.durability + damage - tool.type.maxDurability else 0
    }
}