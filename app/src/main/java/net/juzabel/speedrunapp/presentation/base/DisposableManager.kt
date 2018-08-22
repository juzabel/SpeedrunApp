package net.juzabel.speedrunapp.presentation.base

import io.reactivex.disposables.CompositeDisposable

open class DisposableManager {
    protected val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun destroy() {
        compositeDisposable.clear()
    }
}