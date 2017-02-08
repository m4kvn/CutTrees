package com.masahirosaito.spigot.cuttrees.tools

import com.masahirosaito.spigot.cuttrees.trees.BaseTree
import com.masahirosaito.spigot.cuttrees.utils.damage
import com.masahirosaito.spigot.cuttrees.utils.isBroken
import org.bukkit.inventory.ItemStack

abstract class CutTreesTool(val itemStack: ItemStack) {
    val material = itemStack.type!!

    abstract fun isValid(): Boolean

    abstract fun canBeDamaged(): Boolean

    abstract fun calcDamage(tree: BaseTree): Int

     fun damage(tree: BaseTree): Boolean {
         return if (!isUnBreakable()) itemStack.damage(calcDamage(tree)).let { true } else false
     }

    fun isUnBreakable() = if (canBeDamaged()) itemStack.itemMeta.spigot().isUnbreakable else true

    fun isBroken() = if (!isUnBreakable()) itemStack.isBroken() else false
}