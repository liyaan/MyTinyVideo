package com.liyaan.libnavcompiler


import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import com.google.auto.service.AutoService
import com.liyaan.libnavannotation.ActivityDestinationKotlin
import com.liyaan.libnavannotation.FragmentDestinationKotlin
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStreamWriter
import java.util.*
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.Element
import javax.lang.model.element.TypeElement
import javax.tools.Diagnostic
import javax.tools.StandardLocation

@AutoService(Processor::class)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes(
    "com.liyaan.libnavannotation.ActivityDestinationKotlin",
    "com.liyaan.libnavannotation.FragmentDestinationKotlin"
)
class NavKotlinProcessor: AbstractProcessor() {
    private var messager: Messager? = null
    private var filer: Filer? = null
    private val OUTPUT_FILE_NAME = "destnation.json"

    @Synchronized
    override fun init(processingEnvironment: ProcessingEnvironment) {
        super.init(processingEnvironment)
        messager = processingEnvironment.messager
        filer = processingEnvironment.filer
    }

    override fun process(
        set: Set<TypeElement?>?,
        roundEnvironment: RoundEnvironment
    ): Boolean {
        val fragmentElements: Set<Element> =
            roundEnvironment.getElementsAnnotatedWith(
                FragmentDestinationKotlin::class.java
            )
        val activityElements: Set<Element> =
            roundEnvironment.getElementsAnnotatedWith(
                ActivityDestinationKotlin::class.java
            )
        if (!fragmentElements.isEmpty() || !activityElements.isEmpty()) {
            val destMap: HashMap<String?, JSONObject?> =
                HashMap<String?, JSONObject?>()
            handleDestination(
                fragmentElements,
                FragmentDestinationKotlin::class.java, destMap
            )
            handleDestination(
                activityElements,
                ActivityDestinationKotlin::class.java, destMap
            )
            var fos: FileOutputStream? = null
            var writer: OutputStreamWriter? = null
            try {
                val resource =
                    filer!!.createResource(StandardLocation.CLASS_OUTPUT, "", OUTPUT_FILE_NAME)
                val resourcePath = resource.toUri().path
                messager!!.printMessage(Diagnostic.Kind.NOTE, "resourcePath:$resourcePath")
                val appPath =
                    resourcePath.substring(0, resourcePath.indexOf("app") + 4)
                val assetsPath = appPath + "src/main/assets"
                val file = File(assetsPath)
                if (!file.exists()) {
                    file.mkdirs()
                }
                val outPutFile = File(file, OUTPUT_FILE_NAME)
                if (outPutFile.exists()) {
                    outPutFile.delete()
                }
                outPutFile.createNewFile()
                val content: String = JSON.toJSONString(destMap)
                fos = FileOutputStream(outPutFile)
                writer = OutputStreamWriter(fos, "UTF-8")
                writer.write(content)
                writer.flush()
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                if (writer != null) {
                    try {
                        writer.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
                if (fos != null) {
                    try {
                        fos.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
        }
        return true
    }

    private fun handleDestination(
        elements: Set<Element>,
        annotationClz: Class<out Annotation>,
        destMap: HashMap<String?,JSONObject?>
    ) {
        for (element in elements) {
            val typeElement = element as TypeElement
            var pageUrl: String? = null
            val clazName = typeElement.qualifiedName.toString()
            val id = Math.abs(clazName.hashCode())
            var needLogin = false
            var asStarter = false
            var isFragment = false
            val annotation = typeElement.getAnnotation(annotationClz)
            if (annotation is FragmentDestinationKotlin) {
                val dest: FragmentDestinationKotlin =
                    annotation
                pageUrl = dest.pageUrl
                asStarter = dest.asStarter
                needLogin = dest.needLogin
                isFragment = true
            } else if (annotation is ActivityDestinationKotlin) {
                val dest: ActivityDestinationKotlin =
                    annotation
                pageUrl = dest.pageUrl
                asStarter = dest.asStarter
                needLogin = dest.needLogin
                isFragment = false
            }
            if (destMap.containsKey(pageUrl)) {
                messager!!.printMessage(Diagnostic.Kind.ERROR, "不同的页面不允许使用相同的 pageUrl:$pageUrl")
            } else {
                val jsonObject: JSONObject = JSONObject()
                jsonObject.put("id", id)
                jsonObject.put("pageUrl", pageUrl)
                jsonObject.put("clazName", clazName)
                jsonObject.put("needLogin", needLogin)
                jsonObject.put("asStarter", asStarter)
                jsonObject.put("isFragment", isFragment)
                destMap[pageUrl] = jsonObject
            }
        }
    }
}

private operator fun <K, V> HashMap<K, V>.set(pageUrl: K?, value: V) {
    put(pageUrl!!,value)
}
