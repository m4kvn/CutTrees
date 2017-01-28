package com.masahirosaito.spigot.cuttrees.utils

import org.bukkit.inventory.ItemStack

fun ItemStack.isBraek(damage: Int): Boolean = durability + damage >= type.maxDurability

fun ItemStack.getRemainingDurability(): Int = type.maxDurability - durability