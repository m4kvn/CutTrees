package com.masahirosaito.spigot.cuttrees.players

import com.masahirosaito.spigot.cuttrees.tools.CutTreesAxe
import com.masahirosaito.spigot.cuttrees.trees.BaseTree
import com.masahirosaito.spigot.cuttrees.utils.isCreativeMode
import com.masahirosaito.spigot.cuttrees.utils.itemInMainHand
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.Statistic
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class CutTreesPlayer(val player: Player) {
    val tool = CutTreesAxe(player.itemInMainHand())

    fun isValid(): Boolean = when {
        !tool.isValid() -> false
        else -> true
    }

    fun incrementStatics(tree: BaseTree) {
        player.incrementStatistic(Statistic.MINE_BLOCK, tree.material(), tree.blocks.size)
        player.incrementStatistic(Statistic.USE_ITEM, tool.material, tree.blocks.size)
    }

    fun breakItemInMainHand() {
        player.world.playSound(player.location, Sound.ENTITY_ITEM_BREAK, 1f, 1f)
        player.inventory.itemInMainHand = ItemStack(Material.AIR)
    }

    fun DamageToTool(tree: BaseTree) {
        if (tool.damage(tree)) if (tool.isBroken()) breakItemInMainHand()
    }

    fun isCreative() = player.isCreativeMode()
}