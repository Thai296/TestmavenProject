package com.simplicia.pages.web.dat.createDAT;

import com.simplicia.pages.web.dat.DATMainPage;
import controls.RadioButton;
import controls.TextField;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.Map;

public class LesTemoinsTab extends DATMainPage {

    //###################
    // Locators
    //##################
    // ##### LES TÉMOINS #####
    final static String RAD_TEMOINS_FALSE = "//input[@name='updateWitnessOrFirstPersonNotified' and @value='false']";
    final static String RAD_TEMOINS_TRUE = "//input[@name='updateWitnessOrFirstPersonNotified' and @value='true']";

    // if selects yes/OUI
    //witness
    final static String RAD_TEMOIN = "//input[@name='updateFirstWitness' and @value='true']";

    //witness2/ Temoins2
    final static String RAD_TEMOINS2 = "//input[@name='updateSecondWitness' and @value='true']";
    final static String RAD_TEMOINS3 = "//input[@name='updateThirdWitness' and @value='true']";
    final static String RAD_TEMOINS4 = "//input[@name='updateFourthWitness' and @value='true']";

    // first informed person
    final static String RAD_1ERE_PERSONNE_AVISEE = "//input[@name='updateFirstWitness' and @value='false']";

    // witness 1
    final static String TXT_FIELD_TÉMOINS_NOM = "input[name='temoinNom']";
    final static String TXT_FIELD_TÉMOINS_PRENOM = "input[name='temoinPrenom']";
    final static String TXT_FIELD_TÉMOINS_POINT_DE_REMISE = "input[name='temoinAdressePointRemise']";
    final static String TXT_FIELD_TÉMOINS_COMPLEMENT = "input[name='temoinAdresseComplement']";
    final static String TXT_FIELD_TÉMOINS_VOIE = "input[name='temoinAdresseRue']";
    final static String TXT_FIELD_TÉMOINS_MENTION_DISTB = "input[name='temoinAdresseMentionDistribution']";
    final static String TXT_FIELD_TÉMOINS_POSTAL_CODE = "input[name='temoinAdresseLocalite']";
    final static String TXT_FIELD_TÉMOINS_PAYS = "input[name='temoinAdresseNomPays']";

    // witness 2
    final static String TXT_FIELD_TÉMOINS_NOM1 = "input[name='temoin1Nom']";
    final static String TXT_FIELD_TÉMOINS_PRENOM1 = "input[name='temoin1Prenom']";
    final static String TXT_FIELD_TÉMOINS_POINT_DE_REMISE1 = "input[name='temoin1AdressePointRemise']";
    final static String TXT_FIELD_TÉMOINS_COMPLEMENT1 = "input[name='temoin1AdresseComplement']";
    final static String TXT_FIELD_TÉMOINS_VOIE1 = "input[name='temoin1AdresseRue']";
    final static String TXT_FIELD_TÉMOINS_MENTION_DISTB1 = "input[name='temoin1AdresseMentionDistribution']";
    final static String TXT_FIELD_TÉMOINS_POSTAL_CODE1 = "input[name='temoin1AdresseLocalite']";
    final static String TXT_FIELD_TÉMOINS_PAYS1 = "input[name='temoin1AdresseNomPays']";

    // witness 3
    final static String TXT_FIELD_TÉMOINS_NOM2 = "input[name='temoin2Nom']";
    final static String TXT_FIELD_TÉMOINS_PRENOM2 = "input[name='temoin2Prenom']";
    final static String TXT_FIELD_TÉMOINS_POINT_DE_REMISE2 = "input[name='temoin2AdressePointRemise']";
    final static String TXT_FIELD_TÉMOINS_COMPLEMENT2 = "input[name='temoin2AdresseComplement']";
    final static String TXT_FIELD_TÉMOINS_VOIE2 = "input[name='temoin2AdresseRue']";
    final static String TXT_FIELD_TÉMOINS_MENTION_DISTB2 = "input[name='temoin2AdresseMentionDistribution']";
    final static String TXT_FIELD_TÉMOINS_POSTAL_CODE2 = "input[name='temoin2AdresseLocalite']";
    final static String TXT_FIELD_TÉMOINS_PAYS2 = "input[name='temoin2AdresseNomPays']";

