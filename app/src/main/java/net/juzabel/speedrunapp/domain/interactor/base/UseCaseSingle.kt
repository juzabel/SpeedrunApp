package net.juzabel.speedrunapp.domain.interactor.base

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers

abstract class UseCaseSingle<T, P> {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun execute(disposable: DisposableSingleObserver<T>, params: P?){

        val single: Single<T> = buildCaseSingle(params)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())

        single.subscribeWith(disposable).addTo(compositeDisposable)
    }

    fun clear() {
        compositeDisposable.clear()
    }

    abstract fun buildCaseSingle(params: P?): Single<T>

}