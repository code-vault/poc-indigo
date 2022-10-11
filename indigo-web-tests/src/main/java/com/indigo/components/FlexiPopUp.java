package com.indigo.components;

import com.avis.qa.core.AbstractBasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class FlexiPopUp extends AbstractBasePage {

    @FindBy(xpath = "//div[@id='flexiFareModal']//button[text() = 'Skip & continue with Saver Fare']")
    private WebElement skip_btn;

    public LoginDetailPopUp skipFlexiFare(){
        skip_btn.click();
        return new LoginDetailPopUp(driver);
    }

    public FlexiPopUp(WebDriver driver) {
        super(driver);
    }

    @Override
    public void isOnPage() {

    }
}
