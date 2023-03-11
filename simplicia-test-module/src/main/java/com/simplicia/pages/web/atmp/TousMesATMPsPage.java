package com.simplicia.pages.web.atmp;

import controls.Header;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


/**
 * This class contains the locators of TousMesATMPsPage page
 */
public class TousMesATMPsPage extends ATMPPage
{

    //==========
    // Locator =
    //==========
    final static String TOUS_MES_ATMPS_PAGE_HEADER = "//h6[text()='Tous mes ATMPs']";

    //====================
    // Selenium Controls =
    //====================
    private final Header tousMes_ATMPs_Header;

    public TousMesATMPsPage(WebDriver browser) {
        super(browser);
        tousMes_ATMPs_Header = new Header(browser, By.xpath(TOUS_MES_ATMPS_PAGE_HEADER), "Page Header");
    }

    //================
    // Check actions =
    //================
    /**
     * Method to check presence of Tous mes ATMPs page Header
     */
    public void checkPresenceOfTousMesATMPHeader(){
        tousMes_ATMPs_Header.shouldBeDisplayed();
    }
}
