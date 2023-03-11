package com.simplicia.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.testng.ITestListener;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.simplicia.api.model.AtmpResponse;
import com.simplicia.api.model.LoginRequest;
import com.simplicia.api.model.LoginResponse;
import com.simplicia.api.model.Message;
import com.simplicia.pages.web.utils.TestData;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

//@Listeners({ com.simplicia.report.listeners.TestAllureListener.class })
public class MailNotificationTest implements ITestListener{
    private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(MailNotificationTest.class);
    private static String API_TOKEN = "";
    private static final String CLIENT_ID = TestData.CLIENT_ID;
    private static final String AUTHORIZATION_BASIC = "Basic c2ltcGxpY2lhLWNsaWVudDpzaW1wbGljaWEtc2VjcmV0";
    private static String apiDataPath = System.getProperty("user.dir") + File.separator + "src/test/resources/ApiData";
    private static final long WAIT_FOR_MAIL_SENT_DURATION = 3000;
    private List<AtmpResponse> createdATMPs;
    private final String STRING_OCCURRENCES = ".com@simplicia.co";
    private final long NUMBER_OF_TO_RECEIPENTS = 2;
    private final long NUMBER_OF_TO_RECEIPENTS_DRAFT = 2;// waiting for Minh to enable the email notification

    private static RequestSpecification spec;
    private static final String CREATION_SUBJECT_MAILS = "Nouvel ATMP impute au compte employeur";
    private static final String CREATION_REGEX_MAILS = "L'ATMP n°(.*?)\\s";
    private static final String UPDATE_SUBJECT_MAILS = "Le nombre de jours de l’ATMP";
    // Regex for 0-9999
    // \\b([0-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])\\b
    private static final String UPDATE_DAYS_OFF_MAILS = "l’ATMP n°(.*?) déjà imputé au compte employeur a dépassé \\b([0-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])\\b";

    @BeforeClass(alwaysRun = true)
    public void loginAndGetAccessToken() {
        try {
            spec = new RequestSpecBuilder()
                    .setContentType(ContentType.JSON)
                    .setBaseUri(TestData.API_URL)
                    .addFilter(new ResponseLoggingFilter())
                    .addFilter(new RequestLoggingFilter())
                    .build();

            LoginRequest credentials = new LoginRequest()
                    .setUsername(TestData.ADMIN_EMAIL)
                    .setPassword(TestData.ADMIN_PASSWORD)
                    .setGrantType("password");

            LoginResponse loginResponse = RestAssured.given()
                    .spec(spec)
                    .header("Authorization", AUTHORIZATION_BASIC)
                    .body(credentials)
                    .when()
                    .post("/oauth/token")
                    .then()
                    .statusCode(200)
                    .extract().as(LoginResponse.class);

            API_TOKEN = loginResponse.getAccessToken();

            assertNotEquals("", API_TOKEN);

        } catch (Exception exe) {
            LOGGER.info("Unable to Login into Application");
        }
    }

    @Test
    public void Test01_AddNewATMP_WithNbDaysOffGreaterThanMin_VerifyMailNotification_Lawyer()
            throws InterruptedException {
        // create new ATMPs with nbJoursArret greater than number of FrontEnd website
        // for Lawyer
        File atmpsJsonFile = new File(apiDataPath + "/atmps_nbdaysoff.json");
        List<AtmpResponse> atmpResponseList = saveAtmps(atmpsJsonFile);
        assertTrue(atmpResponseList.size() > 0);

        createdATMPs = atmpResponseList;
        Thread.sleep(WAIT_FOR_MAIL_SENT_DURATION);
        // check the email is sending out to mail trap
        List<Long> atmpIds = atmpResponseList.stream().map(atmp -> atmp.getId()).collect(Collectors.toList());
        int foundMailsCount = findAtmpsCreationMailsLawyer(atmpIds);
        assertEquals(foundMailsCount, atmpIds.size());
    }

    @Test
    public void Test01_AddNewATMP_WithNbDaysOffGreaterThanMin_VerifyMailNotification_Client()
            throws InterruptedException {
        // create new ATMPs with nbJoursArret greater than number of FrontEnd website
        // for Client
        File atmpsJsonFile = new File(apiDataPath + "/atmps_nbdaysoff.json");
        List<AtmpResponse> atmpResponseList = saveAtmps(atmpsJsonFile);
        assertTrue(atmpResponseList.size() > 0);

        createdATMPs = atmpResponseList;
        Thread.sleep(WAIT_FOR_MAIL_SENT_DURATION);

        // check the email is sending out to mail trap and the number of receipents is
        // eqqual to the number that we define
        List<Long> atmpIds = atmpResponseList.stream().map(atmp -> atmp.getId()).collect(Collectors.toList());
        boolean isFound = findAtmpsCreationMailsClient(atmpIds);
        assertTrue(isFound == true);
    }

