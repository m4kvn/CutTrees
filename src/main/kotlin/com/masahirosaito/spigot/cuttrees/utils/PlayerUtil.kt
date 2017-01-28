package com.masahirosaito.spigot.cuttrees.utils

import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

fun Player.itemInMainHand() = inventory.itemInMainHand!!

fun Player.isCreativeMode() = gameMode == GameMode.CREATIVE

fun Player.onBreakItemInMainHand() {
    world.playSound(location, Sound.ENTITY_ITEM_BREAK, 1f, 1f)
    inventory.itemInMainHand = ItemStack(Material.AIR)
}