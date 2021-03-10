package com.example.android.politicalpreparedness.election

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.politicalpreparedness.database.ElectionDao
import com.example.android.politicalpreparedness.network.CivicsApi
import com.example.android.politicalpreparedness.network.models.*
import kotlinx.coroutines.launch
import java.lang.Exception

class VoterInfoViewModel(private val dataSource: ElectionDao) : ViewModel() {

    //TODO: Add live data to hold voter info
    private var _voterInfoLiveData = MutableLiveData<VoterInfoResponse>()
    val voterInfo:LiveData<VoterInfoResponse>
    get() = _voterInfoLiveData
    private var _buttonState = MutableLiveData<Button>()
    val buttonState:LiveData<Button>
    get() = _buttonState
    var election = MutableLiveData<Election>()
    var state = MutableLiveData<State>()
    var administrationBody = MutableLiveData<AdministrationBody>()
    var address = MutableLiveData<Address>()



    //TODO: Add var and methods to populate voter info

    fun getVoterInfo(electionId: Int,address:String){

        viewModelScope.launch {
            try {
                val voterInfoResponse = CivicsApi.retrofitService.getVoterInfo(address,electionId!!)
                _voterInfoLiveData.value = voterInfoResponse

                state.value = voterInfoResponse.state?.first()
                administrationBody.value = voterInfoResponse.state?.first()?.electionAdministrationBody
                this@VoterInfoViewModel.address.value = voterInfoResponse.state?.first()?.electionAdministrationBody?.correspondenceAddress

            }catch (e:Exception){
                Log.e(TAG, "Got error ${e.message}")
            }
        }
    }
    //TODO: Add var and methods to support loading URLs

    //TODO: Add var and methods to save and remove elections to local database
    private fun saveElection(election:Election){

        viewModelScope.launch {
            dataSource.insert(election)
        }

    }
    private fun removeElection(electionId: Int){

        viewModelScope.launch {
            dataSource.deleteElection(electionId)
        }

    }
    fun onClick(){
        if(buttonState.value == Button.ON){
            election.value?.let { saveElection(it) }

        }else{
            election.value?.let { removeElection(it.id) }


        }
        toggleButtonState()
    }
    //TODO: cont'd -- Populate initial state of save button to reflect proper action based on election saved status
    fun findButtonState(electionId: Int){
        viewModelScope.launch {
            val election = dataSource.get(electionId)


            if(election == null){
                _buttonState.value = Button.ON
            }else{
                _buttonState.value = Button.OFF

            }
        }

    }
    private fun toggleButtonState(){

        when(buttonState.value){
            Button.ON -> _buttonState.value = Button.OFF
            Button.OFF -> _buttonState.value = Button.ON
        }
    }

    /**
     * Hint: The saved state can be accomplished in multiple ways. It is directly related to how elections are saved/removed from the database.
     */

}
enum class Button{
    ON,
    OFF
}

private const val TAG = "VoterInfoViewModel"
        