package com.simplicia.pages.web.atmp;

import com.simplicia.functions.Utility;
import com.simplicia.pages.web.SimpliciaPage;
import controls.*;
import exception.MSeleniumException;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.text.MessageFormat;
import java.util.List;

public class ATMPPage extends SimpliciaPage
{
    private static final org.apache.log4j.Logger LOGGER = Logger.getLogger(ATMPPage.class);

    //==========
    // Locator =
    //==========
//    final static String TXT_FIELD_SEARCH_BOX = "input[type=text][aria-label='Chercher']";
	final static String TXT_FIELD_SEARCH_BOX = "input[type=text][aria-label='Rechercher']";	
//	final static String BTN_EXPORT_TABLE = "button[title='Export Table in CSV Format']";
	final static String BTN_EXPORT_TABLE = "button[title='Exporter le tableau au format CSV']";
    final static String TABLE_FILTERS = "//p[text()=''Filtres: '']/parent::div/button/span[text()=''{0}'']";

    final static String CHK_PLUS_DE_DETAILS = "//span[text()='Plus de détails:']/../..//input";
    final static String CHKD_PLUS_DE_DETAILS = "//span[text()='Plus de détails:']/../..//input/../../../span[contains(@class, 'Mui-checked')]";
    final static String PLUS_DE_DETAIL_TEXT = "//span[text()='Plus de détails:']";

    final static String TABLE_HEADER = "//table/thead//th/span[text()=''{0}'']";
    final static String NON_CLICKABLE_TABLE_HEADER = "//table/thead//th[text()=''{0}'']";
    final static String BOLD_TABLE_HEADER = "//table/thead//th//b[text()=''{0}'']";
    final static String DETAIL_TABLE = "table[aria-labelledby='tableTitle']";
    final static String SWITCH_BUTTON = "span.MuiSwitch-switchBase";
    final static String ICON_SELECT_PAGINATION = "div.MuiTablePagination-input>div";
    final static String OPT_PAGINATION_LIST = "//ul[@class=''MuiList-root MuiMenu-list MuiList-padding'']/li[text()=''{0}'']";
    final static String TXT_ELEMENTS_PAR_PAGE = "//p[text()='Elements par page']";
    final static String NUMBER_OF_RECORDS_IN_TABLE = "table>tbody>tr";
    final static String BTN_NEXT_PAGE = "button[title='Next page']";
    final static String BTN_NEXT_PAGE_DISABLED = "//button[@title='Next page' and @disabled]";
    final static String BTN_PREVIOUS_PAGE = "button[title='Previous page']";
    final static String BTN_PREVIOUS_PAGE_DISABLED = "//button[@title='Previous page' and @disabled]";
    final static String ICON_SELECT_STATUT = "//tbody/tr[1]/th[3]/div[1]/div[1]//*[local-name()='svg']";
    final static String ICON_SELECT_NB_JOURS = "//p[normalize-space()='Période']/preceding::*[local-name()='svg']";
    final static String ICON_SELECT_PERIODE = "//p[normalize-space()='Type']/preceding::*[local-name()='svg']";
    final static String ICON_SELECT_TYPE = "//span[normalize-space()='Rentes']/preceding::*[local-name()='svg']";
    final static String CHK_RENTES = "//input[@name='checkedA']";
    final static String BUTTON_EFFACER = "//span[normalize-space()='Effacer']";
    final static String RESULT = "//p[text()='Elements par page']/following-sibling::p";


