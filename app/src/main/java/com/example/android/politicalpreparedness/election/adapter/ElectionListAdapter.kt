package com.example.android.politicalpreparedness.election.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.politicalpreparedness.databinding.ElectionInfoBinding

import com.example.android.politicalpreparedness.network.models.Election

class ElectionListAdapter(private val clickListener: ElectionListener): ListAdapter<Election, ElectionViewHolder>(ElectionDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ElectionViewHolder {
        return ElectionViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ElectionViewHolder, position: Int) {
        holder.bind(clickListener,getItem(position))
    }

    //TODO: Bind ViewHolder

    //TODO: Add companion object to inflate ViewHolder (from)
}







//TODO: Create ElectionViewHolder
class ElectionViewHolder private constructor(val binding:ElectionInfoBinding) : RecyclerView.ViewHolder(binding.root){
    fun bind(clickListener: ElectionListener,item:Election){
        binding.election = item
        binding.clickListener = clickListener
        binding.executePendingBindings()
    }
    companion object {
        fun from(parent: ViewGroup): ElectionViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ElectionInfoBinding.inflate(layoutInflater, parent, false)

            return ElectionViewHolder(binding)
        }
    }

}
//TODO: Create ElectionDiffCallback
class ElectionDiffCallback:DiffUtil.ItemCallback<Election>() {
    override fun areItemsTheSame(oldItem: Election, newItem: Election): Boolean {
        return  oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Election, newItem: Election): Boolean {
        return oldItem == newItem
    }


}
//TODO: Create ElectionListener
class ElectionListener(val clickListener:(electionId:Int) -> Unit) {
    fun onClick(election:Election) = clickListener(election.id)

}