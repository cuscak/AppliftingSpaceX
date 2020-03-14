package ua.cuscak.appliftingspacex.ui.rockets.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.CoreMatchers
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RocketOverviewViewModelTest{

    @Test
    fun showRocketDetailTest(){
        // Given a fresh RocketOverviewViewModel
        val rocketViewModel = RocketOverviewViewModel(ApplicationProvider.getApplicationContext())
        val rocketName = "TestRocket"

        // Create observer - no need for it to do anything!
        val observer = Observer<String> {}

        try{
            // Observe the LiveData forever
            rocketViewModel.navigateToSelectedRocket.observeForever(observer)

            // When clicking on a rocket
            rocketViewModel.displayRocketDetails(rocketName)

            val value = rocketViewModel.navigateToSelectedRocket.value

            // Then rocket name is set
            assertEquals(value, rocketName)

        } finally {
            rocketViewModel.navigateToSelectedRocket.removeObserver(observer)
        }




    }

}