    // witness 4
    final static String TXT_FIELD_TÉMOINS_NOM3 = "input[name='temoin3Nom']";
    final static String TXT_FIELD_TÉMOINS_PRENOM3 = "input[name='temoin3Prenom']";
    final static String TXT_FIELD_TÉMOINS_POINT_DE_REMISE3 = "input[name='temoin3AdressePointRemise']";
    final static String TXT_FIELD_TÉMOINS_COMPLEMENT3 = "input[name='temoin3AdresseComplement']";
    final static String TXT_FIELD_TÉMOINS_VOIE3 = "input[name='temoin3AdresseRue']";
    final static String TXT_FIELD_TÉMOINS_MENTION_DISTB3 = "input[name='temoin3AdresseMentionDistribution']";
    final static String TXT_FIELD_TÉMOINS_POSTAL_CODE3 = "input[name='temoin3AdresseLocalite']";
    final static String TXT_FIELD_TÉMOINS_PAYS3 = "input[name='temoin3AdresseNomPays']";


    // first informed person details
    final static String TXT_FIELD_PERSONNE_AVISEE_NOM = "input[name='personneAviseeNom']";
    final static String TXT_FIELD_PERSONNE_AVISEE_PRENOM = "input[name='personneAviseePrenom']";
    final static String TXT_FIELD_PERSONNE_AVISEE_POINT_DE_REMISE = "input[name='personneAviseeAdressePointRemise']";
    final static String TXT_FIELD_PERSONNE_AVISEE_COMPLEMENT = "input[name='personneAviseeAdresseComplement']";
    final static String TXT_FIELD_PERSONNE_AVISEE_VOIE = "input[name='personneAviseeAdresseRue']";
    final static String TXT_FIELD_PERSONNE_AVISEE_MENTION_DISTB = "input[name='personneAviseeAdresseMentionDistribution']";
    final static String TXT_FIELD_PERSONNE_AVISEE_POSTAL_CODE = "input[name='personneAviseeAdresseLocalite']";
    final static String TXT_FIELD_PERSONNE_AVISEE_PAYS = "input[name='personneAviseeAdresseNomPays']";

    //=============
    // Selenium Controls
    //=============
    private final RadioButton temoinsFalseRB;
    private final RadioButton temoinsTrueRB;
    private final RadioButton qualitéTemoinsTrueRB;
    private final RadioButton qualitéTemoinsFalseRB;
    private final TextField temoinsNomTF;
    private final TextField temoinsPreNomTF;
    private final TextField temoinsPointOfRemiseTF;
    private final TextField temoinsComlementTF;
    private final TextField temoinsStreetTF;
    private final TextField temoinsMentionDistTF;
    private final TextField temoinsPostalCOdeTF;
    private final TextField temoinsPays;

    // witnss 2
    private final TextField temoins1NomTF;
    private final TextField temoins1PreNomTF;
    private final TextField temoins1PointOfRemiseTF;
    private final TextField temoins1ComlementTF;
    private final TextField temoins1StreetTF;
    private final TextField temoins1MentionDistTF;
    private final TextField temoins1PostalCOdeTF;
    private final TextField temoins1Pays;

    //witness 3
    private final TextField temoins2NomTF;
    private final TextField temoins2PreNomTF;
    private final TextField temoins2PointOfRemiseTF;
    private final TextField temoins2ComlementTF;
    private final TextField temoins2StreetTF;
    private final TextField temoins2MentionDistTF;
    private final TextField temoins2PostalCOdeTF;
    private final TextField temoins2Pays;

    //witness 4
    private final TextField temoins3NomTF;
    private final TextField temoins3PreNomTF;
    private final TextField temoins3PointOfRemiseTF;
    private final TextField temoins3ComlementTF;
    private final TextField temoins3StreetTF;
    private final TextField temoins3MentionDistTF;
    private final TextField temoins3PostalCOdeTF;
    private final TextField temoins3Pays;

    private final TextField personneAviseeNomTF;
    private final TextField personneAviseePreNomTF;
    private final TextField personneAviseePoindRemiseTF;
    private final TextField personneAviseeComplementTF;
    private final TextField personneAviseeVoieTF;
    private final TextField personneAviseeMentionDistTF;
    private final TextField personneAviseePostalCodeTF;
    private final TextField personneAviseePaysTF;

