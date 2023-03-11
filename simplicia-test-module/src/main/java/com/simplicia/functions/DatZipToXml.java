package com.simplicia.functions;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.simplicia.functions.DATEle.TemoinEle;
import com.simplicia.functions.DATRoot.Param;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import util.CommonUtil;

import java.io.File;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class DatZipToXml {

/* public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {

        convertDatZipToXml(new File("C:\\Users\\admin\\Downloads\\dat (1).zip"));
}*/

    public static String convertDatZipToXml(File file, String suffix) {

        ZipFile datZipFile;
        try {
            datZipFile = new ZipFile(file);
            Enumeration<? extends ZipEntry> entries = datZipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                if ("DAT.xml".endsWith(entry.getName())) {
                    String output = file.getAbsolutePath() + ".DAT-" + suffix + ".xml";
                    String filePath = file.getAbsolutePath();
                    convertDatZipToXml(datZipFile.getInputStream(entry), new File(output), filePath);

                    String outputText = FileUtils.readFileToString(new File(output), StandardCharsets.UTF_8);
                    CommonUtil.log("CONTENT of " + output);
                    CommonUtil.log(outputText);

                    return output;
                }
            }
        } catch (Exception e1) {/**/}

        return null;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static void convertDatZipToXml(InputStream in, File desc, String filePath) throws Exception {

        DATEle dat = new DATEle();

        List<Map> listProf = new ArrayList<>();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); // the date format we will fill the web DAT form

        for (int i = 0; i < dat.getjArray().length(); i++) {
            HashMap<String, Object> result =  new ObjectMapper().readValue(dat.getjArray().get(i).toString(), HashMap.class);
            listProf.add(result);
        }

        try {
            Files.deleteIfExists(desc.toPath());
            dat = new XmlMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).readValue(in, DATEle.class);
        } catch (Exception e1) {/**/}

        DATRoot root = new DATRoot();

        root.getMyProfileDetails().getParam().add(new Param("ACCESS_NET_ENTERPRISE", dat.getEmetteur().getSiret()));
        root.getMyProfileDetails().getParam().add(new Param("SELECTIONNEZ_UN_EASTB", dat.getAccidentDuTravail().getVictime().getEmployeur().getRaisonSociale()));
        root.getMyProfileDetails().getParam().add(new Param("COMPLEMENT", dat.getAccidentDuTravail().getVictime().getEmployeur().getAdresse().getComplement()));
        root.getMyProfileDetails().getParam().add(new Param("VOIE", dat.getAccidentDuTravail().getVictime().getEmployeur().getAdresse().getNomVoie()));
        root.getMyProfileDetails().getParam().add(new Param("POSTAL_CODE", dat.getAccidentDuTravail().getVictime().getEmployeur().getAdresse().getLocalite() != null ?
                dat.getAccidentDuTravail().getVictime().getEmployeur().getAdresse().getLocalite() : "75001 PARIS"));
        root.getMyProfileDetails().getParam().add(new Param("PAYS", dat.getAccidentDuTravail().getVictime().getEmployeur().getAdresse().getNomPays()));
        root.getMyProfileDetails().getParam().add(new Param("SIRET", dat.getAccidentDuTravail().getVictime().getEtablissementAttache().getSiret()));
        root.getMyProfileDetails().getParam().add(new Param("EMAIL", dat.getAccidentDuTravail().getVictime().getEtablissementAttache().getTelecom().getEmail()));
        root.getMyProfileDetails().getParam().add(new Param("TELEPHONE", dat.getAccidentDuTravail().getVictime().getEtablissementAttache().getTelecom().getTelephone()));
        root.getMyProfileDetails().getParam().add(new Param("NUMERO_DE_RISQUE", dat.getAccidentDuTravail().getVictime().getEtablissementAttache().getRisqueSS()));
        root.getMyProfileDetails().getParam().add(new Param("CA_POINT_REMISE", dat.getAccidentDuTravail().getVictime().getEtablissementAttache().getAdresse().getPointRemise()));
        root.getMyProfileDetails().getParam().add(new Param("CA_COMPLEMENT", dat.getAccidentDuTravail().getVictime().getEtablissementAttache().getAdresse().getComplement()));
        root.getMyProfileDetails().getParam().add(new Param("CA_VOIE", dat.getAccidentDuTravail().getVictime().getEtablissementAttache().getAdresse().getNomVoie()));
        root.getMyProfileDetails().getParam().add(new Param("CA_MENTION_DISTB", dat.getAccidentDuTravail().getVictime().getEtablissementAttache().getAdresse().getMentionDistribution()));
        root.getMyProfileDetails().getParam().add(new Param("CA_POSTAL_CODE", dat.getAccidentDuTravail().getVictime().getEtablissementAttache().getAdresse().getLocalite() != null ?
                dat.getAccidentDuTravail().getVictime().getEtablissementAttache().getAdresse().getLocalite() : "75001 PARIS"));
        root.getMyProfileDetails().getParam().add(new Param("CA_PAYS", dat.getAccidentDuTravail().getVictime().getEtablissementAttache().getAdresse().getNomPays()));
        root.getMyProfileDetails().getParam().add(new Param("HS_NOM", dat.getAccidentDuTravail().getVictime().getServiceSanteTravail().getNom()));
        root.getMyProfileDetails().getParam().add(new Param("HS_POINT_REMISE", dat.getAccidentDuTravail().getVictime().getServiceSanteTravail().getAdresse().getPointRemise()));
        root.getMyProfileDetails().getParam().add(new Param("HS_COMPLEMENT", dat.getAccidentDuTravail().getVictime().getServiceSanteTravail().getAdresse().getComplement()));
        root.getMyProfileDetails().getParam().add(new Param("HS_VOIE", dat.getAccidentDuTravail().getVictime().getServiceSanteTravail().getAdresse().getNomVoie()));
        root.getMyProfileDetails().getParam().add(new Param("HS_MENTION_DISTB", dat.getAccidentDuTravail().getVictime().getServiceSanteTravail().getAdresse().getMentionDistribution()));
        root.getMyProfileDetails().getParam().add(new Param("HS_POSTAL_CODE", dat.getAccidentDuTravail().getVictime().getServiceSanteTravail().getAdresse().getLocalite() != null ?
                dat.getAccidentDuTravail().getVictime().getServiceSanteTravail().getAdresse().getLocalite() : "75001 PARIS"));
        root.getMyProfileDetails().getParam().add(new Param("HS_PAYS", dat.getAccidentDuTravail().getVictime().getServiceSanteTravail().getAdresse().getNomPays()));

        root.getLaVictimeDetails().getParam().add(new Param("TXT_FIELD_TV_REGISTRATION", dat.getAccidentDuTravail().getVictime().getNir()));
        root.getLaVictimeDetails().getParam().add(new Param("ICON_SELECT_TV_SEX", dat.getAccidentDuTravail().getVictime().getSexe() == null ? "Féminin" : (dat.getAccidentDuTravail().getVictime().getSexe() == 2 ? "Féminin" : "Masculin")));
        root.getLaVictimeDetails().getParam().add(new Param("TXT_FIELD_TV_DATE_OF_BIRTH", dat.getAccidentDuTravail().getVictime().getNaissance() != null
                ? sdf.format(dat.getAccidentDuTravail().getVictime().getNaissance()) : DATRoot.generateRandomName()));
        root.getLaVictimeDetails().getParam().add(new Param("TXT_FIELD_TV_LAST_NAME", dat.getAccidentDuTravail().getVictime().getNom()));
        root.getLaVictimeDetails().getParam().add(new Param("TXT_FIELD_TV_FIRST_NAME", dat.getAccidentDuTravail().getVictime().getPrenom()));
        root.getLaVictimeDetails().getParam().add(new Param("TXT_FIELD_TV_POINT_OF_DELIVERY", dat.getAccidentDuTravail().getVictime().getAdresse().getPointRemise()));
        root.getLaVictimeDetails().getParam().add(new Param("TXT_FIELD_TV_BUILDING", dat.getAccidentDuTravail().getVictime().getAdresse().getComplement()));
        root.getLaVictimeDetails().getParam().add(new Param("TXT_FIELD_TV_STREET", dat.getAccidentDuTravail().getVictime().getAdresse().getNomVoie()));
        root.getLaVictimeDetails().getParam().add(new Param("TXT_FIELD_TV_DISTRIBUTION_STMT", dat.getAccidentDuTravail().getVictime().getAdresse().getMentionDistribution()));
        root.getLaVictimeDetails().getParam().add(new Param("TXT_FIELD_TV_POSTAL_CODE", dat.getAccidentDuTravail().getVictime().getAdresse().getLocalite() != null ?
                dat.getAccidentDuTravail().getVictime().getAdresse().getLocalite() : "75001 PARIS"));
        root.getLaVictimeDetails().getParam().add(new Param("TXT_FIELD_TV_COUNTRY", dat.getAccidentDuTravail().getVictime().getAdresse().getNomPays()));
        root.getLaVictimeDetails().getParam().add(new Param("TXT_FIELD_TV_DATE_HIRE", dat.getAccidentDuTravail().getVictime().getEmbauche() != null
                ?sdf.format(dat.getAccidentDuTravail().getVictime().getEmbauche()) : DATRoot.generateRandomName()));

        switch (dat.getAccidentDuTravail().getVictime().getNationalite()) {
            case 1:
                root.getLaVictimeDetails().getParam().add(new Param("ICON_SELECT_TV_NATIONALIE", "Non precise"));
                break;
            case 2:
                root.getLaVictimeDetails().getParam().add(new Param("ICON_SELECT_TV_NATIONALIE", "Francaise"));
                break;
            case 4:
                root.getLaVictimeDetails().getParam().add(new Param("ICON_SELECT_TV_NATIONALIE", "Autres pays étrangers"));
                break;
            case 5:
                root.getLaVictimeDetails().getParam().add(new Param("ICON_SELECT_TV_NATIONALIE", "Union Européenne"));
                break;
            default:
                root.getLaVictimeDetails().getParam().add(new Param("ICON_SELECT_TV_NATIONALIE", DATRoot.generateRandomName()));
                break;
        }

        if (dat.getAccidentDuTravail().getVictime().getEmploi().getAttributProfs().size() > 0) {

            for (int i = 0; i < listProf.size(); i++) {
                if (listProf.get(i).get("code").equals(dat.getAccidentDuTravail().getVictime().getEmploi().getAttributProfs().get(0).getValue())) {

                    switch ((String) listProf.get(i).get("professionType")) {
                        case "01":
                            root.getLaVictimeDetails().getParam().add(new Param("ICON_SELECT_TV_PROFESSION_CAT", "Non precisee"));
                            break;
                        case "02":
                            root.getLaVictimeDetails().getParam().add(new Param("ICON_SELECT_TV_PROFESSION_CAT", "Directeurs, cadres de direction et gerants"));
                            break;
                        case "03":
                            root.getLaVictimeDetails().getParam().add(new Param("ICON_SELECT_TV_PROFESSION_CAT", "Professions intellectuelles et scientifiques"));
                            break;
                        case "04":
                            root.getLaVictimeDetails().getParam().add(new Param("ICON_SELECT_TV_PROFESSION_CAT", "Professions intermediaires"));
                            break;
                        case "05":
                            root.getLaVictimeDetails().getParam().add(new Param("ICON_SELECT_TV_PROFESSION_CAT", "Employes de type administratif"));
                            break;
                        case "06":
                            root.getLaVictimeDetails().getParam().add(new Param("ICON_SELECT_TV_PROFESSION_CAT", "Personnel des services directs aux particuliers, commercants et vendeurs"));
                            break;
                        case "07":
                            root.getLaVictimeDetails().getParam().add(new Param("ICON_SELECT_TV_PROFESSION_CAT", "Agriculteurs et ouvriers qualifiers de l'agriculture, de la sylviculture et de la peche"));
                            break;
                        case "08":
                            root.getLaVictimeDetails().getParam().add(new Param("ICON_SELECT_TV_PROFESSION_CAT", "Metiers qualifies de l'industrie et de l'artisanat"));
                            break;
                        case "09":
                            root.getLaVictimeDetails().getParam().add(new Param("ICON_SELECT_TV_PROFESSION_CAT", "Conducteurs d'installations et de machines, et ouvriers de l'assemblage"));
                            break;
                        case "10":
                            root.getLaVictimeDetails().getParam().add(new Param("ICON_SELECT_TV_PROFESSION_CAT", "Professions elementaires"));
                            break;
                        case "11":
                            root.getLaVictimeDetails().getParam().add(new Param("ICON_SELECT_TV_PROFESSION_CAT", "Professions militaires"));
                            break;
                        default:
                            root.getLaVictimeDetails().getParam().add(new Param("ICON_SELECT_TV_PROFESSION_CAT", DATRoot.generateRandomName()));
                            break;
                    }

                    root.getLaVictimeDetails().getParam().add(new Param("ICON_SELECT_TV_PROFESSION", (String) listProf.get(i).get("label")));
                }
            }

            if (dat.getAccidentDuTravail().getVictime().getEmploi().getAttributProfs().size() >= 2) {
                switch (dat.getAccidentDuTravail().getVictime().getEmploi().getAttributProfs().get(1).getValue()) {
                    case "1":
                        root.getLaVictimeDetails().getParam().add(new Param("ICON_SELECT_TV_QUALIFICATION_PROF", "Non precise"));
                        break;
                    case "2":
                        root.getLaVictimeDetails().getParam().add(new Param("ICON_SELECT_TV_QUALIFICATION_PROF", "Cadre, technicien, agent de maîtrise"));
                        break;
                    case "3":
                        root.getLaVictimeDetails().getParam().add(new Param("ICON_SELECT_TV_QUALIFICATION_PROF", "Employé"));
                        break;
                    case "4":
                        root.getLaVictimeDetails().getParam().add(new Param("ICON_SELECT_TV_QUALIFICATION_PROF", "Apprenti"));
                        break;
                    case "5":
                        root.getLaVictimeDetails().getParam().add(new Param("ICON_SELECT_TV_QUALIFICATION_PROF", "Elèves enseignement techniques (AT survenu a compter du 1.1.1994)"));
                        break;
                    case "6":
                        root.getLaVictimeDetails().getParam().add(new Param("ICON_SELECT_TV_QUALIFICATION_PROF", "Ouvrier non qualifié"));
                        break;
                    case "7":
                        root.getLaVictimeDetails().getParam().add(new Param("ICON_SELECT_TV_QUALIFICATION_PROF", "Ouvrier qualifié"));
                        break;
                    case "8":
                        root.getLaVictimeDetails().getParam().add(new Param("ICON_SELECT_TV_QUALIFICATION_PROF", "Divers"));
                        break;
                    default:
                        root.getLaVictimeDetails().getParam().add(new Param("ICON_SELECT_TV_QUALIFICATION_PROF", DATRoot.generateRandomName()));
                        break;
                }
            }

            root.getLaVictimeDetails().getParam().add(new Param("TXT_FIELD_TV_NAME_IN_COMPANY",
                    dat.getAccidentDuTravail().getVictime().getEmploi().getAttributProfs().size() == 3
                            ? dat.getAccidentDuTravail().getVictime().getEmploi().getAttributProfs().get(2).getValue()
                            : DATRoot.generateRandomName()));

            switch (dat.getAccidentDuTravail().getVictime().getEmploi().getContratDeTravail().getNature()) {
                case "0":
                    root.getLaVictimeDetails().getParam().add(new Param("ICON_SELECT_TV_NATURE_OF_CONTRACT",  "Non connue"));
                    break;
                case "1":
                    root.getLaVictimeDetails().getParam().add(new Param("ICON_SELECT_TV_NATURE_OF_CONTRACT",  "CDI"));
                    break;
                case "2":
                    root.getLaVictimeDetails().getParam().add(new Param("ICON_SELECT_TV_NATURE_OF_CONTRACT",  "CDD"));
                    break;
                case "3":
                    root.getLaVictimeDetails().getParam().add(new Param("ICON_SELECT_TV_NATURE_OF_CONTRACT",  "Intérimaire"));
                    break;
                case "5":
                    root.getLaVictimeDetails().getParam().add(new Param("ICON_SELECT_TV_NATURE_OF_CONTRACT",  "Apprenti / élève"));
                    break;
                case "9":
                    root.getLaVictimeDetails().getParam().add(new Param("ICON_SELECT_TV_NATURE_OF_CONTRACT",  "Autre contrat de travail"));
                    break;
                default:
                    root.getLaVictimeDetails().getParam().add(new Param("ICON_SELECT_TV_NATURE_OF_CONTRACT",  dat.getAccidentDuTravail().getVictime().getEmploi().getContratDeTravail().getNature()));
                    break;
            }

            switch (dat.getAccidentDuTravail().getVictime().getAnciennete()) {
                case "1":
                    root.getLaVictimeDetails().getParam().add(new Param("ICON_SELECT_TV_SENIORITY_IN_POS", "Non precise"));
                    break;
                case "2":
                    root.getLaVictimeDetails().getParam().add(new Param("ICON_SELECT_TV_SENIORITY_IN_POS", "Moins d'une semaine"));
                    break;
                case "3":
                    root.getLaVictimeDetails().getParam().add(new Param("ICON_SELECT_TV_SENIORITY_IN_POS", "Une semaine a moins d'un mois"));
                    break;
                case "4":
                    root.getLaVictimeDetails().getParam().add(new Param("ICON_SELECT_TV_SENIORITY_IN_POS", "Un mois a moins de trois mois"));
                    break;
                case "5":
                    root.getLaVictimeDetails().getParam().add(new Param("ICON_SELECT_TV_SENIORITY_IN_POS", "Trois mois a moins d'un an"));
                    break;
                case "6":
                    root.getLaVictimeDetails().getParam().add(new Param("ICON_SELECT_TV_SENIORITY_IN_POS", "Un an et plus"));
                    break;
                default:
                    root.getLaVictimeDetails().getParam().add(new Param("ICON_SELECT_TV_SENIORITY_IN_POS", DATRoot.generateRandomName()));
                    break;
            }
        }
        if (dat.getAccidentDuTravail().getVictime().getEntrepriseUtilisatrice().getAdresse() != null && dat.getAccidentDuTravail().getVictime().getEntrepriseUtilisatrice().getTelecom() != null
            && dat.getAccidentDuTravail().getVictime().getEntrepriseUtilisatrice().getRaisonSociale() != null && dat.getAccidentDuTravail().getVictime().getEntrepriseUtilisatrice().getRisqueSS() != null
            && dat.getAccidentDuTravail().getVictime().getEntrepriseUtilisatrice().getSiret() != null) {
            root.getLaVictimeDetails().getParam().add(new Param("EU_NOM", dat.getAccidentDuTravail().getVictime().getEntrepriseUtilisatrice().getRaisonSociale()));
            root.getLaVictimeDetails().getParam().add(new Param("EU_SIRET", dat.getAccidentDuTravail().getVictime().getEntrepriseUtilisatrice().getSiret()));
            root.getLaVictimeDetails().getParam().add(new Param("EU_EMAIL", dat.getAccidentDuTravail().getVictime().getEntrepriseUtilisatrice().getTelecom().getEmail()));
            root.getLaVictimeDetails().getParam().add(new Param("EMAIL", dat.getAccidentDuTravail().getVictime().getEntrepriseUtilisatrice().getTelecom().getEmail()));
            root.getLaVictimeDetails().getParam().add(new Param("EU_TELEPHONE", dat.getAccidentDuTravail().getVictime().getEntrepriseUtilisatrice().getTelecom().getTelephone()));
            root.getLaVictimeDetails().getParam().add(new Param("EU_NUMÉRO_DE_RISQUE_DE_SÉCURITÉ_SOCIALE", dat.getAccidentDuTravail().getVictime().getEntrepriseUtilisatrice().getRisqueSS()));
            root.getLaVictimeDetails().getParam().add(new Param("EU_POINT_DE_REMISE", dat.getAccidentDuTravail().getVictime().getEntrepriseUtilisatrice().getAdresse().getPointRemise()));
            root.getLaVictimeDetails().getParam().add(new Param("EU_COMPLEMENT", dat.getAccidentDuTravail().getVictime().getEntrepriseUtilisatrice().getAdresse().getComplement()));
            root.getLaVictimeDetails().getParam().add(new Param("EU_VOIE", dat.getAccidentDuTravail().getVictime().getEntrepriseUtilisatrice().getAdresse().getNomVoie()));
            root.getLaVictimeDetails().getParam().add(new Param("EU_MENTIONE_DE_DISB", dat.getAccidentDuTravail().getVictime().getEntrepriseUtilisatrice().getAdresse().getMentionDistribution()));
            root.getLaVictimeDetails().getParam().add(new Param("EU_POSTAL_CODE", dat.getAccidentDuTravail().getVictime().getEntrepriseUtilisatrice().getAdresse().getLocalite() != null ?
                    dat.getAccidentDuTravail().getVictime().getEntrepriseUtilisatrice().getAdresse().getLocalite() : "75001 PARIS"));
            root.getLaVictimeDetails().getParam().add(new Param("EU_PAYS", dat.getAccidentDuTravail().getVictime().getEntrepriseUtilisatrice().getAdresse().getNomPays()));
            root.getLaVictimeDetails().getParam().add(new Param("CHK_TV_OTHER_VICTIM", dat.getAccidentDuTravail().getAutreVictime()));
        }

        root.getAccident1Details().getParam().add(new Param("TXT_FIELD_DATE_DE_ACCIDENT", dat.getAccidentDuTravail().getDate() != null ? sdf.format(dat.getAccidentDuTravail().getDate()) : DATRoot.generateRandomName()));
        root.getAccident1Details().getParam().add(new Param("TXT_FILED_HEURE_DE_ACCIDENT", dat.getAccidentDuTravail().getHeure().replaceAll("\\:[0-9]{2}$", "")));

        if (dat.getAccidentDuTravail().getHoraires().size() == 1) {
            root.getAccident1Details().getParam().add(new Param("TXT_FIELD_DE", dat.getAccidentDuTravail().getHoraires().get(0).getDebut()));
            root.getAccident1Details().getParam().add(new Param("TXT_FIELD_A", dat.getAccidentDuTravail().getHoraires().get(0).getFin()));
        }

        if (dat.getAccidentDuTravail().getHoraires().size() == 2) {
            root.getAccident1Details().getParam().add(new Param("TXT_FIELD_DE", dat.getAccidentDuTravail().getHoraires().get(0).getDebut()));
            root.getAccident1Details().getParam().add(new Param("TXT_FIELD_A", dat.getAccidentDuTravail().getHoraires().get(0).getFin()));
            root.getAccident1Details().getParam().add(new Param("TXT_FIELD_ET_DE", dat.getAccidentDuTravail().getHoraires().get(1).getDebut()));
            root.getAccident1Details().getParam().add(new Param("TXT_FIELD_END_A", dat.getAccidentDuTravail().getHoraires().get(1).getFin()));
        }

        switch (dat.getAccidentDuTravail().getLieu().getCode()) {
            case "1":
                root.getAccident1Details().getParam().add(new Param("ICON_SELECT_PRECISION", "Non precise"));
                break;
            case "2":
                root.getAccident1Details().getParam().add(new Param("ICON_SELECT_PRECISION", "Lieu de travail habituel"));
                break;
            case "3":
                root.getAccident1Details().getParam().add(new Param("ICON_SELECT_PRECISION", "Lieu de travail occasionnel"));
                break;
            case "4":
                root.getAccident1Details().getParam().add(new Param("ICON_SELECT_PRECISION",  "Lieu du repas"));
                break;
            case "5":
                root.getAccident1Details().getParam().add(new Param("ICON_SELECT_PRECISION", "Au cours du trajet entre le domicile et le lieu de travail"));
                break;
            case "6":
                root.getAccident1Details().getParam().add(new Param("ICON_SELECT_PRECISION", "Au cours du trajet entre le travail et le lieu du repas"));
                break;
            case "7":
                root.getAccident1Details().getParam().add(new Param("ICON_SELECT_PRECISION", "Au cours d'un déplacement pour l'employeur"));
                break;
            default:
                root.getAccident1Details().getParam().add(new Param("ICON_SELECT_PRECISION", DATRoot.generateRandomName()));
                break;
        }

        root.getAccident1Details().getParam().add(new Param("TXT_FIELD_NOM", dat.getAccidentDuTravail().getLieu().getNom()));
        root.getAccident1Details().getParam().add(new Param("TXT_FIELD_POINT_DE_REMISE", dat.getAccidentDuTravail().getLieu().getAdresse().getPointRemise()));
        root.getAccident1Details().getParam().add(new Param("TXT_FIELD_COMPLEMENT", dat.getAccidentDuTravail().getLieu().getAdresse().getComplement()));
        root.getAccident1Details().getParam().add(new Param("TXT_FIELD_VOIE", dat.getAccidentDuTravail().getLieu().getAdresse().getNomVoie()));
        root.getAccident1Details().getParam().add(new Param("TXT_FIELD_MENTION_DISTB", dat.getAccidentDuTravail().getLieu().getAdresse().getMentionDistribution()));
        root.getAccident1Details().getParam().add(new Param("TXT_FIELD_POSTAL_CODE", dat.getAccidentDuTravail().getLieu().getAdresse().getLocalite() != null ?
                dat.getAccidentDuTravail().getLieu().getAdresse().getLocalite() : "75001 PARIS"));
        root.getAccident1Details().getParam().add(new Param("TXT_FIELD_PAYS", dat.getAccidentDuTravail().getLieu().getAdresse().getNomPays()));
        root.getAccident1Details().getParam().add(new Param("TXT_FIELD_SIRET", dat.getAccidentDuTravail().getLieu().getSiret()));
        root.getAccident1Details().getParam().add(new Param("TXT_FIELD_ACTIVITE_DE_LA", dat.getAccidentDuTravail().getCirconstances().getActivite()));
        root.getAccident1Details().getParam().add(new Param("TXT_FIELD_NATURE_DE_ACCIDENT", dat.getAccidentDuTravail().getCirconstances().getNature()));
        root.getAccident1Details().getParam().add(new Param("TXT_FIELD_OBJET_DONT_LE_CONTACT", dat.getAccidentDuTravail().getCirconstances().getObjet()));
        root.getAccident1Details().getParam().add(new Param("TXT_FIELD_RESERVES_MOTIVEES", dat.getAccidentDuTravail().getReserves()));
        root.getAccident1Details().getParam().add(new Param("TXT_FIELD_SIEGE_DES_LESIONS", dat.getAccidentDuTravail().getLesion().getInfoSiege()));
        root.getAccident1Details().getParam().add(new Param("TXT_FIELD_NATURE_DES_LESIONS", dat.getAccidentDuTravail().getLesion().getInfoNature()));

        //
        root.getAccident2Details().getParam().add(new Param("ICON_SELECT_TYPE", dat.getAccidentDuTravail().getType().equals("0") ?  "Accident de travail" : "Accident de trajet"));
        root.getAccident2Details().getParam().add(new Param("ICON_SELECT_PAR", dat.getAccidentDuTravail().getConstatAT().getConstatant().equals("1") ? "Employeur"
                : (dat.getAccidentDuTravail().getConstatAT().getConstatant().equals("2") ? "Préposé" : "Autres")));

        root.getAccident2Details().getParam().add(new Param("TXT_FIELD_DATE_OF_CONSTAT",
                dat.getAccidentDuTravail().getConstatAT().getTemps() != null ?
                    sdf.format(
                            new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(
                                    dat.getAccidentDuTravail().getConstatAT().getTemps())
                    ) :
                    DATRoot.generateRandomName()));

        root.getAccident2Details().getParam().add(new Param("TXT_FIELD_TIME_OF_CONSTAT",
                dat.getAccidentDuTravail().getConstatAT().getTemps().replaceAll(".*T", "").replaceAll("\\:[0-9]{2}$", "")));

        root.getAccident2Details().getParam().add(new Param("TXT_FIELD_DATE_OF_CONSTAT_REGISTER", dat.getAccidentDuTravail().getConstatAT().getEnregistre() != null ?
                sdf.format(dat.getAccidentDuTravail().getConstatAT().getEnregistre()) : DATRoot.generateRandomName()));