    //====================
    // locators
    //====================
    protected static final By CHK_SHOW_ATMP_STATUSES = By.xpath("//span[text()='Afficher Statuts ATMP:']/..//span/input");
    protected static final By CHKD_SHOW_ATMP_STATUSES = By.xpath("//span[text()='Afficher Statuts ATMP:']/..//span/span[contains(@class, 'Mui-checked')]");
    protected static final By TEXT_SHOW_ATMP_STATUSES = By.xpath("//span[text()='Afficher Statuts ATMP:']");
    protected static final By FILTER_STATUS = By.xpath("//p[text() = 'Statut']/following-sibling::div/div/div");
    protected static final By FILTER_START_DATE = By.cssSelector("input[id='date-start']");
    protected static final By POPOVER = By.xpath("//div[contains(@class, 'MuiPopover-root')]");
    protected static final By RESULT_TABLE_ROWS_COUNT = By.xpath("//tbody/tr[contains(@class, 'MuiTableRow-root')]/th/button/../..");
    protected static final By RESULT_TABLE_HEADER_DISPUTE = By.xpath("//table/thead/tr/th[text()='Dossier contesté']");
    protected static final By RESULT_TABLE_HEADER_STATUS = By.xpath("//table/thead/tr/th[text()='Statut']");
    protected static final By RESULT_TABLE_HEADER_STATUS_CHANGE_DATE = By.xpath("//table/thead/tr/th[text()='Dernier changement de statut']");
    /** Records of dispute checkbox of first row. */
    protected static final By RESULT_TABLE_FIRST_ROW_CHK_DISPUTE = By.xpath("//table/tbody/tr[1]/th//../span/input[@type='checkbox']/..");
    protected static final By RESULT_TABLE_FIRST_ROW_CHKD_DISPUTE = By.xpath("//table/tbody/tr[1]/th//../span/span[contains(@class, 'Mui-checked')]");
    /** Dropdown status of first row, column 3 */
    protected static final By RESULT_TABLE_FIRST_ROW_DD_STATUS = By.xpath("//table/tbody/tr[1]/th[3]/div/div/div");
    protected static final String XPAHT_RESULT_TABLE_FIRST_ROW_DD_STATUS_EXPECT_VALUE = "//table/tbody/tr[1]/th[3]/div/div/div[contains(text(), ''{0}'')]";
    /** Status last change date of first row, column 4. */
    protected static final By RESULT_TABLE_FIRST_ROW_STATUS_LAST_CHANGE_DATE = By.xpath("//table/tbody/tr[1]/th[4]");
    /** Open actions.*/
    protected static final By RESULT_TABLE_FIRST_ROW_ACTIONS = By.xpath("//table/tbody/tr[1]/th[1]/button");
    protected static final By RESULT_TABLE_FIRST_ROW_ACTION_CHAT = By.xpath("//div/button/span/span[contains(text(), 'chat')]");
    protected static final By CHAT_POPUP_SELECT_TAG = By.xpath("//div[contains(@class, 'MuiDialogContent-root')]//../label[contains(text(), 'Marque')]/following-sibling::div/select");
    protected static final By CHAT_POPUP_SELECT_OPTION_QUESTIONNAIRE = By.xpath("//div[contains(@class, 'MuiDialogContent-root')]//../label[contains(text(), 'Marque')]/..//select/option[@value='Questionnaire']");
    protected static final By CHAT_POPUP_INPUT_MESSAGE = By.xpath("//div[contains(@class, 'MuiDialogContent-root')]//../label[contains(text(), 'Ajouter un commentaire')]/..//input[@name='message']");
    protected static final By CHAT_POPUP_BTN_SEND = By.xpath("//div[contains(@class, 'MuiDialogContent-root')]//../label[contains(text(), 'Ajouter un commentaire')]/..//button[@title='Envoyer']");
    protected static final String XPATH_CHAT_POPUP_COMMENT_BUBBLE_MESSAGE = "//div[contains(@class, 'MuiDialogContent-root')]//../div[contains(@class, 'commentBubble')]/p[contains(text(), ''{0}'')]";
    protected static final String XPATH_CHAT_POPUP_COMMENT_BUBBLE_DATE = "//div[contains(@class, 'MuiDialogContent-root')]//../div[contains(@class, 'commentBubble')]/p[contains(text(), ''{0}'')]/following-sibling::p";
    protected static final String XPATH_CHAT_POPUP_COMMENT_BUBBLE_TAG = "//div[contains(@class, 'MuiDialogContent-root')]//../div[contains(@class, 'commentBubble')]/p[contains(text(), ''{0}'')]/following-sibling::div/span";
    protected static final By CHAT_POPUP_BTN_CLOSE = By.xpath("//div[contains(@class, 'MuiDialogTitle-root')]//../span[contains(@class, 'material-icons') and contains(text(), 'close')]");
    protected static final By CHAT_POPUP = By.xpath("//div[contains(@class, 'MuiDialog-paper')]");
    protected static final By COMMENT_POPUP = By.xpath("//*[@id='scrollBarTable']/table/tbody/tr[1]/th[11]/button");
    protected static final By COMMENT_POPUP_SELECT_TAG = By.xpath("//div[contains(@class, 'MuiDrawer-root MuiDrawer-modal')]//../label[contains(text(), 'Marque')]/following-sibling::div/select");
    protected static final By COMMENT_POPUP_SELECT_OPTION_QUESTIONNAIRE = By.xpath("//div[contains(@class, 'MuiDrawer-root MuiDrawer-modal')]//../label[contains(text(), 'Marque')]/..//select/option[@value='Questionnaire']");
    protected static final By COMMENT_POPUP_INPUT_MESSAGE = By.xpath("//div[contains(@class, 'MuiDrawer-root MuiDrawer-modal')]//../label[contains(text(), 'Ajouter un commentaire')]/..//input[@name='message']");
    protected static final By COMMENT_POPUP_BTN_SEND = By.xpath("//div[contains(@class, 'MuiDrawer-root MuiDrawer-modal')]//../label[contains(text(), 'Ajouter un commentaire')]/..//button[@title='Envoyer']");
    protected static final String XPATH_COMMENT_POPUP_BUBBLE_MESSAGE = "//div[contains(@class, 'MuiDrawer-root MuiDrawer-modal')]//../li//p[contains(text(), ''{0}'')]";
    protected static final String XPATH_COMMENT_POPUP_BUBBLE_MESSAGE_DATE = "//div[contains(@class, 'MuiDrawer-root MuiDrawer-modal')]//../li//p[contains(text(), ''{0}'')]/following-sibling::p";
    protected static final String XPATH_COMMENT_POPUP_BUBBLE_MESSAGE_TAG = "//div[contains(@class, 'MuiDrawer-root MuiDrawer-modal')]//../li//p[contains(text(), ''{0}'')]/following-sibling::div/span";
    

