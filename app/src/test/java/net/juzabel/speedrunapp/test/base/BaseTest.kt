package net.juzabel.speedrunapp.test.base

import org.junit.Rule

abstract class BaseTest {

    @get:Rule
    val trampolineSchedulerRule: TrampolineSchedulerRule = TrampolineSchedulerRule()
}