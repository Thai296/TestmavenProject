package com.simplicia.web.dat;

import com.simplicia.functions.CompareXMLFiles;
import com.simplicia.pages.web.dat.DATMainPage;
import com.simplicia.web.LoginOutClientPerMethodBaseTest;
import io.qameta.allure.Description;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.testng.Assert;
import org.testng.annotations.Test;


/**
 * - import dat
 * - then verify if the exported dat is as expected
 * - don't test dat form
 */
public class DatImportVsExportTest extends LoginOutClientPerMethodBaseTest {
    public static String datsInputPath = FilenameUtils.separatorsToSystem(System.getProperty("user.dir") + File.separator + "src/test/resources/dat-import-vs-export/datimport2.zip");
    public static String expectedOutputFolder = FilenameUtils.separatorsToSystem(System.getProperty("user.dir") + File.separator + "src/test/resources/dat-import-vs-export/expected-output/");
    private String nns1 = "265124221808710";
    private String nns2 = "272094220708746";

    @Test
    @Description("Test the exported DAT should be the same as the imported file")
    public void testImportVsExport() throws Exception {

        homePage.openLeftNavigation();
        //leftNavigationMenuPage.clickExpandIconToOpenMenu("DAT");
        //leftNavigationMenuPage.checkPresenceOfLeftNavigationMenuOption("Mes DATs");
        //leftNavigationMenuPage.clickMenuItemInLeftNavigation("Mes DATs");
        leftNavigationMenuPage.openMyDATSPage();
        //leftNavigationMenuPage.closeLeftNavigation();

        boolean dat1Imported = false;
        boolean dat2Imported = false;
        try {

            println("importing DAT: " + datsInputPath);
            datMainPage.importDATF(datsInputPath, DATMainPage.DAT_IMPORT_SUCCESS_MESSAGE_2);
            dat2Imported = datMainPage.isDatPresentOnDashboard(nns2);
            dat1Imported = datMainPage.isDatPresentOnDashboard(nns1);
            Assert.assertTrue(dat2Imported, "DAT 2 is imported successfully");
            println("Dat imported successfully: " + nns2);
            Assert.assertTrue(dat1Imported, "DAT 1 is imported successfully");
            println("Dat imported successfully: " + nns1);

            // download DAT 1
            if (browser.getCurrentUrl().contains("staging")
                    || browser.getCurrentUrl().contains("test-deploy")
                    || browser.getCurrentUrl().contains("localhost")) {

                println("Download DAT to verify: " + nns1);
                Map<String, Object> datFiles = datMainPage.downloadDATZIPWithFiles("dat.zip");
                // 2 files: EDA-DAT.xml and DAT.xml
                String expectedDatBodyXml = readXml(FilenameUtils.concat(expectedOutputFolder, "DAT.xml"));
                String expectedDatHeaderXml = readXml(FilenameUtils.concat(expectedOutputFolder, "EDA-DAT.xml"));

                String actualDatBodyXml = (String) datFiles.get("DAT.xml");
                String actualDatHeaderXml = (String) datFiles.get("EDA-DAT.xml");

                println("Comparing DAT body import vs export: " + nns1);
                CompareXMLFiles.compareAndValidateXMLContentDiff(expectedDatBodyXml, actualDatBodyXml);
                println("Compare DONE, DAT body import vs export: " + nns1);

                println("Comparing DAT header import vs export: " + nns1);
                CompareXMLFiles.compareAndValidateXMLContentDiff(expectedDatHeaderXml, actualDatHeaderXml);
                println("Compare DONE, DAT header import vs export: " + nns1);
            }

        } finally {

            println("############### Deleting the 2 DATs #################");

            if (dat2Imported) {
                deleteDatSilently(nns2);
            }
            if (dat1Imported) {
                deleteDatSilently(nns1);
            }
        }

    }

    private void deleteDatSilently(String nns) {
        try {
            // filter to see dat
            boolean found = datMainPage.isDatPresentOnDashboard(nns);
            if (!found) {
                return;
            }
            println("############### Deleting the DAT " + nns + " #################");
            datMainPage.performHorizontalScroll();
            retry(() -> datMainPage.DeleteDAT());
            datMainPage.validateDeleteDAT();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String readXml(String path) {
        try {
            return FileUtils.readFileToString(new File(path), "UTF-8");
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

}