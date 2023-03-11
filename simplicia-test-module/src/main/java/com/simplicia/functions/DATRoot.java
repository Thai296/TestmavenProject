package com.simplicia.functions;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;

public class DATRoot {

    @JacksonXmlElementWrapper(localName = "myProfileDetails")
    private MyProfileDetails myProfileDetails = new MyProfileDetails();

    @JacksonXmlElementWrapper(localName = "accident1Details")
    private Accident1Details accident1Details = new Accident1Details();

    @JacksonXmlElementWrapper(localName = "accident2Details")
    private Accident2Details accident2Details = new Accident2Details();

    @JacksonXmlElementWrapper(localName = "laVictimeDetails")
    private LaVictimeDetails laVictimeDetails = new LaVictimeDetails();

    @JacksonXmlElementWrapper(localName = "lesTemonisDetails")
    private LesTemonisDetails lesTemonisDetails = new LesTemonisDetails();

    @JacksonXmlElementWrapper(localName = "leTiersDetails")
    private LeTiersDetails leTiersDetails = new LeTiersDetails();

    @JacksonXmlElementWrapper(localName = "signatureDetails")
    private SignatureDetails signatureDetails = new SignatureDetails();

    public MyProfileDetails getMyProfileDetails() {
        return myProfileDetails;
    }

    public void setMyProfileDetails(MyProfileDetails myProfileDetails) {
        this.myProfileDetails = myProfileDetails;
    }

    public Accident2Details getAccident2Details() {
        return accident2Details;
    }

    public void setAccident2Details(Accident2Details accident2Details) {
        this.accident2Details = accident2Details;
    }

    public Accident1Details getAccident1Details() {
        return accident1Details;
    }

    public void setAccident1Details(Accident1Details accident1Details) {
        this.accident1Details = accident1Details;
    }

    public LesTemonisDetails getLesTemonisDetails() {
        return lesTemonisDetails;
    }

    public void setLesTemonisDetails(LesTemonisDetails lesTemonisDetails) {
        this.lesTemonisDetails = lesTemonisDetails;
    }

    public LeTiersDetails getLeTiersDetails() {
        return leTiersDetails;
    }

    public void setLeTiersDetails(LeTiersDetails leTiersDetails) {
        this.leTiersDetails = leTiersDetails;
    }

    public SignatureDetails getSignatureDetails() {
        return signatureDetails;
    }

    public void setSignatureDetails(SignatureDetails signatureDetails) {
        this.signatureDetails = signatureDetails;
    }

    public LaVictimeDetails getLaVictimeDetails() {
        return laVictimeDetails;
    }

    public void setLaVictimeDetails(LaVictimeDetails laVictimeDetails) {
        this.laVictimeDetails = laVictimeDetails;
    }

    public static class Param {

        @JacksonXmlProperty(isAttribute = true)
        private String name;

        @JacksonXmlText
        private String value;

        public Param(String name, String value) {
            this.name = name;
            this.value = value == null ? generateRandomName() : value;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "<param name=\"" + name + "\">" + value + "</param>";
        }
    }

    public static String generateRandomName() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        char[] chr = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        String name = "";

        for(int i = 0; i < 6; ++i) {
            char c = chr[random.nextInt(chr.length)];
            sb.append(c);
        }

        name = sb.toString().toUpperCase();
        return name;
    }

    public static class MyProfileDetails {
        @JacksonXmlElementWrapper(useWrapping = false)
        private List<Param> param = new ArrayList<>();

        public List<Param> getParam() {
            return param;
        }

        public void setParam(List<Param> param) {
            this.param = param;
        }
    }

    public static class Accident2Details {

        @JacksonXmlElementWrapper(useWrapping = false)
        private List<Param> param = new ArrayList<>();

        public List<Param> getParam() {
            return param;
        }

        public void setParam(List<Param> param) {
            this.param = param;
        }
    }

    public static class LaVictimeDetails {

        @JacksonXmlElementWrapper(useWrapping = false)
        private List<Param> param = new ArrayList<>();

        public List<Param> getParam() {
            return param;
        }

        public void setParam(List<Param> param) {
            this.param = param;
        }
    }

    public static class SignatureDetails {

        @JacksonXmlElementWrapper(useWrapping = false)
        private List<Param> param = new ArrayList<>();

        public List<Param> getParam() {
            return param;
        }

        public void setParam(List<Param> param) {
            this.param = param;
        }
    }

    public static class LeTiersDetails {

        @JacksonXmlElementWrapper(useWrapping = false)
        private List<Param> param = new ArrayList<>();

        public List<Param> getParam() {
            return param;
        }

        public void setParam(List<Param> param) {
            this.param = param;
        }
    }

    public static class LesTemonisDetails {

        @JacksonXmlElementWrapper(useWrapping = false)
        private List<Param> param = new ArrayList<>();

        public List<Param> getParam() {
            return param;
        }

        public void setParam(List<Param> param) {
            this.param = param;
        }
    }

    public static class Accident1Details {

        @JacksonXmlElementWrapper(useWrapping = false)
        private List<Param> param = new ArrayList<>();

        public List<Param> getParam() {
            return param;
        }

        public void setParam(List<Param> param) {
            this.param = param;
        }
    }
}
