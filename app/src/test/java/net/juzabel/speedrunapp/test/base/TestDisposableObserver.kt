package net.juzabel.speedrunapp.test.base

import io.reactivex.observers.DisposableObserver

class TestDisposableObserver<T> : DisposableObserver<T>() {

    val listValues: ArrayList<T> = ArrayList()
    val listErrors: ArrayList<Throwable> = ArrayList()
    var completed: Boolean = false

    override fun onComplete() {
        completed = true
    }

    override fun onNext(t: T) {
        listValues.add(t)
    }

    override fun onError(e: Throwable) {
        listErrors.add(e)
    }
}