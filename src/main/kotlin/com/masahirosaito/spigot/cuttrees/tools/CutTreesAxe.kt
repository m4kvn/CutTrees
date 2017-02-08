package com.masahirosaito.spigot.cuttrees.tools

import com.masahirosaito.spigot.cuttrees.trees.BaseTree
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack
import java.util.*

class CutTreesAxe(itemStack: ItemStack) : CutTreesTool(itemStack) {

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

    override fun calcDamage(tree: BaseTree): Int {
        var damage = tree.blocks.size
        if (hasDurabilityEnchant()) kotlin.repeat(tree.blocks.size) {
            damage -= if (Random().nextInt(itemStack.getEnchantmentLevel(Enchantment.DURABILITY)) == 0) 0 else 1
        }
        return damage
    }

    private fun hasDurabilityEnchant() = itemStack.containsEnchantment(Enchantment.DURABILITY)
}