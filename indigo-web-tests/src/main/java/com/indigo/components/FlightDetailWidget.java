package com.indigo.components;

import com.avis.qa.core.AbstractBasePage;
import com.indigo.utils.IndigoConstants;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.Map;

public class FlightDetailWidget extends AbstractBasePage {
     private String fare_xpath = "(//div[contains(@class, 'trip-0')]//span[@class='flight-fare'])[%d]";
     private String departure_date_xpath = "//div[contains(@class, 'trip-0')]//span[@class='date-of-flight'][%d]";

     private String source_xpath = "(//div[contains(@class, 'trip-0')]//span[contains(@class,'source-place')])[%d]";
     private String destination_xpath = "(//div[contains(@class, 'trip-0')]//span[contains(@class,'destination-place')])[%d]";
     private String departure_time_xpath = "(//div[contains(@class, 'trip-0')]//span[contains(@class,'timing-flight-depart')])[%d]/div";
     private String arrival_time_xpath = "(//div[contains(@class, 'trip-0')]//span[contains(@class,'timing-flight-arrival')])[%d]/div";
     private String book_btn_xpath = "(//div[contains(@class, 'trip-0')]//button[text()='Book'])[%d]"; //n+2
     private WebElement fare;
     private WebElement source;
     private WebElement destination;
     private WebElement departure_time;
     private WebElement arrival_time;
     private WebElement book_btn;

     private WebElement departure_date;


    public FlightDetailWidget(WebDriver driver, int index) {
        super(driver);
        this.fare = driver.findElement(By.xpath(String.format(fare_xpath, index)));
        this.fare.click();
        this.source = driver.findElement(By.xpath(String.format(source_xpath, index)));
        this.destination = driver.findElement(By.xpath(String.format(destination_xpath, index)));
        this.departure_time = driver.findElement(By.xpath(String.format(departure_time_xpath, index)));
        this.arrival_time = driver.findElement(By.xpath(String.format(arrival_time_xpath, index)));
        this.book_btn = driver.findElement(By.xpath(String.format(book_btn_xpath, index)));
        this.departure_date = driver.findElement(By.xpath(String.format(departure_date_xpath, index)));
    }

    public void bookFLight(){
        IndigoConstants.FLIGHT_DETAILS.put("source", source.getText());
        IndigoConstants.FLIGHT_DETAILS.put("destination", destination.getText());
        IndigoConstants.FLIGHT_DETAILS.put("departure_date", departure_date.getText());
        IndigoConstants.FLIGHT_DETAILS.put("departure_time", departure_time.getText());
        IndigoConstants.FLIGHT_DETAILS.put("arrival_time", arrival_time.getText());
        fare.click();
        book_btn.click();
    }

    public int getFare(){
        return Integer.parseInt(this.fare.getText());
    }

    @Override
    public void isOnPage() {

    }
}
