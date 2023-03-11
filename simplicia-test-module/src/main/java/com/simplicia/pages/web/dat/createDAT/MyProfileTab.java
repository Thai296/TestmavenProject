package com.simplicia.pages.web.dat.createDAT;

import com.simplicia.functions.TestDataEngine;
import com.simplicia.pages.web.atmp.ATMPPage;
import com.simplicia.pages.web.dat.DATMainPage;
import controls.Icon;
import controls.RadioButton;
import controls.TextField;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.Map;

public class MyProfileTab extends DATMainPage {

    private static final org.apache.log4j.Logger LOGGER = Logger.getLogger(ATMPPage.class);


    //============
    // Locators
    //============
    // ##### My Profile-MP #####
    final static String ICON_SELECT_MP_CHOOSE_COMPANY = "//label[normalize-space()='Accès net-entreprises *']/following::div[2]";// "//*[@id='loginAddresses' and @type='hidden']";
    //final static String SELECT_MP_SELECT_ESTB = "//div[@class='MuiPaper-root MuiCard-root mb-10 " +
    //      "MuiPaper-elevation1 MuiPaper-rounded']//div[2]//div[1]//div[1]";
    final static String TXT_FIELD_MP_COMP_ADD_BUILD = "//input[@name='employeurAdresseComplement']";
    final static String TXT_FIELD_MP_RAISON_SOCIALE_LETABLISSMENT = "//input[@name='employeurNom']";
    final static String ICON_SELECT_MP_SELECT_ESTB = TXT_FIELD_MP_RAISON_SOCIALE_LETABLISSMENT + "/preceding::div[1]";
    final static String TXT_FIELD_MP_EMAIL_SIGNATURE = "//input[@name='signataireEmail']";
    final static String TXT_FIELD_MP_STREET = "input[name='employeurAdresseRue']";
    final static String TXT_FIELD_MP_POSTAL_CODE = "input[name='employeurAdresseLocalite']";
    final static String TXT_FIELD_MP_COUNTRY = "input[name='employeurAdresseNomPays']";
    final static String TXT_FIELD_MP_SIRET = "input[name='etablissementAttacheSiret']";
    final static String TXT_FIELD_MP_EMAIL = "input[name='etablissementAttacheMail']";
    final static String TXT_FIELD_MP_TELEPHONE = "input[name='etablissementAttacheTel']";
    final static String TXT_FIELD_MP_SS_RISK_NUMBER = "input[name='etablissementAttacheRisquess']";
    final static String RAD_MP_IS_ADDRESS_DIFFERENT = "//input[@name='isCentralizedManagementAddressDifferent' and @value='true']";
            //"//legend[contains(text(),'Avez vous mis en place une gestion centralisée des')]/following::input[@type='radio' and @value='true']";
    final static String RAD_MP_DETAILS_OF_HEALTH_SERVICE = "//p[normalize-space()='SERVICE DE SANTE AU TRAVAIL']/following::input[@type='radio' and @value='true']";
    final static String TXT_FIELD_CA_POINT_REMISE = "//input[@name='etablissementAttacheAdressePointRemise']";
    final static String TXT_FIELD_CA_COMPLEMENT = "//input[@name='etablissementAttacheAdresseComplement']";
    final static String TXT_FIELD_CA_VOIE = "//input[@name='etablissementAttacheAdresseRue']";
    final static String TXT_FIELD_CA_MENTION_DISTB = "//input[@name='etablissementAttacheAdresseMentionDistribution']";
    final static String TXT_FIELD_CA_POSTAL_CODE = "//input[@name='etablissementAttacheAdresseLocalite']";
    final static String TXT_FIELD_CA_PAYS = "//input[@name='etablissementAttacheAdresseNomPays']";
    final static String TXT_FIELD_HS_NOM = "//input[@name='servicesanteTravailNom']";
    final static String TXT_FIELD_HS_POINT_REMISE = "//input[@name='servicesanteTravailAdressePointRemise']";
    final static String TXT_FIELD_HS_COMPLEMENT = "//input[@name='servicesanteTravailAdresseComplement']";
    final static String TXT_FIELD_HS_VOIE = "//input[@name='servicesanteTravailAdresseRue']";
    final static String TXT_FIELD_HS_MENTION_DISTB = "//input[@name='servicesanteTravailAdresseMentionDistribution']";
    final static String TXT_FIELD_HS_POSTAL_CODE = "//input[@name='servicesanteTravailAdresseLocalite']";
    final static String TXT_FIELD_HS_PAYS = "//input[@name='servicesanteTravailAdresseNomPays']";


