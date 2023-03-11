package com.simplicia.pages.web.dat.createDAT;

import com.simplicia.functions.TestDataEngine;
import com.simplicia.functions.Utility;
import com.simplicia.pages.web.dat.DATMainPage;
import controls.CheckBox;
import controls.Icon;
import controls.TextField;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import util.CustomWebDriverWait;

import java.util.Arrays;
import java.util.Map;

public class LaVictimeTab extends DATMainPage {


    //###################
    // Locators
    //##################

    //  ##### The Victim - TV #####
    final static String TXT_FIELD_TV_REGISTRATION = "//input[@name='victimeNir']";
    final static String ICON_SELECT_TV_SEX = "//input[@name='victimeNir']/following::div[@role='button'][1]";
    final static String TXT_FIELD_TV_DATE_OF_BIRTH = "input[name='victimeDateDeNaissance']";
    final static String TXT_FIELD_TV_LAST_NAME = "input[name='victimeNom']";
    final static String TXT_FIELD_TV_FIRST_NAME = "input[name='victimePrenom']";
    final static String TXT_FIELD_TV_POINT_OF_DELIVERY = "input[name='victimeAdressePointRemise']";
    final static String TXT_FIELD_TV_BUILDING = "input[name='victimeAdresseComplement']";
    final static String TXT_FIELD_TV_STREET = "input[name='victimeAdresseRue']";
    final static String TXT_FIELD_TV_DISTRIBUTION_STMT = "input[name='victimeAdresseMentionDistribution']";
    final static String TXT_FIELD_TV_POSTAL_CODE = "input[name='victimeAdresseLocalite']";
    final static String TXT_FIELD_TV_COUNTRY = "//input[@name='victimeAdresseNomPays']";
    final static String TXT_FIELD_TV_DATE_HIRE = "//input[@id='date-of-hire']";
    final static String ICON_SELECT_TV_NATIONALIE = TXT_FIELD_TV_DATE_HIRE + "/preceding::div[1]"; //text()='Non precise']";
    final static String ICON_SELECT_TV_PROFESSION_CAT = "//label[normalize-space()='Categorie de profession...*']/following::div[2]"; //TXT_FIELD_TV_DATE_HIRE + "/following::div[4]";
    final static String ICON_SELECT_TV_PROFESSION = "//div[normalize-space()='Profession Précise...']";
    final static String TXT_FIELD_TV_NAME_IN_COMPANY = "//input[@name='victimeProfessionTexteLibre']";
    final static String ICON_SELECT_TV_QUALIFICATION_PROF = TXT_FIELD_TV_NAME_IN_COMPANY + "/following::div[@role='button'][1]";
    //final static String ICON_SELECT_TV_QUALIFICATION_PROF = "//input[@id='victimeQualificationProfessionnelle']";
    final static String ICON_SELECT_TV_SENIORITY_IN_POS = "//label[normalize-space()='Ancienneté dans le poste *']/following::div[1]";
    final static String ICON_SELECT_TV_NATURE_OF_CONTRACT = "//label[normalize-space()='Nature du contrat *']/following::div[2]";
    //final static String ICON_SELECT_TV_NATURE_OF_CONTRACT = "//div[normalize-space()='Non connue']";
    final static String CHK_TV_OTHER_VICTIM = "//input[@name='circonstancesAutresVictimes']";

    // if Interimaire / Temporary worker option is selected for Nature du Contrat then fill below fields.
    // ENTREPRISE UTILISATRICE
    final static String TXT_FIELD_EU_NOM = "input[name='entrepriseUtilisatriceNom']";
    final static String TXT_FIELD_EU_SIRET = "input[name='entrepriseUtilisatriceSiret']";
    final static String TXT_FIELD_EU_EMAIL = "input[name='entrepriseUtilisatriceMail']";
    final static String TXT_FIELD_EU_TELPHONE = "input[name='entrepriseUtilisatriceTel']";
    final static String TXT_FILED_EU_NUMERO_SECURITE_SOCIALE = "input[name='entrepriseUtilisatriceRisquess']";
    final static String TXT_FIELD_EU_POINT_DE_REMISE = "input[name='entrepriseUtilisatriceAdressePointRemise']";
    final static String TXT_FIELD_EU_COMPLEMENT = "input[name='entrepriseUtilisatriceAdresseComplement']";
    final static String TXT_FIELD_EU_VOIE = "input[name='entrepriseUtilisatriceAdresseRue']";
    final static String TXT_FIELD_EU_MENTION_DE_DISTB = "input[name='entrepriseUtilisatriceAdresseMentionDistribution']";
    final static String TXT_FIELD_EU_POSTAL_CODE = "input[name='entrepriseUtilisatriceAdresseLocalite']";
    final static String TXT_FILED_EU_PAYS = "input[name='entrepriseUtilisatriceAdresseNomPays']";


