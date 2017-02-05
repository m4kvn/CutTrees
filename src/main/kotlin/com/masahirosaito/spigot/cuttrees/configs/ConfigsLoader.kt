package com.masahirosaito.spigot.cuttrees.configs

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName
import org.bukkit.Material
import java.io.File

data class ConfigsLoader(

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

        @SerializedName("発動させるツールの種類")
        var toolTypeNames: List<String> = listOf(
                Material.DIAMOND_AXE.name,
                Material.IRON_AXE.name,
                Material.GOLD_AXE.name,
                Material.STONE_AXE.name,
                Material.WOOD_AXE.name
        ),

        @SerializedName("破壊できるブロックの種類(ブロック名, 葉ブロック名)")
        var anotherBlockTypeNames: Map<String, String> = mapOf()

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

            return configs
        }
    }
}