    //====================
    // Selenium Controls =
    //====================
    private final TextField searchBoxTextField;
    private final Button exportTableButton;
    private final ControlledElement plusDeDetailsText;
    private final CheckBox plusDeDetailsCheckBox;
    private final ControlledElement detailTable;
    private final ControlledElement switchButton;
    private final Icon selectPaginationIcon;
    private final ControlledElement elementsParPageText;
    private final ControlledList numberOfRecordsInTable;
    private final Button nextPageButton;
    private final Button previousPageButton;
    private final Icon selectStatut;
    private final Icon selectNbJours;
    private final Icon selectPeriode;
    private final Icon selectType;
    private final CheckBox rentes;
    private final Button effacer;
    private JavascriptExecutor executor;

    public ATMPPage(WebDriver browser) {
        super(browser);
        executor = (JavascriptExecutor) browser;
        searchBoxTextField = new TextField(browser, By.cssSelector(TXT_FIELD_SEARCH_BOX), "Search Box Text Field");
        exportTableButton = new Button(browser, By.cssSelector(BTN_EXPORT_TABLE), "Export Table Button");
        plusDeDetailsText = new ControlledElement(browser, By.xpath(PLUS_DE_DETAIL_TEXT), "Plus de details text");
        plusDeDetailsCheckBox = new CheckBox(browser, By.cssSelector(CHK_PLUS_DE_DETAILS), "Plus de details Checkbox");
        detailTable = new ControlledElement(browser, By.cssSelector(DETAIL_TABLE), "Details Table");
        switchButton = new ControlledElement(browser, By.cssSelector(SWITCH_BUTTON), "Plus de details Switch button");
        selectPaginationIcon = new Icon(browser, By.cssSelector(ICON_SELECT_PAGINATION), "Select Pagination icon");
        elementsParPageText = new ControlledElement(browser, By.xpath(TXT_ELEMENTS_PAR_PAGE), "Elements Par page Text");
        numberOfRecordsInTable = new ControlledList(browser, By.cssSelector(NUMBER_OF_RECORDS_IN_TABLE), "Number Of Records In Table");
        nextPageButton = new Button(browser, By.cssSelector(BTN_NEXT_PAGE), "Next Page Button");
        previousPageButton = new Button(browser, By.cssSelector(BTN_PREVIOUS_PAGE), "Previous Page Button");
        selectStatut = new Icon(browser, By.xpath(ICON_SELECT_STATUT));
        selectNbJours =  new Icon(browser, By.xpath(ICON_SELECT_NB_JOURS));
        selectPeriode =  new Icon(browser, By.xpath(ICON_SELECT_PERIODE));
        selectType = new Icon(browser, By.xpath(ICON_SELECT_TYPE));
        rentes = new CheckBox(browser, By.xpath(CHK_RENTES));
        effacer = new Button(browser, By.xpath(BUTTON_EFFACER));
    }