    @Test
    public void Test02_AddNewATMP_WithPercentIpGreaterThanMin_VerifyMailNotification_Lawyer()
            throws InterruptedException {
        // create new ATMPs with pourcentIp greater than number of FrontEnd website for
        // Client
        File atmpsJsonFile = new File(apiDataPath + "/atmps_percentip.json");

        List<AtmpResponse> atmpResponseList = saveAtmps(atmpsJsonFile);
        assertTrue(atmpResponseList.size() > 0);

        createdATMPs = atmpResponseList;
        Thread.sleep(WAIT_FOR_MAIL_SENT_DURATION);

        // check the email is sending out to mail trap
        List<Long> atmpIds = atmpResponseList.stream().map(atmp -> atmp.getId()).collect(Collectors.toList());
        int foundMailsCount = findAtmpsCreationMailsLawyer(atmpIds);
        assertEquals(foundMailsCount, atmpIds.size());
    }

    @Test
    public void Test02_AddNewATMP_WithPercentIpGreaterThanMin_VerifyMailNotification_Client()
            throws InterruptedException {
        // create new ATMPs with pourcentIp greater than number of FrontEnd website for
        // Client
        File atmpsJsonFile = new File(apiDataPath + "/atmps_percentip.json");

        List<AtmpResponse> atmpResponseList = saveAtmps(atmpsJsonFile);
        assertTrue(atmpResponseList.size() > 0);

        createdATMPs = atmpResponseList;
        Thread.sleep(WAIT_FOR_MAIL_SENT_DURATION);

        // check the email is sending out to mail trap and the number of receipents is
        // eqqual to the number that we define
        List<Long> atmpIds = atmpResponseList.stream().map(atmp -> atmp.getId()).collect(Collectors.toList());
        boolean isFound = findAtmpsCreationMailsClient(atmpIds);
        assertTrue(isFound == true);
    }

    @Test
    public void Test03_ChangeNbDaysOffATMP_VerifyMailNotification_Lawyer() throws InterruptedException {
        // create new ATMPs with pourcentIp greater than number of FrontEnd website for
        // Lawyer. less notification sent
        File createAtmpsFile = new File(apiDataPath + "/atmps_lessmail.json");
        List<AtmpResponse> createdAtmpResponseList = saveAtmps(createAtmpsFile);
        assertTrue(createdAtmpResponseList.size() > 0);

        Thread.sleep(WAIT_FOR_MAIL_SENT_DURATION);
        List<Long> atmpIds = createdAtmpResponseList.stream().map(atmp -> atmp.getId()).collect(Collectors.toList());
        findAtmpsCreationMailsLawyer(atmpIds); // also delete mails if found

        // change #days off, mail notification sent
        File updateAtmpsJsonFile = new File(apiDataPath + "/nbDaysOffAtmps.json");
        List<AtmpResponse> updatedAtmpResponseList = saveAtmps(updateAtmpsJsonFile);
        assertTrue(updatedAtmpResponseList.size() > 0);

        createdATMPs = updatedAtmpResponseList;
        Thread.sleep(WAIT_FOR_MAIL_SENT_DURATION);

        // check the email is sending out to mail trap for lawyer
        Map<Long, Integer> nbDaysOffATMPs = updatedAtmpResponseList.stream()
                .collect(Collectors.toMap(AtmpResponse::getId, AtmpResponse::getNbJoursArret));
        int foundMailsCount = findAtmpsUpdateDaysOffMailsLawyer(nbDaysOffATMPs);
        assertEquals(foundMailsCount, nbDaysOffATMPs.size());
    }

