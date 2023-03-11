package com.simplicia.pages.web.dat.createDAT;

import com.simplicia.pages.web.dat.DATMainPage;
import controls.RadioButton;
import controls.TextField;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.Map;

public class LeTiersTab extends DATMainPage {

    //###################
    // Locators
    //##################
    // ###### LE TIERS #####
    final static String RAD_LE_TIERS_FALSE = "//input[@name='updateThird' and @value='false']";
    final static String RAD_LE_TIERS_TRUE = "//input[@name='updateThird' and @value='true']";

    // if select Yes/OUI
    final static String TXT_FIELD_LE_TIERS_NOM = "input[name='tiersResponsableNom']";
    final static String TXT_FIELD_LE_TIERS_PRENOM = "input[name='tiersResponsablePrenom']";
    final static String TXT_FIELD_LE_TIERS_RAISON_SOCIALE = "input[name='tiersResponsableRaisonSociale']";
    final static String TXT_FIELD_LE_TIERS_POINT_DE_REMISE = "input[name='tiersResponsableAdressePointRemise']";
    final static String TXT_FIELD_LE_TIERS_COMPLEMENT = "input[name='tiersResponsableAdresseComplement']";
    final static String TXT_FIELD_LE_TIERS_VOIE = "input[name='tiersResponsableAdresseRue']";
    final static String TXT_FIELD_LE_TIERS_MENTION_DISTB = "input[name='tiersResponsableAdresseMentionDistribution']";
    final static String TXT_FIELD_LE_TIERS_POSTAL_CODE = "input[name='tiersResponsableAdresseLocalite']";
    final static String TXT_FIELD_LE_TIERS_PAYS = "input[name='tiersResponsableAdresseNomPays']";

    // third party insurance company/ Société d'assurance du tiers
    final static String RAD_LE_TIERS_SOCIETE_ASSURANCE_FALSE = "//input[@name='updateInsuranceCompany' and @value='false']";
    final static String RAD_LE_TIERS_SOCIETE_ASSURANCE_TRUE = "//input[@name='updateInsuranceCompany' and @value='true']";

    // if third party insurance true
    final static String TXT_FIELD_SA_IDENTITE = "input[name='assureurIdentite']";
    final static String TXT_FIELD_SA_SOCIETE_ASSURANCE = "input[name='assureurSocieteDassuranceDuTiers']";
    final static String TXT_FIELD_SA_POINT_DE_REMISE = "input[name='assureurAdressePointRemise']";
    final static String TXT_FIELD_SA_COMPLEMENT = "input[name='assureurAdresseComplement']";
    final static String TXT_FIELD_SA_VOIE = "input[name='assureurAdresseRue']";
    final static String TXT_FIELD_SA_MENTION_DISTB = "input[name='assureurAdresseMentionDistribution']";
    final static String TXT_FIELD_SA_POSTAL_CODE = "input[name='assureurAdresseLocalite']";
    final static String TXT_FIELD_SA_PAYS = "input[name='assureurAdresseNomPays']";
    final static String TXT_FIELD_SA_NUMERO_DE_CONTRAT = "input[name='assureurNDeContrat']";

    //=============
    // Selenium Controls
    //=============
    private final RadioButton leTiersFalseRB;
    private final RadioButton leTierTrueRB;
    private final TextField leTiersNomTF;
    private final TextField leTiersPreNomTF;
    private final TextField leTriesRaisonSocialeTF;
    private final TextField leTiersPointDeRemiseTF;
    private final TextField leTiersComplementTF;
    private final TextField leTiersVoieTF;
    private final TextField leTiersMentionDistbTF;
    private final TextField leTiersPostalCodeTF;
    private final TextField leTiersPaysTF;
    private final RadioButton leTiersSocieteAssuranceFalseRB;
    private final RadioButton leTiersSociateAssuranceTrueRB;
    private final TextField saIdentiteTF;
    private final TextField saSocieteAssuranceTF;
    private final TextField saPointDeRemiseTF;
    private final TextField saComplementTF;
    private final TextField saVoieTF;
    private final TextField saMentionDistbTF;
    private final TextField saPostalCodeTF;
    private final TextField saPaysTF;
    private final TextField saNumeroDeContrat;

