package net.juzabel.speedrunapp.presentation.base

interface BaseContract {
    interface BaseView {
    }
    interface BasePresenter {
        fun create()
        fun destroy()
    }
}