    @Test
    public void Test03_ChangeNbDaysOffATMP_VerifyMailNotification_Client() throws InterruptedException {
        // create new ATMPs with pourcentIp greater than number of FrontEnd website for
        // Client. less notification sent
        File createAtmpsFile = new File(apiDataPath + "/atmps_lessmail.json");
        List<AtmpResponse> createdAtmpResponseList = saveAtmps(createAtmpsFile);
        assertTrue(createdAtmpResponseList.size() > 0);

        // also delete mails if found from above step
        Thread.sleep(WAIT_FOR_MAIL_SENT_DURATION);
        List<Long> atmpIds = createdAtmpResponseList.stream().map(atmp -> atmp.getId()).collect(Collectors.toList());
        findAtmpsCreationMailsLawyer(atmpIds);

        // change #days off, mail notification is sent
        File updateAtmpsJsonFile = new File(apiDataPath + "/nbDaysOffAtmps.json");
        List<AtmpResponse> updatedAtmpResponseList = saveAtmps(updateAtmpsJsonFile);
        assertTrue(updatedAtmpResponseList.size() > 0);

        createdATMPs = updatedAtmpResponseList;
        Thread.sleep(WAIT_FOR_MAIL_SENT_DURATION);

        // check the email is sending out to mail trap and the number of receipents is
        // eqqual to the number that we define
        Map<Long, Integer> nbDaysOffATMPs = updatedAtmpResponseList.stream()
                .collect(Collectors.toMap(AtmpResponse::getId, AtmpResponse::getNbJoursArret));
        boolean isFound = findAtmpsUpdateDaysOffMailsClient(nbDaysOffATMPs);
        assertTrue(isFound == true);
    }

    @AfterMethod
    public void afterMethod() {
        if (createdATMPs != null && createdATMPs.size() > 0) {
            createdATMPs.stream().forEach(atmp -> deleteAtmp(atmp.getSiret(), atmp.getNns()));
        }
    }

    private List<AtmpResponse> saveAtmps(File inputJson) {
        List<AtmpResponse> atmpResponseList = RestAssured.given()
                .spec(spec)
                .header("Authorization", "Bearer " + API_TOKEN)
                .queryParams("clientId", CLIENT_ID)
                .body(inputJson)
                .when()
                .post("/atmp")
                .then()
                .statusCode(201)
                .extract().body().jsonPath().getList(".", AtmpResponse.class);
        return atmpResponseList;
    }

    private int findAtmpsCreationMailsLawyer(List<Long> atmpIds) {
        MailTrap mailtrap = new MailTrap();
        List<Message> messages = mailtrap.getMessages();
        List<Long> messageIds = messages.stream().filter(msg -> msg.getSubject().contains(CREATION_SUBJECT_MAILS))
                .map(msg -> msg.getId()).collect(Collectors.toList());

        int foundMailsCount = 0;
        for (Long messageId : messageIds) {
            String messageBody = mailtrap.getMessageBody(messageId.toString());
            Pattern pattern = Pattern.compile(CREATION_REGEX_MAILS);
            Matcher matcher = pattern.matcher(messageBody);
            try {
                matcher.find();
                long atmpId = Long.parseLong(matcher.group(1));
                if (atmpIds.contains(atmpId)) {
                    foundMailsCount++;
                    mailtrap.deleteMessage(messageId.toString());
                }
            } catch (Exception e) {
                continue;
            }
        }
        return foundMailsCount;
    }

    /*
     * Return true when we find the email and the number of To emails is equal to
     * the number that we define
     * 
     * @atmpIds : List of atmpIds to provide
     */
    private boolean findAtmpsCreationMailsClient(List<Long> atmpIds) {
        boolean isFound = false;
        MailTrap mailtrap = new MailTrap();
        List<Message> messages = mailtrap.getMessages();
        List<Long> messageIds = messages.stream().filter(msg -> msg.getSubject().contains(CREATION_SUBJECT_MAILS))
                .map(msg -> msg.getId()).collect(Collectors.toList());

        int foundMailsCount = 0;
        for (Long messageId : messageIds) {
            String messageBody = mailtrap.getMessageBody(messageId.toString());
            Pattern pattern = Pattern.compile(CREATION_REGEX_MAILS);
            Matcher matcher = pattern.matcher(messageBody);
            String toEmails = mailtrap.getToEmails(messageId.toString());
            LOGGER.info("toEmails is " + toEmails);
            long countOccurrences = countOccurrences(toEmails, STRING_OCCURRENCES);
            LOGGER.info("countOccurrences is " + countOccurrences);
            try {
                matcher.find();
                long atmpId = Long.parseLong(matcher.group(1));
                if (atmpIds.contains(atmpId)) {
                    foundMailsCount++;
                    if (foundMailsCount > 0 && countOccurrences == NUMBER_OF_TO_RECEIPENTS) {
                        isFound = true;
                    }
                    mailtrap.deleteMessage(messageId.toString());
                    break;
                }
            } catch (Exception e) {
                continue;
            }
        }
        return isFound;
    }
     