    //================
    // Check actions =
    //================
    /**
     * Method to check presence of Search Box Text Field
     */
    public void checkPresenceOfSearchBoxTextField(){
        searchBoxTextField.shouldBeDisplayed();
    }

    /**
     * Method to check presence of export table button
     */
    public void checkPresenceOfExportTableButton(){
        exportTableButton.shouldBeDisplayed();
    }

    /**
     * Method to check presence of export table button
     */
    public void checkAbsenceOfExportTableButton(){
        exportTableButton.shouldNotBeDisplayed();
    }

    /**
     * Method to check presence of Plus de détails text
     */
    public void checkPresenceOfPlusDeDetailsText(){
        plusDeDetailsText.shouldBeDisplayed();
    }

    /**
     * Method to check presence of Plus de détails checkbox
     */
    public void checkPresenceOfPlusDeDetailsCheckbox(){
    	By locator = By.xpath(CHK_PLUS_DE_DETAILS);
    	LOGGER.info("finding controlled object " + locator);
        long findStart = System.currentTimeMillis();
        Utility.waitForElement(browser, 5, ExpectedConditions.presenceOfElementLocated(locator));
    	long timeToRetrieveElement = (System.currentTimeMillis() - findStart);
        LOGGER.info(locator.toString() + " was found in " + timeToRetrieveElement + "ms");
    }

    public void checkUncheckPlusDeDetailsCheckbox() {
    	LOGGER.info("uncheck " + By.xpath(CHK_PLUS_DE_DETAILS));
    	if (!browser.findElements(By.xpath(CHK_PLUS_DE_DETAILS)).isEmpty()) {
    		executor.executeScript("arguments[0].click();", this.findElement(By.xpath(CHK_PLUS_DE_DETAILS)));
    		LOGGER.info("unchecked " + By.xpath(CHK_PLUS_DE_DETAILS));
    	}
    }
    
    //public void checkPresenceOfMoinsDeDetailsCheckbox(){
    //    moinsDeDetailsCheckBox.shouldBeDisplayed();
    //}

    /**
     * Method to check presence of Plus de détails checkbox
     */
    public void checkPresenceOfDetailsTable(){
        detailTable.shouldBeDisplayed();
    }

    /**
     * Method to check the presence of table filters button
     *
     * @param str_Table_Filter button text
     */
    public void checkPresenceOfTableFilterButton(String str_Table_Filter){
        ControlledElement tableFilter = new ControlledElement(browser, By.xpath(MessageFormat.format(TABLE_FILTERS, str_Table_Filter)), "Table Filter");
        tableFilter.shouldBeDisplayed();
    }

    /**
     * Method to check the presence of table header
     *
     * @param str_HeaderText header text
     */
    /*public void checkPresenceOfTableHeader(String str_HeaderText){
        Header tableHeader = new Header(browser, By.xpath(MessageFormat.format(TABLE_HEADER, str_HeaderText)), "Table Header");
        tableHeader.shouldBeDisplayed();
    }*/
    
    public void checkPresenceOfTableHeader(String str_HeaderText) {
    	By locator = By.xpath(MessageFormat.format(TABLE_HEADER, str_HeaderText));
    	LOGGER.info("finding controlled object " + locator);
    	long findStart = System.currentTimeMillis();
        Utility.waitForElement(browser, 5, ExpectedConditions.presenceOfElementLocated(locator));
    	long timeToRetrieveElement = (System.currentTimeMillis() - findStart);
        LOGGER.info(locator.toString() + " was found in " + timeToRetrieveElement + "ms");
    }


    public void checkNotPresenceOfTableHeader(String str_HeaderText) {
        By locator = By.xpath(MessageFormat.format(TABLE_HEADER, str_HeaderText));
        LOGGER.info("expecting controlled object not presence " + locator);
        try {
            this.findElement(locator);
            LOGGER.info("controlled object was found, not expected: " + locator);
            throw new MSeleniumException("Element " + locator + " is not expected but found.", browser);
        } catch (Exception e) {
            // as expected
            LOGGER.info("controlled object not presence as expected " + locator);
        }
    }


