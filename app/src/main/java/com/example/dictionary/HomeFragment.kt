package com.example.dictionary

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dictionary.adapters.DictionaryListAdapter
import com.example.dictionary.databinding.FragmentHomeBinding
import com.example.dictionary.models.Definition
import com.example.dictionary.repository.DictionaryRepository
import com.example.dictionary.retrofit.RetrofitHelper
import com.example.dictionary.retrofit.RetrofitService
import com.example.dictionary.viewModel.DictionaryViewModel
import com.example.dictionary.viewModel.DictionaryViewModelFactory
import kotlinx.android.synthetic.main.definition_item.*


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var dictionaryService: RetrofitService
    private lateinit var dictionaryRepository: DictionaryRepository
    private lateinit var dictionaryViewModel: DictionaryViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        dictionaryService = RetrofitHelper.getInstance().create(RetrofitService::class.java)
        dictionaryRepository = DictionaryRepository(dictionaryService)
        dictionaryViewModel =
            ViewModelProvider(this, DictionaryViewModelFactory(dictionaryRepository)).get(
                DictionaryViewModel::class.java
            )
        binding.definitionRv.layoutManager = LinearLayoutManager(requireContext())
        setListeners()
        initObservers()
        return binding.root
    }

    private fun initObservers() {
        dictionaryViewModel.searchResponseData.observe(viewLifecycleOwner, {
            if(!it.isNullOrEmpty()) {
                val definitionList = mutableListOf<Definition>()
                it?.forEach{ wordSearchResponseItem ->
                    wordSearchResponseItem?.meanings?.forEach{ meaning ->
                        meaning?.definitions?.forEach{ definition ->
                            definitionList.add(definition)
                        }
                    }
                }
                binding.wordName.text = it[0].word
                binding.definitionRv.adapter = DictionaryListAdapter(definitionList)
            } else {
                Toast.makeText(requireContext(), "No definitions found, try another word.", Toast.LENGTH_LONG).show()
            }
            binding.loadingAnimation.visibility = View.GONE
        })

        dictionaryViewModel.errorObserver.observe(viewLifecycleOwner, {
            if(!it.isNullOrEmpty()){
                binding.loadingAnimation.visibility = View.GONE
                Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun setListeners() {
        binding.searchingButton.setOnClickListener{
            fetchDefinitions()
            hideKeyboard()
        }
    }

    private fun isLetters(string: String): Boolean {
        if(string.isNullOrEmpty()){
            return false
        }
        for (c in string)
        {
            if (c !in 'A'..'Z' && c !in 'a'..'z') {
                return false
            }
        }
        return true
    }

    private fun fetchDefinitions() {
        val word = binding.wordSearchEt.text.trim().toString()
        if(!isLetters(word)) {
            Toast.makeText(requireContext(), "Please enter a valid word", Toast.LENGTH_LONG).show()
            return
        }
        binding.loadingAnimation.visibility = View.VISIBLE
        dictionaryViewModel.getDefinitions(word.toLowerCase())
    }

    fun hideKeyboard() {
        val imm = activity?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        //Find the currently focused view, so we can grab the correct window token from it.
        var view = activity?.currentFocus
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}