    //=============
    // Selenium Controls
    //=============
    private final Icon selectCompanyIcon;
    private final TextField empAddressTextField;
    private final Icon selectEstablishmentIcon;
    private final TextField raisonSocialeLetablissment;
    private final TextField emailSignature;
    private final TextField streetAddTextField;
    private final TextField postalCodeTextField;
    private final TextField countryTextField;
    private final TextField siretTextField;
    private final TextField emailTextField;
    private final TextField telephoneTextField;
    private final TextField ssRiskNumberTextField;
    private final RadioButton isAddressDifferentCB;
    private final TextField caPointRemiseTF;
    private final TextField caComplementTF;
    private final TextField caVoieTF;
    private final TextField caMentionDistbTF;
    private final TextField caPostalCodeTF;
    private final TextField caPaysTF;
    private final RadioButton detailsOfHealthServiceCB;
    private final TextField hsNomTF;
    private final TextField hsPointRemiseTF;
    private final TextField hsComplementTF;
    private final TextField hsVoieTF;
    private final TextField hsMentionDistbTF;
    private final TextField hsPostalCodeTF;
    private final TextField hsPaysTF;


    public MyProfileTab(WebDriver browser) {
        super(browser);

        selectCompanyIcon = new Icon(browser, By.xpath(ICON_SELECT_MP_CHOOSE_COMPANY), "Choose company list");
        empAddressTextField = new TextField(browser, By.xpath(TXT_FIELD_MP_COMP_ADD_BUILD), "Enter employer address text field");
        selectEstablishmentIcon = new Icon(browser, By.xpath(ICON_SELECT_MP_SELECT_ESTB), "select establishment list");
        raisonSocialeLetablissment = new TextField(browser, By.xpath(TXT_FIELD_MP_RAISON_SOCIALE_LETABLISSMENT), "Enter raison sociale ");
        emailSignature = new TextField(browser, By.xpath(TXT_FIELD_MP_EMAIL_SIGNATURE), "Enter email sifnature");
        streetAddTextField = new TextField(browser, By.cssSelector(TXT_FIELD_MP_STREET), "Enter street address text field");
        postalCodeTextField = new TextField(browser, By.cssSelector(TXT_FIELD_MP_POSTAL_CODE), "Enter postal code text field");
        countryTextField = new TextField(browser, By.cssSelector(TXT_FIELD_MP_COUNTRY), "Enter country name text field");
        siretTextField = new TextField(browser, By.cssSelector(TXT_FIELD_MP_SIRET), "Enter SIRET text field");
        emailTextField = new TextField(browser, By.cssSelector(TXT_FIELD_MP_EMAIL), "Enter Email text field");
        telephoneTextField = new TextField(browser, By.cssSelector(TXT_FIELD_MP_TELEPHONE), "Enter telephone number text field");
        ssRiskNumberTextField = new TextField(browser, By.cssSelector(TXT_FIELD_MP_SS_RISK_NUMBER), "Enter social security risk number text field");
        isAddressDifferentCB = new RadioButton(browser, By.cssSelector(RAD_MP_IS_ADDRESS_DIFFERENT), "Is centralize Mgmt address different checkbox");
        caPointRemiseTF = new TextField(browser, By.xpath(TXT_FIELD_CA_POINT_REMISE), "Enter point remise for centralized accident mgmt ");
        caComplementTF = new TextField(browser, By.xpath(TXT_FIELD_CA_COMPLEMENT), "Enter complement for centralized accident mgmt");
        caVoieTF = new TextField(browser, By.xpath(TXT_FIELD_CA_VOIE), "Enter voie for centralized accident mgmt");
        caMentionDistbTF = new TextField(browser, By.xpath(TXT_FIELD_CA_MENTION_DISTB), "Enter mention distribution   for centralized accident mgmt");
        caPostalCodeTF = new TextField(browser, By.xpath(TXT_FIELD_CA_POSTAL_CODE), "Enter postal code for centralized accident mgmt");
        caPaysTF = new TextField(browser, By.xpath(TXT_FIELD_CA_PAYS), "Enter voie for centralized accident mgmt");
        detailsOfHealthServiceCB = new RadioButton(browser, By.xpath(RAD_MP_DETAILS_OF_HEALTH_SERVICE), "Details of health service checkbox");
        hsNomTF = new TextField(browser, By.xpath(TXT_FIELD_HS_NOM), "Enter nom for health service ");
        hsPointRemiseTF = new TextField(browser, By.xpath(TXT_FIELD_HS_POINT_REMISE), "Enter point remise for health service");
        hsComplementTF = new TextField(browser, By.xpath(TXT_FIELD_HS_COMPLEMENT), "Enter complement for health service");
        hsVoieTF = new TextField(browser, By.xpath(TXT_FIELD_HS_VOIE), "Enter voie for health service");
        hsMentionDistbTF = new TextField(browser, By.xpath(TXT_FIELD_HS_MENTION_DISTB), "Enter mention distribution for health service");
        hsPostalCodeTF = new TextField(browser, By.xpath(TXT_FIELD_HS_POSTAL_CODE), "Enter postal code for chealth service");
        hsPaysTF = new TextField(browser, By.xpath(TXT_FIELD_HS_PAYS), "Enter voie for health service");
    }

