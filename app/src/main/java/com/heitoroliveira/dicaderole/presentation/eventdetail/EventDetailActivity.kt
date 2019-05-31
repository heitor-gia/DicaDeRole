package com.heitoroliveira.dicaderole.presentation.eventdetail

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.heitoroliveira.dicaderole.R
import com.heitoroliveira.dicaderole.databinding.ActivityEventDetailBinding
import com.heitoroliveira.dicaderole.presentation.core.BaseActivity
import com.heitoroliveira.dicaderole.presentation.core.extensions.navigateToThere
import com.heitoroliveira.dicaderole.presentation.eventdetail.checkin.CheckinDialog
import com.heitoroliveira.dicaderole.presentation.eventdetail.checkin.CheckinStatus
import org.koin.android.scope.currentScope
import org.koin.android.viewmodel.ext.android.viewModel


class EventDetailActivity : BaseActivity() {
    private val eventDetailViewModel: EventDetailViewModel by viewModel()
    private val organizersListAdapter: OrganizersListAdapter by currentScope.inject()
    private lateinit var viewBinding: ActivityEventDetailBinding
    private val eventId: String by lazy { intent.getStringExtra(EVENT_ID_KEY) }
    private lateinit var checkinDialog: CheckinDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = DataBindingUtil.setContentView(this, R.layout.activity_event_detail)
        setupBinding()
        setupObservers()
        setupCheckinDialog()
    }

    private fun setupCheckinDialog() {
        checkinDialog = CheckinDialog(
            this,
            onConfirm = { name, email -> eventDetailViewModel.doCheckin(name, email) }
        )
    }

    private fun setupObservers() {
        with(eventDetailViewModel) {
            event.observe(this@EventDetailActivity, Observer {
                if (it != null) {
                    organizersListAdapter.items = it.organizers.toMutableList()
                    viewBinding.event = it
                }
            })

            isLoading.observe(this@EventDetailActivity, Observer {
                viewBinding.loadingLayout.visibility = if (it == true) View.VISIBLE else View.GONE
            })

            errors.observe(this@EventDetailActivity, Observer { processError(it) })

            checkinStatus.observe(this@EventDetailActivity, Observer {
                checkinDialog.setCheckinStatus(it ?: CheckinStatus.Idle)
            })
        }
    }

    private fun setupBinding() {
        viewBinding.rvOrganizers.layoutManager = GridLayoutManager(this, 2)
        viewBinding.rvOrganizers.adapter = organizersListAdapter
        setSupportActionBar(viewBinding.toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.item_detail_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.eventCheckin -> showCheckinDialog()
            R.id.eventRoute -> goToRoute()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showCheckinDialog() {
        checkinDialog.show()
    }

    private fun goToRoute() {
        eventDetailViewModel.event
            .value?.location?.navigateToThere(this)
    }

    override fun onResume() {
        super.onResume()
        eventDetailViewModel.loadEvent(eventId)
    }

    companion object {
        private const val EVENT_ID_KEY = "event.id"

        fun getIntent(context: Context, eventId: String) =
            Intent(context, EventDetailActivity::class.java).apply {
                putExtra(EVENT_ID_KEY, eventId)
            }
    }
}
