package com.simplicia.pages.web.dat.createDAT;

import com.simplicia.pages.web.dat.DATMainPage;

import controls.Icon;
import controls.TextField;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.Map;

public class Accident1Tab extends DATMainPage {

    //###################
    // Locators
    //##################
    // ##### Accident #####
    //Date and hour
    final static String TXT_FIELD_DATE_DE_ACCIDENT = "#date-of-accident";
    final static String TXT_FILED_HEURE_DE_ACCIDENT = "#time-of-accident";

    //Work schedule of the victim on the day of the accident
    final static String TXT_FIELD_DE = "#time-of-start-work1";
    final static String TXT_FIELD_A = "#time-of-end-work1";
    final static String TXT_FIELD_ET_DE = "#time-of-start-work2";
    final static String TXT_FIELD_END_A = "#time-of-end-work2";

    //Lieu de l'accident
    final static String ICON_SELECT_PRECISION="//div[normalize-space()='Non precise']";

    //Name and address of the accident site
    final static String TXT_FIELD_NOM = "input[name='lieuNom']";
    final static String TXT_FIELD_POINT_DE_REMISE = "input[name='lieuAdressePointRemise']";
    final static String TXT_FIELD_COMPLEMENT = "input[name='lieuAdresseComplement']";
    final static String TXT_FIELD_VOIE = "input[name='lieuAdresseRue']";
    final static String TXT_FIELD_MENTION_DISTB = "input[name='lieuAdresseMentionDistribution']";
    final static String TXT_FIELD_POSTAL_CODE = "input[name='lieuAdresseLocalite']";
    final static String TXT_FIELD_PAYS = "input[name='lieuAdresseNomPays']";
    final static String TXT_FIELD_SIRET = "input[name='lieuSiret']";

    //Detailed accident circumstances / Circonstances détaillées accident
    final static String TXT_FIELD_ACTIVITE_DE_LA = "input[name='circonstancesActivite']";
    final static String TXT_FIELD_NATURE_DE_ACCIDENT = "input[name='circonstancesNature']";
    final static String TXT_FIELD_OBJET_DONT_LE_CONTACT = "input[name='circonstancesObjet']";

    // Possible reasoned reservations / Eventuelles réserves motivées
    final static String TXT_FIELD_RESERVES_MOTIVEES = "input[name='circonstancesReserves']";

    //Circonstances détaillées accident /Site and nature of lesions
    final static String TXT_FIELD_SIEGE_DES_LESIONS = "input[name='lesionSiegeDesLesions']";
    final static String TXT_FIELD_NATURE_DES_LESIONS = "input[name='lesionNatureLesion']";

    //=============
    // Selenium Controls
    //=============
    private final TextField dateOfAccidentTF;
    private final TextField hourOfAccidentTF;
    private final TextField deStartWork1TF;
    private final TextField aEndWork1TF;
    private final TextField deStartWork2TF;
    private final TextField aEndWork2TF;
    private final Icon selectPrecisionIcon;
    private final TextField lieuNomTF;
    private final TextField lieuPointRemiseTF;
    private final TextField complementTF;
    private final TextField streetAddressTF;
    private final TextField mentionDistributionTF;
    private final TextField postalCodeTF;
    private final TextField countryTF;
    private final TextField siretTF;
    private final TextField activiteDeLaTF;
    private final TextField natureOfAccidentTF;
    private final TextField objectDontTF;
    private final TextField reserveMotiveesTF;
    private final TextField siegeDesLesionsTF;
    private final TextField natureDesLesionsTF;

