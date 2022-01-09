package com.liyaan.mytinyvideo.utils

import android.content.ComponentName
import androidx.fragment.app.FragmentActivity
import androidx.navigation.ActivityNavigator
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.NavGraphNavigator
import androidx.navigation.fragment.FragmentNavigator
import com.liyaan.libcommon.utils.AppGlobals

object NavGraphBuilder {
    fun build(activity:FragmentActivity,controller:NavController,containerId:Int){
        val provider = controller.navigatorProvider
        val navGraph = NavGraph(NavGraphNavigator(provider))

        val fragmentNavigator = provider.getNavigator(FragmentNavigator::class.java)
        val activityNavigator = provider.getNavigator(ActivityNavigator::class.java)

        val destConfig = AppConfig.getSDestConfig()

        destConfig.forEach {
            if (it.value.isFragment){
                val destination = fragmentNavigator.createDestination()
                destination.id = it.value.id
                destination.className = it.value.clazName
                destination.addDeepLink(it.value.pageUrl)
                navGraph.addDestination(destination)
            }else {
                val destination = activityNavigator.createDestination()
                destination.id = it.value.id
                destination.setComponentName(ComponentName(AppGlobals.sApplication?.packageName?:"",it.value.clazName))
                destination.addDeepLink(it.value.pageUrl)
                navGraph.addDestination(destination)
            }
            if (it.value.asStarter){
                navGraph.startDestination = it.value.id
            }
        }
        controller.graph = navGraph
    }
}