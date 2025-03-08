package com.receipesta.receipefinderapp.user_interface.details.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.receipesta.receipefinderapp.R
import com.receipesta.receipefinderapp.adapter.IngredientsAdapter
import com.receipesta.receipefinderapp.network.ExtendedIngredient

class IngredientsFragment : Fragment() {

    private lateinit var mAdapter : IngredientsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_ingredients, container, false)

        val ingredients = arguments?.getParcelableArrayList<ExtendedIngredient>("ingredientsKey")
        mAdapter = IngredientsAdapter(ingredients ?: emptyList())

        setupRecyclerView(view)

        return view
    }

    @SuppressLint("CutPasteId")
    private fun setupRecyclerView(view: View){
        view.findViewById<RecyclerView>(R.id.ingredients_recyclerView).adapter = mAdapter
        view.findViewById<RecyclerView>(R.id.ingredients_recyclerView).layoutManager = LinearLayoutManager(requireContext())
    }

}