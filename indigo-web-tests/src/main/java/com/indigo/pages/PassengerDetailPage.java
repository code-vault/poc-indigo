package com.indigo.pages;

import com.avis.qa.core.AbstractBasePage;
import com.indigo.utils.IndigoConstants;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class PassengerDetailPage extends AbstractBasePage {

    @FindBy(xpath = "//div[@id='paxDetails-tab']//input[@name='pax-title-0']")
    private List<WebElement> pax_titles;

    @FindBy(xpath = "//div[@id='paxDetails-tab']//input[@name='adt-fname-0']")
    private WebElement pax_first_name;

    @FindBy(xpath = "//div[@id='paxDetails-tab']//input[@name='adt-lname-0']")
    private WebElement pax_last_name;

    @FindBy(xpath = "//div[@id='paxDetails-tab']//button[@type='submit']")
    private WebElement continue_add_on_btn;

    @FindBy(xpath = "(//div[@id='top-up-buttons']//button)[4]")
    private WebElement continue_seat_select;

    @FindBy(xpath = "//div[@class='upsell-content']// button[@type='submit'][1]")
    private WebElement deny_upsell_popup;

    @FindBy(xpath = "(//div[@id='booking-section']//p)[1]")
    private WebElement itinerary;

    @FindBy(xpath = "//p[@class='booking-sum-title-16-semi-bold-black']/span")
    private WebElement payable_amount;

    private String seat_xpath = "//div[contains(@class, 'seat-select-list')]//li[@id='seat-no-%s']";

    @FindBy(xpath = "//span[contains(@class, 'p-2 seat-fare')]/span")
    private WebElement seat_popover_fare;

    @FindBy(xpath = "//div[@class='popover-inner']//button[text()='OK']")
    private WebElement seat_popover_ok_btn;

    @FindBy(xpath = "//button[text()='Continue to Payment']")
    private WebElement continue_to_payment;

    public PassengerDetailPage(WebDriver driver) {
        super(driver);
    }

    public PaymentsPage fillPassengerDetails(String first_name, String last_name, String seat){
        waitForClickabilityOfElement(pax_titles.get(0), 10).click();
        pax_first_name.sendKeys(first_name);
        pax_last_name.sendKeys(last_name);
        continue_add_on_btn.click();

        waitForClickabilityOfElement(continue_seat_select, 10).click();

        waitForClickabilityOfElement(deny_upsell_popup, 10).click();

        seat_xpath = String.format(seat_xpath, seat);
        waitForClickabilityOfElement(driver.findElement(By.xpath(seat_xpath)), 10).click();
        seat_popover_ok_btn.click();
        continue_to_payment.click();

        IndigoConstants.FLIGHT_DETAILS.put("pax_name", first_name + " " + last_name);
        IndigoConstants.FLIGHT_DETAILS.put("seat_fee", seat_popover_fare.getText());
        return new PaymentsPage(driver);
    }

    @Override
    public void isOnPage() {

    }
}
