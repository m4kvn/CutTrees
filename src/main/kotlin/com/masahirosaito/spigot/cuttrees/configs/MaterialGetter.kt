package com.masahirosaito.spigot.cuttrees.configs

import com.masahirosaito.spigot.cuttrees.exceptions.NotFoundMaterialException
import org.bukkit.Material

class MaterialGetter {
    fun get(name: String): Material {
        return Material.getMaterial(name) ?: throw NotFoundMaterialException(name)
    }
}