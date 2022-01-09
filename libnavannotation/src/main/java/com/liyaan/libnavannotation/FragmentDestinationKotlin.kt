package com.liyaan.libnavannotation

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class FragmentDestinationKotlin(val pageUrl:String,val needLogin:Boolean = false,val asStarter:Boolean = false)