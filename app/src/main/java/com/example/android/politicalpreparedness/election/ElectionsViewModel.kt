package com.example.android.politicalpreparedness.election

import androidx.lifecycle.*
import com.example.android.politicalpreparedness.database.ElectionDao
import com.example.android.politicalpreparedness.database.ElectionDatabase
import com.example.android.politicalpreparedness.network.CivicsApi
import com.example.android.politicalpreparedness.network.models.Election
import kotlinx.coroutines.launch

private const val TAG = "ElectionsViewModel"
//TODO: Construct ViewModel and provide election datasource
class ElectionsViewModel(val dataSource: ElectionDao) : ViewModel() {


    //TODO: Create live data val for upcoming elections
    private var _elections:MutableLiveData<List<Election>> = MutableLiveData()


    val elections:LiveData<List<Election>>
        get() = _elections
    //TODO: Create live data val for saved elections
    

    val savedElections:LiveData<List<Election>>
        get() = dataSource.getElections()
    //TODO: Create val and functions to populate live data for upcoming elections from the API and saved elections from local database
    init {

        viewModelScope.launch {
            getElectionsFromNetwork()

        }

    }
    private suspend fun getElectionsFromNetwork(){
        _elections.value = CivicsApi.retrofitService.getElections().elections
    }

    //TODO: Create functions to navigate to saved or upcoming election voter info

}