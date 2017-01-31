package com.masahirosaito.spigot.cuttrees.configs

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName
import com.masahirosaito.spigot.cuttrees.events.PlayerStatisticsEvent
import com.masahirosaito.spigot.cuttrees.utils.isTree
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.material.Tree
import java.io.File

data class Configs(

        @SerializedName("コンソールにデバッグメッセージを表示する")
        val onDebug: Boolean = false,

        @SerializedName("クリエイティブモード時に道具の耐久値を減らす")
        val onCreativeDurabilityReduce: Boolean = false,

        @SerializedName("ブロックを破壊した時にメッセージを表示する")
        val onBlockBreakMessage: Boolean = true,

        @SerializedName("一度に壊せるブロックの数")
        val maxBlockAmount: Int = 200,

        @SerializedName("破壊する隣接ブロックの距離")
        val rangeBreakBlock: Int = 2,

        @SerializedName("破壊する隣接葉ブロックの距離")
        val rangeDecayLeaves: Int = 2,

        @SerializedName("スネーキング状態の時に木をきる")
        val onSneaking: Boolean = true,

        @SerializedName("統計を増やす")
        val incrementStatistics: Boolean = true,

        @SerializedName("クリエイティブモード時に統計を増やす")
        val incrementStatisticsCreative: Boolean = false

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

    fun isValid(player: Player): Boolean = when {
        onSneaking -> player.isSneaking
        else -> false
    }


    fun isNotMax(blocks: Collection<Block>): Boolean = blocks.size < maxBlockAmount
}