    public Accident1Tab(WebDriver browser) {
        super(browser);
        dateOfAccidentTF = new TextField(browser, By.cssSelector(TXT_FIELD_DATE_DE_ACCIDENT), "Enter date of accident field");
        hourOfAccidentTF = new TextField(browser, By.cssSelector(TXT_FILED_HEURE_DE_ACCIDENT), "Hour of Accident field");
        deStartWork1TF = new TextField(browser, By.cssSelector(TXT_FIELD_DE), "Enter start work1 hour field");
        aEndWork1TF = new TextField(browser, By.cssSelector(TXT_FIELD_A), "Enter End work1 hour field");
        deStartWork2TF = new TextField(browser, By.cssSelector(TXT_FIELD_ET_DE), "Enter start work2 hour field");
        aEndWork2TF = new TextField(browser, By.cssSelector(TXT_FIELD_END_A), "Enter End work2 hour field");
        selectPrecisionIcon = new Icon(browser, By.xpath(ICON_SELECT_PRECISION), "Select precision list");
        lieuNomTF = new TextField(browser, By.cssSelector(TXT_FIELD_NOM), "Enter place Nom field");
        lieuPointRemiseTF = new TextField(browser, By.cssSelector(TXT_FIELD_POINT_DE_REMISE), "Enter point of remise field");
        complementTF = new TextField(browser, By.cssSelector(TXT_FIELD_COMPLEMENT), "Enter company building field");
        streetAddressTF = new TextField(browser, By.cssSelector(TXT_FIELD_VOIE), "Street address field");
        mentionDistributionTF = new TextField(browser, By.cssSelector(TXT_FIELD_MENTION_DISTB), "Mention distribution field");
        postalCodeTF = new TextField(browser, By.cssSelector(TXT_FIELD_POSTAL_CODE), "postal code field");
        countryTF = new TextField(browser, By.cssSelector(TXT_FIELD_PAYS), "Country field");
        siretTF = new TextField(browser, By.cssSelector(TXT_FIELD_SIRET), "SIRET field");
        activiteDeLaTF = new TextField(browser, By.cssSelector(TXT_FIELD_ACTIVITE_DE_LA), "Activity of the victim during the accident");
        natureOfAccidentTF = new TextField(browser, By.cssSelector(TXT_FIELD_NATURE_DE_ACCIDENT), "Nature of the accident field");
        objectDontTF = new TextField(browser, By.cssSelector(TXT_FIELD_OBJET_DONT_LE_CONTACT), "Object whose contact injured the victim field");
        reserveMotiveesTF = new TextField(browser, By.cssSelector(TXT_FIELD_RESERVES_MOTIVEES), "Possible justified reservations");
        siegeDesLesionsTF = new TextField(browser, By.cssSelector(TXT_FIELD_SIEGE_DES_LESIONS), "Reasoned reservations");
        natureDesLesionsTF = new TextField(browser, By.cssSelector(TXT_FIELD_NATURE_DES_LESIONS), "Nature of lesions field");
    }

    public void fillAccidentTab1Details(Map<String, String> mAccident1Details) {

        // Enter Date et Heure/ date and time
       dateOfAccidentTF.typeKeys(mAccident1Details.get("TXT_FIELD_DATE_DE_ACCIDENT"));
       hourOfAccidentTF.typeKeys(mAccident1Details.get("TXT_FILED_HEURE_DE_ACCIDENT"));

       // Enter Horaire de travail de la victime le jour de l'accident
       deStartWork1TF.typeKeys(mAccident1Details.get("TXT_FIELD_DE"));
       aEndWork1TF.typeKeys(mAccident1Details.get("TXT_FIELD_A"));
       deStartWork2TF.typeKeys(mAccident1Details.get("TXT_FIELD_ET_DE"));
       aEndWork2TF.typeKeys(mAccident1Details.get("TXT_FIELD_END_A"));

       // enter Lieu de l'accident
        downKey(By.xpath(ICON_SELECT_PRECISION));

       selectItemFromDropDownList(mAccident1Details.get("ICON_SELECT_PRECISION"));
       lieuNomTF.typeKeys(mAccident1Details.get("TXT_FIELD_NOM"));
       lieuPointRemiseTF.typeKeys(mAccident1Details.get("TXT_FIELD_POINT_DE_REMISE"));
       complementTF.typeKeys(mAccident1Details.get("TXT_FIELD_COMPLEMENT"));
       streetAddressTF.typeKeys(mAccident1Details.get("TXT_FIELD_VOIE"));
       mentionDistributionTF.typeKeys(mAccident1Details.get("TXT_FIELD_MENTION_DISTB"));
       postalCodeTF.typeKeys(mAccident1Details.get("TXT_FIELD_POSTAL_CODE"));
       countryTF.typeKeys(mAccident1Details.get("TXT_FIELD_PAYS"));

       // enter  SIRET
        try {
            siretTF.typeKeys(selectAllAndDeleteKeys());
            Thread.sleep(1000);
        }catch (Exception e){
            e.printStackTrace();
        }
        siretTF.typeKeys(mAccident1Details.get("TXT_FIELD_SIRET"));

       // enter Circonstances détaillées accident
       activiteDeLaTF.typeKeys(mAccident1Details.get("TXT_FIELD_ACTIVITE_DE_LA"));
       natureOfAccidentTF.typeKeys(mAccident1Details.get("TXT_FIELD_NATURE_DE_ACCIDENT"));
       objectDontTF.typeKeys(mAccident1Details.get("TXT_FIELD_OBJET_DONT_LE_CONTACT"));

       //enter Eventuelles réserves motivées
       reserveMotiveesTF.typeKeys(mAccident1Details.get("TXT_FIELD_RESERVES_MOTIVEES"));

       // enter Siège et nature des lésions
       siegeDesLesionsTF.typeKeys(mAccident1Details.get("TXT_FIELD_SIEGE_DES_LESIONS"));
       natureDesLesionsTF.typeKeys(mAccident1Details.get("TXT_FIELD_NATURE_DES_LESIONS"));


    }
}
