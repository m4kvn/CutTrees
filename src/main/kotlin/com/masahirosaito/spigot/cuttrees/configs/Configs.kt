package com.masahirosaito.spigot.cuttrees.configs

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName
import com.masahirosaito.spigot.cuttrees.utils.isTree
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.inventory.ItemStack
import org.bukkit.material.Tree
import java.io.File

data class Configs(

        @SerializedName("一度に壊せるブロックの数")
        val maxBlockAmount: Int = 200,

        @SerializedName("破壊する隣接ブロックの距離")
        val rangeBreakBlock: Int = 1

) {
    companion object {

        fun load(file: File): Configs {

            if (!file.parentFile.exists()) {
                file.parentFile.mkdirs()
            }

            if (!file.exists()) return Configs().apply {
                file.createNewFile()
                file.writeText(GsonBuilder().setPrettyPrinting().create().toJson(this))
            }

            return Gson().fromJson(file.readText(), Configs::class.java).apply {
                file.writeText(GsonBuilder().setPrettyPrinting().create().toJson(this))
            }
        }
    }

    fun isValid(block: Block): Boolean = when {
        block.isTree() -> true
        else -> false
    }

    fun isValid(tool: ItemStack): Boolean = when (tool.type) {
        Material.DIAMOND_AXE,
        Material.WOOD_AXE,
        Material.STONE_AXE,
        Material.GOLD_AXE,
        Material.IRON_AXE -> true
        else -> false
    }

    fun isNotMax(blocks: Collection<Block>): Boolean = blocks.size < maxBlockAmount
}