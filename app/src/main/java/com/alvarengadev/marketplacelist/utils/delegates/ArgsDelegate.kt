package com.alvarengadev.marketplacelist.utils.delegates

import androidx.navigation.NavArgs

interface ArgsDelegate<T : NavArgs> {
    fun registerArgDelegation(args: T, mediationDelegate: MediationDelegate)
    fun args(): T
}

class ArgsDelegation<T : NavArgs> : ArgsDelegate<T> {

    private lateinit var viewModel: MediationDelegate

    private lateinit var args: T

    override fun registerArgDelegation(args: T, mediationDelegate: MediationDelegate) {
        this.viewModel = mediationDelegate
        this.args = args
        this.viewModel.registerArgs(this)
    }

    override fun args(): T {
        return args
    }
}
