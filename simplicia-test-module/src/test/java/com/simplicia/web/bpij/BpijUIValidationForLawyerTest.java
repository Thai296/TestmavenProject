package com.simplicia.web.bpij;

import com.simplicia.web.LoginOutLawyerPerMethodBaseTest;
import io.qameta.allure.Description;
import org.testng.annotations.Test;

/**
 * - access BPIJS:
 * ...
 */
public class BpijUIValidationForLawyerTest extends LoginOutLawyerPerMethodBaseTest {

    @Test
    @Description("Lawyer can work on All BPIJs of client Test Simplicia")
    public void testAllBpijsForLawyer() {
        lawyerSwitchClient("Test Simplicia");

        homePage.openLeftNavigation();
        leftNavigationMenuPage.checkPresenceOfLeftNavigationMenuOption("BPIJ");
        leftNavigationMenuPage.clickExpandIconToOpenMenu("BPIJ");
        leftNavigationMenuPage.checkPresenceOfLeftNavigationMenuOption("Mes BPIJs");
        leftNavigationMenuPage.clickMenuItemInLeftNavigation("Mes BPIJs");
        leftNavigationMenuPage.closeLeftNavigation();

        // TODO
        // expect more than 2 rows: $x("//table[contains(@class, 'MuiTable-root')]/tbody/tr")
        // filters:
        // - start date: $x("//input[@id='date-start']") --> 04/03/2021
        // - siret $x("//div[contains(@class, 'makeStyles-header')]//../label[contains(text(), 'Siret Connexion')]/following-sibling::div/input[@type='text']") --> 34481082500366
        // - Code Cpam:
        //   + click dropdown: $x("//div[contains(@class, 'makeStyles-header')]//../label[contains(text(), 'Code Cpam')]/following-sibling::div/div[contains(@class, 'MuiInputBase-input')]")
        //   + scroll to CPAM de PARIS:
        //   ++ $x("//div[contains(@class, 'makeStyles-header')]//../label[contains(text(), 'Code Cpam')]/../following-sibling::div//div[contains(text(), 'CPAM de PARIS')]")
        //   ++ and click
        // - click apply: $x("//div[contains(@class, 'makeStyles-header')]//../button/span[text()='Appliquer']/..")
        // expect 1 row:
        // + column 1: $x("//table[contains(@class, 'MuiTable-root')]/tbody/tr/th[1][contains(text(), '34481082500366')]")
        // + column 6: $x("//table[contains(@class, 'MuiTable-root')]/tbody/tr/th[6][contains(text(), '1766436770')]")
        // + column 7: download XML file $x("//table[contains(@class, 'MuiTable-root')]/tbody/tr/th[7]/button")
        // + column 8: download PDF file $x("//table[contains(@class, 'MuiTable-root')]/tbody/tr/th[8]/button")
        // + column 9: download CSV file $x("//table[contains(@class, 'MuiTable-root')]/tbody/tr/th[9]/button")
        // can export the table to CSV: click $x("//div[contains(@class, 'makeStyles-header')]//../button[@title='Exporter le tableau au format CSV']")

    }

}