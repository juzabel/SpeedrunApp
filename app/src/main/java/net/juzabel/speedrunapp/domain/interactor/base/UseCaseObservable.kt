package net.juzabel.speedrunapp.domain.interactor.base

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers

abstract class UseCaseObservable<T, P> {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun execute(disposable: DisposableObserver<T>, params: P?) {

        val observable: Observable<T> = buildCaseObservable(params)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread(), true)

        observable.subscribeWith(disposable).addTo(compositeDisposable)
    }

    abstract fun buildCaseObservable(params: P?): Observable<T>

    fun clear() {
        compositeDisposable.clear()
    }
}