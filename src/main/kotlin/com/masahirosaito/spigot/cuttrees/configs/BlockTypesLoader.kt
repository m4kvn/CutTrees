package com.masahirosaito.spigot.cuttrees.configs

import org.bukkit.Material

class BlockTypesLoader(val blockTypeNames: Map<String, String>) {

    fun load(): MutableMap<Material, Material?> {
        return mutableMapOf<Material, Material?>().apply { convert() }
    }

    private fun MutableMap<Material, Material?>.convert() {
        MaterialGetter().apply {
            blockTypeNames.forEach { pair ->
                try {
                    put(get(pair.key), material(pair))
                } catch(e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun MaterialGetter.material(pair: Map.Entry<String, String>): Material? {
        return if (!pair.value.isNullOrBlank()) {
            try {
                get(pair.value)
            } catch(e: Exception) {
                e.printStackTrace()
                null
            }
        } else null
    }
}