    /**
     * Method to check the presence of non clickable table header
     *
     * @param str_HeaderText header text
     */
    /*public void checkPresenceOfNonClickableTableHeader(String str_HeaderText){
        Header nonClickableTableHeader = new Header(browser, By.xpath(MessageFormat.format(NON_CLICKABLE_TABLE_HEADER, str_HeaderText)), "Non Clickable Table Header");
        nonClickableTableHeader.shouldBeDisplayed();
    }*/
    
    public void checkPresenceOfNonClickableTableHeader(String str_HeaderText) {
    	By locator = By.xpath(MessageFormat.format(NON_CLICKABLE_TABLE_HEADER, str_HeaderText));
    	LOGGER.info("finding controlled object " + locator);
    	long findStart = System.currentTimeMillis();
        Utility.waitForElement(browser, 5, ExpectedConditions.presenceOfElementLocated(locator));
    	long timeToRetrieveElement = (System.currentTimeMillis() - findStart);
        LOGGER.info(locator.toString() + " was found in " + timeToRetrieveElement + "ms");
    }
    
    public void checkPresenceOfBoldTableHeader(String str_HeaderText) {
    	By locator = By.xpath(MessageFormat.format(BOLD_TABLE_HEADER, str_HeaderText));
    	LOGGER.info("finding controlled object " + locator);
    	long findStart = System.currentTimeMillis();
        Utility.waitForElement(browser, 5, ExpectedConditions.presenceOfElementLocated(locator));
    	long timeToRetrieveElement = (System.currentTimeMillis() - findStart);
        LOGGER.info(locator.toString() + " was found in " + timeToRetrieveElement + "ms");
    }

    /**
     * Method to check the presence of option in pagination list
     *
     * @param str_ListOption list option
     */
    public void checkPresenceOfPaginationListOption(String str_ListOption){
        ControlledElement listOption = new Header(browser, By.xpath(MessageFormat.format(OPT_PAGINATION_LIST, str_ListOption)), "Pagination List Option");
        listOption.shouldBeDisplayed();
    }
    
    public String getPaginationText() {
    	By locator = By.xpath(RESULT);
    	LOGGER.info("finding controlled object " + locator);
    	long findStart = System.currentTimeMillis();
    	String text = Utility.waitForElement(browser, 5, ExpectedConditions.presenceOfElementLocated(locator)).getText();
    	long timeToRetrieveElement = (System.currentTimeMillis() - findStart);
        LOGGER.info(locator.toString() + " was found in " + timeToRetrieveElement + "ms");
        return text;
    }

    /**
     * Method to check the presence of next page Button
     */
    public void checkPresenceOfNextPageButton(){
    	By locator = By.cssSelector(BTN_NEXT_PAGE);
    	LOGGER.info("finding controlled object " + locator);
    	long findStart = System.currentTimeMillis();
        Utility.waitForElement(browser, 5, ExpectedConditions.presenceOfElementLocated(locator));
    	long timeToRetrieveElement = (System.currentTimeMillis() - findStart);
        LOGGER.info(locator.toString() + " was found in " + timeToRetrieveElement + "ms");
        //nextPageButton.shouldBeDisplayed();
    }

    /**
     * Method to check the absence of next page Button
     */
    public void checkAbsenceOfNextPageButton(){
        nextPageButton.shouldNotBeDisplayed();
    }

    /**
     * Method to check the presence of previous page Button
     */
    public void checkPresenceOfPreviousPageButton(){
    	By locator = By.cssSelector(BTN_PREVIOUS_PAGE);
    	LOGGER.info("finding controlled object " + locator);
    	long findStart = System.currentTimeMillis();
        Utility.waitForElement(browser, 5, ExpectedConditions.presenceOfElementLocated(locator));
    	long timeToRetrieveElement = (System.currentTimeMillis() - findStart);
        LOGGER.info(locator.toString() + " was found in " + timeToRetrieveElement + "ms");
        //previousPageButton.shouldBeDisplayed();
    }

    /**
     * Method to check the absence of previous page Button
     */
    public void checkAbsenceOfPreviousPageButton(){
        previousPageButton.shouldNotBeDisplayed();
    }

