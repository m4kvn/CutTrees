package com.masahirosaito.spigot.cuttrees.events

import com.masahirosaito.spigot.cuttrees.CutTrees
import com.masahirosaito.spigot.cuttrees.utils.damage
import com.masahirosaito.spigot.cuttrees.utils.getRemainingDurability
import com.masahirosaito.spigot.cuttrees.utils.isBreak
import com.masahirosaito.spigot.cuttrees.utils.onBreakItemInMainHand
import org.bukkit.enchantments.Enchantment
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.inventory.ItemStack
import java.util.*

class ReduceTreeBreakEvent(event: BlockBreakEvent, plugin: CutTrees) : TreeBreakEvent(event, plugin)