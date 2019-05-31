package com.heitoroliveira.dicaderole.presentation.events

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.heitoroliveira.dicaderole.databinding.ItemEventBinding

class EventItemAdapter : RecyclerView.Adapter<EventItemAdapter.ViewHolder>() {

    var onItemClick: (EventItem, View) -> Unit = { _,_ -> }
    var onShareItemClick: (EventItem) -> Unit = {}

    var itens: MutableList<EventItem> = mutableListOf()
        set(value) {
            itens.clear()
            itens.addAll(value)
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = itens.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val eventItem = itens[position]
        holder.eventBinding.item = eventItem
        holder.itemView.setOnClickListener { onItemClick(eventItem, holder.itemView) }
        holder.eventBinding.btnItemShare.setOnClickListener { onShareItemClick(eventItem) }
    }


    class ViewHolder(val eventBinding: ItemEventBinding) :
        RecyclerView.ViewHolder(eventBinding.root)
}