    //=============
    // Selenium Controls
    //=============
    private final TextField resigtrationTF;
    private final Icon selectSexIcon;
    private final TextField dateOfBirthTF;
    private final TextField victimeNomTF;
    private final TextField victimePreNomTF;
    private final TextField victimePointOfDeliveryTF;
    private final TextField victimeBuildingAddressTF;
    private final TextField victimeAddressStreetTF;
    private final TextField mentionDistributionTF;
    private final TextField victimePostalCodeTF;
    private final TextField victineCountryTF;
    private final TextField victimeDateOfHireTF;
    private final Icon selectNationalityIcon;
    private final Icon selectProfessionalCatogryIcon;
    private final Icon selectProfessionIcon;
    private final TextField nameInYourComapnyTF;
    private final Icon selectQualificationProfessionIcon;
    private final Icon selectSeniorityInPositionIcon;
    private final Icon selectNatureOfContractIcon;
    private final CheckBox otherVictimCB;
    private final TextField euNomTF;
    private final TextField euSiretTF;
    private final TextField euEmailTF;
    private final TextField euTelephoneTF;
    private final TextField euNumeroSecuriteSocialeTF;
    private final TextField euPointDeRemiseTF;
    private final TextField euComplementTF;
    private final TextField euVoieTF;
    private final TextField euMentionDEDistbTF;
    private final TextField euPostalCodeTF;
    private final TextField euPaysTF;

