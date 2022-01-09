package com.liyaan.mytinyvideo.utils

import android.text.TextUtils
import android.util.Log
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.TypeReference
import com.liyaan.libcommon.utils.AppGlobals
import com.liyaan.mytinyvideo.model.BottomBar
import com.liyaan.mytinyvideo.model.Destination
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

object AppConfig {
//    val sDestConfig:HashMap<String,Destination> by lazy {
//        val content = parseFile("destnation.json")
//        JSON.parseObject(content,object: TypeReference<HashMap<String, Destination>>(){})
//    }
    fun getSDestConfig():HashMap<String,Destination>{
        val content = parseFile("destnation.json")
        return JSON.parseObject(content,object: TypeReference<HashMap<String, Destination>>(){})
    }
//    val bottomBar:BottomBar by lazy {
//        val content = parseFile("main_tabs_config.json")
//        JSON.parseObject(content,BottomBar::class.java)
//    }
    fun getBottomBar():BottomBar{
        val content = parseFile("main_tabs_config.json")
        if (TextUtils.isEmpty(content)){
            Log.i("aaaaaaa","dfdfdsdfsdfsdf")
        }
        return JSON.parseObject(content,BottomBar::class.java)
    }
    fun parseFile(fileName: String):String{
        val builder = StringBuilder()
        AppGlobals.sApplication?.apply {
            val assets = javaClass?.classLoader
            var stream:InputStream? = null
            var reader:BufferedReader? = null

            try {
                stream = assets?.getResourceAsStream("assets/$fileName")
                reader = BufferedReader(InputStreamReader(stream))
                var line: String? = null
                while (reader.readLine().also { line = it } != null) {
                    builder.append(line)
                }
            }catch (e: Exception){
                e.printStackTrace()
            }finally {
                stream?.close()
                reader?.close()
            }
        }

        return builder.toString()
    }
}




