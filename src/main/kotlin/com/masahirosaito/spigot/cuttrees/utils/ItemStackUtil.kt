package com.masahirosaito.spigot.cuttrees.utils

import org.bukkit.inventory.ItemStack

fun ItemStack.isBreak(damage: Int) = durability + damage >= type.maxDurability

fun ItemStack.getRemainingDurability() = type.maxDurability - durability

fun ItemStack.damage(damage: Int) = this.apply { durability = durability.plus(damage).toShort() }

fun ItemStack.isBroken() = durability >= type.maxDurability