    public LaVictimeTab(WebDriver browser) {
        super(browser);

        resigtrationTF = new TextField(browser, By.xpath(TXT_FIELD_TV_REGISTRATION), "Enter registration text field");
        selectSexIcon = new Icon(browser, By.xpath(ICON_SELECT_TV_SEX), "select victim sex List");
        dateOfBirthTF = new TextField(browser, By.cssSelector(TXT_FIELD_TV_DATE_OF_BIRTH), "Enter date of birth");
        victimeNomTF = new TextField(browser, By.cssSelector(TXT_FIELD_TV_LAST_NAME), "Victime Nom field");
        victimePreNomTF = new TextField(browser, By.cssSelector(TXT_FIELD_TV_FIRST_NAME), "Victime prenom field");
        victimePointOfDeliveryTF = new TextField(browser, By.cssSelector(TXT_FIELD_TV_POINT_OF_DELIVERY), "Point of delivery field");
        victimeBuildingAddressTF = new TextField(browser, By.cssSelector(TXT_FIELD_TV_BUILDING), "Victime building address field");
        victimeAddressStreetTF = new TextField(browser, By.cssSelector(TXT_FIELD_TV_STREET), "Victime street address field");
        mentionDistributionTF = new TextField(browser, By.cssSelector(TXT_FIELD_TV_DISTRIBUTION_STMT), "Victime address mention distribution field");
        victimePostalCodeTF = new TextField(browser, By.cssSelector(TXT_FIELD_TV_POSTAL_CODE), "Victime postal code field");
        victineCountryTF = new TextField(browser, By.xpath(TXT_FIELD_TV_COUNTRY), "Victime country field");
        victimeDateOfHireTF = new TextField(browser, By.xpath(TXT_FIELD_TV_DATE_HIRE), "Victime date of hire field");
        selectNationalityIcon = new Icon(browser, By.xpath(ICON_SELECT_TV_NATIONALIE), "select nationality list");
        selectProfessionalCatogryIcon = new Icon(browser, By.xpath(ICON_SELECT_TV_PROFESSION_CAT), "Select professional category");
        selectProfessionIcon = new Icon(browser, By.xpath(ICON_SELECT_TV_PROFESSION), "Select profession list");
        nameInYourComapnyTF = new TextField(browser, By.xpath(TXT_FIELD_TV_NAME_IN_COMPANY), "victime Profession");
        selectQualificationProfessionIcon = new Icon(browser, By.xpath(ICON_SELECT_TV_QUALIFICATION_PROF), "Select qualified profession");
        selectSeniorityInPositionIcon = new Icon(browser, By.xpath(ICON_SELECT_TV_SENIORITY_IN_POS), "Select seniority in position");
        selectNatureOfContractIcon = new Icon(browser, By.xpath(ICON_SELECT_TV_NATURE_OF_CONTRACT), "Select nature of contract");
        otherVictimCB = new CheckBox(browser, By.cssSelector(CHK_TV_OTHER_VICTIM), " other victime checkbox");
        euNomTF = new TextField(browser, By.cssSelector(TXT_FIELD_EU_NOM), "Enter Nom");
        euSiretTF = new TextField(browser, By.cssSelector(TXT_FIELD_EU_SIRET), "Enter Siret");
        euEmailTF = new TextField(browser, By.cssSelector(TXT_FIELD_EU_EMAIL), "Enter email");
        euTelephoneTF = new TextField(browser, By.cssSelector(TXT_FIELD_EU_TELPHONE), "Enter telephone");
        euNumeroSecuriteSocialeTF = new TextField(browser, By.cssSelector(TXT_FILED_EU_NUMERO_SECURITE_SOCIALE), "Enter numero securite sociale");
        euPointDeRemiseTF = new TextField(browser, By.cssSelector(TXT_FIELD_EU_POINT_DE_REMISE), "Enter point de remise");
        euComplementTF = new TextField(browser, By.cssSelector(TXT_FIELD_EU_COMPLEMENT), "Enter complement");
        euVoieTF = new TextField(browser, By.cssSelector(TXT_FIELD_EU_VOIE), "Enter voie");
        euMentionDEDistbTF = new TextField(browser, By.cssSelector(TXT_FIELD_EU_MENTION_DE_DISTB), "Enter mention de distribution");
        euPostalCodeTF = new TextField(browser, By.cssSelector(TXT_FIELD_EU_POSTAL_CODE), "Enter postal code");
        euPaysTF = new TextField(browser, By.cssSelector(TXT_FILED_EU_PAYS), "Enter pays");

    }

