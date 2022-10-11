package com.indigo.components;

import com.avis.qa.core.AbstractBasePage;
import com.indigo.pages.FlightListPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

public class BookingWidget extends AbstractBasePage {

    @FindBy(xpath = "//input[@name='or-src' or @name='origin']")
    private WebElement source_field;

    @FindBy(xpath = "//input[@name='or-dest' or @name='destination']")
    private WebElement destination_field;

    @FindBy(xpath = "//input[@name='or-src' or @name='origin']//following-sibling::div[contains(@class, 'autocomplete-result')]")
    private List<WebElement> source_dropdown_items;

    @FindBy(xpath = "//input[@name='or-dest' or @name='destination']//following-sibling::div[contains(@class, 'autocomplete-result')]")
    private List<WebElement> destination_dropdown_items;

    @FindBy(xpath = "//div[contains(@class, 'date-picker-container')]//input[@name='or-depart'] | //input[@id='fieldDepart']")
    private WebElement departure_date_btn;

    @FindBy(xpath = "//div[contains(@class, 'ui-datepicker-group-first')]//table[@class='ui-datepicker-calendar']")
    private WebElement departure_date_table;

    @FindBy(xpath = "//div[contains(@class, 'ui-datepicker-group-last')]//table[@class='ui-datepicker-calendar']")
    private WebElement return_date_table;

    @FindBy(xpath = "//a[@title='Prev']/span")
    private WebElement month_prev_btn;

    @FindBy(xpath = "//a[@title='Next']/span")
    private WebElement month_next_btn;

    @FindBy(xpath = "//div[contains(@class, 'date-picker-container ')]//input[@name='or-return'] | //input[@id='fieldReturn1']")
    private WebElement return_date_btn;

    @FindBy(xpath = "//span[@class='hp-src-btn']//parent::button | //button[@type='submit']")
    private WebElement search_flight_btn;

    @FindBy(xpath = "(//button[contains(@class, 'btn-acc-cookie')])[1]")
    private WebElement accept_cookie_btn;

    public BookingWidget(WebDriver driver) {
        super(driver);
    }

    @Override
    public void isOnPage() {

    }

    public FlightListPage searchFlight(String source, String destination, String departure_date){
        waitForClickabilityOfElement(accept_cookie_btn, 10).click();
        new WebDriverWait(driver, 20).until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@class='mainLoader-cont']")));
        selectSource(source);
        selectDestination(destination);
        selectDate(departure_date);
        waitForVisibilityOfElement(search_flight_btn).click();
        return new FlightListPage(driver);
    }

    public void searchFlight(String source, String destination, String departure_date, String return_date){
        new WebDriverWait(driver, 20).until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@class='mainLoader-cont']")));
        selectSource(source);
        selectDestination(destination);
        selectDate(departure_date, return_date);
    }

    private void selectSource(String source){
        waitForClickabilityOfElement(source_field, 10).sendKeys(source);
        waitForClickabilityOfElement(source_dropdown_items.get(0), 10).click();
    }

    private void selectDestination(String destination){
        waitForClickabilityOfElement(destination_field, 10).sendKeys(destination);
        waitForClickabilityOfElement(destination_dropdown_items.get(0), 10).click();
    }

    private void selectDate(String departureDate){
        waitForClickabilityOfElement(departure_date_btn, 10).click();
        LocalDate localDate = LocalDate.parse(departureDate);
        String month_name = localDate.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
        int month_val = localDate.getMonthValue();
        int day = localDate.getDayOfMonth();
        int year = localDate.getYear();
        String year1 = driver.findElement(By.xpath("(//span[@class='ui-datepicker-year'])[1]")).getText();
        String year2 = driver.findElement(By.xpath("(//span[@class='ui-datepicker-year'])[2]")).getText();

        String month1 = driver.findElement(By.xpath("(//span[@class='ui-datepicker-month'])[1]")).getText();
        String month2 = driver.findElement(By.xpath("(//span[@class='ui-datepicker-month'])[2]")).getText();

        if(year > Integer.parseInt(year1)){
            while(year > Integer.parseInt(year1)){
                month_next_btn.click();
                year1 = driver.findElement(By.xpath("(//span[@class='ui-datepicker-year'])[1]")).getText();
            }
        }
        if(!month_name.equalsIgnoreCase(month1)){
            while(!month_name.equalsIgnoreCase(month1)) {
                month_next_btn.click();
                month1 = driver.findElement(By.xpath("(//span[@class='ui-datepicker-month'])[1]")).getText();
            }
        }

        String date_btn_xpath = String.format("//td[@data-month='%d' and @data-year='%d']/a[text()='%d']", month_val-1, year, day);
        waitForClickabilityOfElement(driver.findElement(By.xpath(date_btn_xpath)), 10).click();
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void selectDate(String departureDate, String returnDate){
        selectDate(departureDate);
        selectDate(returnDate);
    }
}