    /**
     * Click on enterprise list and select any value
     * and if enterprise has option to select establishment then select that
     * @param mDATMyProfile
     */
    public void selectEnterpriseValue(Map<String, String> mDATMyProfile, Map<String, String> signatureDetails) throws Exception{

        selectCompanyIcon.shouldBeDisplayed();
        retry(() -> {
            selectCompanyIcon.click();
            selectItemFromDropDownList(mDATMyProfile.get("ACCESS_NET_ENTERPRISE"));
        });
       // new CustomWebDriverWait(browser, 3).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("ICON_SELECT_MP_SELECT_ESTB")));

        //Select establishment if any company has
        if(mDATMyProfile.get("ACCESS_NET_ENTERPRISE").equals("SIMPLICIA")||mDATMyProfile.get("ACCESS_NET_ENTERPRISE").equals("84250305400012")){

            Thread.sleep(2000);
            enterEmplacememnnts(mDATMyProfile);
        } else {
            LOGGER.info("--> raisonSocialeLetablissment");
            // enter raison sociale letablissment and email signature, in case of simplicia it will be autopopulated from DB
            raisonSocialeLetablissment.typeKeys(mDATMyProfile.get("SELECTIONNEZ_UN_EASTB"));
            emailSignature.typeKeys(signatureDetails.get("TXT_FIELD_SIGN_EMAIL"));

        }

    }

    public void enterEmplacememnnts(Map<String, String> mDATMyProfile) throws Exception {
        LOGGER.info("--> enterEmplacememnnts");
        retry(() -> {
            downKey(By.xpath(ICON_SELECT_MP_SELECT_ESTB));
            selectItemFromDropDownList(mDATMyProfile.get("SELECTIONNEZ_UN_EASTB"));
        });

        // let complement value auto populate
        Thread.sleep(4000);
    }

    /**
     * Enter complement/ address
     * @param mDATMyProfile
     */
    public void setComplement(Map<String, String> mDATMyProfile){

        empAddressTextField.shouldBeDisplayed();
        empAddressTextField.typeKeys(mDATMyProfile.get("COMPLEMENT"));

    }