    public LesTemoinsTab(WebDriver browser) {
        super(browser);
        temoinsFalseRB = new RadioButton(browser, By.xpath(RAD_TEMOINS_FALSE), "update Witness Or First Person Notified");
        temoinsTrueRB = new RadioButton(browser, By.xpath(RAD_TEMOINS_TRUE));
        qualitéTemoinsTrueRB = new RadioButton(browser, By.xpath(RAD_TEMOIN));
        qualitéTemoinsFalseRB = new RadioButton(browser, By.xpath(RAD_1ERE_PERSONNE_AVISEE));
        temoinsNomTF = new TextField(browser, By.cssSelector(TXT_FIELD_TÉMOINS_NOM), "Temoins nom field");
        temoinsPreNomTF = new TextField(browser, By.cssSelector(TXT_FIELD_TÉMOINS_PRENOM), "Temoins prenom");
        temoinsPointOfRemiseTF = new TextField(browser, By.cssSelector(TXT_FIELD_TÉMOINS_POINT_DE_REMISE), "Temoin Adresse Point Remise field");
        temoinsComlementTF = new TextField(browser, By.cssSelector(TXT_FIELD_TÉMOINS_COMPLEMENT), "Temoin Adresse Complement field");
        temoinsStreetTF = new TextField(browser, By.cssSelector(TXT_FIELD_TÉMOINS_VOIE), "Temoin Adresse Rue ");
        temoinsMentionDistTF = new TextField(browser, By.cssSelector(TXT_FIELD_TÉMOINS_MENTION_DISTB), "Temoin Adresse Mention Distribution");
        temoinsPostalCOdeTF = new TextField(browser, By.cssSelector(TXT_FIELD_TÉMOINS_POSTAL_CODE), "Temoins postal code");
        temoinsPays = new TextField(browser, By.cssSelector(TXT_FIELD_TÉMOINS_PAYS), "Temoins country");

        // witness 2
        temoins1NomTF = new TextField(browser, By.cssSelector(TXT_FIELD_TÉMOINS_NOM1), "Temoins2 nom field");
        temoins1PreNomTF = new TextField(browser, By.cssSelector(TXT_FIELD_TÉMOINS_PRENOM1), "Temoins2 prenom");
        temoins1PointOfRemiseTF = new TextField(browser, By.cssSelector(TXT_FIELD_TÉMOINS_POINT_DE_REMISE1), "Temoin2 Adresse Point Remise field");
        temoins1ComlementTF = new TextField(browser, By.cssSelector(TXT_FIELD_TÉMOINS_COMPLEMENT1), "Temoin2 Adresse Complement field");
        temoins1StreetTF = new TextField(browser, By.cssSelector(TXT_FIELD_TÉMOINS_VOIE1), "Temoins2 Adresse Rue ");
        temoins1MentionDistTF = new TextField(browser, By.cssSelector(TXT_FIELD_TÉMOINS_MENTION_DISTB1), "Temoins2 Adresse Mention Distribution");
        temoins1PostalCOdeTF = new TextField(browser, By.cssSelector(TXT_FIELD_TÉMOINS_POSTAL_CODE1), "Temoins2 postal code");
        temoins1Pays = new TextField(browser, By.cssSelector(TXT_FIELD_TÉMOINS_PAYS1), "Temoins2 country");

        //witness 3
        temoins2NomTF = new TextField(browser, By.cssSelector(TXT_FIELD_TÉMOINS_NOM2), "Temoins3 nom field");
        temoins2PreNomTF = new TextField(browser, By.cssSelector(TXT_FIELD_TÉMOINS_PRENOM2), "Temoins3 prenom");
        temoins2PointOfRemiseTF = new TextField(browser, By.cssSelector(TXT_FIELD_TÉMOINS_POINT_DE_REMISE2), "Temoins3 Adresse Point Remise field");
        temoins2ComlementTF = new TextField(browser, By.cssSelector(TXT_FIELD_TÉMOINS_COMPLEMENT2), "Temoins3 Adresse Complement field");
        temoins2StreetTF = new TextField(browser, By.cssSelector(TXT_FIELD_TÉMOINS_VOIE2), "Temoins3 Adresse Rue ");
        temoins2MentionDistTF = new TextField(browser, By.cssSelector(TXT_FIELD_TÉMOINS_MENTION_DISTB2), "Temoins3 Adresse Mention Distribution");
        temoins2PostalCOdeTF = new TextField(browser, By.cssSelector(TXT_FIELD_TÉMOINS_POSTAL_CODE2), "Temoins3 postal code");
        temoins2Pays = new TextField(browser, By.cssSelector(TXT_FIELD_TÉMOINS_PAYS2), "Temoins3 country");

        //witness 4
        temoins3NomTF = new TextField(browser, By.cssSelector(TXT_FIELD_TÉMOINS_NOM3), "Temoins3 nom field");
        temoins3PreNomTF = new TextField(browser, By.cssSelector(TXT_FIELD_TÉMOINS_PRENOM3), "Temoins3 prenom");
        temoins3PointOfRemiseTF = new TextField(browser, By.cssSelector(TXT_FIELD_TÉMOINS_POINT_DE_REMISE3), "Temoins3 Adresse Point Remise field");
        temoins3ComlementTF = new TextField(browser, By.cssSelector(TXT_FIELD_TÉMOINS_COMPLEMENT3), "Temoins3 Adresse Complement field");
        temoins3StreetTF = new TextField(browser, By.cssSelector(TXT_FIELD_TÉMOINS_VOIE3), "Temoins3 Adresse Rue ");
        temoins3MentionDistTF = new TextField(browser, By.cssSelector(TXT_FIELD_TÉMOINS_MENTION_DISTB3), "Temoins3 Adresse Mention Distribution");
        temoins3PostalCOdeTF = new TextField(browser, By.cssSelector(TXT_FIELD_TÉMOINS_POSTAL_CODE3), "Temoins3 postal code");
        temoins3Pays = new TextField(browser, By.cssSelector(TXT_FIELD_TÉMOINS_PAYS3), "Temoins3 country");

        personneAviseeNomTF = new TextField(browser, By.cssSelector(TXT_FIELD_PERSONNE_AVISEE_NOM), "Personne Avisee Nom");
        personneAviseePreNomTF = new TextField(browser, By.cssSelector(TXT_FIELD_PERSONNE_AVISEE_PRENOM), "Personne Avisee PreNom");
        personneAviseePoindRemiseTF = new TextField(browser, By.cssSelector(TXT_FIELD_PERSONNE_AVISEE_POINT_DE_REMISE), "personne Avisee Adresse Point Remise");
        personneAviseeComplementTF = new TextField(browser, By.cssSelector(TXT_FIELD_PERSONNE_AVISEE_COMPLEMENT), "personne Avisee Adresse Complement");
        personneAviseeVoieTF = new TextField(browser, By.cssSelector(TXT_FIELD_PERSONNE_AVISEE_VOIE), "personne Avisee Adresse Rue");
        personneAviseeMentionDistTF = new TextField(browser, By.cssSelector(TXT_FIELD_PERSONNE_AVISEE_MENTION_DISTB), "personne Avisee Adresse Mention Distribution");
        personneAviseePostalCodeTF = new TextField(browser, By.cssSelector(TXT_FIELD_PERSONNE_AVISEE_POSTAL_CODE), "personne Avisee Adresse Localite");
        personneAviseePaysTF = new TextField(browser, By.cssSelector(TXT_FIELD_PERSONNE_AVISEE_PAYS), "personne Avisee Adresse NomPays");

    }

