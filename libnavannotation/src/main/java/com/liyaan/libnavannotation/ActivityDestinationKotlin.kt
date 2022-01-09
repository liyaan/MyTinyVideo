package com.liyaan.libnavannotation

@Target(AnnotationTarget.CLASS)
annotation class ActivityDestinationKotlin(val pageUrl:String, val needLogin:Boolean = false, val asStarter:Boolean = false)