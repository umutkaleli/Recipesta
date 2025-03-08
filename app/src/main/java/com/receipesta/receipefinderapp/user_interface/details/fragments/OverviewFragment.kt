package com.receipesta.receipefinderapp.user_interface.details.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import coil.load
import com.receipesta.receipefinderapp.R


class OverviewFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_overview, container, false)


        val title = arguments?.getString("titleKey")
        val poster = arguments?.getString("posterKey")
        val readyInMinutes = arguments?.getInt("readyInMinutesKey", 0)
        val aggregateLikes = arguments?.getInt("aggregateLikesKey", 0)
        val summary = arguments?.getString("summaryKey")

        view.findViewById<ImageView>(R.id.overview_imageView).load(poster)
        view.findViewById<TextView>(R.id.title_textView).text = title
        view.findViewById<TextView>(R.id.favorites_textView).text = aggregateLikes.toString()
        view.findViewById<TextView>(R.id.time_textView).text = readyInMinutes.toString()
        view.findViewById<TextView>(R.id.summary_textView).text = summary

        return view
    }

}