package com.simplicia.pages.web.dat.createDAT;

import com.simplicia.pages.web.dat.DATMainPage;
import controls.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.text.ParseException;
import java.util.Map;

public class Accident2Tab extends DATMainPage {

    //###################
    // Locators
    //##################
    //##### Accident page 2 ########

    //Description de l'accident
    final static String RAD_LA_VICTIME_NON = "//legend[text()='La victime a-t-elle été transportée ?']/following::input[@name='transportVictimeTransportee' and @value='false']";
    final static String RAD_LA_VICTIME_OUI = "//legend[text()='La victime a-t-elle été transportée ?']/following::input[@name='transportVictimeTransportee' and @value='true']";

    // if victime transportee yes
    final static String VICTIME_TRANSPORTEE_A = "input[name='transportVictimeTransporteeA']";
    final static String VICTIME_TRANSPORTEE_POINT_DE_REMISE = "input[name='transportAdressePointRemise']";
    final static String VICTIME_TRANSPORTEE_COMPLEMENT = "input[name='transportAdresseComplement']";
    final static String VICTIME_TRANSPORTEE_VOIE = "input[name='transportAdresseRue']";
    final static String VICTIME_TRANSPORTEE_MENTION_DE_DISTB = "input[name='transportAdresseMentionDistribution']";
    final static String VICTIME_TRANSPORTEE_POSTAL_CODE = "input[name='transportAdresseLocalite']";
    final static String VICTIME_TRANSPORTEE_PAYS = "input[name='transportAdresseNomPays']";

    //Constatation
    final static String RAD_CONSTATE = "//input[@name ='constatatAccident' and @value=1]";
    final static String RAD_CONNU = "//input[@name ='constatatAccident' and @value=2]";
    final static String ICON_SELECT_TYPE = "//label[normalize-space()='Type *']/following::div[2]";
    final static String ICON_SELECT_PAR = "//label[normalize-space()='Par *']/following::div[2]";
    final static String CHK_DECRIT_PAR_LA_VICTIM = "//span[normalize-space()='Décrit par la victime']/preceding::input[@type='checkbox'][1]";
    final static String TXT_FIELD_DATE_OF_CONSTAT = "#date-of-constat";
    final static String TXT_FIELD_TIME_OF_CONSTAT = "#time-of-constat";

    final static String TOGGLE_DATE_OF_CONSTAT_REGISTER = "//span[normalize-space()='Inscrit au registre d’accidents du travail bénins']//preceding-sibling::span/span";
    final static String LBL_DATE_OF_CONSTAT_REGISTER = "//label[contains(normalize-space(), 'inscription au registre d’accidents du travail bénins')]";
    final static String TXT_FIELD_DATE_OF_CONSTAT_REGISTER = "//label[contains(normalize-space(), 'inscription au registre d’accidents du travail bénins')]/following::input[@type='text'][1]";
    // Date d'inscription au registre d’accidents du travail bénins

    final static String RAD_CONSTAT_SANS_ARRET_DE_TRAVAIL = "//input[@name ='constatatConsequences' and @value=1]";
    final static String RAD_CONSTAT_AVEC_ARRET_DE_TRAVAIL = "//input[@name ='constatatConsequences' and @value=2]";
    final static String CHK_DECES= "//span[normalize-space()='Décès']/preceding::input[@type='checkbox'][1]";
    final static String TXT_FIELD_SOUS_LE_NUMERO = "input[name='constatatAccidentInscritAuRegistreDuTravail']";
    final static String TXT_FIELD_IDENTIFIANT = "input[name='constatatIdentifiant']";
    final static String TXT_FIELD_RAPPORT_DE_POLICE_A_ETE_ETABLI_PAR = "//input[@value='true'and @name='rapportDePolice']//ancestor::label//parent::div//following-sibling::div//input";
    final static String RAD_RAPPORT_DE_POLICE = "//input[@value='true'and @name='rapportDePolice']";

    //=============
    // Selenium Controls
    //=============
    private final RadioButton wasTheVictimTransPortedNoRB;
    private final RadioButton wasTheVictimTransPortedYesRB;
    private final RadioButton constatatAccidentConstateRB;
    private final RadioButton constatatAccidentConnuRB;
    private final Icon selectTypeIcon;
    private final Icon selectParIcon;
    private final CheckBox parLaVictimCB;
    private final TextField dateOfConstateTF;
    private final TextField timeOfConstateTF;

    private final TextField dateOfConstateRegister;
    private final ControlledElement dateOfConstateRegisterToggle;

