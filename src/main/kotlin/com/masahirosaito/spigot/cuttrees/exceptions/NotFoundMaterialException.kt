package com.masahirosaito.spigot.cuttrees.exceptions

class NotFoundMaterialException(val materialName: String) : Exception(
        "追加するブロックが見つかりません($materialName)"
)