package com.simplicia.web.atmp;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.testng.ITestListener;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.simplicia.api.MailTrap;
import com.simplicia.api.model.Message;
import com.simplicia.executor.SeleniumTestAsSimpliciaUser;
import com.simplicia.functions.Utility;
import com.simplicia.pages.web.atmp.TousMesATMPsPage;
import com.simplicia.pages.web.home.HomePage;
import com.simplicia.pages.web.leftnavigationmenu.LeftNavigationMenuPage;
import com.simplicia.pages.web.utils.SimpliciaReusableActions;
import com.simplicia.pages.web.utils.TestData;

//@Listeners({ com.simplicia.report.listeners.TestAllureListener.class })
public class ATMPAddCommentsMailNotificationTest extends SeleniumTestAsSimpliciaUser implements ITestListener{
    private static final String CREATION_SUBJECT_MAILS = "ATMP - Ajout d'un commentaire par un utilisateur";
    private static final String CREATION_REGEX_MAILS = "l'ATMP n°(.*?) :";
    private static final long ATMP_ID = 5618;
    private static String randomSentences;
    private static final String LAYWER_FULL_NAME="paul.bedoucha";
    // "L'ATMP n°(.*?)\\s";

    protected HomePage homePage;
    protected LeftNavigationMenuPage leftNavigationMenuPage;
    protected SimpliciaReusableActions simpliciaReusableActions;
    protected TousMesATMPsPage tousMesATMPsPage;

    @BeforeClass(alwaysRun = true)
    public void pageSetUp() {
        try {
            println(">>>> SeleniumTestAsSimpliciaUser@BeforeClass: " + this.getClass().getName());
            retrySilently(() -> {
                loadUrl(TestData.APPLICATION_URL);
                sleepSilently(2000);
            });
            homePage = new HomePage(browser);
            leftNavigationMenuPage = new LeftNavigationMenuPage(browser);
            simpliciaReusableActions = new SimpliciaReusableActions(browser);
            tousMesATMPsPage = new TousMesATMPsPage(browser);
            println("<<<< SeleniumTestAsSimpliciaUser@BeforeClass: " + this.getClass().getName());

        } catch (Exception exe) {
            LOGGER.info("Unable to Login into Application");
        }
    }

    // @Test(enabled = false)
    @Test(enabled = true)
    public void Test04_AddComments_VerifyMailNotification_Client() throws InterruptedException {
        // Login to Application
        simpliciaReusableActions.logIn();

        // Navigate to left navigation menu
        homePage.openLeftNavigation();

        // Click ATMP's Expand icon
        leftNavigationMenuPage.checkPresenceOfLeftNavigationMenuOption("Comptes employeurs");
        leftNavigationMenuPage.clickExpandIconToOpenMenu("Comptes employeurs");
        leftNavigationMenuPage.checkPresenceOfLeftNavigationMenuOption("Tous mes ATMPs");
        // leftNavigationMenuPage.clickExpandIconToOpenMenu("Tous mes ATMPs");
        leftNavigationMenuPage.clickMenuItemInLeftNavigation("Tous mes ATMPs");

        /* Add Comments */
        tousMesATMPsPage.openComment();
        tousMesATMPsPage.waitForCommentPopupTagSelect();
        tousMesATMPsPage.selectTagQuestionnaireComment();
        tousMesATMPsPage.waitForPopupMessageCommentInput();
        randomSentences = Utility.randomSentence();

        tousMesATMPsPage.inputCommentOnPopup(randomSentences);
        tousMesATMPsPage.clickSendCommentOnPopup();

        /* Wait for comments to be displayed */
        // sleepSilently(1000);
        // tousMesATMPsPage.waitForCommentMessageOnPopup(message);
        // tousMesATMPsPage.checkPresenceOfCommentOnPopup(message);

        int foundMailsCount = findAtmpsCreationMailsClient(ATMP_ID, randomSentences);
        assertTrue(foundMailsCount > 0);

    }

    private int findAtmpsCreationMailsClient(long atmpId, String randomSentence) {
        MailTrap mailtrap = new MailTrap();
        List<Message> messages = mailtrap.getMessages();
        List<Long> messageIds = messages.stream().filter(msg -> msg.getSubject().contains(CREATION_SUBJECT_MAILS))
                .map(msg -> msg.getId()).collect(Collectors.toList());

        int foundMailsCount = 0;
        for (Long messageId : messageIds) {
            String messageBody = mailtrap.getMessageBody(messageId.toString());
            Boolean isSentenceFound = messageBody.contains(randomSentence);
            LOGGER.info("isFound is " + isSentenceFound);
            String toEmails = mailtrap.getToEmails(messageId.toString());
            LOGGER.info("toEmails is " + toEmails);
            Boolean isEmailFound = toEmails.contains(LAYWER_FULL_NAME);
            LOGGER.info("isEmailFound is " + isEmailFound);
            Pattern pattern = Pattern.compile(CREATION_REGEX_MAILS);
            Matcher matcher = pattern.matcher(messageBody);
            try {
                matcher.find();
                LOGGER.info("matcher.group(1) " + matcher.group(1));
                if (atmpId == Long.parseLong(matcher.group(1)) && isSentenceFound == true && isEmailFound == true) {
                    foundMailsCount++;
                    LOGGER.info("foundMailsCount is " + foundMailsCount);
                    mailtrap.deleteMessage(messageId.toString());
                    break;
                }

            } catch (Exception e) {
                continue;
            }
        }
        return foundMailsCount;
    }

}