package com.masahirosaito.spigot.cuttrees.events

import com.masahirosaito.spigot.cuttrees.CutTrees
import com.masahirosaito.spigot.cuttrees.utils.damage
import com.masahirosaito.spigot.cuttrees.utils.getRemainingDurability
import com.masahirosaito.spigot.cuttrees.utils.isBreak
import com.masahirosaito.spigot.cuttrees.utils.onBreakItemInMainHand
import org.bukkit.enchantments.Enchantment
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.inventory.ItemStack
import java.util.*

class ReduceTreeBreakEvent(event: BlockBreakEvent, plugin: CutTrees) : TreeBreakEvent(event, plugin) {

    fun calcDurabilityDamage() {
        if (tool.containsEnchantment(Enchantment.DURABILITY)) {
            for (i in 1..blocks.size) damage -= if (isReduce(tool)) 0 else 1
        }
    }

    fun takeBlocks() {
        if (tool.isBreak(damage)) {
            blocks = blocks.take(tool.getRemainingDurability()).toMutableSet()
        }
    }

    fun onDamage() {
        if (tool.isBreak(damage)) player.onBreakItemInMainHand()
        else tool.damage(damage)
    }

    private fun isReduce(tool: ItemStack): Boolean {
        return Random().nextInt(tool.getEnchantmentLevel(Enchantment.DURABILITY)) == 0
    }
}