    private final RadioButton constateSansArretDeTravailRB;
    private final RadioButton constateAvecArretDeTravailRB;
    private final CheckBox decesCB;
    private final TextField sousLeNumeroTF;
    private final TextField identifiantTF;
    private final RadioButton rapportDePoliceRB;
    private final TextField rapportDePoliceTF;
    private final TextField victimeTransporteeATF;
    private final TextField victimeTransporteePointDeRemiseTF;
    private final TextField victimeTransporteeComplementTF;
    private final TextField victimeTransporteeVoieTF;
    private final TextField victimeTransporteeMentionDeDistTF;
    private final TextField victimeTransporteepostalCodeTF;
    private final TextField victimeTransporteePaysTF;

    public Accident2Tab(WebDriver browser) {
        super(browser);
        wasTheVictimTransPortedNoRB =  new RadioButton(browser, By.xpath(RAD_LA_VICTIME_NON), "Was the victim transported No radio");
        wasTheVictimTransPortedYesRB =  new RadioButton(browser, By.xpath(RAD_LA_VICTIME_OUI), "Was the victim transported Yes radio");
        constatatAccidentConstateRB =  new RadioButton(browser, By.xpath(RAD_CONSTATE), "Constate accident constate radio");
        constatatAccidentConnuRB =  new RadioButton(browser, By.xpath(RAD_CONNU), "constate accident connu radio");
        selectTypeIcon = new Icon(browser, By.xpath(ICON_SELECT_TYPE), "select Type list");
        selectParIcon = new Icon(browser, By.xpath(ICON_SELECT_PAR), "select par list");
        parLaVictimCB = new CheckBox(browser, By.xpath(CHK_DECRIT_PAR_LA_VICTIM), "Par la victim checkbox");
        dateOfConstateTF = new TextField(browser, By.cssSelector(TXT_FIELD_DATE_OF_CONSTAT), "Date of constate field");
        timeOfConstateTF = new TextField(browser, By.cssSelector(TXT_FIELD_TIME_OF_CONSTAT), "Time of constate field");

        dateOfConstateRegisterToggle = new ControlledElement(browser, By.xpath(TOGGLE_DATE_OF_CONSTAT_REGISTER));
        dateOfConstateRegister = new TextField(browser, By.xpath(TXT_FIELD_DATE_OF_CONSTAT_REGISTER), "Date d'inscription au registre d’accidents du travail bénins");

        constateSansArretDeTravailRB = new RadioButton(browser, By.xpath(RAD_CONSTAT_SANS_ARRET_DE_TRAVAIL));
        constateAvecArretDeTravailRB = new RadioButton(browser, By.xpath(RAD_CONSTAT_AVEC_ARRET_DE_TRAVAIL));
        decesCB = new CheckBox(browser, By.xpath(CHK_DECES));
        sousLeNumeroTF = new TextField(browser, By.cssSelector(TXT_FIELD_SOUS_LE_NUMERO), "Souc Le Numero field");
        identifiantTF = new TextField(browser, By.cssSelector(TXT_FIELD_IDENTIFIANT), "Constate identifiant field");
        rapportDePoliceRB = new RadioButton(browser, By.cssSelector(RAD_RAPPORT_DE_POLICE));
        rapportDePoliceTF = new TextField(browser, By.xpath(TXT_FIELD_RAPPORT_DE_POLICE_A_ETE_ETABLI_PAR), "Rapport De police field");
        victimeTransporteeATF = new TextField(browser, By.cssSelector(VICTIME_TRANSPORTEE_A), "Victime transportee A");
        victimeTransporteePointDeRemiseTF = new TextField(browser, By.cssSelector(VICTIME_TRANSPORTEE_POINT_DE_REMISE), "Victime transportee point de remise");
        victimeTransporteeComplementTF = new TextField(browser, By.cssSelector(VICTIME_TRANSPORTEE_COMPLEMENT), "Complement");
        victimeTransporteeVoieTF = new TextField(browser, By.cssSelector(VICTIME_TRANSPORTEE_VOIE), "Street address");
        victimeTransporteeMentionDeDistTF = new TextField(browser, By.cssSelector(VICTIME_TRANSPORTEE_MENTION_DE_DISTB), "Mention de distribution");
        victimeTransporteepostalCodeTF = new TextField(browser, By.cssSelector(VICTIME_TRANSPORTEE_POSTAL_CODE), "postal code");
        victimeTransporteePaysTF = new TextField(browser, By.cssSelector(VICTIME_TRANSPORTEE_PAYS), "country");

    }