    public LeTiersTab(WebDriver browser) {
        super(browser);

        leTiersFalseRB = new RadioButton(browser, By.xpath(RAD_LE_TIERS_FALSE));
        leTierTrueRB = new RadioButton(browser, By.xpath(RAD_LE_TIERS_TRUE));
        leTiersNomTF = new TextField(browser, By.cssSelector(TXT_FIELD_LE_TIERS_NOM), "Le Tiers Responseble Nom");
        leTiersPreNomTF = new TextField(browser, By.cssSelector(TXT_FIELD_LE_TIERS_PRENOM), "Le Tiers Responsable PreNom");
        leTriesRaisonSocialeTF = new TextField(browser, By.cssSelector(TXT_FIELD_LE_TIERS_RAISON_SOCIALE), "Le Tiers Raison Sociale");
        leTiersPointDeRemiseTF = new TextField(browser, By.cssSelector(TXT_FIELD_LE_TIERS_POINT_DE_REMISE), "tiers Responsable Adresse Point Remise");
        leTiersComplementTF = new TextField(browser, By.cssSelector(TXT_FIELD_LE_TIERS_COMPLEMENT), "tiers Responsable Adresse Complement");
        leTiersVoieTF = new TextField(browser, By.cssSelector(TXT_FIELD_LE_TIERS_VOIE), "tiers Responsable Adresse Rue");
        leTiersMentionDistbTF = new TextField(browser, By.cssSelector(TXT_FIELD_LE_TIERS_MENTION_DISTB), "tiers Responsable Adresse Mention Distribution");
        leTiersPostalCodeTF = new TextField(browser, By.cssSelector(TXT_FIELD_LE_TIERS_POSTAL_CODE), "tiers Responsable Adresse Localite");
        leTiersPaysTF = new TextField(browser, By.cssSelector(TXT_FIELD_LE_TIERS_PAYS), "tiers Responsable Adresse Nom Pays");
        leTiersSocieteAssuranceFalseRB = new RadioButton(browser, By.xpath(RAD_LE_TIERS_SOCIETE_ASSURANCE_FALSE), "update Insurance Company false ");
        leTiersSociateAssuranceTrueRB = new RadioButton(browser, By.xpath(RAD_LE_TIERS_SOCIETE_ASSURANCE_TRUE), "update Insurance Company True");
        saIdentiteTF = new TextField(browser, By.cssSelector(TXT_FIELD_SA_IDENTITE), " Assureur Identite");
        saSocieteAssuranceTF = new TextField(browser, By.cssSelector(TXT_FIELD_SA_SOCIETE_ASSURANCE), "assureur Societe D assurance Du Tiers");
        saPointDeRemiseTF = new TextField(browser, By.cssSelector(TXT_FIELD_SA_POINT_DE_REMISE), "assureur Adresse Point Remise");
        saComplementTF = new TextField(browser, By.cssSelector(TXT_FIELD_SA_COMPLEMENT), "assureur Adresse Complement");
        saVoieTF = new TextField(browser, By.cssSelector(TXT_FIELD_SA_VOIE), "assureur Adresse Rue");
        saMentionDistbTF = new TextField(browser, By.cssSelector(TXT_FIELD_SA_MENTION_DISTB), "assureur Adresse Mention Distribution");
        saPostalCodeTF = new TextField(browser, By.cssSelector(TXT_FIELD_SA_POSTAL_CODE), "assureur Adresse Localite");
        saPaysTF = new TextField(browser, By.cssSelector(TXT_FIELD_SA_PAYS), "assureur Adresse NomPays");
        saNumeroDeContrat = new TextField(browser, By.cssSelector(TXT_FIELD_SA_NUMERO_DE_CONTRAT), "assureur Societe D assurance DuTiers");

    }

    public void fillleTiersTabDetails(Map<String, String> leTiersDetails) throws Exception {

        clickCloseMessage1();
        clickOnRadioButtonUsingJavaScript(RAD_LE_TIERS_TRUE);
        sleep(1000);

        // fill in the details for Renseignements sur le tiers
        leTiersNomTF.typeKeys(leTiersDetails.get("LE_TIERS_NOM"));
        leTiersPreNomTF.typeKeys(leTiersDetails.get("LE_TIERS_PRENOM"));
        //leTriesRaisonSocialeTF.typeKeys(leTiersDetails.get("LE_TIERS_RAISON_SOCIALE"));
        leTiersPointDeRemiseTF.typeKeys(leTiersDetails.get("LE_TIERS_POINT_DE_REMISE"));
        leTiersComplementTF.typeKeys(leTiersDetails.get("LE_TIERS_COMPLEMENT"));
        leTiersVoieTF.typeKeys(leTiersDetails.get("LE_TIERS_VOIE"));
        leTiersMentionDistbTF.typeKeys(leTiersDetails.get("LE_TIERS_MENTION_DISTB"));
        if(leTiersDetails.get("LE_TIERS_POSTAL_CODE").matches("^[0-9]{5} [a-zA-Z].*$")) {

            leTiersPostalCodeTF.typeKeys(leTiersDetails.get("LE_TIERS_POSTAL_CODE"));

        } else {

            leTiersPostalCodeTF.typeKeys("75001 PARIS");
        }
        leTiersPaysTF.typeKeys(leTiersDetails.get("LE_TIERS_PAYS"));

        // fill Société d'assurance du tiers details
        clickOnRadioButtonUsingJavaScript(RAD_LE_TIERS_SOCIETE_ASSURANCE_TRUE);
        saIdentiteTF.typeKeys(leTiersDetails.get("SA_IDENTITE"));
        saSocieteAssuranceTF.typeKeys(leTiersDetails.get("SA_SOCIETE_ASSURANCE"));
        saPointDeRemiseTF.typeKeys(leTiersDetails.get("SA_POINT_DE_REMISE"));
        saComplementTF.typeKeys(leTiersDetails.get("SA_COMPLEMENT"));
        saVoieTF.typeKeys(leTiersDetails.get("SA_VOIE"));
        saMentionDistbTF.typeKeys(leTiersDetails.get("SA_MENTION_DISTB"));
        if(leTiersDetails.get("SA_POSTAL_CODE").matches("^[0-9]{5} [a-zA-Z].*$")) {
            saPostalCodeTF.typeKeys(leTiersDetails.get("SA_POSTAL_CODE"));

        } else {

            saPostalCodeTF.typeKeys("75001 PARIS");
        }
        saPaysTF.typeKeys(leTiersDetails.get("SA_PAYS"));
        saNumeroDeContrat.typeKeys(leTiersDetails.get("SA_NUMERO_DE_CONTRAT"));


    }
}