    //================
    // Get actions =
    //================

    /**
     * Get Placeholder of the search text box
     *
     * @return placeholder value
     */
    public String getPlaceHolderOfSearchBoxTextField(){
        searchBoxTextField.shouldBeDisplayed();
        return searchBoxTextField.getControlledObject().getAttribute("placeholder");
    }
    

    /**
     * Method to get state of Plus de détails checkbox
     *
     * @return checked if switched or unchecked if not switched
     */
    public String getPlusDeDetailsCheckboxState(){
        checkPresenceOfPlusDeDetailsCheckbox();
        List<WebElement> state = browser.findElements(By.xpath(CHKD_PLUS_DE_DETAILS));
        if (!state.isEmpty())
            return "Checked";
        else
            return "Unchecked";
    }

    /**
     * Get number of record in table
     *
     * @return numbers of records in table
     */
    public int getNumberOfRecordsInTable(){
        numberOfRecordsInTable.shouldBeDefined();
        return numberOfRecordsInTable.getSizeOfDisplayedElements();
    }

    /**
     * Method to return true if next button is enabled
     *
     * @return true or false
     */
    public boolean isNextButtonDisabled() {
    	By locator = By.xpath(BTN_NEXT_PAGE_DISABLED);
    	return !browser.findElements(locator).isEmpty(); 
    }

    /**
     * Method to return true if previous button is enabled
     *
     * @return true or false
     */
    public boolean isPreviousButtonDisabled() {
    	By locator = By.xpath(BTN_PREVIOUS_PAGE_DISABLED);
    	return !browser.findElements(locator).isEmpty();
    }

    //================
    // Click actions
    //================

    /**
     * Click plus de details check box to check the check box
     */
    public void checkPlusDeDetailsCheckBox() {
        plusDeDetailsText.shouldBeDisplayed();
        plusDeDetailsText.click();
    }

