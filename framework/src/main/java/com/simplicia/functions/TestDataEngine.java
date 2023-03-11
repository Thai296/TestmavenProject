package com.simplicia.functions;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

/**
 * This class will serve as test data engine to data driven test cases
 */

public class TestDataEngine {

    /**
     * This method will parse the given xml and return map of xpaths locator and their values
     *
     * @param filePath  - XML path
     * @param sFile:    filename
     * @param sTagName: xml tag for data e.g
     *                  <login>
     *                  <param name = "LOGIN_INPUT">Test</param>		 *
     *                  </Login>
     * @return
     * @throws Exception
     */
    public static Map<String, String> parseXML(String filePath, String sFile, String sTagName) throws Exception {

        // Use linked hashmap so that insertion order is maintained
        LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
        File file = new File(filePath + File.separator + sFile);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(file);

        // get the parent node
        NodeList nList = document.getElementsByTagName("test");

        // get child nodes from parent
        NodeList parentdNodes = nList.item(0).getChildNodes();

        // Loop thru the child nodes and further get element nodes of the child
        // nodes
        for (int i = 0; i < parentdNodes.getLength(); i++) {

            NodeList childNodes = parentdNodes.item(i).getChildNodes();

            for (int child = 0; child < childNodes.getLength(); child++) {

                // compare child nodes with node name you want to parse
                if (parentdNodes.item(i).getNodeName().equals(sTagName)) {


                    try {

                        // get the attribute value and split the attribute
                        String[] sKey = ((childNodes
                                .item(child).getAttributes().getNamedItem("name"))
                                .toString()).split("=");

                        // get the content and if value to be randomize replace
                        // the _ random with random values
                        String sValue = childNodes
                                .item(child).getTextContent();

                        String sRandomName = generateRandomName(6);
                        if (sValue.contains("_random") && !sValue.equals("random_email") && !sValue.equals("randomNumber")) {
                            sValue = sValue.replace("_random", sRandomName);
                        } else if (sValue.contains("random") && !sValue.equals("random_email") && !sValue.equals("randomNumber")) {
                            sValue = sValue.replace("random", sRandomName);
                        } else if (sValue.contains("random_email")) {
                            sRandomName = sRandomName.toLowerCase();
                            sValue = sValue.replace("random_email", sRandomName + "@gmail.com");
                        }
                        map.put(sKey[1].replaceAll("\"", ""), sValue);
                    } catch (Exception e) {
                        e.getMessage();
                    }
                }
            }


        }

        return map;

    }

    /**
     * Generate random name for name, address etc.
     *
     * @param noOfChar
     * @return random name
     * <p>
     * e.g. getRandomName(5) will create name as MGNHF
     */
    public static String generateRandomName(int noOfChar) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        // define character array of alphabets
        char[] chr = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        String name = "";

        // Traverse thru the array for noOfChar times and append random char to
        // stringbuilder's reference
        for (int i = 0; i < noOfChar; i++) {
            char c = chr[random.nextInt(chr.length)];
            sb.append(c);
        }
        name = sb.toString().toUpperCase();
        return name;
    }


    /**
     * Generate random number for mobile etc. e.g. For Mobile number enter
     * num as 9 and prefix it with some digit greater then 0. otherwise if first
     * digit is 0 that would be wrong phone number.
     *
     * @param num
     * @return randomly generated number
     */
    public static String generateRandomNumber(int num) {
        Random random = new Random();
        String number = "";

        for (int i = 0; i < num; i++) {

            number = number + Integer.toString(random.nextInt(9));

        }

        return number;
    }
}


