package com.masahirosaito.spigot.cuttrees.configs

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName
import org.bukkit.Material
import java.io.File

data class ConfigsLoader(

        @SerializedName("スニーキング状態の時に木をきる")
        val onSneaking: Boolean = false,

        @SerializedName("統計を増やす")
        val incrementStatistics: Boolean = true,

        @SerializedName("クリエイティブモード時に木をきる")
        val onCreative: Boolean = false,

        @SerializedName("クリエイティブモード時に道具の耐久値を減らす")
        val onCreativeDurability: Boolean = false,

        @SerializedName("クリエイティブモード時に統計を増やす")
        val onCreativeStatics: Boolean = false

) {
    companion object {

        fun load(file: File): ConfigsLoader {

            if (!file.parentFile.exists()) {
                file.parentFile.mkdirs()
            }

            var configs: ConfigsLoader

            if (!file.exists()) {
                configs = ConfigsLoader()
                file.createNewFile()
            } else {
                configs = Gson().fromJson(file.readText(), ConfigsLoader::class.java)
            }

            file.writeText(GsonBuilder().setPrettyPrinting().create().toJson(configs))

            return configs
        }
    }
}