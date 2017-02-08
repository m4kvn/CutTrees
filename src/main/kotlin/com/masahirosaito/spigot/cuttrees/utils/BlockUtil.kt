package com.masahirosaito.spigot.cuttrees.utils

import org.bukkit.block.Block
import org.bukkit.material.Leaves
import org.bukkit.material.Mushroom
import org.bukkit.material.Tree

fun Block.getRelatives(r: Int) = mutableListOf<Block>().apply {
    for (x in -r..r) for (y in -r..r) for (z in -r..r) add(getRelative(x, y, z))
}

fun Block.isTree(): Boolean = state.data is Tree

fun Block.asTree(): Tree = state.data as Tree

fun Block.isLeaves(): Boolean = state.data is Leaves

fun Block.asLeaves(): Leaves = state.data as Leaves

fun Block.isMushroom(): Boolean = state.data is Mushroom

fun Block.asMushroom(): Mushroom = state.data as Mushroom