package com.indigo.pages;

import com.avis.qa.core.AbstractBasePage;
import com.indigo.utils.IndigoConstants;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PaymentsPage extends AbstractBasePage {
    @FindBy(xpath = "(//div[@class='stations_name'][1]/span)[1]")
    private WebElement source;

    @FindBy(xpath = "(//div[@class='stations_name'][1]/span)[2]")
    private WebElement departure_date;

    @FindBy(xpath = "(//div[@class='stations_name'][1]/span)[3]")
    private WebElement departure_time;

    @FindBy(xpath = "(//div[@class='stations_name'][2]/span)[1]")
    private WebElement destination;

    @FindBy(xpath = "(//div[@class='stations_name'][2]/span)[2]")
    private WebElement arrival_date;

    @FindBy(xpath = "(//div[@class='stations_name'][2]/span)[3]")
    private WebElement arrival_time;

    @FindBy(xpath = "//li[@class='passengerList']//span")
    private WebElement pax_name;

    @FindBy(xpath = "//span[contains(@class,'BaseFare')]")
    private WebElement base_fare;

    @FindBy(xpath = "//span[contains(@class,'//span[contains(@class,'SeatFee')]')]")
    private WebElement seat_fee;

    @FindBy(xpath = "//li[@class='hide fare_details' and @style='display: list-item;']//span[contains(@class,'payFee')]")
    private WebElement convenience_fee;

    @FindBy(xpath = "//div[contains(@class,'totalPriceWrap') and @style='display: block;']//div[@class='totalPriceSummary_Base']")
    private WebElement final_fare;

    private Map<String, String> flightDetails;


    public PaymentsPage(WebDriver driver) {
        super(driver);
    }

    public List<String> compareFlightDetails(){
        List<String> mismatches = new ArrayList<>();

        flightDetails = new HashMap<>();
        flightDetails.put("source", source.getText());
        flightDetails.put("destination", destination.getText());
        flightDetails.put("departure_date", departure_date.getText());
        flightDetails.put("arrival_date", arrival_date.getText());
        flightDetails.put("departure_time", departure_time.getText());
        flightDetails.put("arrival_time", arrival_time.getText());
        flightDetails.put("pax_name", pax_name.getText());   //
        flightDetails.put("base_fare", base_fare.getText());   //
        flightDetails.put("seat_fee", seat_fee.getText());
        flightDetails.put("convenience_fee", convenience_fee.getText());
        flightDetails.put("final_fare", final_fare.getText());

        for(String key: IndigoConstants.FLIGHT_DETAILS.keySet()){
            if(!flightDetails.get(key).contains(IndigoConstants.FLIGHT_DETAILS.get(key)))
                mismatches.add(String.format("%s : Expected '%s', found '%s'", key, IndigoConstants.FLIGHT_DETAILS.get(key), flightDetails.get(key)));
        }
        return mismatches;
    }


    @Override
    public void isOnPage() {

    }
}
