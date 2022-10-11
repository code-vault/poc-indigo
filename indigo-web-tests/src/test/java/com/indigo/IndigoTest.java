package com.indigo;

import com.avis.qa.core.TestBase;
import com.indigo.pages.FlightListPage;
import com.indigo.pages.HomePage;
import com.indigo.pages.PaymentsPage;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class IndigoTest extends TestBase {
    @Test
    public void test1(){
        launchUrl();
        HomePage homePage = new HomePage(getDriver());
        homePage.getBookingWidget().searchFlight("DEL", "BLR", "2023-06-12");
    }

    @Test
    public void test2(){
        launchUrl();
        HomePage homePage = new HomePage(getDriver());
        homePage.getBookingWidget().searchFlight("DEL", "BLR", "2023-06-12");
        FlightListPage flightListPage = new FlightListPage(getDriver());
        int highestPrice = flightListPage.getHighestPrice();
        flightListPage.sortByPrice();
        int top_flight_price = flightListPage.getFlightDetailWidget(0).getFare();
        Assert.assertEquals(highestPrice, top_flight_price, "Sorting did not perform correctly.");
    }

    @Test public void book_flight_e2e(){
        launchUrl();
        HomePage homePage = new HomePage(getDriver());
        FlightListPage flightListPage = homePage.getBookingWidget().searchFlight("DEL", "BLR", "2023-06-12");
        PaymentsPage paymentsPage =  flightListPage.bookFLight("f", "l", "123", "abc@abc.com", "4F");
        List<String> dataMismatches = paymentsPage.compareFlightDetails();
        Assert.assertEquals(dataMismatches.size(), 0, dataMismatches.toString());
    }
}