    public void fillLesTemoinsTab(Map<String, String> mlesTemonisDetails, boolean bTamoins, boolean bTamoins2, boolean bTamoins3, boolean bTamoins4) throws Exception {
        clickCloseMessage1();
        clickOnRadioButtonUsingJavaScript(RAD_TEMOINS_TRUE);

        if (bTamoins) {

            //Enter first witness details
            temoinsNomTF.typeKeys(mlesTemonisDetails.get("TÉMOINS_NOM"));
            temoinsPreNomTF.typeKeys(mlesTemonisDetails.get("TÉMOINS_PRENOM"));
            temoinsPointOfRemiseTF.typeKeys(mlesTemonisDetails.get("TÉMOINS_POINT_DE_REMISE"));
            temoinsComlementTF.typeKeys(mlesTemonisDetails.get("TÉMOINS_COMPLEMENT"));
            temoinsStreetTF.typeKeys(mlesTemonisDetails.get("TÉMOINS_VOIE"));
            temoinsMentionDistTF.typeKeys(mlesTemonisDetails.get("TÉMOINS_MENTION_DISTB"));
            temoinsPostalCOdeTF.typeKeys(mlesTemonisDetails.get("TÉMOINS_POSTAL_CODE"));
            temoinsPays.typeKeys(mlesTemonisDetails.get("TÉMOINS_PAYS"));

            // if witness 2 is selected
            if (bTamoins2) {
                clickOnRadioButtonUsingJavaScript(RAD_TEMOINS2);

                //Enter second witness details
                temoins1NomTF.typeKeys(mlesTemonisDetails.get("TÉMOINS_NOM"));
                temoins1PreNomTF.typeKeys(mlesTemonisDetails.get("TÉMOINS_PRENOM"));
                temoins1PointOfRemiseTF.typeKeys(mlesTemonisDetails.get("TÉMOINS_POINT_DE_REMISE"));
                temoins1ComlementTF.typeKeys(mlesTemonisDetails.get("TÉMOINS_COMPLEMENT"));
                temoins1StreetTF.typeKeys(mlesTemonisDetails.get("TÉMOINS_VOIE"));
                temoins1MentionDistTF.typeKeys(mlesTemonisDetails.get("TÉMOINS_MENTION_DISTB"));
                temoins1PostalCOdeTF.typeKeys(mlesTemonisDetails.get("TÉMOINS_POSTAL_CODE"));
                temoins1Pays.typeKeys(mlesTemonisDetails.get("TÉMOINS_PAYS"));

                //if witness 3 is to be selected
                if (bTamoins3) {
                    clickOnRadioButtonUsingJavaScript(RAD_TEMOINS3);

                    //Enter third  witness details
                    temoins2NomTF.typeKeys(mlesTemonisDetails.get("TÉMOINS_NOM"));
                    temoins2PreNomTF.typeKeys(mlesTemonisDetails.get("TÉMOINS_PRENOM"));
                    temoins2PointOfRemiseTF.typeKeys(mlesTemonisDetails.get("TÉMOINS_POINT_DE_REMISE"));
                    temoins2ComlementTF.typeKeys(mlesTemonisDetails.get("TÉMOINS_COMPLEMENT"));
                    temoins2StreetTF.typeKeys(mlesTemonisDetails.get("TÉMOINS_VOIE"));
                    temoins2MentionDistTF.typeKeys(mlesTemonisDetails.get("TÉMOINS_MENTION_DISTB"));
                    temoins2PostalCOdeTF.typeKeys(mlesTemonisDetails.get("TÉMOINS_POSTAL_CODE"));
                    temoins2Pays.typeKeys(mlesTemonisDetails.get("TÉMOINS_PAYS"));

                    //if witness4 is to be selected
                    if (bTamoins4) {
                        clickOnRadioButtonUsingJavaScript(RAD_TEMOINS4);

                        //Enter fourth witness details
                        temoins3NomTF.typeKeys(mlesTemonisDetails.get("TÉMOINS_NOM"));
                        temoins3PreNomTF.typeKeys(mlesTemonisDetails.get("TÉMOINS_PRENOM"));
                        temoins3PointOfRemiseTF.typeKeys(mlesTemonisDetails.get("TÉMOINS_POINT_DE_REMISE"));
                        temoins3ComlementTF.typeKeys(mlesTemonisDetails.get("TÉMOINS_COMPLEMENT"));
                        temoins3StreetTF.typeKeys(mlesTemonisDetails.get("TÉMOINS_VOIE"));
                        temoins3MentionDistTF.typeKeys(mlesTemonisDetails.get("TÉMOINS_MENTION_DISTB"));
                        temoins3PostalCOdeTF.typeKeys(mlesTemonisDetails.get("TÉMOINS_POSTAL_CODE"));
                        temoins3Pays.typeKeys(mlesTemonisDetails.get("TÉMOINS_PAYS"));

                    }
                }
            }

        } else {

            // select 1 ere personne avisee
            clickOnRadioButtonUsingJavaScript(RAD_1ERE_PERSONNE_AVISEE);
            personneAviseeNomTF.typeKeys(mlesTemonisDetails.get("PERSONNE_AVISEE_NOM"));
            personneAviseePreNomTF.typeKeys(mlesTemonisDetails.get("PERSONNE_AVISEE_PRENOM"));
            personneAviseePoindRemiseTF.typeKeys(mlesTemonisDetails.get("PERSONNE_AVISEE_POINT_DE_REMISE"));
            personneAviseeComplementTF.typeKeys(mlesTemonisDetails.get("PERSONNE_AVISEE_COMPLEMENT"));
            personneAviseeVoieTF.typeKeys(mlesTemonisDetails.get("PERSONNE_AVISEE_VOIE"));
            personneAviseeMentionDistTF.typeKeys(mlesTemonisDetails.get("PERSONNE_AVISEE_MENTION_DISTB"));
            personneAviseePostalCodeTF.typeKeys(mlesTemonisDetails.get("PERSONNE_AVISEE_POSTAL_CODE"));
            personneAviseePaysTF.typeKeys(mlesTemonisDetails.get("PERSONNE_AVISEE_PAYS"));

        }

    }
}
