package com.masahirosaito.spigot.cuttrees.configs

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName
import com.masahirosaito.spigot.cuttrees.CutTrees
import com.masahirosaito.spigot.cuttrees.exceptions.NotFoundMaterialException
import com.masahirosaito.spigot.cuttrees.utils.isMushroom
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
        val incrementStatisticsCreative: Boolean = false,

        @SerializedName("プレイヤーが設置したブロックを破壊しない")
        val onAntiBLock: Boolean = true,

        @SerializedName("破壊できるブロックの種類(ブロック名, 葉ブロック名)")
        val anotherBlockTypeNames: Map<String, String> = mapOf()

) {
    companion object {

        fun load(file: File): Configs {

            if (!file.parentFile.exists()) {
                file.parentFile.mkdirs()
            }

            var configs: Configs

            if (!file.exists()) {
                configs = Configs()
                file.createNewFile()
            } else {
                configs = Gson().fromJson(file.readText(), Configs::class.java)
            }

            file.writeText(GsonBuilder().setPrettyPrinting().create().toJson(configs))

            configs.loadAnotherBlockTypes()

            return configs
        }
    }

    fun isValid(block: Block): Boolean = when {
        block.isTree() -> true
        block.isMushroom() -> true
        isAnotherBlock(block) -> true
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
        onSneaking && !player.isSneaking-> false
        else -> true
    }

    fun isNotMax(blocks: Collection<Block>): Boolean = blocks.size < maxBlockAmount

    fun isNotAnti(block:Block, plugin: CutTrees): Boolean {
        return if(onAntiBLock) !plugin.antiBlockManager.isAnti(block) else true
    }

    private fun getMaterial(name: String): Material {
        return Material.getMaterial(name) ?: throw NotFoundMaterialException(name)
    }

    private fun loadAnotherBlockTypes() {
        anotherBlockTypeNames.forEach { pair ->
            try {
                getMaterial(pair.key)
            } catch(e: Exception) {
                e.printStackTrace()
            }
            try {
                if (!pair.value.isNullOrBlank()) {
                    getMaterial(pair.value)
                }
            } catch(e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun isAnotherBlock(block: Block): Boolean = anotherBlockTypeNames.containsKey(block.type.name)
}