    public void checkPresenceOfShowAtmpStatusesCheckbox() {
        checkPresenceWait(CHK_SHOW_ATMP_STATUSES);
    }
    public boolean isShowAtmpStatusesCheckboxChecked() {
        checkPresenceOfShowAtmpStatusesCheckbox();
        List<WebElement> state = browser.findElements(CHKD_SHOW_ATMP_STATUSES);
        return !state.isEmpty();
    }
    public void checkShowAtmpStatusesCheckBox() {
        checkPresence(TEXT_SHOW_ATMP_STATUSES, "Show ATMP statuses bottom checkbox");
        click(TEXT_SHOW_ATMP_STATUSES);
    }
    public void expectShowAtmpStatusesCheckBoxChecked() {
        checkPresenceWait(CHKD_SHOW_ATMP_STATUSES);
    }
    public void filterByStartDate(String startDate) {
        fillWithText(FILTER_START_DATE, startDate);
    }
    public void expectHeaderDisputeVisible() {
        checkPresence(RESULT_TABLE_HEADER_DISPUTE, "Column header Records of Disputes");
    }
    public void expectHeaderStatusVisible() {
        checkPresence(RESULT_TABLE_HEADER_STATUS, "Column header Status");
    }
    public void expectHeaderStatusChangeDateVisible() {
        checkPresence(RESULT_TABLE_HEADER_STATUS_CHANGE_DATE, "Column header Status last change date");
    }
    public void waitForZeroRowInResult() {
        waitForNrRowInResult(0);
    }
    public void waitForOneRowInResult() {
        waitForNrRowInResult(1);
    }
    public void waitForNrRowInResult(int rowCount) {
        waitForCondition(new ExpectedCondition<Boolean>() {

            @Override
            public Boolean apply(WebDriver driver) {
                try {
                    return _apply();
                } catch (StaleElementReferenceException e) {
                    sleepSilently(1000);
                    // retry because of DOM update
                    return _apply();
                }
            }

            private Boolean _apply() {
                List<WebElement> rows = browser.findElements(RESULT_TABLE_ROWS_COUNT);
                if (rows.size() == rowCount) {
                    return true;
                }
                return false;
            }

            @Override
            public String toString() {
                return "Expect ATMP table has " + rowCount + " row left after filter: " + RESULT_TABLE_ROWS_COUNT;
            }
        });
    }
    public void checkPresenceOfDisputeCheckbox() {
        checkPresence(RESULT_TABLE_FIRST_ROW_CHK_DISPUTE, "Records of disputes");
    }
    public void checkDisputeCheckBox() {
        checkPresence(RESULT_TABLE_FIRST_ROW_CHK_DISPUTE, "Records of disputes");
        click(RESULT_TABLE_FIRST_ROW_CHK_DISPUTE);
    }
    public void waitForDisputeCheckboxState(final boolean checked) {
        waitForCondition(new ExpectedCondition<Boolean>() {

            @Override
            public Boolean apply(WebDriver driver) {
                try {
                    return _apply();
                } catch (StaleElementReferenceException e) {
                    sleepSilently(1000);
                    // retry because of DOM update
                    return _apply();
                }
            }

            private Boolean _apply() {
                List<WebElement> rows = browser.findElements(RESULT_TABLE_FIRST_ROW_CHKD_DISPUTE);
                if (checked) {
                    if (rows.size() == 1) {
                        return true;
                    }
                    return false;
                } else {
                    if (rows.size() == 0) {
                        return true;
                    }
                    return false;
                }
            }

            @Override
            public String toString() {
                return "Expect records of dispute checkbox: " + RESULT_TABLE_FIRST_ROW_CHKD_DISPUTE + ", to be: " + (checked ? "checked" : "unchecked");
            }
        });
    }
    public void checkPresenceOfOnRowDropdownStatus() {
        checkPresence(RESULT_TABLE_FIRST_ROW_DD_STATUS, "First row status");
    }
    public void expectOnRowStatusValue(String expectedStatus) {
        waitForElement(By.xpath(MessageFormat.format(XPAHT_RESULT_TABLE_FIRST_ROW_DD_STATUS_EXPECT_VALUE, expectedStatus)),
                "Expecting First row status to be " + expectedStatus);
    }
    public void selectOnRowStatus(String newStatus) {
        LOGGER.info("Putting down key on: " + RESULT_TABLE_FIRST_ROW_DD_STATUS.toString() + ", to select item: " + newStatus);
        downKey(RESULT_TABLE_FIRST_ROW_DD_STATUS);
        LOGGER.info("Selecting item: " + newStatus);
        selectItemFromDropDownList(newStatus);
        LOGGER.info("Selected item: " + newStatus);
    }
    public void filterByStatus(String newStatus) {
        click(FILTER_STATUS);
        selectItemFromDropDownList(newStatus);
    }
    public void openOnRowActions() {
        click(RESULT_TABLE_FIRST_ROW_ACTIONS);
    }
    public void openChat() {
        click(RESULT_TABLE_FIRST_ROW_ACTION_CHAT);
    }

