package com.simplicia.pages.web.atmp;

import controls.Header;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ATMPsDuMoisPage extends ATMPPage
{

    //==========
    // Locator =
    //==========
    final static String ATMPS_DU_MOIS_HEADER = "//h6[text()='ATMPs du mois']";

    //====================
    // Selenium Controls =
    //====================
    private final Header atmpsDuMoisHeader;

    public ATMPsDuMoisPage(WebDriver browser) {
        super(browser);
        atmpsDuMoisHeader = new Header(browser, By.xpath(ATMPS_DU_MOIS_HEADER), "ATMPs du mois Page Header");
    }

    //================
    // Check actions =
    //================
    /**
     * Method to check presence of ATMPs du mois page Header
     */
    public void checkPresenceOfATMPsDuMoisHeader(){
        atmpsDuMoisHeader.shouldBeDisplayed();
    }
}
