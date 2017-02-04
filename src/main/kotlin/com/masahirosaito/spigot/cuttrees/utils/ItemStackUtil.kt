package com.masahirosaito.spigot.cuttrees.utils

import org.bukkit.inventory.ItemStack

fun ItemStack.isBreak(damage: Int): Boolean = durability + damage >= type.maxDurability

fun ItemStack.getRemainingDurability(): Int = type.maxDurability - durability

fun ItemStack.damage(damage: Int) { durability = durability.plus(damage).toShort() }