package com.vitantonio.nagauzzi.funpone.test.rule

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest as originalRunTest
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description

@OptIn(ExperimentalCoroutinesApi::class)
class CoroutineRule : TestWatcher() {
    private val testDispatcher = UnconfinedTestDispatcher()
    val scope = TestScope(testDispatcher)

    override fun starting(description: Description?) {
        super.starting(description)
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description?) {
        super.finished(description)
        Dispatchers.resetMain()
    }

    fun runTest(block: suspend TestScope.() -> Unit) {
        originalRunTest(context = testDispatcher) { block() }
    }
}
