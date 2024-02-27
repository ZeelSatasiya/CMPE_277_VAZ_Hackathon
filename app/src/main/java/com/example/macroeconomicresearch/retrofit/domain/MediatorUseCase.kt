package com.example.macroeconomicresearch.retrofit.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer

abstract class MediatorUseCase<in P, R> {

    protected val result = CustomMediatorLiveData<Result<R>>()

    // Make this as open so that mock instances can mock this method
    open fun observe(): LiveData<Result<R>> {
        return result
    }

    abstract fun execute(parameters: P)

    open class CustomMediatorLiveData<T> : MediatorLiveData<T>()
    {
        fun <S>addNewSource(liveData : LiveData<S>, onChanged: Observer<in S>)
        {
            removeSource(liveData)
            addSource(liveData, onChanged)
        }

        fun <S>onlyGetLatestData(liveData: LiveData<S>, doAfterFetched : (data:S) -> Unit){
            addNewSource(liveData) {
                removeSource(liveData)
                doAfterFetched(it)
            }
        }
    }
}