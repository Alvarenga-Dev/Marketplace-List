package com.alvarengadev.marketplacelist.utils.delegates

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner

interface StateDelegate {
    fun StateDelegate.registerStateDelegation(
        lifecycle: Lifecycle,
        viewModel: MediationDelegate
    )
    fun renderState(viewState: ViewState)
}

class StateDelegation : DefaultLifecycleObserver, StateDelegate {

    private lateinit var viewModel: MediationDelegate

    private lateinit var delegate: StateDelegate

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)

        viewModel.mediator().observe(owner) {
            renderState(it)
            delegate.renderState(it)
        }
    }

    override fun StateDelegate.registerStateDelegation(
        lifecycle: Lifecycle,
        viewModel: MediationDelegate
    ) {
        this@StateDelegation.viewModel = viewModel
        this@StateDelegation.delegate = this
        lifecycle.addObserver(this@StateDelegation)
        lifecycle.addObserver(viewModel)
    }

    override fun renderState(viewState: ViewState) {
        when (viewState) {
            is ViewState.Success -> {
            }
            is ViewState.Failure -> {
            }
            is ViewState.Loading -> {
            }
        }
    }
}
