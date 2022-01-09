package com.liyaan.mytinyvideo.model

data class Destination(
    var asStarter: Boolean = true,
    var clazName: String = "",
    var id: Int = 0,
    var isFragment: Boolean = true,
    var needLogin: Boolean = true,
    var pageUrl: String = ""
)