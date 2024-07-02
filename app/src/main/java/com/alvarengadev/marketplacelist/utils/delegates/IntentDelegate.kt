package com.alvarengadev.marketplacelist.utils.delegates

import android.content.Intent

interface IntentDelegate {
    fun IntentDelegate.registerIntentDelegation(intent: Intent?, mediationDelegate: MediationDelegate)
    fun intent(): Intent
}

class IntentDelegation : IntentDelegate {

    private lateinit var viewModel: MediationDelegate

    private lateinit var intent: Intent

    override fun IntentDelegate.registerIntentDelegation(intent: Intent?, mediationDelegate: MediationDelegate) {
        viewModel = mediationDelegate
        intent?.let {
            this@IntentDelegation.intent = it
        }
        viewModel.registerIntent(this@IntentDelegation)
    }

    override fun intent(): Intent {
        return intent
    }
}
