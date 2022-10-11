package com.indigo.pages;

import com.avis.qa.core.AbstractBasePage;
import com.indigo.components.BookingWidget;
import lombok.Data;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends AbstractBasePage {

    @FindBy(xpath = "(//img[@alt='indigo-logo'])[1]")
    private WebElement logo;

    private BookingWidget bookingWidget;

    public HomePage(WebDriver driver) {
        super(driver);
        this.bookingWidget = new BookingWidget(driver);
    }

    @Override
    public void isOnPage() {
        System.out.println("User is on HomePage");
    }

    public BookingWidget getBookingWidget(){
        return this.bookingWidget;
    }
}
