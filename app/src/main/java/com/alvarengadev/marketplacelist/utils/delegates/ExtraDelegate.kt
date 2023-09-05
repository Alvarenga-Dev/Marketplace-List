package com.alvarengadev.marketplacelist.utils.delegates

import android.os.Parcelable

interface ExtraDelegate<T : Parcelable> {
    fun registerExtraDelegate(args: T?, mediationDelegate: MediationDelegate)
    fun extra(): T
}

class ExtraDelegation<T : Parcelable> : ExtraDelegate<T> {

    private lateinit var viewModel: MediationDelegate

    private lateinit var extra: T

    override fun registerExtraDelegate(args: T?, mediationDelegate: MediationDelegate) {
        this.viewModel = mediationDelegate
        if (args != null) {
            this.extra = args
        }
        this.viewModel.registerExtras(this)
    }

    override fun extra(): T {
        return extra
    }
}
