package com.heitoroliveira.dicaderole.presentation.eventdetail

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.heitoroliveira.dicaderole.databinding.ItemOrganizerBinding
import com.heitoroliveira.dicaderole.presentation.eventdetail.model.Organizer

class OrganizersListAdapter : RecyclerView.Adapter<OrganizersListAdapter.OrganizerViewHolder>(){

    var items: MutableList<Organizer> = mutableListOf()
        set(value) {
            items.clear()
            items.addAll(value)
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrganizerViewHolder {
        val binding = ItemOrganizerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrganizerViewHolder(binding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: OrganizerViewHolder, position: Int) {
        holder.itemOrganizerBinding.organizer = items[position]
    }


    class OrganizerViewHolder(val itemOrganizerBinding: ItemOrganizerBinding) : RecyclerView.ViewHolder(itemOrganizerBinding.root)
}