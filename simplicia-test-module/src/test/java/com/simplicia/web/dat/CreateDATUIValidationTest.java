package com.simplicia.web.dat;

import com.simplicia.executor.SeleniumTestAsSimpliciaUser;
import com.simplicia.functions.CompareXMLFiles;
import com.simplicia.functions.DatZipToXml;
import com.simplicia.functions.TestDataEngine;
import com.simplicia.pages.web.dat.DATMainPage;
import com.simplicia.pages.web.dat.createDAT.*;
import com.simplicia.pages.web.home.HomePage;
import com.simplicia.pages.web.leftnavigationmenu.LeftNavigationMenuPage;
import com.simplicia.pages.web.utils.SimpliciaReusableActions;
import com.simplicia.pages.web.utils.TestData;
import io.qameta.allure.Description;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.collections.Lists;

import java.io.File;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;

public class CreateDATUIValidationTest extends SeleniumTestAsSimpliciaUser {

    HomePage homePage;
    LeftNavigationMenuPage leftNavigationMenuPage;
    SimpliciaReusableActions simpliciaReusableActions;
    DATMainPage datMainPage;
    MyProfileTab myProfileTab;
    LaVictimeTab laVictimeTab;
    Accident1Tab accident1Tab;
    Accident2Tab accident2Tab;
    LesTemoinsTab lesTemoinsTab;
    LeTiersTab leTiersTab;
    SignatureTab signatureTab;

    public static String sDATTestDataXMLPath = System.getProperty("user.dir") + File.separator + "src/test/resources/DATTestData";
    public static String sFileToImport = System.getProperty("user.dir") + File.separator + "src/test/resources/datimport.zip";
    public static String sConvertToXMLPath = System.getProperty("user.dir") + File.separator + "src/test/resources/ZIPtoXMLTestData";


    String sUpdatedRegistrationNumber = "";
    boolean bDat; // return true if dat is displayed on searching sNumeroSS
    boolean bUploadFile = false; // if file to be uploaded to DAT, default false
    String sImportedDATNumeroSS = "666777988899999";
    
    @BeforeMethod
    public void pageSetUp(Method method) throws Exception{
        try {
            // Setup pages
            println("################################################################");
            println("Starting " + method.getName());
            println("################################################################");
            homePage = new HomePage(browser);
            leftNavigationMenuPage = new LeftNavigationMenuPage(browser);
            simpliciaReusableActions = new SimpliciaReusableActions(browser);
            datMainPage = new DATMainPage(browser);
            myProfileTab = new MyProfileTab(browser);
            laVictimeTab = new LaVictimeTab(browser);
            accident1Tab = new Accident1Tab(browser);
            accident2Tab = new Accident2Tab(browser);
            lesTemoinsTab = new LesTemoinsTab(browser);
            leTiersTab = new LeTiersTab(browser);
            signatureTab = new SignatureTab(browser);

            // disable support technique

            // delete dat zip file if any
            if(browser.getCurrentUrl().contains("staging") || browser.getCurrentUrl().contains("test-deploy") || browser.getCurrentUrl().contains("qa2")) {
                deleteZipFile(".zip");
            } else {
                deleteZipFile(".pdf");
            }

        } catch (Exception e) {
            // ignore, tests will be failed and it goes to report
        }
    }


    @AfterMethod(alwaysRun = true)
    public void pageTearDown(Method method) throws InterruptedException {
    	logoutFlag = false;
        println("################################################################");
        println("Finished " + method.getName());
        println("################################################################");
        //resetBrowser();

    }

    /**
     * Steps:
     * 1. Login to the application and navigate to left menu
     * 2. Click on DAT, amd then Mes DATs
     * 3. Click on Créer une nouvelle DAT to create new DAT
     * 4. Fill in the details for step 1 thru step 7
     * 5. Click on Save and exit button
     * 6. Go to Mes DAT dash board
     * 7. Search for the created DAT using numero SS
     * 8. Clean up the test case - delete the created DAT.
     *
     * @throws Exception
     */
    @Test(enabled=true)
    @Description("Navigate to Left navigation menu. Click on 'DAT' and click 'Mes DATs" +
            "'create a new DAT")
    public void dat_Test_01_create() throws Exception {

        try {
        	
        	LOGGER.info("*** Step 1 Action: Login the simplicia page ***");
            simpliciaReusableActions.logIn();
            
            LOGGER.info("*** Step 2 Action: Navigate to Mes DATs ***");
            navigateToDatOption("Mes DATs", true);

            LOGGER.info("*** Step 3, 4 and 5 Action: Create a new DATs"
            		+ " - Fill in the details for step 1 thru step 7"
            		+ " - Click on Save and exit button"
            		+ " - Go to Mes DAT dash board***");

            String sNumeroSS = createDAT(true, false, false);
            
            LOGGER.info("*** Step 6 and 7 Action: Search for the created DAT using numero SS "
            		+ " - DATs is displayed on main DAT dashboard ***");
            bDat = datMainPage.isDatPresentOnDashboard(sNumeroSS);
            Assert.assertTrue(bDat, "The Numero SS is present on the dashboard");

        } catch (Exception e) {

            e.printStackTrace();
            Assert.fail(e.getMessage(), e);

        } finally {

            try {
                if (bDat) {
                	
                	LOGGER.info("*** Step 8 Action: Clean up the test case - delete the created DAT ***");
                    cleanDAT();


                } else {
                    //resetBrowser();

                    println("###################### No DAT was created ##############################");
                }

                isDATClosed();
            } finally {
                simpliciaReusableActions.logOut();
            }
        }
    }



