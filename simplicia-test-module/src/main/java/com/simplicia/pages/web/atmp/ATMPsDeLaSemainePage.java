package com.simplicia.pages.web.atmp;

import controls.Header;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ATMPsDeLaSemainePage extends ATMPPage
{

    //==========
    // Locator =
    //==========
    final static String ATMPS_DE_LA_SEMAINE_HEADER = "//h6[text()='ATMPs de la semaine']";

    //====================
    // Selenium Controls =
    //====================
    private final Header atmpsDeLaSemaineHeader;

    public ATMPsDeLaSemainePage(WebDriver browser) {
        super(browser);
        atmpsDeLaSemaineHeader = new Header(browser, By.xpath(ATMPS_DE_LA_SEMAINE_HEADER), "ATMPs de la semaine Header");
    }

    //================
    // Check actions =
    //================

    /**
     * Method to check presence of ATMPs de la semaine page Header
     */
    public void checkPresenceOfATMPsDeLaSemaineHeader() {
        atmpsDeLaSemaineHeader.shouldBeDisplayed();
    }
}