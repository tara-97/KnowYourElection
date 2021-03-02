package com.example.android.politicalpreparedness.election

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.android.politicalpreparedness.R
import com.example.android.politicalpreparedness.databinding.FragmentElectionBinding
import com.example.android.politicalpreparedness.network.models.Election

private const val TAG = "ElectionsFragment"
class ElectionsFragment: Fragment() {

    //TODO: Declare ViewModel


    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        //TODO: Add ViewModel values and create ViewModel
        val binding = FragmentElectionBinding.inflate(layoutInflater,container,false)

        val viewModel:ElectionsViewModel by viewModels()
        //TODO: Add binding values
        viewModel.elections.observe(viewLifecycleOwner, Observer {
            elections -> Log.d(TAG,"Elections size is ${elections.size}")
        })
        //TODO: Link elections to voter info

        //TODO: Initiate recycler adapters

        //TODO: Populate recycler adapters
        return binding.root

    }

    //TODO: Refresh adapters when fragment loads

}