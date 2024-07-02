package com.alvarengadev.marketplacelist.utils.delegates

import android.os.Parcelable
import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavArgs
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

interface MediationDelegate : DefaultLifecycleObserver {
    val dispatcher: CoroutineDispatcher
    fun <T : ViewState> intoMediator(): MutableLiveData<T>
    fun mediator(): LiveData<ViewState>
    fun ViewModel.load(asyncBlock: suspend () -> Result<Any>): Job
    fun ViewModel.launchResult(asyncBlock: suspend () -> Result<Any>): Job
    fun ViewModel.launch(asyncBlock: suspend () -> Unit): Job

    fun <T> args(): T?
    fun <T : NavArgs> registerArgs(argsDelegate: ArgsDelegate<T>)

    fun <T> extras(): T?
    fun <T : Parcelable> registerExtras(extraDelegate: ExtraDelegate<T>)

    fun intentDelegate(): IntentDelegate?
    fun registerIntent(intentDelegate: IntentDelegate)
}

class MediationDelegation(override val dispatcher: CoroutineDispatcher) : MediationDelegate {

    private val viewStateLiveData by lazy {
        MediatorLiveData<ViewState>()
    }

    private var argsDelegate: ArgsDelegate<NavArgs>? = null

    private var extras: ExtraDelegate<Parcelable>? = null

    private var intentDelegate: IntentDelegate? = null

    override fun <T : ViewState> intoMediator(): MutableLiveData<T> {
        val liveData = MutableLiveData<T>()
        viewStateLiveData.addSource(liveData) {
            // Timber?
            Log.i("ViewState", "-> ${it.javaClass.simpleName}")
            viewStateLiveData.value = it
        }
        return liveData
    }

    override fun mediator(): LiveData<ViewState> {
        return viewStateLiveData
    }

    override fun ViewModel.load(asyncBlock: suspend () -> Result<Any>): Job {
        Log.i("ViewState", "-> ${ViewState.Loading.javaClass.simpleName}")
        viewStateLiveData.postValue(ViewState.Loading)
        return viewModelScope.launch(dispatcher) {
            asyncBlock.invoke()
                .onSuccess {
                    Log.i("ViewState", "-> ${ViewState.Success.javaClass.simpleName}")
                    viewStateLiveData.postValue(ViewState.Success)
                }
                .onFailure {
                    Log.i("ViewState", "-> ${ViewState.Failure.javaClass.simpleName}")
                    viewStateLiveData.postValue(ViewState.Failure)
                }
        }
    }

    override fun ViewModel.launchResult(asyncBlock: suspend () -> Result<Any>): Job {
        return viewModelScope.launch(dispatcher) {
            asyncBlock.invoke()
        }
    }

    override fun ViewModel.launch(asyncBlock: suspend () -> Unit): Job {
        return viewModelScope.launch(dispatcher) {
            asyncBlock.invoke()
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T> args(): T? {
        return argsDelegate?.args() as? T
            ?: throw ArgsException("Make sure you are registering args delegation")
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : NavArgs> registerArgs(argsDelegate: ArgsDelegate<T>) {
        this.argsDelegate = argsDelegate as ArgsDelegate<NavArgs>
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T> extras(): T? {
        return extras?.extra() as? T
            ?: throw ExtraException("Make sure you are registering extra delegation")
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : Parcelable> registerExtras(extraDelegate: ExtraDelegate<T>) {
        this.extras = extraDelegate as ExtraDelegate<Parcelable>
    }

    override fun intentDelegate(): IntentDelegate? {
        return intentDelegate
    }

    override fun registerIntent(intentDelegate: IntentDelegate) {
        this.intentDelegate = intentDelegate
    }

    internal class ArgsException(message: String) : Exception(message)
    internal class ExtraException(message: String) : Exception(message)
}
