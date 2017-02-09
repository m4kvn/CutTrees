package com.masahirosaito.spigot.cuttrees.materials

import org.bukkit.Material
import org.bukkit.block.Block

data class DurabilityMaterial(val material: Material, val durability: Short) {
    companion object {
        fun new(block: Block): DurabilityMaterial {
            return DurabilityMaterial(block.type, block.state.data.toItemStack().durability)
        }
    }
}