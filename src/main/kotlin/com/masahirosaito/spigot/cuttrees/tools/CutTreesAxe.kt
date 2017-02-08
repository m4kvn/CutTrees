package com.masahirosaito.spigot.cuttrees.tools

import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class CutTreesAxe(tool: ItemStack) : CutTreesTool(tool) {

    override fun isValid(): Boolean {
        return when (material) {
            Material.WOOD_AXE,
            Material.STONE_AXE,
            Material.GOLD_AXE,
            Material.IRON_AXE,
            Material.DIAMOND_AXE -> true
            else -> false
        }
    }

    override fun canBeDamaged() = true
}