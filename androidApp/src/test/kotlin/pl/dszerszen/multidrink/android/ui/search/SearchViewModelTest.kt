package pl.dszerszen.multidrink.android.ui.search

import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldBeEmpty
import io.kotest.matchers.types.shouldBeTypeOf
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import pl.dszerszen.multidrink.android.base.BaseTest
import pl.dszerszen.multidrink.android.ui.base.InAppEvent
import pl.dszerszen.multidrink.android.ui.base.InAppEventDispatcher
import pl.dszerszen.multidrink.android.ui.base.NavScreen
import pl.dszerszen.multidrink.android.ui.error.BottomInfoMessageType
import pl.dszerszen.multidrink.domain.AppException
import pl.dszerszen.multidrink.domain.handleSuspend
import pl.dszerszen.multidrink.domain.repository.DrinksRepository

@OptIn(ExperimentalCoroutinesApi::class)
internal class SearchViewModelTest : BaseTest() {

    private val drinksRepository = mockk<DrinksRepository>(relaxed = true)
    private val eventDispatcher = mockk<InAppEventDispatcher>(relaxed = true)

    private lateinit var sut: SearchViewModel

    @Test
    fun `should have empty state when screen initialized`() = runTest {
        //Arrange
        prepare()
        //Act
        coVerify(exactly = 0) { drinksRepository.findByName(any()) }
        //Assert
        with(sut.viewState.value) {
            drinks.shouldBeEmpty()
            searchInput.shouldBeEmpty()
            isInitialState.shouldBeTrue()
        }
    }

    @Test
    fun `should fetch data from repository on input changed`() = runTest {
        //Arrange
        val input = "sample input"
        val drinksListSize = 3
        coEvery { drinksRepository.findByName(any()) } returns handleSuspend { List(drinksListSize) { mockk(relaxed = true) } }
        prepare()
        //Act
        sut.onUiIntent(SearchUiIntent.OnInputChanged(input))
        advanceUntilIdle()
        //Assert
        coVerify { drinksRepository.findByName(input) }
        with(sut.viewState.value) {
            drinks.shouldHaveSize(drinksListSize)
            searchInput shouldBe input
            isInitialState.shouldBeFalse()
        }
    }

    @Test
    fun `should navigate to details screenwith proper drink id when clicked on drink item`() = runTest {
        //Arrange
        val mockedId = "0"
        val eventSlot = slot<InAppEvent>()
        coEvery { drinksRepository.findByName(any()) } returns handleSuspend {
            List(2) {
                mockk(relaxed = true) {
                    every { id } returns "$it"
                }
            }
        }
        prepare()
        //Act
        sut.onUiIntent(SearchUiIntent.OnInputChanged("test"))
        advanceUntilIdle()
        sut.onUiIntent(SearchUiIntent.OnItemClicked("0"))
        //Assert
        verify(exactly = 1) { eventDispatcher.dispatchEvent(capture(eventSlot)) }
        with(eventSlot.captured) {
            shouldBeTypeOf<InAppEvent.Navigate>()
            target shouldBe NavScreen.Details
            argument shouldBe mockedId
        }
    }

    @Test
    fun `should dispatch error info event when received exception from api response`() = runTest {
        //Arrange
        val eventSlot = slot<InAppEvent>()
        val mockedTitle = "error title"
        coEvery { drinksRepository.findByName(any()) } returns handleSuspend {
            throw AppException.NetworkException(
                mockedTitle
            )
        }
        prepare()
        //Act
        sut.onUiIntent(SearchUiIntent.OnInputChanged("test"))
        advanceUntilIdle()
        //Assert
        verify(exactly = 1) { eventDispatcher.dispatchEvent(capture(eventSlot)) }
        with(eventSlot.captured) {
            shouldBeTypeOf<InAppEvent.BottomInfo>()
            type shouldBe BottomInfoMessageType.ERROR
            title shouldBe mockedTitle
        }
    }

    private fun prepare() {
        sut = SearchViewModel(
            drinksRepository = drinksRepository,
            eventDispatcher = eventDispatcher
        )
    }
}