    /**
     * Enter voie/street name
     * @param mDATMyProfile
     */
    public void setVoie(Map<String, String> mDATMyProfile){

        if(mDATMyProfile.get("ACCESS_NET_ENTERPRISE").equals("SIMPLICIA")||mDATMyProfile.get("ACCESS_NET_ENTERPRISE").equals("84250305400012")){

            // do nothing

        } else {
            streetAddTextField.shouldBeDisplayed();
            streetAddTextField.typeKeys(mDATMyProfile.get("VOIE"));
        }
    }

    /**
     * Enter postal code
     * @param mDATMyProfile
     */
    public void setPostalCode(Map<String, String>mDATMyProfile){

        if(mDATMyProfile.get("ACCESS_NET_ENTERPRISE").equals("SIMPLICIA")||mDATMyProfile.get("ACCESS_NET_ENTERPRISE").equals("84250305400012")){

            // do nothing

        } else {
            postalCodeTextField.shouldBeDisplayed();
            postalCodeTextField.typeKeys(mDATMyProfile.get("POSTAL_CODE"));
        }

    }

    /**
     * Enter Pays/country name
     * @param mDATMyProfile
     */
    public void setPays(Map<String, String>mDATMyProfile) {

        if(mDATMyProfile.get("ACCESS_NET_ENTERPRISE").equals("SIMPLICIA")||mDATMyProfile.get("ACCESS_NET_ENTERPRISE").equals("84250305400012")){

            // do nothing

        } else {

            countryTextField.shouldBeDisplayed();
            countryTextField.typeKeys(mDATMyProfile.get("PAYS"));
        }
    }

    /**
     *
     * Enter SIRET , lets generate one  randomly , suffixing 8 so that it should not start with 0
     */
    public void setSiret(Map<String, String>mDATMyProfile){

        if(mDATMyProfile.get("ACCESS_NET_ENTERPRISE").equals("SIMPLICIA")||mDATMyProfile.get("ACCESS_NET_ENTERPRISE").equals("84250305400012")){

            // do nothing

        } else {

            String sSiret = "8" + TestDataEngine.generateRandomNumber(13);
            siretTextField.shouldBeDisplayed();
            siretTextField.typeKeys(sSiret);
        }
    }

    /**
     * Enter email address, will be generated randomly
     * @param mDATMyProfile
     */
    public void setEmailAddress(Map<String, String>mDATMyProfile){

//        if(mDATMyProfile.get("ACCESS_NET_ENTERPRISE").equals("SIMPLICIA")||mDATMyProfile.get("ACCESS_NET_ENTERPRISE").equals("84250305400012")){
//
//            // do nothing
//
//        } else {

            emailTextField.shouldBeDisplayed();
            emailTextField.typeKeys(mDATMyProfile.get("EMAIL"));
//        }

    }

    /**
     * Enter telephone number, lets create a random one of 10 digit.
     */
    public void setTelephoneNumber(Map<String, String>mDATMyProfile){

        if(mDATMyProfile.get("ACCESS_NET_ENTERPRISE").equals("SIMPLICIA")||mDATMyProfile.get("ACCESS_NET_ENTERPRISE").equals("84250305400012")){

            // do nothing

        } else {

            telephoneTextField.shouldBeDisplayed();
            telephoneTextField.typeKeys(mDATMyProfile.get("TELEPHONE"));
        }

    }

    /**
     * Enter risk number, lets genrate it too randomly.
     */
    public void setNumeroDeRisqueDeSecuriteSociale (Map<String, String>mDATMyProfile){

        ssRiskNumberTextField.shouldBeDisplayed();
        //ssRiskNumberTextField.typeKeys(TestDataEngine.generateRandomNumber(3) + TestDataEngine.generateRandomName(2));
        ssRiskNumberTextField.typeKeys(mDATMyProfile.get("NUMERO_DE_RISQUE"));

    }

