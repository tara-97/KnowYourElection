package com.example.android.politicalpreparedness.election

import com.example.android.politicalpreparedness.election.ElectionsFragmentDirections
import com.example.android.politicalpreparedness.election.ElectionsViewModel



import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.android.politicalpreparedness.R
import com.example.android.politicalpreparedness.database.ElectionDatabase
import com.example.android.politicalpreparedness.databinding.FragmentElectionBinding
import com.example.android.politicalpreparedness.election.adapter.ElectionListAdapter
import com.example.android.politicalpreparedness.election.adapter.ElectionListener
import com.example.android.politicalpreparedness.network.models.Election
import kotlinx.android.synthetic.main.fragment_election.*

private const val TAG = "ElectionsFragment"
class ElectionsFragment: Fragment() {

    private lateinit var upComingListAdapter: ElectionListAdapter
    private lateinit var savedElectionListAdapter: ElectionListAdapter
    //TODO: Declare ViewModel
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        //TODO: Add ViewModel values and create ViewModel
        val binding = FragmentElectionBinding.inflate(layoutInflater,container,false)

        upComingListAdapter = ElectionListAdapter(ElectionListener { election ->
            val action = ElectionsFragmentDirections.actionElectionsFragmentToVoterInfoFragment(election)
            val navController = findNavController()
            navController.navigate(action)
        })
        savedElectionListAdapter = ElectionListAdapter(ElectionListener { election ->
            val action = ElectionsFragmentDirections.actionElectionsFragmentToVoterInfoFragment(election)
            val navController = findNavController()
            navController.navigate(action)
        })

        binding.electionList.adapter = upComingListAdapter
        binding.savedelectionlist.adapter = savedElectionListAdapter

        val application = requireNotNull(this.activity).application

        val dataSource = ElectionDatabase.getInstance(application)

        val viewModelFactory = ElectionsViewModelFactory(dataSource)
        val viewModel = ViewModelProvider(this,viewModelFactory).get(ElectionsViewModel::class.java)
        //TODO: Add binding values

        //TODO: Link elections to voter info

        //TODO: Initiate recycler adapters

        //TODO: Populate recycler adapters
        viewModel.elections.observe(viewLifecycleOwner, Observer {it?.let {
                upComingListAdapter.submitList(it)
            }
        })
        viewModel.savedElections.observe(viewLifecycleOwner, Observer { it?.let {
            savedElectionListAdapter.submitList(it)
        } })
        return binding.root

    }


    //TODO: Refresh adapters when fragment loads

}