    /**
     * Method to decide if need to enter Description de l'accident or not
     * @param b
     */
    public void descriptionDeAccident(Map<String, String> mAccident2Details, boolean b){

        if(b){

            // click on Oiu
            //ScrollToElementUsingTab(RAD_LA_VICTIME_OUI);
            clickCloseMessage();
            clickOnRadioButtonUsingJavaScript(RAD_LA_VICTIME_OUI);
            victimeTransporteeATF.typeKeys(mAccident2Details.get("VICTIME_TRANSPORTEE_A"));
            victimeTransporteePointDeRemiseTF.typeKeys(mAccident2Details.get("VICTIME_TRANSPORTEE_POINT_DE_REMISE"));
            victimeTransporteeComplementTF.typeKeys(mAccident2Details.get("VICTIME_TRANSPORTEE_COMPLEMENT"));
            victimeTransporteeVoieTF.typeKeys(mAccident2Details.get("VICTIME_TRANSPORTEE_VOIE"));
            victimeTransporteeMentionDeDistTF.typeKeys(mAccident2Details.get("VICTIME_TRANSPORTEE_MENTION_DE_DISTB"));
            if (mAccident2Details.get("VICTIME_TRANSPORTEE_POSTAL_CODE").matches("^[0-9]{5} [a-zA-Z].*$")) {
                victimeTransporteepostalCodeTF.typeKeys(mAccident2Details.get("VICTIME_TRANSPORTEE_POSTAL_CODE"));
            } else {
                victimeTransporteepostalCodeTF.typeKeys("75001 PARIS");
            }
            victimeTransporteePaysTF.typeKeys(mAccident2Details.get("VICTIME_TRANSPORTEE_PAYS"));


        } /*else {

            wasTheVictimTransPortedNoRB.click();
        }
*/
    }

    public void fillAccident2TabDetails(Map<String, String> mAccident2Details) throws InterruptedException, ParseException {

        // click yes/No for if victim was transported or not
        descriptionDeAccident(mAccident2Details, true);

        //select accident type
        downKey(By.xpath(ICON_SELECT_TYPE));
        selectItemFromDropDownList(mAccident2Details.get("ICON_SELECT_TYPE"));

        //select par
        downKey(By.xpath(ICON_SELECT_PAR));
        selectItemFromDropDownList(mAccident2Details.get("ICON_SELECT_PAR"));

       //Enter date and time for constat
        /*if(mAccident2Details.get("TXT_FIELD_DATE_OF_CONSTAT").matches("[0-9]{4}-[0-9]{2}-[0-9]{2}")){
            String date = mAccident2Details.get("TXT_FIELD_DATE_OF_CONSTAT");
            DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
            DateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yyyy");
            Date dateOfConstat = dateFormat1.parse(date);
            String strDateOFContat = dateFormat2.format(dateOfConstat);
            dateOfConstateTF.typeKeys(strDateOFContat);

        } else {
            DateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yyyy");
            dateOfConstateTF.typeKeys(dateFormat2.format(new Date()));
        }*/
        dateOfConstateTF.click();
        dateOfConstateTF.typeKeys(mAccident2Details.get("TXT_FIELD_DATE_OF_CONSTAT"));
        timeOfConstateTF.click();
        timeOfConstateTF.typeKeys(mAccident2Details.get("TXT_FIELD_TIME_OF_CONSTAT"));
        
        clickOnRadioButtonUsingJavaScript(TOGGLE_DATE_OF_CONSTAT_REGISTER);
        
        clickHiddenElementUsingJavaScript(LBL_DATE_OF_CONSTAT_REGISTER);
        dateOfConstateRegister.typeKeys(mAccident2Details.get("TXT_FIELD_DATE_OF_CONSTAT_REGISTER"));

        //enter Inscrit au registre d’accidents du travail bénins
        //sousLeNumeroTF.typeKeys(mAccident2Details.get("TXT_FIELD_SOUS_LE_NUMERO"));

        //enter Identifiant
       // identifiantTF.typeKeys(mAccident2Details.get("TXT_FIELD_IDENTIFIANT"));

        // enter Rapport de police a été établi par
        clickOnRadioButtonUsingJavaScript(RAD_RAPPORT_DE_POLICE);
        rapportDePoliceTF.typeKeys(mAccident2Details.get("TXT_FIELD_RAPPORT_DE_POLICE_A_ETE_ETABLI_PAR"));

    }
}