    /**
     * if Centralized accident management is Yes, fill in the below details
     *
     */
    public void fillIsCentralizedAccidentMgmt(Map<String, String>mDATMyProfile, boolean bIsCentralAccidentMgmt){

        if(bIsCentralAccidentMgmt){
            ScrollToElementUsingTab(RAD_MP_IS_ADDRESS_DIFFERENT);
            clickOnRadioButtonUsingJavaScript(RAD_MP_IS_ADDRESS_DIFFERENT);
            //isAddressDifferentCB.click();

//            try {
//                Thread.sleep(3000);
//                caPointRemiseTF.typeKeys(mDATMyProfile.get("CA_POINT_REMISE"));
//                Thread.sleep(3000);
//            } catch (Exception e){
//
//                e.printStackTrace();
//            }
            caPointRemiseTF.typeKeys(mDATMyProfile.get("CA_POINT_REMISE"));
            caComplementTF.typeKeys(mDATMyProfile.get("CA_COMPLEMENT"));
            caVoieTF.typeKeys(mDATMyProfile.get("CA_VOIE"));
            caMentionDistbTF.typeKeys(mDATMyProfile.get("CA_MENTION_DISTB"));
            if(mDATMyProfile.get("CA_POSTAL_CODE").matches("^[0-9]{5} [a-zA-Z].*$")) {
                caPostalCodeTF.typeKeys(mDATMyProfile.get("CA_POSTAL_CODE"));
            } else {
                caPostalCodeTF.typeKeys("75011 PARIS");
            }
            caPaysTF.typeKeys(mDATMyProfile.get("CA_PAYS"));
        }
    }



    /**
     *
     * if occupational health service is yes, then fill in the details.
     */
    public void fillOccupationHealthServiceDetails(Map<String, String>mDATMyProfile, boolean bIsOccuationalHealthService){

        if(bIsOccuationalHealthService){

            ScrollToElementUsingTab(RAD_MP_DETAILS_OF_HEALTH_SERVICE);
            clickOnRadioButtonUsingJavaScript(RAD_MP_DETAILS_OF_HEALTH_SERVICE);
//            detailsOfHealthServiceCB.click();
            hsNomTF.typeKeys(mDATMyProfile.get("HS_NOM"));
            hsPointRemiseTF.typeKeys(mDATMyProfile.get("HS_POINT_REMISE"));
            hsComplementTF.typeKeys(mDATMyProfile.get("HS_COMPLEMENT"));
            hsVoieTF.typeKeys(mDATMyProfile.get("HS_VOIE"));
            hsMentionDistbTF.typeKeys(mDATMyProfile.get("HS_MENTION_DISTB"));
            if(mDATMyProfile.get("HS_POSTAL_CODE").matches("^[0-9]{5} [a-zA-Z].*$")) {
                hsPostalCodeTF.typeKeys(mDATMyProfile.get("HS_POSTAL_CODE"));
            } else {
                hsPostalCodeTF.typeKeys("75011 PARIS");
            }
            hsPaysTF.typeKeys(mDATMyProfile.get("HS_PAYS"));
        }
    }


    /**
     *
     * Common method to fill the entire my profile page
     * @param mDATMyProfile
     */
    public void fillMyProfileDetails(Map<String, String>mDATMyProfile, Map<String, String> signatureDetails, boolean bIsCentralizedAccidentMgmt, boolean bOccupationalHealthService) throws Exception{

        // Select the enterprise value
        selectEnterpriseValue(mDATMyProfile, signatureDetails);

        // Enter building number
        setComplement(mDATMyProfile);

        // Enter street address
        setVoie(mDATMyProfile);

        //Enter postal code
        setPostalCode(mDATMyProfile);

        //enter Country
        setPays(mDATMyProfile);

        //Enter SIRET
        setSiret(mDATMyProfile);

        //Enter email address
        setEmailAddress(mDATMyProfile);

        // enter telephone number
        setTelephoneNumber(mDATMyProfile);

        //Enter Risque security social number
        setNumeroDeRisqueDeSecuriteSociale(mDATMyProfile);

        // fill centralized accident mgmt details
        fillIsCentralizedAccidentMgmt(mDATMyProfile, bIsCentralizedAccidentMgmt);

        // fill occupational health service
        fillOccupationHealthServiceDetails(mDATMyProfile, bOccupationalHealthService);
    }




    /* private final CheckBox isAddressDifferentCB;
    private final CheckBox detailsOfHealthServiceCB;*/


}
