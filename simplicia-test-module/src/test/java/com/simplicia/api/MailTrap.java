package com.simplicia.api;

import java.util.List;

import com.simplicia.api.model.Message;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class MailTrap {
    private final String MAIL_TRAP_API_URL = "https://mailtrap.io/api/v1";
    private final String API_TOKEN = "fca0d284755b1b46d83a7575cc2a535b";
    private final String INBOX_ID = "1524618";
    private final String TO_FIELD = "to_email";
    private final String SUBJECT = "subject";
   

    public List<Message> getMessages() {
        RequestSpecification spec = getSpec();
        List<Message> messages = RestAssured.given()
                .spec(spec)
                .when()
                .get("/inboxes/{inbox_id}/messages", INBOX_ID)
                .then()
                .statusCode(200)
                .extract().body().jsonPath().getList(".", Message.class);
        return messages;
    }

    public String getMessageBody(String messageId) {
        RequestSpecification spec = getSpec();
        String messageBody = RestAssured.given()
                .spec(spec)
                .when()
                .get("/inboxes/{inbox_id}/messages/{id}/body.html", INBOX_ID, messageId)
                .then()
                .statusCode(200)
                .extract().body().asString();
        return messageBody;
    }

    public String getToEmails(String messageId) {
        RequestSpecification spec = getSpec();
        String toEmails = RestAssured.given()
                .spec(spec)
                .when()
                .get("/inboxes/{inbox_id}/messages/{id}/", INBOX_ID, messageId)
                .then()
                .statusCode(200)
                .extract().body().jsonPath().getString(TO_FIELD);
        return toEmails;
    }

    public String getMessageSubject(String messageId) {
        RequestSpecification spec = getSpec();
        String messageSubject = RestAssured.given()
                .spec(spec)
                .when()
                .get("/inboxes/{inbox_id}/messages/{id}/", INBOX_ID, messageId)
                .then()
                .statusCode(200)
                .extract().body().jsonPath().getString(SUBJECT);
        return messageSubject;
    }

    public void deleteMessage(String messageId) {
        RequestSpecification spec = getSpec();
        RestAssured.given()
                .spec(spec)
                .when()
                .delete("/inboxes/{inbox_id}/messages/{id}", INBOX_ID, messageId)
                .then()
                .statusCode(200);
    }

    private RequestSpecification getSpec() {
        RequestSpecification spec = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBaseUri(MAIL_TRAP_API_URL)
                .addHeader("Api-Token", API_TOKEN)
                .build();
        return spec;
    }

    
    public static void main(String[] args) {
        MailTrap mailtrap = new MailTrap();
//        String body = mailtrap.getMessageBody("2847464904");
//        LOGGER.info(body);
        mailtrap.getMessages();
    }
}
