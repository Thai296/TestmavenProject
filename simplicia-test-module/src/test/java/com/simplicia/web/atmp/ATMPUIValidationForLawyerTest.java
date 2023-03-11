package com.simplicia.web.atmp;

import com.simplicia.functions.Utility;
import com.simplicia.web.LoginOutLawyerPerMethodBaseTest;
import io.qameta.allure.Description;
import org.testng.annotations.Test;

/**
 * - access ATMPs
 * - show status column
 * - modify records of disputes
 * - modify status, check status last change date
 * - add comment
 */
public class ATMPUIValidationForLawyerTest extends LoginOutLawyerPerMethodBaseTest {

    @Test(enabled = false)
    @Description("Lawyer can work on ATMPs of client Adobe")
    public void testLawyerCanModifyAtmp() {
        lawyerSwitchClient("Adobe");

        homePage.openLeftNavigation();
        leftNavigationMenuPage.checkPresenceOfLeftNavigationMenuOption("Comptes employeurs");
        leftNavigationMenuPage.clickExpandIconToOpenMenu("Comptes employeurs");
        leftNavigationMenuPage.checkPresenceOfLeftNavigationMenuOption("Tous mes ATMPs");
        leftNavigationMenuPage.clickMenuItemInLeftNavigation("Tous mes ATMPs");
        leftNavigationMenuPage.closeLeftNavigation();

        tousMesATMPsPage.expectHeaderDisputeVisible();
        if (!tousMesATMPsPage.isShowAtmpStatusesCheckboxChecked()) {
            tousMesATMPsPage.checkShowAtmpStatusesCheckBox();
        }
        tousMesATMPsPage.expectShowAtmpStatusesCheckBoxChecked();
        tousMesATMPsPage.expectHeaderStatusVisible();
        tousMesATMPsPage.expectHeaderStatusChangeDateVisible();
        tousMesATMPsPage.filterByStartDate("30/10/2020");
        tousMesATMPsPage.waitForOneRowInResult();

        // change records of dispute
        tousMesATMPsPage.checkPresenceOfDisputeCheckbox();
        tousMesATMPsPage.waitForDisputeCheckboxState(false);
        tousMesATMPsPage.checkDisputeCheckBox();
        tousMesATMPsPage.waitForDisputeCheckboxState(true);
        tousMesATMPsPage.checkDisputeCheckBox();
        tousMesATMPsPage.waitForDisputeCheckboxState(false);

        // change status
        tousMesATMPsPage.checkPresenceOfOnRowDropdownStatus();
        tousMesATMPsPage.selectOnRowStatus("Aucun");
        tousMesATMPsPage.waitForOneRowInResult();
        tousMesATMPsPage.expectOnRowStatusValue("Aucun");

        tousMesATMPsPage.checkPresenceOfOnRowDropdownStatus();
        tousMesATMPsPage.selectOnRowStatus("Saisine de CRA");
        tousMesATMPsPage.waitForOneRowInResult();
        tousMesATMPsPage.expectOnRowStatusValue("Saisine de CRA");

        tousMesATMPsPage.filterByStatus("Saisine de CMRA");
        tousMesATMPsPage.waitForZeroRowInResult();

        tousMesATMPsPage.filterByStatus("Saisine de CRA");
        tousMesATMPsPage.waitForOneRowInResult();
        tousMesATMPsPage.expectOnRowStatusValue("Saisine de CRA");

        String currentDate = Utility.getCurrentDate("dd/MM/yyyy");
        tousMesATMPsPage.expectOnRowStatusLastChangeDate(currentDate);

        // add comment
        tousMesATMPsPage.openOnRowActions();
        tousMesATMPsPage.openChat();
        tousMesATMPsPage.waitForPopupTagSelect();
        tousMesATMPsPage.selectTagQuestionnaire();
        tousMesATMPsPage.waitForPopupMessageInput();

        String message = Utility.randomSentence();

        tousMesATMPsPage.inputChatOnPopup(message);
        tousMesATMPsPage.clickSendChatOnPopup();
        tousMesATMPsPage.waitForChatMessageOnPopup(message);
        tousMesATMPsPage.expectChatDateOnPopup(message, currentDate);
        tousMesATMPsPage.expectChatTagQuestionnaireOnPopup(message);
        tousMesATMPsPage.closeChatPopup();
        tousMesATMPsPage.waitForChatPopupClosed();

        tousMesATMPsPage.closeOnRowActions();
    }

}