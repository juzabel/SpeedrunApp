package net.juzabel.speedrunapp.domain.interactor.base

abstract class UseCase<T, P> {
    fun execute( params: P?) = buildCaseObservable(params)

    abstract fun buildCaseObservable(params: P?): T
}