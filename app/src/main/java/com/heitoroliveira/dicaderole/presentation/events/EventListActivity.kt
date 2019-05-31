package com.heitoroliveira.dicaderole.presentation.events

import android.app.ActivityOptions
import android.arch.lifecycle.Observer
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.LinearLayoutManager.VERTICAL
import android.view.View
import com.heitoroliveira.dicaderole.R
import com.heitoroliveira.dicaderole.databinding.ActivityEventListBinding
import com.heitoroliveira.dicaderole.presentation.core.BaseActivity
import com.heitoroliveira.dicaderole.presentation.eventdetail.EventDetailActivity
import org.koin.android.scope.currentScope
import org.koin.android.viewmodel.ext.android.viewModel

class EventListActivity : BaseActivity() {
    private val eventViewModel: EventListViewModel by viewModel()
    private val eventItemAdapter: EventItemAdapter by currentScope.inject()
    private lateinit var viewBinding: ActivityEventListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = DataBindingUtil.setContentView(this, R.layout.activity_event_list)
        setupBinding()
        setupAdapter()
        setupObservers()
    }

    private fun setupObservers() {
        eventViewModel.events.observe(this, Observer { items ->
            eventItemAdapter.itens = items.orEmpty().toMutableList()
        })
        eventViewModel.isLoading.observe(this, Observer {
            viewBinding.swipeLayout.isRefreshing = it ?: false
        })
        eventViewModel.errors.observe(this, Observer { processError(it) })
    }

    private fun setupBinding() {
        with(viewBinding) {
            viewModel = eventViewModel
            rvEvents.adapter = eventItemAdapter
            rvEvents.layoutManager = LinearLayoutManager(this@EventListActivity, VERTICAL, false)
            swipeLayout.setOnRefreshListener { eventViewModel.loadEvents() }
        }
    }

    private fun setupAdapter() {
        eventItemAdapter.onItemClick = { item, view ->
            val eventImage = view.findViewById<View>(R.id.ivEventImage)
            val intent = EventDetailActivity.getIntent(this, item.id)
            val options = ActivityOptions
                .makeSceneTransitionAnimation(this, eventImage, "eventImage")
            startActivity(intent, options.toBundle())
        }
        eventItemAdapter.onShareItemClick = {
            startActivity(Intent().apply {
                action = Intent.ACTION_SEND
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, it.title)
            })
        }
    }
}
