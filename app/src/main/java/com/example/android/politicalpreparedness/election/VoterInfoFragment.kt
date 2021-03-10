package com.example.android.politicalpreparedness.election

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.android.politicalpreparedness.database.ElectionDatabase
import com.example.android.politicalpreparedness.databinding.FragmentVoterInfoBinding
import com.example.android.politicalpreparedness.network.models.Division
import com.example.android.politicalpreparedness.network.models.Election
import kotlinx.android.synthetic.main.fragment_voter_info.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

class VoterInfoFragment : Fragment() {
    private val args:VoterInfoFragmentArgs by navArgs()
    private lateinit var election:Election



    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding = FragmentVoterInfoBinding.inflate(inflater,container,false)
        //TODO: Add ViewModel values and create ViewModel
        val application = requireNotNull(this.activity).application

        val dataSource = ElectionDatabase.getInstance(application)
        val viewModelFactory = VoterInfoViewModelFactory(dataSource)
        val viewModel = ViewModelProvider(this,viewModelFactory).get(VoterInfoViewModel::class.java)

        election = args.argElection
        viewModel.election.value = election
        viewModel.getVoterInfo(election.id,election.division.state+" "+election.division.country)
        viewModel.findButtonState(election.id)
        binding.lifecycleOwner = this
        binding.viewmodel = viewModel



        //TODO: Add binding values
        viewModel.buttonState.observe(viewLifecycleOwner, Observer {
            when(it){
                Button.ON ->{
                    binding.followButton.text = "Follow Election"
                }
                Button.OFF -> {
                    binding.followButton.text = "Unfollow Election"
                }
            }
        })

        //TODO: Populate voter info -- hide views without provided data.
        /**
        Hint: You will need to ensure proper data is provided from previous fragment.
        */


        //TODO: Handle loading of URLs

        //TODO: Handle save button UI state
        //TODO: cont'd Handle save button clicks

        return binding.root

    }

    //TODO: Create method to load URL intents

}

private const val TAG = "VoterInfoFragment"