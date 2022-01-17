package com.example.dictionary.viewModel

import android.util.Log
import androidx.lifecycle.*
import com.example.dictionary.models.WordSearchResponseData
import com.example.dictionary.repository.DictionaryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class DictionaryViewModel(private val repository: DictionaryRepository) : ViewModel() {
    private val _searchResponseData: MutableLiveData<WordSearchResponseData> = MutableLiveData()
    val searchResponseData: LiveData<WordSearchResponseData>
        get() = _searchResponseData

    private val _errorObserver: MutableLiveData<String> = MutableLiveData()
    val errorObserver: LiveData<String> get() = _errorObserver

    fun getDefinitions(word: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response: WordSearchResponseData? = repository.getDefinitions(word)
                if(response != null){
                    _searchResponseData.postValue(response)
                    _errorObserver.postValue(null)
                }else{
                    _errorObserver.postValue("Please enter a valid word.")
                }
            } catch (e: Exception) {
                _errorObserver.postValue(e.toString())
                Log.e("CHECKK", e.toString())
            }
        }
    }
}