package com.example.android.politicalpreparedness.representative


import android.util.Log
import androidx.databinding.Bindable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.politicalpreparedness.network.CivicsApi
import com.example.android.politicalpreparedness.network.models.Office
import com.example.android.politicalpreparedness.network.models.Official
import com.example.android.politicalpreparedness.representative.model.Representative
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.example.android.politicalpreparedness.network.models.Address

class RepresentativeViewModel: ViewModel() {

    //TODO: Establish live data for representatives and address
    private var _representatives = MutableLiveData<List<Representative>>()
    val representatives:LiveData<List<Representative>>
    get() = _representatives


    var line1 = MutableLiveData<String>()
    var line2 = MutableLiveData<String>()
    var city = MutableLiveData<String>()
    var state = MutableLiveData<String>()
    var zip = MutableLiveData<String>()
    var address = MutableLiveData<Address>()



    //TODO: Create function to fetch representatives from API from a provided address

    /**
     *  The following code will prove helpful in constructing a representative from the API. This code combines the two nodes of the RepresentativeResponse into a single official :

    val (offices, officials) = getRepresentativesDeferred.await()
    _representatives.value = offices.flatMap { office -> office.getRepresentatives(officials) }

    Note: getRepresentatives in the above code represents the method used to fetch data from the API
    Note: _representatives in the above code represents the established mutable live data housing representatives

     */
    fun getRepresentatives(address: String){
        try {
            viewModelScope.launch {
                val (offices,officials) = CivicsApi.retrofitService.getRepresentatives(address)
                _representatives.value = offices.flatMap { office -> office.getRepresentatives(officials) }
            }
        }catch (e:Exception){
            Log.d(TAG, "getRepresentatives: Got error ${e.message}")
        }
        


    }

    //TODO: Create function get address from geo location
    fun getLocationAndFindRepresentative(){
        address.value?.let {
            line1.value = it.line1
            line2.value = it.line2
            city.value = it.city
            state.value = it.state
            zip.value = it.zip
            getRepresentatives(it.toFormattedString())
        }
    }
    fun getFieldsAndFindRepresentative(){

        if(!isInValid()){
            val typedAddress = Address(line1.value!!,line2.value,city.value!!,state.value!!,zip.value!!)
            getRepresentatives(typedAddress.toFormattedString())
        }



    }
    fun isInValid():Boolean{
        return line1.value.isNullOrEmpty() || city.value.isNullOrEmpty() || state.value.isNullOrEmpty() || zip.value.isNullOrEmpty()
    }

    //TODO: Create function to get address from individual fields


}

private const val TAG = "RepresentativeViewModel"