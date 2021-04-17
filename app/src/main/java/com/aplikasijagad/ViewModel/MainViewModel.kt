package com.aplikasijagad.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aplikasijagad.API.Repository
import com.aplikasijagad.Model.APICoronaProv
import com.aplikasijagad.Model.ApiSPB
import com.aplikasijagad.Model.DataAPI
//import com.aplikasijagad.Model.ResiBarang
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(private val repository: Repository) : ViewModel() {
    val myResponse: MutableLiveData<Response<List<DataAPI>>> = MutableLiveData()
    fun getResiviewModel() {
        viewModelScope.launch {
            val response = repository.getResiRepository()
            myResponse.value = response
        }
    }
}