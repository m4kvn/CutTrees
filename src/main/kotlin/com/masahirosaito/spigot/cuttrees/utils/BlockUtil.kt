package com.masahirosaito.spigot.cuttrees.utils

import org.bukkit.block.Block
import org.bukkit.material.Leaves
import org.bukkit.material.Tree

fun Block.getRelatives(range: Int): List<Block> =
        mutableListOf<Block>().apply {
            for (x in -range..range)
                for (y in -range..range)
                    for (z in -range..range)
                        add(getRelative(x, y, z))
        }

fun Block.isTree(): Boolean = state.data is Tree

fun Block.asTree(): Tree = state.data as Tree

fun Block.isLeaves(): Boolean = state.data is Leaves

fun Block.asLeaves(): Leaves = state.data as Leaves

fun Block.sameType(block: Block): Boolean = type == block.type