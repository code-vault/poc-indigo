package com.indigo.components;

import com.avis.qa.core.AbstractBasePage;
import com.indigo.pages.PassengerDetailPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginDetailPopUp extends AbstractBasePage {

    @FindBy(xpath = "//div[@id='loginDetailModal']//input[@name='mobileNum']")
    private WebElement mobile_number_field;

    @FindBy(xpath = "//div[@id='loginDetailModal']//input[@name='emailId']")
    private WebElement email_field;

    @FindBy(xpath = "//div[@id='loginDetailModal']//input[@id='legalConsentFormField']")
    private WebElement consent_checkbox;

    @FindBy(xpath = "//div[@id='loginDetailModal']//button[@type='submit']")
    private WebElement next_btn;

    public LoginDetailPopUp(WebDriver driver) {
        super(driver);
    }


    public PassengerDetailPage fillLoginDetails(String mobile_number, String email){
        waitForClickabilityOfElement(mobile_number_field, 10).sendKeys(mobile_number);
        waitForClickabilityOfElement(email_field, 10).sendKeys(email);
        consent_checkbox.click();
        next_btn.click();
        return new PassengerDetailPage(driver);
    }


    @Override
    public void isOnPage() {

    }
}
