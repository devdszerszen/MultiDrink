package pl.dszerszen.multidrink.android.base

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.extension.AfterEachCallback
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtensionContext

@ExperimentalCoroutinesApi
class TestCoroutineExtension : BeforeEachCallback, AfterEachCallback {
    override fun beforeEach(context: ExtensionContext?) {
        Dispatchers.setMain(StandardTestDispatcher())
    }

    override fun afterEach(context: ExtensionContext?) {
        Dispatchers.resetMain()
    }

}