    /**
     * this method will fill the La Victime details
     */
    public String fillLaVictimeDetails(Map<String, String> mLAVictimeDetails) throws Exception{

        // Enter N° d’Immatriculation / Registration number, this number should begin with 6 digit then
        // a char and then 6 or 8 digit.
        // Lets create it randomly
        String sRegNumber = fillRegistrationNumber(mLAVictimeDetails);

        //Select gender
        String gender = mLAVictimeDetails.get("ICON_SELECT_TV_SEX");
        String sGender;
        if (Arrays.asList("Masculin", "Féminin").contains(gender)) {
            sGender = gender;
        } else {
            sGender= Utility.getRandomItemFromList(Arrays.asList("Masculin", "Féminin"));
        }
        mLAVictimeDetails.put("ICON_SELECT_TV_SEX", sGender);
        selectSexIcon.click();
        selectItemFromDropDownList(sGender);

        //enter date of birth
        //SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
        //SimpleDateFormat sdf2 = new SimpleDateFormat("MM/dd/yyyy");
        //Date birthday = sdf1.parse(mLAVictimeDetails.get("TXT_FIELD_TV_DATE_OF_BIRTH"));
        dateOfBirthTF.shouldBeDisplayed();
        String dateOfBirth = mLAVictimeDetails.get("TXT_FIELD_TV_DATE_OF_BIRTH");
        println("filling TXT_FIELD_TV_DATE_OF_BIRTH with dateOfBirth: " + dateOfBirth);
        dateOfBirthTF.typeKeys(dateOfBirth);

        //enter the Nom/last name of the victime
        victimeNomTF.shouldBeDisplayed();
        victimeNomTF.typeKeys(mLAVictimeDetails.get("TXT_FIELD_TV_LAST_NAME"));

        // Enter the PreNom/ first name of the victime
        victimePreNomTF.shouldBeDisplayed();
        victimePreNomTF.typeKeys(mLAVictimeDetails.get("TXT_FIELD_TV_FIRST_NAME"));

        // locality and the precise place of the accident
        victimePointOfDeliveryTF.shouldBeDisplayed();
        victimePointOfDeliveryTF.typeKeys(mLAVictimeDetails.get("TXT_FIELD_TV_POINT_OF_DELIVERY"));

        // enter address
        victimeBuildingAddressTF.shouldBeDisplayed();
        victimeBuildingAddressTF.typeKeys(mLAVictimeDetails.get("TXT_FIELD_TV_BUILDING"));
        victimeAddressStreetTF.shouldBeDisplayed();
        victimeAddressStreetTF.typeKeys(mLAVictimeDetails.get("TXT_FIELD_TV_STREET"));

        // Mention de distribution (lieu dit, BP, etc.)
        mentionDistributionTF.shouldBeDisplayed();
        mentionDistributionTF.typeKeys(mLAVictimeDetails.get("TXT_FIELD_TV_DISTRIBUTION_STMT"));

        // Enter Code Postal
        victimePostalCodeTF.shouldBeDisplayed();
        victimePostalCodeTF.typeKeys(mLAVictimeDetails.get("TXT_FIELD_TV_POSTAL_CODE"));

        //enter pays and select nationalie
        victineCountryTF.shouldBeDisplayed();
        victineCountryTF.typeKeys(mLAVictimeDetails.get("TXT_FIELD_TV_COUNTRY"));
        downKey(By.xpath(ICON_SELECT_TV_NATIONALIE));
        selectItemFromDropDownList(mLAVictimeDetails.get("ICON_SELECT_TV_NATIONALIE"));

        // Enter date of hire
        victimeDateOfHireTF.shouldBeDisplayed();
        victimeDateOfHireTF.typeKeys(mLAVictimeDetails.get("TXT_FIELD_TV_DATE_HIRE"));

        // select Categorie de proferssion and profession

        // JavascriptExecutor js = (JavascriptExecutor) browser;
        //  js.executeScript("arguments[0].scrollIntoView(true);"); //this.findElement(By.xpath(ICON_SELECT_TV_PROFESSION_CAT)));
        /**
         // Scroll Down using Actions class
         Actions actions = new Actions(browser);
         for(int i = 0; i<2; i++) {
         actions.keyDown(Keys.CONTROL).sendKeys(Keys.TAB).build().perform();
         }
         */
//        ScrollToElementUsingTab(ICON_SELECT_TV_PROFESSION_CAT);
        selectProfessionalCatogryIcon.click();
        selectItemFromDropDownList(mLAVictimeDetails.get("ICON_SELECT_TV_PROFESSION_CAT"));
        selectProfessionIcon.click();
        if(mLAVictimeDetails.get("ICON_SELECT_TV_PROFESSION").equals("Métiers de lélectricité et de lélectrotechnique"))
            mLAVictimeDetails.put("ICON_SELECT_TV_PROFESSION", "Métiers de l'électricité et de l'électrotechnique");
        selectItemFromDropDownList(mLAVictimeDetails.get("ICON_SELECT_TV_PROFESSION"));

        // Précisez la profession si nécessaire (dénomination dans votre entreprise)
        // Specify the profession if necessary (name in your company)
        nameInYourComapnyTF.typeKeys(mLAVictimeDetails.get("TXT_FIELD_TV_NAME_IN_COMPANY"));

        // Select Qualification professionnelle *
        ScrollToElementUsingTab(ICON_SELECT_TV_QUALIFICATION_PROF);
        selectQualificationProfessionIcon.click();
        String sQualification = mLAVictimeDetails.get("ICON_SELECT_TV_QUALIFICATION_PROF");
        if(sQualification==null) {
            selectItemFromDropDownList("Divers");
        }else {
            selectItemFromDropDownList(mLAVictimeDetails.get("ICON_SELECT_TV_QUALIFICATION_PROF"));
        }

        // Ancienneté dans le poste * / select Seniority in the position
        selectSeniorityInPositionIcon.click();
        selectItemFromDropDownList(mLAVictimeDetails.get("ICON_SELECT_TV_SENIORITY_IN_POS"));

        // Select Nature du contrat *
        ScrollToElementUsingTab(ICON_SELECT_TV_NATURE_OF_CONTRACT);
        selectNatureOfContractIcon.click();
        selectItemFromDropDownList(mLAVictimeDetails.get("ICON_SELECT_TV_NATURE_OF_CONTRACT"));

        // if nature du contrat is interimaire then fill in the displayed details
        if (mLAVictimeDetails.get("ICON_SELECT_TV_NATURE_OF_CONTRACT").equals("Intérimaire")) {

            //ScrollToElementUsingTab(TXT_FIELD_EU_NOM);
            euNomTF.typeKeys(mLAVictimeDetails.get("EU_NOM"));
            if(mLAVictimeDetails.get("EU_SIRET").matches("[a-zA-Z]{6}")) {
                mLAVictimeDetails.put("EU_SIRET", "8" + TestDataEngine.generateRandomNumber(13));

            } else {
               // do nothing
            }
            euSiretTF.typeKeys(mLAVictimeDetails.get("EU_SIRET"));
            euEmailTF.typeKeys(mLAVictimeDetails.get("EU_EMAIL"));
            euTelephoneTF.typeKeys(mLAVictimeDetails.get("EU_TELEPHONE"));
            euNumeroSecuriteSocialeTF.typeKeys(mLAVictimeDetails.get("EU_NUMÉRO_DE_RISQUE_DE_SÉCURITÉ_SOCIALE"));
            euPointDeRemiseTF.typeKeys(mLAVictimeDetails.get("EU_POINT_DE_REMISE"));
            euComplementTF.typeKeys(mLAVictimeDetails.get("EU_COMPLEMENT"));
            euVoieTF.typeKeys(mLAVictimeDetails.get("EU_VOIE"));
            euMentionDEDistbTF.typeKeys(mLAVictimeDetails.get("EU_MENTIONE_DE_DISB"));
            if (mLAVictimeDetails.get("EU_POSTAL_CODE").matches("^[0-9]{5} [a-zA-Z].*$")) {
                euPostalCodeTF.typeKeys(mLAVictimeDetails.get("EU_POSTAL_CODE"));
            } else {
                euPostalCodeTF.typeKeys("75001 PARIS");
            }

            euPaysTF.typeKeys(mLAVictimeDetails.get("EU_PAYS"));

        }

        // return Numero SS
        return sRegNumber;

    }