//        root.getAccident2Details().getParam().add(new Param("TXT_FIELD_SOUS_LE_NUMERO", dat.getAccidentDuTravail().getConstatAT().getSousLeNumero()));
//        root.getAccident2Details().getParam().add(new Param("TXT_FIELD_IDENTIFIANT", dat.getAccidentDuTravail().getConstatAT().getIdentifiant()));
        root.getAccident2Details().getParam().add(new Param("TXT_FIELD_RAPPORT_DE_POLICE_A_ETE_ETABLI_PAR", dat.getAccidentDuTravail().getUniteDePolice().getRaisonSociale()));
        root.getAccident2Details().getParam().add(new Param("VICTIME_TRANSPORTEE_A", dat.getAccidentDuTravail().getTransporte().getLigne()));
        root.getAccident2Details().getParam().add(new Param("VICTIME_TRANSPORTEE_POINT_DE_REMISE", dat.getAccidentDuTravail().getTransporte().getPointRemise()));
        root.getAccident2Details().getParam().add(new Param("VICTIME_TRANSPORTEE_COMPLEMENT", dat.getAccidentDuTravail().getTransporte().getComplement()));
        root.getAccident2Details().getParam().add(new Param("VICTIME_TRANSPORTEE_VOIE", dat.getAccidentDuTravail().getTransporte().getNomVoie()));
        root.getAccident2Details().getParam().add(new Param("VICTIME_TRANSPORTEE_MENTION_DE_DISTB", dat.getAccidentDuTravail().getTransporte().getMentionDistribution()));
        root.getAccident2Details().getParam().add(new Param("VICTIME_TRANSPORTEE_POSTAL_CODE", dat.getAccidentDuTravail().getTransporte().getLocalite() != null ?
                dat.getAccidentDuTravail().getTransporte().getLocalite() : "75001 PARIS"));
        root.getAccident2Details().getParam().add(new Param("VICTIME_TRANSPORTEE_PAYS", dat.getAccidentDuTravail().getTransporte().getNomPays()));

        if (StringUtils.isNotBlank(dat.getAccidentDuTravail().getPersonneAvisee().getNomUsage()) && StringUtils.isNotBlank(dat.getAccidentDuTravail().getPersonneAvisee().getPrenom())) {
            root.getLesTemonisDetails().getParam().add(new Param("PERSONNE_AVISEE_NOM", dat.getAccidentDuTravail().getPersonneAvisee().getNomUsage()));
            root.getLesTemonisDetails().getParam().add(new Param("PERSONNE_AVISEE_PRENOM", dat.getAccidentDuTravail().getPersonneAvisee().getPrenom()));
            root.getLesTemonisDetails().getParam().add(new Param("PERSONNE_AVISEE_POINT_DE_REMISE", dat.getAccidentDuTravail().getPersonneAvisee().getAdresse().getPointRemise()));
            root.getLesTemonisDetails().getParam().add(new Param("PERSONNE_AVISEE_COMPLEMENT", dat.getAccidentDuTravail().getPersonneAvisee().getAdresse().getComplement()));
            root.getLesTemonisDetails().getParam().add(new Param("PERSONNE_AVISEE_VOIE", dat.getAccidentDuTravail().getPersonneAvisee().getAdresse().getNomVoie()));
            root.getLesTemonisDetails().getParam().add(new Param("PERSONNE_AVISEE_MENTION_DISTB", dat.getAccidentDuTravail().getPersonneAvisee().getAdresse().getMentionDistribution()));
            root.getLesTemonisDetails().getParam().add(new Param("PERSONNE_AVISEE_POSTAL_CODE", dat.getAccidentDuTravail().getPersonneAvisee().getAdresse().getLocalite() != null ?
                    dat.getAccidentDuTravail().getPersonneAvisee().getAdresse().getLocalite() : "75001 PARIS"));
            root.getLesTemonisDetails().getParam().add(new Param("PERSONNE_AVISEE_PAYS", dat.getAccidentDuTravail().getPersonneAvisee().getAdresse().getNomPays()));
        }

        if (dat.getAccidentDuTravail().getTemoins().size() > 0) {
            for (int i = 0; i < dat.getAccidentDuTravail().getTemoins().size(); i++) {
                String sf = (i == 0 ? "" : i) + "";
                TemoinEle tm = dat.getAccidentDuTravail().getTemoins().get(i);
                root.getLesTemonisDetails().getParam().add(new Param("TÉMOINS_NOM" + sf, tm.getNomUsage()));
                root.getLesTemonisDetails().getParam().add(new Param("TÉMOINS_PRENOM" + sf, tm.getPrenom()));
                root.getLesTemonisDetails().getParam().add(new Param("TÉMOINS_POINT_DE_REMISE" + sf, tm.getAdresse().getPointRemise()));
                root.getLesTemonisDetails().getParam().add(new Param("TÉMOINS_COMPLEMENT" + sf, tm.getAdresse().getComplement()));
                root.getLesTemonisDetails().getParam().add(new Param("TÉMOINS_VOIE" + sf, tm.getAdresse().getNomVoie()));
                root.getLesTemonisDetails().getParam().add(new Param("TÉMOINS_MENTION_DISTB" + sf, tm.getAdresse().getMentionDistribution()));
                root.getLesTemonisDetails().getParam().add(new Param("TÉMOINS_POSTAL_CODE" + sf, tm.getAdresse().getLocalite() != null ? tm.getAdresse().getLocalite()
                        : "75001 PARIS"));
                root.getLesTemonisDetails().getParam().add(new Param("TÉMOINS_PAYS" + sf, tm.getAdresse().getNomPays()));
            }
        }

        root.getLeTiersDetails().getParam().add(new Param("LE_TIERS_NOM", dat.getAccidentDuTravail().getTiersResponsable().getNomUsage()));
        root.getLeTiersDetails().getParam().add(new Param("LE_TIERS_PRENOM", dat.getAccidentDuTravail().getTiersResponsable().getPrenom()));