    /**
     * Steps:
     * 1. Login to the application and navigate to left menu
     * 2. Click on DAT, amd then Mes DATs
     * 3. Click on Créer une nouvelle DAT to create new DAT
     * 4. Fill in the details for step 1 thru step 7
     * 5. Click on Save and exit button
     * 6. Go to Mes DAT dash board
     * 7. Search for the created DAT using numero SS
     * 8. Scroll horizontally, to display DAT action button and click on it
     * 9. Click on Edit DAT button
     * 10. Go to step 2 and update Numero ss number, save and exit
     * 11. Refresh the view to reflect the changes
     * 12. Search with updated Numero SS and validate
     * 13. Clean up the test case - delete the created DAT.
     *
     * @throws Exception
     */

    @Test(enabled=true)
    @Description("create a DAT, update the registration number , save the changes and validate the updates")
    public void dat_Test_02_update() throws Exception {
        try {
            
        	LOGGER.info("*** Step 1 Action: Login the simplicia page ***");
            simpliciaReusableActions.logIn();
            
            LOGGER.info("*** Step 2 Action: Navigate to Mes DATs ***");
            navigateToDatOption("Mes DATs", true);

            // create dat

            LOGGER.info("*** Step 3, 4 and 5 Action:"
            + " - Click on Créer une nouvelle DAT to create new DAT"
            + " - Fill in the details for step 1 thru step 7"
            + " - Click on Save and exit button ***");
            String sNumeroSS = createDAT(false, false, true);

            LOGGER.info("*** Step 6 and 7 Action: Search for the created DAT using numero SS "
            		+ " - DATs is displayed on main DAT dashboard ***");
            bDat = datMainPage.isDatPresentOnDashboard(sNumeroSS);

            // update DAT
            if (bDat) {

                LOGGER.info("*** Step 8 and 9 Action:"
                + " - Scroll horizontally, to display DAT action button and click on it"
                + " - Click on Edit DAT button"
                + " - Click on Save and exit button ***");
                datMainPage.editDAT();
                sleep(10000);
                sUpdatedRegistrationNumber = updateDAT();

                LOGGER.info("*** Step 10 Action: - Go to step 2 and update Numero ss number, save and exit ***");

                saveAndValidateDAT();
                println("###################### DAT with Numero SS number " + sUpdatedRegistrationNumber + " updated ##############################");

                // Assert that registration number is updated
                //sometime updated info is not displayed unless you refresh the view.

                navigateToDatOption("● DATs en cours de rédaction", false);
                navigateToDatOption("Mes DATs", false);
                sleepSilently(10000);

                LOGGER.info("*** Step 11 and 12 Action:" 
                + " - 11. Refresh the view to reflect the changes"
                + " - 12. Search with updated Numero SS and validate ***");

                bDat = datMainPage.isDatPresentOnDashboard(sUpdatedRegistrationNumber);
                Assert.assertTrue(bDat, "The updated Numero SS/registration number " + sUpdatedRegistrationNumber + " must be present on the dashboard");
                println("###################### DAT with Numero SS number " + sUpdatedRegistrationNumber + " is found ##############################");

            } else {

                println("###################### No DAT with Numero SS number " + sUpdatedRegistrationNumber + " is present to be updated ##############################");

            }

        } catch (Exception e) {

            e.printStackTrace();
            Assert.fail(e.getMessage(), e);

        } finally {

            try {
                if (bDat) {

                    try {
                        LOGGER.info("*** Step 13 Action: Clean up the test case - delete the created DAT ***");
                        Thread.sleep(5000);
                        cleanDAT();

                    } catch (Exception e) {

                        e.printStackTrace();
                    }

                } else {

                    // resetBrowser();
                    println("###################### No DAT was Created ##############################");
                }

                isDATClosed();
            } finally {
                simpliciaReusableActions.logOut();
            }
        }
    }