    /**
     * Registration number
     *
     * @param mLAVictimeDetails
     * @return
     */

    public String fillRegistrationNumber(Map<String, String> mLAVictimeDetails) throws Exception{
        String sRegNumber = "";
        Thread.sleep(5000);
        sRegNumber = mLAVictimeDetails.get("TXT_FIELD_TV_REGISTRATION");
        if(sRegNumber.matches("[a-zA-Z]{6}")) {
            sRegNumber = "8" + TestDataEngine.generateRandomNumber(5) + /*TestDataEngine.generateRandomName(1) +*/ TestDataEngine.generateRandomNumber(9);
            mLAVictimeDetails.put("TXT_FIELD_TV_REGISTRATION", sRegNumber);
            println("sRegNumber is random to: " + sRegNumber);
        }

        new CustomWebDriverWait(browser, 5).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(TXT_FIELD_TV_REGISTRATION)));
        resigtrationTF.shouldBeDisplayed();
        resigtrationTF.typeKeys(selectAllAndDeleteKeys());
        Thread.sleep(1000);
        println("Filling " + TXT_FIELD_TV_REGISTRATION + " with " + sRegNumber);
        resigtrationTF.typeKeys(sRegNumber);
        Thread.sleep(1000);
        return sRegNumber;
    }

}
