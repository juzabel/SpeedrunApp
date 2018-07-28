package net.juzabel.speedrunapp.test.base

import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins.*
import io.reactivex.schedulers.Schedulers
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

class TrampolineSchedulerRule: TestRule {
    override fun apply(base: Statement, description: Description?): Statement {
        return object: Statement() {
            override fun evaluate() {
                setIoSchedulerHandler { Schedulers.trampoline() }
                setComputationSchedulerHandler { Schedulers.trampoline() }
                setNewThreadSchedulerHandler { Schedulers.trampoline() }
                RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }

                try {
                    base.evaluate()
                } finally {
                    reset()
                    RxAndroidPlugins.reset()
                }
            }
        }
    }
}