    public void openComment(){
        click (COMMENT_POPUP);
    }
    public void waitForPopupTagSelect() {
        sleepSilently(2000);
        checkPresence(CHAT_POPUP_SELECT_TAG, "ATMP chat popup tag selection");
    }
    public void waitForCommentPopupTagSelect() {
        sleepSilently(2000);
        checkPresence(COMMENT_POPUP_SELECT_TAG, "ATMP comment popup tag selection");
    }
    public void selectTagQuestionnaire() {
        click(CHAT_POPUP_SELECT_TAG);
        sleepSilently(500);
        checkPresence(CHAT_POPUP_SELECT_OPTION_QUESTIONNAIRE, "ATMP chat popup tag Questionnaire");
        click(CHAT_POPUP_SELECT_OPTION_QUESTIONNAIRE);
    }
    public void selectTagQuestionnaireComment() {
        click(COMMENT_POPUP_SELECT_TAG);
        sleepSilently(500);
        checkPresence(COMMENT_POPUP_SELECT_OPTION_QUESTIONNAIRE, "ATMP Comment popup tag Questionnaire");
        click(COMMENT_POPUP_SELECT_OPTION_QUESTIONNAIRE);
    }
    public void waitForPopupMessageInput() {
        checkPresence(CHAT_POPUP_INPUT_MESSAGE, "ATMP chat popup message input");
    }
    public void waitForPopupMessageCommentInput() {
        checkPresence(COMMENT_POPUP_INPUT_MESSAGE, "ATMP comment popup message input");
    }
    public void inputChatOnPopup(String message) {
        fillWithText(CHAT_POPUP_INPUT_MESSAGE, message);
    }
    public void inputCommentOnPopup(String message) {
        fillWithText(COMMENT_POPUP_INPUT_MESSAGE, message);
    }
    public void clickSendChatOnPopup() {
        click(CHAT_POPUP_BTN_SEND);
    }
    public void clickSendCommentOnPopup() {
        click(COMMENT_POPUP_BTN_SEND);
    }
    public void waitForChatMessageOnPopup(String message) {
        checkPresence(By.xpath(MessageFormat.format(XPATH_CHAT_POPUP_COMMENT_BUBBLE_MESSAGE, message)), "Chat message on popup");
    }
    // public void waitForCommentMessageOnPopup(String message) {
    //     checkPresence(By.xpath(MessageFormat.format(XPATH_COMMENT_POPUP_BUBBLE_MESSAGE, message)), "Comment message on popup");
    // }
    public void waitForCommentMessageOnPopup(String message){
        waitForElement(By.xpath(MessageFormat.format(XPATH_COMMENT_POPUP_BUBBLE_MESSAGE, message)), "Comment message on popup");
    }
    public void expectChatDateOnPopup(String message, String currentDate) {
        expectTextContain(By.xpath(MessageFormat.format(XPATH_CHAT_POPUP_COMMENT_BUBBLE_DATE, message)), currentDate);
    }
    public void expectCommentDateOnPopup(String message, String currentDate) {
        expectTextContain(By.xpath(MessageFormat.format(XPATH_COMMENT_POPUP_BUBBLE_MESSAGE_DATE, message)), currentDate);
    }
    public void expectChatTagQuestionnaireOnPopup(String message) {
        expectTextContain(By.xpath(MessageFormat.format(XPATH_CHAT_POPUP_COMMENT_BUBBLE_TAG, message)), "Questionnaire");
    }
    public void expectCommentTagQuestionnaireOnPopup(String message) {
        expectTextContain(By.xpath(MessageFormat.format(XPATH_COMMENT_POPUP_BUBBLE_MESSAGE_TAG, message)), "Questionnaire");
    }
    public void closeChatPopup() {
        click(CHAT_POPUP_BTN_CLOSE);
    }
    public void waitForChatPopupClosed() {
        waitForElementToDisappear(CHAT_POPUP, "Chat popup");
    }

    public void closeOnRowActions() {
        closePopover();
    }

    public void closePopover() {
        try {
            checkPresence(POPOVER);
            click(POPOVER);
        } catch (Exception e) {
            // not important, ignore
        }
    }

    /**
     * @param expectedDate dd/MM/yyyy
     */
    public void expectOnRowStatusLastChangeDate(String expectedDate) {
        expectText(RESULT_TABLE_FIRST_ROW_STATUS_LAST_CHANGE_DATE, expectedDate);
    }

    /**
     * Method to click Select Pagination icon
     */
    public void clickSelectPaginationIcon() {
        selectPaginationIcon.shouldBeDisplayed();
        selectPaginationIcon.click();
    }

    /**
     * Method to click the Elements per Page text
     */
    public void clickElementsParPageText() {
        elementsParPageText.shouldBeDisplayed();
        elementsParPageText.click();
    }

    /**
     * Method to Click the option from the pagination list
     *
     * @param str_ListOption option
     */
    public void clickPaginationListOption(String str_ListOption){
        ControlledElement listOption = new Header(browser, By.xpath(MessageFormat.format(OPT_PAGINATION_LIST, str_ListOption)), "Pagination List Option");
        listOption.shouldBeDisplayed();
        listOption.click();
    }
    
    
    /**
     * Method to click next page Button
     */
    public void clickNextPageButton(){
        checkPresenceOfNextPageButton();
        executor.executeScript("arguments[0].click();", By.cssSelector(BTN_NEXT_PAGE));
        LOGGER.info("Clicked on "+By.cssSelector(BTN_NEXT_PAGE));
    }

    /**
     * Method to click previous page Button
     */
    public void clickPreviousPageButton(){
    	checkPresenceOfPreviousPageButton();
    	executor.executeScript("arguments[0].click();", By.cssSelector(BTN_PREVIOUS_PAGE));
        LOGGER.info("Clicked on "+By.cssSelector(BTN_PREVIOUS_PAGE));
    }
}
