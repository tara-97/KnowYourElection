package com.example.android.politicalpreparedness.election

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.politicalpreparedness.network.CivicsApi
import com.example.android.politicalpreparedness.network.CivicsApiService
import com.example.android.politicalpreparedness.network.models.Election
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.await
import java.lang.Exception
private const val TAG = "ElectionsViewModel"
//TODO: Construct ViewModel and provide election datasource
class ElectionsViewModel: ViewModel() {


    //TODO: Create live data val for upcoming elections
    private var _elections:MutableLiveData<List<Election>> = MutableLiveData()

    val elections:LiveData<List<Election>>
        get() = _elections
    //TODO: Create live data val for saved elections
    private var _savedElections:MutableLiveData<List<Election>> = MutableLiveData()

    val savedElections:LiveData<List<Election>>
        get() = _savedElections
    //TODO: Create val and functions to populate live data for upcoming elections from the API and saved elections from local database
    init {
        viewModelScope.launch {
            getElectionsFromNetwork()
        }
    }
    private suspend fun getElectionsFromNetwork(){
                withContext(Dispatchers.IO){
                    _elections.value = CivicsApi.retrofitService.getElections().elections
                }




    }
    //TODO: Create functions to navigate to saved or upcoming election voter info

}