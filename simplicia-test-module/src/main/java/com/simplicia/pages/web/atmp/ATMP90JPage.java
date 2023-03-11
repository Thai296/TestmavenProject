package com.simplicia.pages.web.atmp;

import controls.Header;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ATMP90JPage extends ATMPPage
{

    //==========
    // Locator =
    //==========
    final static String ATMPS_90J_PAGE_HEADER = "//h6[text()='ATMPs +90J']";

    //====================
    // Selenium Controls =
    //====================
    private final Header ATMPs_90J_Header;

    public ATMP90JPage(WebDriver browser) {
        super(browser);
        ATMPs_90J_Header = new Header(browser, By.xpath(ATMPS_90J_PAGE_HEADER), "ATMPs +90J Page Header");
    }

    //================
    // Check actions =
    //================
    /**
     * Method to check presence of ATMPs +90J page Header
     */
    public void checkPresenceOfATMPs90JHeader(){
        ATMPs_90J_Header.shouldBeDisplayed();
    }
}
