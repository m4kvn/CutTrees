package com.masahirosaito.spigot.cuttrees.configs

import com.masahirosaito.spigot.cuttrees.exceptions.InvalidToolTypeException
import com.masahirosaito.spigot.cuttrees.exceptions.NotFoundMaterialException
import org.bukkit.Material

class ToolTypesLoader(val toolTypeNames: List<String>) {

    fun load(): MutableList<Material> {
        return mutableListOf<Material>().apply {
            toolTypeNames.forEach {
                try {
                    add(validToolFilter(getMaterial(it)))
                } catch(e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun getMaterial(name: String): Material {
        return Material.getMaterial(name) ?: throw NotFoundMaterialException(name)
    }

    private fun validToolFilter(toolType: Material): Material {
        if (toolType.maxDurability > 0) return toolType
        else throw InvalidToolTypeException()

    }
}