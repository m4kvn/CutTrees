package com.masahirosaito.spigot.cuttrees.utils

import org.bukkit.material.Leaves
import org.bukkit.material.Tree

fun Leaves.sameSpecies(tree: Tree): Boolean = species == tree.species