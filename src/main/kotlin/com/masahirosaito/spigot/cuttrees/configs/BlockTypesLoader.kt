package com.masahirosaito.spigot.cuttrees.configs

class BlockTypesLoader(val blockTypeNames: Map<String, String>) {

    fun load(): MutableMap<DamagedBlock, DamagedBlock?> {
        return mutableMapOf<DamagedBlock, DamagedBlock?>().apply {
            blockTypeNames.forEach { pair ->
                try {
                    put(getBlock(pair.key), getLeaves(pair.value))
                } catch(e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun getBlock(str: String): DamagedBlock {
        return DamagedBlock(str)
    }

    private fun getLeaves(str: String): DamagedBlock? {
        try {
            return if (str.isNullOrBlank()) null else DamagedBlock(str)
        } catch(e: Exception) {
            e.printStackTrace()
            return null
        }
    }
}