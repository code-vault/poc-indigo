package com.indigo.pages;

import com.avis.qa.core.AbstractBasePage;
import com.indigo.components.FlexiPopUp;
import com.indigo.components.FlightDetailWidget;
import com.indigo.components.LoginDetailPopUp;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.*;

public class FlightListPage extends AbstractBasePage {

    @FindBy(xpath = "//div[contains(@class, 'trip-0')]//div[contains(@class, 'flight-result-row')]")
    private List<WebElement> flight_detail_rows;

    @FindBy(xpath = "(//button[text()='Price'])[1]")
    private WebElement departure_price_sort_btn;

    @FindBy(xpath = "(//button[text()='Price'])[2]")
    private WebElement return_price_sort_btn;

    @FindBy(xpath = "//div[@class='price']/span")
    private WebElement price;

    @FindBy(css = "button#continue-button")
    private WebElement continue_btn;

    private List<FlightDetailWidget> flight_detail_widgets;

    public FlightListPage(WebDriver driver) {
        super(driver);
        flight_detail_widgets = new ArrayList<>();
    }

    public void initFlightDetailWidgets(){
        for(WebElement el : flight_detail_rows){
            flight_detail_widgets.add(new FlightDetailWidget(driver, flight_detail_rows.indexOf(el)));
        }
    }

    public void sortByPrice(){
        departure_price_sort_btn.click();
        flight_detail_widgets.clear();
        initFlightDetailWidgets();
    }

    public int getHighestPrice(){
         return flight_detail_widgets
                .stream()
                .max(Comparator.comparing(FlightDetailWidget::getFare))
                .orElseThrow(NoSuchElementException::new).getFare();
    }

    public PaymentsPage bookFLight(String pax_fname, String pax_lname, String mobile_number, String email, String seat_choice){
        flight_detail_widgets.get(0).bookFLight();
        waitForClickabilityOfElement(continue_btn, 10).click();
        FlexiPopUp flexiPopUp = new FlexiPopUp(driver);
        LoginDetailPopUp loginDetailPopUp = flexiPopUp.skipFlexiFare();
        PassengerDetailPage passengerDetailPage = loginDetailPopUp.fillLoginDetails(mobile_number, email);
        return passengerDetailPage.fillPassengerDetails(pax_fname, pax_lname, seat_choice);
    }

    public FlightDetailWidget getFlightDetailWidget(int index){
        return flight_detail_widgets.get(index);
    }

    @Override
    public void isOnPage() {
        System.out.println("On Flight Detail Widget");
    }
}