    /**
     * Steps:
     * 1. Login to the application and navigate to left menu
     * 2. Click on DAT, amd then Mes DATs
     * 3. Click on Créer une nouvelle DAT to create new DAT
     * 4. Fill in the details for step 1 thru step 7
     * 5. Click on Save and exit button
     * 6. Go to Mes DAT dash board
     * 7. Search for the created DAT using numero SS
     * 8. Scroll horizontally, to display DAT action button and click on it
     * 9. Click on download as zip( as set in chrome option will be downloaded to target folder)
     * 10. Validate the downloaded file.
     * 11. CleanUp the test case- Delete the imported DAT
     *
     * @throws Exception
     */
    @Test(enabled=true)
    @Description("create a DAT, export as zip and validate")
    public void dat_Test_03_Export() throws Exception {
        boolean bDownloaded = false;

        try {

            LOGGER.info("*** Step 1 Action: Login the simplicia page ***");
            simpliciaReusableActions.logIn();
            
            LOGGER.info("*** Step 2 Action: Navigate to Mes DATs ***");
            navigateToDatOption("Mes DATs", true);

            // create dat
            String sNumeroSS = createDAT(false, false, true);
            bDat = datMainPage.isDatPresentOnDashboard(sNumeroSS);

            // download the DAT
            if (bDat) {
                List<String> ls = Lists.newArrayList("● DATs en cours de rédaction", "Mes DATs");
                switchNavigationBetweenDATOption(ls);
                datMainPage.isDatPresentOnDashboard(sNumeroSS);
                if(browser.getCurrentUrl().contains("staging") || browser.getCurrentUrl().contains("test-deploy")||browser.getCurrentUrl().contains("qa2")) {

                    bDownloaded = datMainPage.downloadDATZIP("dat.zip");
                    LOGGER.info("*** dat_Test_03_Export bDownloaded ***" + bDownloaded);
                } else {

                    bDownloaded = datMainPage.downloadDATZIP("dummy.pdf");
                }

            } else {

                println("###################### No DAT with Numero SS number " + sNumeroSS + " is present to be downloaded ##############################");

            }
            Assert.assertTrue(bDownloaded);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage(), e);

        } finally {

            try {
                if (bDat) {

                    cleanDAT();

                } else {

                    //resetBrowser();
                    println("###################### No DAT was Created ##############################");
                }

                isDATClosed();
            } finally {
                simpliciaReusableActions.logOut();
            }
        }
    }

    @Test(enabled=true)
    @Description("create a DAT, export as zip and validate")
    public void dat_Test_13_Export() throws Exception {
        boolean bDownloaded = false;

        try {

            LOGGER.info("*** Step 1 Action: Login the simplicia page ***");
            simpliciaReusableActions.logIn();
            
            LOGGER.info("*** Step 2 Action: Navigate to Mes DATs ***");
            navigateToDatOption("Mes DATs", true);

            // create dat
            String sNumeroSS = createDAT(false, false, true);
            bDat = datMainPage.isDatPresentOnDashboard(sNumeroSS);

            // down load the DAT
            if (bDat) {
                List<String> ls = Lists.newArrayList("● DATs en cours de rédaction", "Mes DATs");
                switchNavigationBetweenDATOption(ls);
                sleepSilently(10000);
                datMainPage.isDatPresentOnDashboard(sNumeroSS);
                if(browser.getCurrentUrl().contains("staging") || browser.getCurrentUrl().contains("test-deploy") || browser.getCurrentUrl().contains("qa2")) {

                    bDownloaded = datMainPage.downloadDATZIP("dat.zip");
                    LOGGER.info("*** dat_Test_13_Export bDownloaded ***" + bDownloaded);
                } else {

                    bDownloaded = datMainPage.downloadDATZIP("dummy.pdf");
                }

            } else {

                println("###################### No DAT with Numero SS number " + sNumeroSS + " is present to be downloaded ##############################");

            }

            Assert.assertTrue(bDownloaded);

        } catch (Exception e) {

            e.printStackTrace();
            Assert.fail(e.getMessage(), e);

        } finally {

            try {
                if (bDat) {

                    cleanDAT();

                } else {
                    //resetBrowser();
                    println("###################### No DAT was Created ##############################");
                }
                isDATClosed();
            } finally {
                simpliciaReusableActions.logOut();
            }
        }
    }

    /**
     * Steps:
     * 1. Login to the application and navigate to left menu
     * 2. Go to Mes DATs dashboard and click on Importer une DAT to import the recently downloaded DAT
     * 3. Refresh the view to reflect the changes. go to DAT dashboard and search with the numero ss and validate if it is imported or not
     * 4. CleanUp the test case- Delete the imported DAT
     *
     * @throws Exception
     */
    @Test(enabled=true)
    @Description("Import the DAT zip and validate")
    public void dat_Test_04_Import() throws Exception {


        try {

            LOGGER.info("*** Step 1 Action: Login the simplicia page ***");
            simpliciaReusableActions.logIn();

            LOGGER.info("*** Step 2 Action: Navigate to Mes DATs ***");
            navigateToDatOption("Mes DATs", true);

            LOGGER.info("*** Step 3 Action: search with the numero ss and validate if it is imported or not ***");
            // import the file
            datMainPage.importDATF(sFileToImport, DATMainPage.DAT_IMPORT_SUCCESS_MESSAGE);
           /* List<String> ls = Lists.newArrayList("● DATs en cours de rédaction", "Mes DATs");
            switchNavigationBetweenDATOption(ls);*/
            bDat = datMainPage.isDatPresentOnDashboard(sImportedDATNumeroSS);
            Assert.assertTrue(bDat, "DAT is imported successfully");

            println("Dat imported successfully: " + sImportedDATNumeroSS);

        } catch (Exception e) {

            e.printStackTrace();
            Assert.fail(e.getMessage(), e);

        } finally {
            try {
                if (bDat) {

                    LOGGER.info("*** Step 4 Action: CleanUp the test case- Delete the imported DAT ***");
                    cleanDAT();

                } else {

                    //resetBrowser();
                    println("###################### No DAT was Created ##############################");
                }

                isDATClosed();
            } finally {
                simpliciaReusableActions.logOut();
            }
        }
    }

    /**
     * Steps:
     * 1. Create/Import a complete DAT with all mandatory fields.
     * 2. Send for lawyer review.
     * 3. Login as lawyer, review and validate the DAT.
     * 4. Login again with user and validate if DAT is reviewed.
     * 5. CleanUp the test case- Delete the imported DAT
     *
     * @throws Exception
     */
    @Test(enabled=true)
    @Description("Import complete DAT, send to lawyer review, and validate")
    public void dat_Test_05_Lawyer_Review() throws Exception {

        try {

            LOGGER.info("*** Step 1 Action: Login the simplicia page ***");
            simpliciaReusableActions.logIn();

            LOGGER.info("*** Step 2 Action: Navigate to Mes DATs ***");
            navigateToDatOption("Mes DATs", true);

            // import the file
            datMainPage.importDATF(sFileToImport, DATMainPage.DAT_IMPORT_SUCCESS_MESSAGE);
            bDat = datMainPage.isDatPresentOnDashboard(sImportedDATNumeroSS);
            Assert.assertTrue(bDat, "DAT is imported successfully");
            LOGGER.info("Dat imported successfully: " + sImportedDATNumeroSS);

            // send for review
            boolean bSent = datMainPage.sendDATForLawyerReview();

            if(bSent) {
                LOGGER.info("Dat was sent to lawyer successfully: " + sImportedDATNumeroSS);
                //logout and login using lawyer credentials

                LOGGER.info("Logging out, and then login as lawyer");
                simpliciaReusableActions.logOut();
                simpliciaReusableActions.logIn(TestData.LAWYEREMAIL, TestData.PASSWORD);
                Thread.sleep(15000);
                LOGGER.info("Logged in as lawyer " + TestData.LAWYEREMAIL);
                // Navigate to left navigation menu
                navigateToDatOption("Mes DATs", true);
                LOGGER.info("Lawyer checking if dat is found: " + sImportedDATNumeroSS);
                bDat = datMainPage.isDatPresentOnDashboard(sImportedDATNumeroSS);
                if(bDat) {
                    LOGGER.info("Lawyer checked, dat is found: " + sImportedDATNumeroSS);
                	//commented by Paul on 20/11/2021 since below String creation generates error and is not used afterwards 
                    //(it was a test of comment feature while here we test lawyer review of DAT form)
                    //String sClientComment = datMainPage.clientMessage();

                    //Assert.assertEquals(sClientComment, sComment);

                    // click on approve button //Check if working
                    boolean bApproved =  datMainPage.approveDATReview();
                    LOGGER.info("Lawyer approved dat: " + sImportedDATNumeroSS);

                    if(bApproved){

                        LOGGER.info("Lawyer approved dat: " + sImportedDATNumeroSS);

                        // log out of the lawyer portal
                        LOGGER.info("Logging out, will log back in as client");
                        simpliciaReusableActions.logOut();

                        // login to user/client
                        simpliciaReusableActions.logIn();
                        LOGGER.info("Logged back in as client");
                        navigateToDatOption("Mes DATs", true);
                        sleepSilently(10000);

                        // validate if approved by lawyer button is displayed or not
                        LOGGER.info("Client checking if dat is found and approved: " + sImportedDATNumeroSS);
                        bDat = datMainPage.isDatPresentOnDashboard(sImportedDATNumeroSS);
                        if (bDat) {
                            LOGGER.info("Dat found: " + sImportedDATNumeroSS);
                        } else {
                            LOGGER.info("Dat NOT found: " + sImportedDATNumeroSS);
                        }
                        if (datMainPage.isDATApproved()) {
                            LOGGER.info("Dat approved: " + sImportedDATNumeroSS);
                        } else {
                            LOGGER.info("Dat NOT approved: " + sImportedDATNumeroSS);
                        }

                        Assert.assertTrue(datMainPage.isDATApproved(), "DAT review is approved by lawyer");
                    } else {
                        LOGGER.info("Lawyer FAILED to approve dat: " + sImportedDATNumeroSS);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage(), e);
        } finally {
            try {
                if (bDat) {
                    cleanDAT();
                } else {
                    //resetBrowser();
                    LOGGER.info("###################### No DAT was Created ##############################");
                }
                isDATClosed();
            } finally {
                simpliciaReusableActions.logOut();
            }
        }
    }

    /**
     * Steps:
     * 1. Create AT
     * 2. Go to Mes DAT dash board
     * 3. Search for the created DAT using numero SS
     * 4. Scroll horizontally, to display Envoi Net-entreprises icon
     * 5. Click on email icon
     * 6. Click ok on alert
     * 7. Validate if it is submitted or not
     * 8. Clean up the test case - delete the created DAT.
     *
     * @throws Exception
     */

    @Test(enabled=true)
    @Description("create a DAT, submit to the health insurance company")
    public void dat_Test_06_Submit_To_Health_Insurance() throws Exception {
            boolean bVerified = false;

        try {

            LOGGER.info("*** Step 1 Action: Login the simplicia page ***");
            simpliciaReusableActions.logIn();

            LOGGER.info("*** Step 2 Action: Navigate to Mes DATs ***");
            navigateToDatOption("Mes DATs", true);

            // create dat
            String sNumeroSS = createDAT();
            bDat = datMainPage.isDatPresentOnDashboard(sNumeroSS);

            // update DAT
            if (bDat) {

                boolean bIncomplete = datMainPage.isDATInComplete();

                LOGGER.info("bIncomplete=" + bIncomplete);
                if(bIncomplete){
                    datMainPage.editDAT();
                    updateDATProfile();
                    saveAndValidateDAT();

                }

                // Send DAT to envoi
                boolean bSent = datMainPage.sendDATToHealthInsurance(sNumeroSS);

                if(bSent)

                {
                    bVerified = datMainPage.validateDATSentToHealthInsuranceSuccesfuly(sNumeroSS);


                }

              // Assert that DAT is sent to health insurance
                Assert.assertTrue(bVerified, "The DAT is sent to health insurance successfully");

            }

        } catch (Exception e) {

            e.printStackTrace();
            Assert.fail(e.getMessage(), e);

        } finally {
            try {
                if (bDat) {

                    cleanDAT();

                } else {
                    // resetBrowser();
                    LOGGER.info("###################### No DAT was Created ##############################");
                }

                isDATClosed();
            } finally {
                simpliciaReusableActions.logOut();
            }
        }
    }


    /**
     * Steps:
     * 1. Convert the dat zip to data xml.
     * 2. Go to DAT dashboard.
     * 3. Click on create new DAT.
     * 4. use above created data xml as input, create the DAT.
     * 5. Export and convert the dat to xml again
     * 6. Compare the two XML's
     * 5. CleanUp the test case- Delete the created DAT
     *
     * @throws Exception
     */
    @Test(enabled=true)
    @Description("Convert DAT.zip to data XML, create a DAT, validate and delete it")
    public void dat_Test_07_ConvertZip_To_XML_CREATE_DAT() throws Exception {

        LOGGER.info("*** Step 1 Action: Login the simplicia page ***");
        simpliciaReusableActions.logIn();

        LOGGER.info("*** Step 2 Action: Navigate to Mes DATs ***");
        navigateToDatOption("Mes DATs", true);

        // delete any existing file
        LOGGER.info("sConvertToXMLPath is: "+ sConvertToXMLPath);
        deleteFile(sConvertToXMLPath, ".xml");
        deleteFile(sConvertToXMLPath, "datdownload.zip");

        try {
        	String datXmlInputPath = DatZipToXml.convertDatZipToXml(new File(sConvertToXMLPath + File.separator + "datimport.zip"), "IN");
            LOGGER.info("datimport: " + datXmlInputPath);
            
            String sNumeroSS = createDATFromConvertedXML(datXmlInputPath, "", "datimport.zip.DAT-IN.xml", false, true);

            // Assert that DAT is displayed on main DAT dashboard
            bDat = datMainPage.isDatPresentOnDashboard(sNumeroSS);
            Assert.assertTrue(bDat, "The Numero SS is present on the dashboard");

            //export the DAT.
            datMainPage.downloadDATZIP("dat.zip");

            // copy downloaded file to zip folder
          /*  Path sourcePath = Paths.get(getResourcePath(""));
            Path destination = Paths.get(sConvertToXMLPath);
            Files.copy(sourcePath.toFile(), destination.resolve(sourcePath.getFileName()).toFile(), StandardCopyOption.REPLACE_EXISTING );*/

            //delete file dat.zip, if already exists
            deleteExistingZIP(sConvertToXMLPath, "dat.zip");
            String resourcePath = downloadFilePath;
            LOGGER.info("resourcePath: " + resourcePath);
            listFilesInDir(resourcePath);
            File source = new File(resourcePath + File.separator + "dat.zip");
            File dest = new File(sConvertToXMLPath + File.separator + "datdownload.zip");
            LOGGER.info("source.toPath(): " + source.toPath());
            LOGGER.info("dest.toPath(): " + dest.toPath());
            Files.copy(source.toPath(), dest.toPath());

            // Convert the exported zip to XML
            String datXmlOutputPath = DatZipToXml.convertDatZipToXml(new File(sConvertToXMLPath + File.separator + "datdownload.zip"), "OUT");
            LOGGER.info("datdownload: "+ datXmlOutputPath);

            // Compare the first and second XML
            CompareXMLFiles.compareAndValidateXMLDiff(datXmlInputPath, datXmlOutputPath);

        } catch (Exception e) {

            e.printStackTrace();
            Assert.fail(e.getMessage(), e);

        } finally {
            try {
                if (bDat) {

                    cleanDAT();


                } else {

                    //resetBrowser();
                    LOGGER.info("###################### No DAT was created ##############################");
                }

                isDATClosed();
            } finally {
                simpliciaReusableActions.logOut();
            }
        }
    }

    /**
     * Method will be called @ finally block, delete the created DAT
     */
    private void cleanDAT() {

        LOGGER.info("###################### Deleting  the DAT ##############################");
        datMainPage.performHorizontalScroll();
        retry(() -> datMainPage.DeleteDAT());
        datMainPage.validateDeleteDAT();
    }

    //@Test(enabled=false)
//    public void delteDATS() throws Exception {
//
//        for (int i = 0; i < 50; i++) {
//            datMainPage.clickSearch();
//            datMainPage.performHorizontalScroll();
//            datMainPage.DeleteDAT();
//            Thread.sleep(2000);
//        }
//    }

    /**
     * Method to create a dat
     *
     * @throws Exception
     */

    // #####################################
    //Private Methods of this test class
    //#####################################

    /**
     * Navigate to left menu and click on desired submenu
     * if you are already logged in no need to expand the Menu, so pass b as false either true
     *
     * @param sMenu
     * @param b
     */
    private void navigateToDatOption(String sMenu, boolean b) throws Exception{
        try {
            /*homePage.navigateToSimpliciaImage();
            //ControlledElement menuHomeText = new ControlledElement(browser, By.xpath("//*[@id=\"fuse-navbar\"]/div/div/header/div/div/p"),"Home");
            //boolean b2 = menuHomeText.getControlledObject().isDisplayed();
            //leftNavigationMenuPage.CheckPresenceOfSaasText();
            ///boolean c = leftNavigationMenuPage.isExpandMore();
            leftNavigationMenuPage.clickToOpenMenu();
            if (b) {
                //Thread.sleep(5000);
                leftNavigationMenuPage.clickExpandIconToOpenMenu("DAT");
            }


            leftNavigationMenuPage.clickExpandIconToOpenMenu("Mes DATs");
            leftNavigationMenuPage.clickMenuItemInLeftNavigation(sMenu);
            leftNavigationMenuPage.closeLeftNavigation();*/
            leftNavigationMenuPage.openMyDATSPage();
        } catch (Exception e) {
            LOGGER.info(e.getMessage());
        }
    }

    private void switchNavigationBetweenDATOption(List<String> ls) throws Exception {

        for (String s : ls) {

            navigateToDatOption(s, false);

        }
    }

    /**
     * Create complete DAT
     *
     * @param bUploadFile
     * @param bMyProfile
     * @param bPersonneAvisee
     * @return
     * @throws Exception
     */
    private String createDAT(boolean bUploadFile, boolean bMyProfile, boolean bPersonneAvisee) throws Exception {

        Map<String, String> mDATMyProfile = TestDataEngine.parseXML(sDATTestDataXMLPath, "CreateDAT.xml", "myProfileDetails");
        Map<String, String> mLAVictimeDetails = TestDataEngine.parseXML(sDATTestDataXMLPath, "CreateDAT.xml", "laVictimeDetails");
        Map<String, String> accident1Details = TestDataEngine.parseXML(sDATTestDataXMLPath, "CreateDAT.xml", "accident1Details");
        Map<String, String> accident2Details = TestDataEngine.parseXML(sDATTestDataXMLPath, "CreateDAT.xml", "accident2Details");
        Map<String, String> lesTemonisDetails = TestDataEngine.parseXML(sDATTestDataXMLPath, "CreateDAT.xml", "lesTemonisDetails");
        Map<String, String> signatureDetails = TestDataEngine.parseXML(sDATTestDataXMLPath, "CreateDAT.xml", "signatureDetails");
        Map<String, String> leTiersDetails = TestDataEngine.parseXML(sDATTestDataXMLPath, "CreateDAT.xml", "leTiersDetails");
        // create dat
        datMainPage.clickCreateNewDATButton();

        if (bMyProfile) {

            myProfileTab.fillMyProfileDetails(mDATMyProfile, signatureDetails, true, false);

        } else {

            myProfileTab.fillMyProfileDetails(mDATMyProfile, signatureDetails, false, false);
        }

        // Click on next to go to LA VICTIME tab,Enter la victime details
        datMainPage.clickNextButton();
        String sNumeroSS = laVictimeTab.fillLaVictimeDetails(mLAVictimeDetails);
        LOGGER.info("############################ Victime numero SS is :" + sNumeroSS + " ####################################");

        // Click on next to go to Accident 1 tab, Enter accident1  details
        datMainPage.clickNextButton();
        accident1Details.put("TXT_FIELD_SIRET", "7" + TestDataEngine.generateRandomNumber(13));
        accident1Tab.fillAccidentTab1Details(accident1Details);

        // Click on next to go to Accident 2 tab, enter accident2 step details.
        datMainPage.clickNextButton();
        accident2Tab.fillAccident2TabDetails(accident2Details);

        // Click on next to go to LES TÉMOINS , enter les taminos details
        datMainPage.clickNextButton();

        if (bPersonneAvisee) {

            lesTemoinsTab.fillLesTemoinsTab(lesTemonisDetails, false, false, false, false);

        } else {

            lesTemoinsTab.fillLesTemoinsTab(lesTemonisDetails, true, true, true, true);
        }


        // Click on next to go to LE TIERS
        datMainPage.clickNextButton();
        leTiersTab.fillleTiersTabDetails(leTiersDetails);

        // Click on next to go to signature tab, fill signature details and save and exit
        datMainPage.clickNextButton();
        signatureTab.fillSignatureTabDetails(signatureDetails);

        //upload a file
        if (bUploadFile) {

            signatureTab.uploadFile(sDATTestDataXMLPath, "CreateDAT.xml");
        }
       saveAndValidateDAT();

        return sNumeroSS;
    }
    private String createDAT(String sDATDataXMlPath, String sXMLFileName, boolean bUploadFile, boolean bMyProfile, boolean bPersonneAvisee) throws Exception {

        Map<String, String> mDATMyProfile = TestDataEngine.parseXML(sDATDataXMlPath, sXMLFileName, "myProfileDetails");
        Map<String, String> mLAVictimeDetails = TestDataEngine.parseXML(sDATDataXMlPath, sXMLFileName, "laVictimeDetails");
        Map<String, String> accident1Details = TestDataEngine.parseXML(sDATDataXMlPath, sXMLFileName, "accident1Details");
        Map<String, String> accident2Details = TestDataEngine.parseXML(sDATDataXMlPath, sXMLFileName, "accident2Details");
        Map<String, String> lesTemonisDetails = TestDataEngine.parseXML(sDATDataXMlPath, sXMLFileName, "lesTemonisDetails");
        Map<String, String> signatureDetails = TestDataEngine.parseXML(sDATDataXMlPath, sXMLFileName, "signatureDetails");
        Map<String, String> leTiersDetails = TestDataEngine.parseXML(sDATDataXMlPath, sXMLFileName, "leTiersDetails");
        // create dat
        datMainPage.clickCreateNewDATButton();

        if (bMyProfile) {

            myProfileTab.fillMyProfileDetails(mDATMyProfile, signatureDetails,true, false);

        } else {

            myProfileTab.fillMyProfileDetails(mDATMyProfile, signatureDetails,false, false);
        }

        // Click on next to go to LA VICTIME tab,Enter la victime details
        datMainPage.clickNextButton();
        String sNumeroSS = laVictimeTab.fillLaVictimeDetails(mLAVictimeDetails);
        LOGGER.info("############################ Victime numero SS is :" + sNumeroSS + " ####################################");

        // Click on next to go to Accident 1 tab, Enter accident1  details
        datMainPage.clickNextButton();
        accident1Details.put("TXT_FIELD_SIRET", "7" + TestDataEngine.generateRandomNumber(13));
        accident1Tab.fillAccidentTab1Details(accident1Details);

        // Click on next to go to Accident 2 tab, enter accident2 step details.
        datMainPage.clickNextButton();
        accident2Tab.fillAccident2TabDetails(accident2Details);

        // Click on next to go to LES TÉMOINS , enter les taminos details
        datMainPage.clickNextButton();

        if (bPersonneAvisee) {

            lesTemoinsTab.fillLesTemoinsTab(lesTemonisDetails, false, false, false, false);

        } else {

            lesTemoinsTab.fillLesTemoinsTab(lesTemonisDetails, true, true, true, true);
        }


        // Click on next to go to LE TIERS
        datMainPage.clickNextButton();
        leTiersTab.fillleTiersTabDetails(leTiersDetails);

        // Click on next to go to signature tab, fill signature details and save and exit
        datMainPage.clickNextButton();
        signatureTab.fillSignatureTabDetails(signatureDetails);

        //upload a file
        if (bUploadFile) {

            signatureTab.uploadFile(sDATTestDataXMLPath, "CreateDAT.xml");
        }

        saveAndValidateDAT();

        return sNumeroSS;
    }

    private String createDATFromConvertedXML(String sDATDataXMlPath, String sXMLFileName, String fileNameToUpload, boolean bMyProfile, boolean bPersonneAvisee) throws Exception {

        Map<String, String> mDATMyProfile = TestDataEngine.parseXML(sDATDataXMlPath, sXMLFileName, "myProfileDetails");
        Map<String, String> mLAVictimeDetails = TestDataEngine.parseXML(sDATDataXMlPath, sXMLFileName, "laVictimeDetails");

        LOGGER.info("mLAVictimeDetails: " + ToStringBuilder.reflectionToString(mLAVictimeDetails, ToStringStyle.SIMPLE_STYLE));

        Map<String, String> accident1Details = TestDataEngine.parseXML(sDATDataXMlPath, sXMLFileName, "accident1Details");
        Map<String, String> accident2Details = TestDataEngine.parseXML(sDATDataXMlPath, sXMLFileName, "accident2Details");
        Map<String, String> lesTemonisDetails = TestDataEngine.parseXML(sDATDataXMlPath, sXMLFileName, "lesTemonisDetails");
        Map<String, String> signatureDetails = TestDataEngine.parseXML(sDATDataXMlPath, sXMLFileName, "signatureDetails");
        Map<String, String> leTiersDetails = TestDataEngine.parseXML(sDATDataXMlPath, sXMLFileName, "leTiersDetails");
        // create dat
        datMainPage.clickCreateNewDATButton();

        if (bMyProfile) {

            myProfileTab.fillMyProfileDetails(mDATMyProfile, signatureDetails, true, false);

        } else {

            myProfileTab.fillMyProfileDetails(mDATMyProfile, signatureDetails, true, true);
        }

        // Click on next to go to LA VICTIME tab,Enter la victime details
        datMainPage.clickNextButton();
        String sNumeroSS = laVictimeTab.fillLaVictimeDetails(mLAVictimeDetails);
        LOGGER.info("############################ Victime numero SS is :" + sNumeroSS + " ####################################");

        // Click on next to go to Accident 1 tab, Enter accident1  details
        datMainPage.clickNextButton();
        /*String sSiretAcc = mLAVictimeDetails.get("EU_SIRET")
        accident1Details.put("TXT_FIELD_SIRET", sSiretAcc);*/
        accident1Tab.fillAccidentTab1Details(accident1Details);

        // Click on next to go to Accident 2 tab, enter accident2 step details.
        datMainPage.clickNextButton();
        accident2Tab.fillAccident2TabDetails(accident2Details);

        // Click on next to go to LES TÉMOINS , enter les taminos details
        datMainPage.clickNextButton();

        if (bPersonneAvisee) {

            lesTemoinsTab.fillLesTemoinsTab(lesTemonisDetails, false, false, false, false);

        } else {

            lesTemoinsTab.fillLesTemoinsTab(lesTemonisDetails, true, true, true, true);
        }


        // Click on next to go to LE TIERS
        datMainPage.clickNextButton();
        leTiersTab.fillleTiersTabDetails(leTiersDetails);

        // Click on next to go to signature tab, fill signature details and save and exit
        datMainPage.clickNextButton();
        signatureTab.fillSignatureTabDetails(signatureDetails);

        //upload a file
        if (StringUtils.isNotBlank(fileNameToUpload)) {
            signatureTab.uploadFile(sConvertToXMLPath, fileNameToUpload);
        }


        saveAndValidateDAT();

        return sNumeroSS;
    }


    private String createDAT() throws Exception {

        Map<String, String> mDATMyProfile = TestDataEngine.parseXML(sDATTestDataXMLPath, "CreateDAT.xml", "myProfileDetails");
        Map<String, String> mLAVictimeDetails = TestDataEngine.parseXML(sDATTestDataXMLPath, "CreateDAT.xml", "laVictimeDetails");
        Map<String, String> accident1Details = TestDataEngine.parseXML(sDATTestDataXMLPath, "CreateDAT.xml", "accident1Details");
        Map<String, String> accident2Details = TestDataEngine.parseXML(sDATTestDataXMLPath, "CreateDAT.xml", "accident2Details");
        Map<String, String> lesTemonisDetails = TestDataEngine.parseXML(sDATTestDataXMLPath, "CreateDAT.xml", "lesTemonisDetails");
        Map<String, String> signatureDetails = TestDataEngine.parseXML(sDATTestDataXMLPath, "CreateDAT.xml", "signatureDetails");
        Map<String, String> leTiersDetails = TestDataEngine.parseXML(sDATTestDataXMLPath, "CreateDAT.xml", "leTiersDetails");
        // create dat
        datMainPage.clickCreateNewDATButton();
        myProfileTab.fillMyProfileDetails(mDATMyProfile, signatureDetails, false, false);

        // Click on next to go to LA VICTIME tab,Enter la victime details
        datMainPage.clickNextButton();
        String sNumeroSS = laVictimeTab.fillLaVictimeDetails(mLAVictimeDetails);
        LOGGER.info("############################ Victime numero SS is :" + sNumeroSS + " ####################################");

        // Click on next to go to Accident 1 tab, Enter accident1  details
        datMainPage.clickNextButton();
        accident1Details.put("TXT_FIELD_SIRET", "7" + TestDataEngine.generateRandomNumber(13));
        accident1Tab.fillAccidentTab1Details(accident1Details);

        // Click on next to go to Accident 2 tab, enter accident2 step details.
        datMainPage.clickNextButton();
        accident2Tab.fillAccident2TabDetails(accident2Details);

        // Click on next to go to LES TÉMOINS , enter les taminos details
        datMainPage.clickNextButton();

        // Click on next to go to LE TIERS
        datMainPage.clickNextButton();

        // Click on next to go to signature tab, fill signature details and save and exit
        datMainPage.clickNextButton();
        signatureTab.fillSignatureTabDetails(signatureDetails);

        saveAndValidateDAT();

        return sNumeroSS;
    }

    /**
     * Save and validate the DAT creation or update
     */
    private void saveAndValidateDAT() {
        // click on save and exit DAT
        signatureTab.clickOnSaveAndExit();
        // assert that success message is displayed
//        datMainPage.validateDATSaved();
    }


    /**
     * update the DAT
     *
     * @return
     * @throws Exception
     */
    private String updateDAT() throws Exception {

        Map<String, String> updateRegitrationNumber = TestDataEngine.parseXML(sDATTestDataXMLPath, "CreateDAT.xml", "laVictimeDetails");

        datMainPage.clickOnStepLink(2);
        sleepSilently(2000);

        // update registration number
        String sUpdatedRegistrationNumber = laVictimeTab.fillRegistrationNumber(updateRegitrationNumber);

        return sUpdatedRegistrationNumber;

    }

    private void updateDATProfile() throws Exception {

        Map<String, String> update = TestDataEngine.parseXML(sDATTestDataXMLPath, "CreateDAT.xml", "myProfileDetails");



        // update registration number
        sleepSilently(10000);
        myProfileTab.enterEmplacememnnts(update);
        datMainPage.clickOnStepLink(1);


    }

    /**
     * Delete the downloaded file , if exists
     *
     * @param sExtension
     */
    private void deleteZipFile(String sExtension) {

        File file = new File(getResourcePath(""));
        File[] fList = file.listFiles();

        for (File f : fList) {

            if (f.getName().endsWith(sExtension)) {

                f.delete();

                LOGGER.info(" <<<<<<<<<<  File deleted is " + f + " >>>>>>>>>>");
            }

        }
    }

    private void deleteFile(String sPath, String sExtension) {

        File file = new File(sPath);
        File[] fList = file.listFiles();

        for (File f : fList) {

            if (f.getName().endsWith(sExtension)) {

                f.delete();

                LOGGER.info(" <<<<<<<<<<  File deleted is " + f + " >>>>>>>>>>");
            }

        }
    }

    private void deleteExistingZIP(String sPath, String fileName) {

        File file = new File(sPath);
        File[] fList = file.listFiles();

        for (File f : fList) {

            if (f.getName().equals(fileName)) {

                f.delete();

                LOGGER.info(" <<<<<<<<<<  File deleted is " + f + " >>>>>>>>>>");
            }

        }
    }

    /**
     * if due to error DA form is not closed, logout will not happen, so in that case lets close it before attempting logout
     */
    private boolean isDATClosed() throws Exception{
        boolean bClosed = false;

        if (datMainPage.closeDATNoChange()) {

            bClosed = true;
        }

        return bClosed;
    }

//    /**
//     * Reset the browser if dat form is not closed
//     * @throws InterruptedException
//     */
//    private void resetBrowser() throws InterruptedException {
//        println("###################### Closing the browser ##############################");
//
//        //browser.close();
//        Thread.sleep(2000);
//        println("###################### opening the browser ##############################");
//        browser.navigate().to(TestData.APPLICATION_URL);
//        Thread.sleep(2000);
//        println("###################### Logging again to logout safely ##############################");
//         simpliciaReusableActions.logOut();
//        Thread.sleep(2000);
//    }



}
