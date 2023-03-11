
package com.simplicia.functions;

import org.custommonkey.xmlunit.DetailedDiff;
import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.Difference;
import org.custommonkey.xmlunit.XMLUnit;
import org.testng.Assert;
import org.xml.sax.SAXException;
import util.CommonUtil;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CompareXMLFiles {
    private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(CompareXMLFiles.class);
    private static final List<String> WHITELIST_DIFF_XPATH =
            Arrays.asList(
                    // skip the file upload and download path
                    "/test[1]/signatureDetails[1]/param[6]/text()[1]",
                    // S2507: the backend doesn't export TXT_FIELD_MENTION_DISTB (lieuAdresseMentionDistribution) anymore
                    "/test[1]/accident1Details[1]/param[12]/text()[1]",

                    // ignore dat id and time when comparing dat import vs. export
                    "/DAT[1]/Identifiant[1]/text()[1]",
                    "/Entete[1]/Identification[1]/text()[1]",
                    "/Entete[1]/Temps[1]/text()[1]",
                    "/Entete[1]/Document[1]/Mindex[1]/Temps[1]/text()[1]",
                    "/Entete[1]/Emetteur[1]/Agrement[1]/Id[1]/text()[1]",
                    "/Entete[1]/Emetteur[1]/Agrement[1]/Version[1]/text()[1]",
                    "/Entete[1]/Fonction[1]/text()[1]",
                    "/Entete[1]/Document[1]/Mindex[1]/Fonction[1]/text()[1]"
            );

    public static void compareAndValidateXMLContentDiff(String sSourceXML, String sTargetXML) throws SAXException, IOException {
        println("sSourceXML:");
        println(sSourceXML);
        println("sTargetXML:");
        println(sTargetXML);

        InputStream i1 = new ByteArrayInputStream(sSourceXML.getBytes(StandardCharsets.UTF_8));
        InputStream i2 = new ByteArrayInputStream(sTargetXML.getBytes(StandardCharsets.UTF_8));
        compareAndValidateXMLDiff(i1, i2);
    }

    public static void compareAndValidateXMLDiff(String sSourceXMLPath, String sTargetXMLPath) throws FileNotFoundException,
            SAXException, IOException {
        // reading two xml file to compare in Java program
        FileInputStream fis1 = new FileInputStream(sSourceXMLPath);
        FileInputStream fis2 = new FileInputStream(sTargetXMLPath);
        compareAndValidateXMLDiff(fis1, fis2);
    }

    private static void compareAndValidateXMLDiff(InputStream sourceStream, InputStream targetStream) throws FileNotFoundException,
            SAXException, IOException {

        try {
            // using BufferedReader for improved performance
            BufferedReader source = new BufferedReader(new InputStreamReader(sourceStream));
            BufferedReader target = new BufferedReader(new InputStreamReader(targetStream));
            //configuring XMLUnit to ignore white spaces
            XMLUnit.setIgnoreWhitespace(true);

            //comparing two XML using XMLUnit in Java
            List<Difference> differences = compareXML(source, target);

            //showing differences found in two xml files
            printDifferences(differences);

            if (!differences.isEmpty()) {
                // there is a difference, failed
                Assert.fail("Found " + differences.size() + " differences between imported and downloaded dat ZIP.");
            } else {
                println("Found no difference between 2 xml files");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    protected static void println(String message) {
        LOGGER.info(CommonUtil.logPrefix() + message);
    }

    public static List<Difference> compareXML(Reader source, Reader target) throws
            SAXException, IOException{

        //creating Diff instance to compare two XML files
        Diff xmlDiff = new Diff(source, target);

        //for getting detailed differences between two xml files
        DetailedDiff detailXmlDiff = new DetailedDiff(xmlDiff);


        List<Difference> allDifferences = detailXmlDiff.getAllDifferences();

        List<Difference> result = new ArrayList<>(allDifferences.size());
        for (Difference diff : allDifferences) {
            if (!isFalseDifference(diff)) {
                result.add(diff);
            }
        }

        return result;
    }

    private static boolean isFalseDifference(Difference diff) {
        if (diff.getControlNodeDetail() != null
            && WHITELIST_DIFF_XPATH.contains(diff.getControlNodeDetail().getXpathLocation())) {
            return true;
        }
        if (diff.getTestNodeDetail() != null
                && WHITELIST_DIFF_XPATH.contains(diff.getTestNodeDetail().getXpathLocation())) {
            return true;
        }
        return false;
    }

    public static void printDifferences(List<Difference> differences) {
        int totalDifferences = differences.size();
        LOGGER.info(CommonUtil.logPrefix() + "===============================");
        LOGGER.info(CommonUtil.logPrefix() + "Total differences : " + totalDifferences);
        LOGGER.info(CommonUtil.logPrefix() + "================================");
        for (Difference difference : differences) {
            LOGGER.info(CommonUtil.logPrefix() + difference);
        }
    }




}

