package com.liyaan.mytinyvideo.ui.find

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.liyaan.libnavannotation.FragmentDestinationKotlin
import com.liyaan.mytinyvideo.R
import com.liyaan.mytinyvideo.ui.sofa.FindViewModel

@FragmentDestinationKotlin(pageUrl = "main/tabs/find",asStarter = false)
class FindFragment : Fragment() {

    private lateinit var dashboardViewModel: FindViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
                ViewModelProvider(this).get(FindViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        val textView: TextView = root.findViewById(R.id.text_dashboard)
        dashboardViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}