    /**  Count the Occurrences of the string
     * @param sourceString
     * @param findString
     * @return
     */
    public static long countOccurrences(String sourceString, String findString) {
        int lastIndex = 0;
        long count = 0;

        while ((lastIndex = sourceString.indexOf(findString, lastIndex)) != -1) {
            count++;
            lastIndex += findString.length() - 1;
        }
        return count;
    }
        private int findAtmpsUpdateDaysOffMailsLawyer(Map<Long, Integer> nbDaysOffATMPs) {
        MailTrap mailtrap = new MailTrap();
        List<Message> messages = mailtrap.getMessages();
        List<Long> messageIds = messages.stream().filter(msg -> msg.getSubject().contains(UPDATE_SUBJECT_MAILS))
                .map(msg -> msg.getId()).collect(Collectors.toList());
        int foundMailsCount = 0;
        for (Long messageId : messageIds) {
            String messageSubject = mailtrap.getMessageSubject(messageId.toString());
            Pattern pattern = Pattern.compile(UPDATE_DAYS_OFF_MAILS);
            Matcher matcher = pattern.matcher(messageSubject);
            try {
                matcher.find();
                long atmpId = Long.parseLong(matcher.group(1));
                long nbDaysOffInMail = Integer.parseInt(matcher.group(2));
                if (nbDaysOffATMPs.containsKey(atmpId)) {
                    int nbDaysOff = nbDaysOffATMPs.get(atmpId);
                    if (nbDaysOff == nbDaysOffInMail) {
                        foundMailsCount++;
                        mailtrap.deleteMessage(messageId.toString());
                        break;
                    }
                }
            } catch (Exception e) {
                continue;
            }
        }
        return foundMailsCount;
    }

    /*
     * Return true when we find the email and the number of To emails is equal to
     * the number that we define
     * 
     * @atmpIds : List of atmpIds to provide
     */
    private boolean findAtmpsUpdateDaysOffMailsClient(Map<Long, Integer> nbDaysOffATMPs) {
        boolean isFound = false;
        MailTrap mailtrap = new MailTrap();
        List<Message> messages = mailtrap.getMessages();
        List<Long> messageIds = messages.stream().filter(msg -> msg.getSubject().contains(UPDATE_SUBJECT_MAILS))
                .map(msg -> msg.getId()).collect(Collectors.toList());
        int foundMailsCount = 0;
        for (Long messageId : messageIds) {
            String messageSubject = mailtrap.getMessageSubject(messageId.toString());
            LOGGER.info("messageSubject is " + messageSubject);
            Pattern pattern = Pattern.compile(UPDATE_DAYS_OFF_MAILS);
            Matcher matcher = pattern.matcher(messageSubject);
            String toEmails = mailtrap.getToEmails(messageId.toString());
            LOGGER.info("toEmails is " + toEmails);
            long countOccurrencesDraft = countOccurrences(toEmails, STRING_OCCURRENCES);
            LOGGER.info("countOccurrencesDraft is " + countOccurrencesDraft);
            try {
                matcher.find();
                long atmpId = Long.parseLong(matcher.group(1));
                LOGGER.info("atmpId is " + atmpId);
                long nbDaysOffInMail = Integer.parseInt(matcher.group(2));
                LOGGER.info("nbDaysOffInMail is " + nbDaysOffInMail);
                if (nbDaysOffATMPs.containsKey(atmpId)) {
                    int nbDaysOff = nbDaysOffATMPs.get(atmpId);
                    if (nbDaysOff == nbDaysOffInMail) {
                        foundMailsCount++;
                        if (foundMailsCount > 0 && countOccurrencesDraft == NUMBER_OF_TO_RECEIPENTS_DRAFT) {
                            isFound = true;
                        }
                        mailtrap.deleteMessage(messageId.toString());
                        break;
                    }
                }
            } catch (Exception e) {
                continue;
            }
        }
        return isFound;
    }

    private void deleteAtmp(String siret, String nns) {
        RestAssured.given()
                .spec(spec)
                .header("Authorization", "Bearer " + API_TOKEN)
                .param("siret", siret).param("nns", nns).param("clientId", CLIENT_ID)
                .when()
                .delete("/atmp")
                .then()
                .statusCode(204);
    }
}