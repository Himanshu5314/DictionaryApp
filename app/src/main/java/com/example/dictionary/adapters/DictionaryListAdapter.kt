package com.example.dictionary.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dictionary.databinding.DefinitionItemBinding
import com.example.dictionary.models.Definition

class DictionaryListAdapter(private val list: List<Definition>) : RecyclerView.Adapter<DictionaryListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = DefinitionItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(private val itemViewBinding: DefinitionItemBinding) : RecyclerView.ViewHolder(itemViewBinding.root) {
        fun bind(definition: Definition) {
            itemViewBinding.definition.text = definition.definition
            if(definition.example.isNullOrEmpty()) {
                itemViewBinding.exampleTitle.visibility = View.GONE
                itemViewBinding.example.visibility = View.GONE
            } else {
                itemViewBinding.example.text = definition.example
            }
            var synonyms: String = ""

            val synonymListSize = definition.synonyms.size

            definition.synonyms?.forEachIndexed { index, item ->
                synonyms += item
                if(index < synonymListSize - 1){
                    synonyms += ", "
                }
            }


            if(synonymListSize == 0){
                itemViewBinding.synonymTitle.visibility = View.GONE
                itemViewBinding.synonyms.visibility = View.GONE
            } else {
                itemViewBinding.synonyms.text = synonyms
            }

            var antonyms: String = ""

            val antonymListSize = definition.antonyms.size

            definition.antonyms?.forEachIndexed { index, item ->
                antonyms += item
                if(index < antonymListSize - 1){
                    antonyms += ", "
                }
            }

            if(antonymListSize == 0){
                itemViewBinding.antonymTitle.visibility = View.GONE
                itemViewBinding.antonyms.visibility = View.GONE
            } else {
                itemViewBinding.antonyms.text = antonyms
            }

            if(synonymListSize == 0 && antonymListSize == 0) {
                itemViewBinding.viewDivider.visibility = View.GONE
            }
        }
    }
}