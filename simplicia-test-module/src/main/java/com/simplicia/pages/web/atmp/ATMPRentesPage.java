package com.simplicia.pages.web.atmp;

import controls.Header;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ATMPRentesPage extends ATMPPage
{
    //==========
    // Locator =
    //==========
    final static String ATMPS_RENTES_HEADER = "//h6[text()='Rentes']";

    //====================
    // Selenium Controls =
    //====================
    private final Header atmpsRentesPageHeader;

    public ATMPRentesPage(WebDriver browser) {
        super(browser);
        atmpsRentesPageHeader = new Header(browser, By.xpath(ATMPS_RENTES_HEADER), "Rentes Header");
    }

    //================
    // Check actions =
    //================

    /**
     * Method to check presence of Rentes page Header
     */
    public void checkPresenceOfRentesHeader() {
        atmpsRentesPageHeader.shouldBeDisplayed();
    }
}