//        root.getLeTiersDetails().getParam().add(new Param("LE_TIERS_RAISON_SOCIALE", dat.getAccidentDuTravail().getTiersResponsable().getRaisonSociale()));
        root.getLeTiersDetails().getParam().add(new Param("LE_TIERS_POINT_DE_REMISE", dat.getAccidentDuTravail().getTiersResponsable().getAdresse().getPointRemise()));
        root.getLeTiersDetails().getParam().add(new Param("LE_TIERS_COMPLEMENT", dat.getAccidentDuTravail().getTiersResponsable().getAdresse().getComplement()));
        root.getLeTiersDetails().getParam().add(new Param("LE_TIERS_VOIE", dat.getAccidentDuTravail().getTiersResponsable().getAdresse().getNomVoie()));
        root.getLeTiersDetails().getParam().add(new Param("LE_TIERS_MENTION_DISTB", dat.getAccidentDuTravail().getTiersResponsable().getAdresse().getMentionDistribution()));
        root.getLeTiersDetails().getParam().add(new Param("LE_TIERS_POSTAL_CODE", dat.getAccidentDuTravail().getTiersResponsable().getAdresse().getLocalite() != null ?
                dat.getAccidentDuTravail().getTiersResponsable().getAdresse().getLocalite() : "75001 PARIS"));
        root.getLeTiersDetails().getParam().add(new Param("LE_TIERS_PAYS", dat.getAccidentDuTravail().getTiersResponsable().getAdresse().getNomPays()));
        root.getLeTiersDetails().getParam().add(new Param("SA_IDENTITE", dat.getAccidentDuTravail().getTiersResponsable().getContratAssurance().getAssureur().getIdentite()));
        root.getLeTiersDetails().getParam().add(new Param("SA_SOCIETE_ASSURANCE", dat.getAccidentDuTravail().getTiersResponsable().getContratAssurance().getAssureur().getRaisonSociale()));
        root.getLeTiersDetails().getParam().add(new Param("SA_POINT_DE_REMISE", dat.getAccidentDuTravail().getTiersResponsable().getContratAssurance().getAssureur().getAdresse().getPointRemise()));
        root.getLeTiersDetails().getParam().add(new Param("SA_COMPLEMENT", dat.getAccidentDuTravail().getTiersResponsable().getContratAssurance().getAssureur().getAdresse().getComplement()));
        root.getLeTiersDetails().getParam().add(new Param("SA_VOIE", dat.getAccidentDuTravail().getTiersResponsable().getContratAssurance().getAssureur().getAdresse().getNomVoie()));
        root.getLeTiersDetails().getParam().add(new Param("SA_MENTION_DISTB", dat.getAccidentDuTravail().getTiersResponsable().getContratAssurance().getAssureur().getAdresse().getMentionDistribution()));
        root.getLeTiersDetails().getParam().add(new Param("SA_POSTAL_CODE", dat.getAccidentDuTravail().getTiersResponsable().getContratAssurance().getAssureur().getAdresse().getLocalite() != null ?
                dat.getAccidentDuTravail().getTiersResponsable().getContratAssurance().getAssureur().getAdresse().getLocalite() : "75001 PARIS"));
        root.getLeTiersDetails().getParam().add(new Param("SA_PAYS", dat.getAccidentDuTravail().getTiersResponsable().getContratAssurance().getAssureur().getAdresse().getNomPays()));
        root.getLeTiersDetails().getParam().add(new Param("SA_NUMERO_DE_CONTRAT", dat.getAccidentDuTravail().getTiersResponsable().getContratAssurance().getIdentifiant()));


        root.getSignatureDetails().getParam().add(new Param("TXT_FIELD_SIGN_NOM", dat.getEmetteur().getNomusage()));
        root.getSignatureDetails().getParam().add(new Param("TXT_FIELD_SIGN_PRENOM", dat.getEmetteur().getPrenom()));
        root.getSignatureDetails().getParam().add(new Param("TXT_FIELD_SIGN_EMAIL", dat.getEmetteur().getEmail()));
        root.getSignatureDetails().getParam().add(new Param("TXT_FIELD_SIGN_QUALITE", dat.getEmetteur().getQualite()));
//        root.getSignatureDetails().getParam().add(new Param("TXT_FIELD_SIGN_FAIT_A", dat.getEmetteur().getAdresse().getLocalite()));
        root.getSignatureDetails().getParam().add(new Param("TXT_FIELD_DATE", dat.getDate()));

        root.getSignatureDetails().getParam().add(new Param("TXT_FIELD_UPLOAD_FILE", filePath));

        XmlMapper mapper = new XmlMapper();
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        mapper.writer().withRootName("test").writeValue(desc, root);
    }
}