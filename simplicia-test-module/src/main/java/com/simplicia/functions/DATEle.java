package com.simplicia.functions;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@JacksonXmlRootElement(localName = "DAT")
public class DATEle {

    @JacksonXmlProperty(isAttribute = true, localName = "Nature")
    private String nature;

    @JacksonXmlProperty(localName = "Version")
    private String version;

    @JacksonXmlProperty(localName = "Identifiant")
    private String identifiant;

    @JacksonXmlProperty(localName = "Date")
    private String date;

    @JacksonXmlProperty(localName = "Fonction")
    private String fonction;

    @JacksonXmlElementWrapper(localName = "Emetteur")
    private EmetteurEle emetteur;

    @JacksonXmlElementWrapper(localName = "AccidentDuTravail")
    private AccidentDuTravailEle accidentDuTravail;

    private JSONArray jArray = new JSONArray(jsonString);

    public JSONArray getjArray() {
        return jArray;
    }
    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getIdentifiant() {
        return identifiant;
    }

    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFonction() {
        return fonction;
    }

    public void setFonction(String fonction) {
        this.fonction = fonction;
    }

    public EmetteurEle getEmetteur() {
        if (emetteur == null) {
            emetteur = new EmetteurEle();
        }
        return emetteur;
    }

    public void setEmetteur(EmetteurEle emetteur) {
        this.emetteur = emetteur;
    }

    public AccidentDuTravailEle getAccidentDuTravail() {
        if (accidentDuTravail == null) {
            accidentDuTravail = new AccidentDuTravailEle();
        }
        return accidentDuTravail;
    }

    public void setAccidentDuTravail(AccidentDuTravailEle accidentDuTravail) {
        this.accidentDuTravail = accidentDuTravail;
    }

    public static class EmetteurEle {

        @JacksonXmlProperty(localName = "SIRET")
        private String siret;

        @JacksonXmlElementWrapper(localName = "Adresse")
        private AddressEle adresse;

        @JacksonXmlProperty(localName = "Email")
        private String email;

        @JacksonXmlProperty(localName = "Qualite")
        private String qualite;

        @JacksonXmlProperty(localName = "NomUsage")
        private String nomusage;

        @JacksonXmlProperty(localName = "Prenom")
        private String prenom;

        public String getSiret() {
            return siret;
        }

        public void setSiret(String siret) {
            this.siret = siret;
        }

        public AddressEle getAdresse() {

            if (adresse == null) {
                adresse = new AddressEle();
            }

            return adresse;
        }

        public void setAdresse(AddressEle adresse) {
            this.adresse = adresse;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getQualite() {
            return qualite;
        }

        public void setQualite(String qualite) {
            this.qualite = qualite;
        }

        public String getNomusage() {
            return nomusage;
        }

        public void setNomusage(String nomusage) {
            this.nomusage = nomusage;
        }

        public String getPrenom() {
            return prenom;
        }

        public void setPrenom(String prenom) {
            this.prenom = prenom;
        }

    }

    public static class AddressEle {

        @JacksonXmlProperty(localName = "PointRemise")
        private String pointRemise;

        @JacksonXmlProperty(localName = "Complement")
        private String complement;

        @JacksonXmlProperty(localName = "NomVoie")
        private String nomVoie;

        @JacksonXmlProperty(localName = "MentionDistribution")
        private String mentionDistribution;

        @JacksonXmlProperty(localName = "Localite")
        private String localite;

        @JacksonXmlProperty(localName = "NomPays")
        private String nomPays;

        public String getPointRemise() {
            return pointRemise;
        }

        public void setPointRemise(String pointRemise) {
            this.pointRemise = pointRemise;
        }

        public String getComplement() {
            return complement;
        }

        public void setComplement(String complement) {
            this.complement = complement;
        }

        public String getNomVoie() {
            return nomVoie;
        }

        public void setNomVoie(String nomVoie) {
            this.nomVoie = nomVoie;
        }

        public String getMentionDistribution() {
            return mentionDistribution;
        }

        public void setMentionDistribution(String mentionDistribution) {
            this.mentionDistribution = mentionDistribution;
        }

        public String getLocalite() {
            return localite;
        }

        public void setLocalite(String localite) {
            this.localite = localite;
        }

        public String getNomPays() {
            return nomPays;
        }

        public void setNomPays(String nomPays) {
            this.nomPays = nomPays;
        }

    }

    public static class AccidentDuTravailEle {
        @JacksonXmlProperty(localName = "Date")
        private Date date;

        @JacksonXmlProperty(localName = "Heure")
        private String heure;

        @JacksonXmlProperty(localName = "AutreVictime")
        private String autreVictime;

        @JacksonXmlElementWrapper(localName = "Circonstances")
        private CirconstancesEle circonstances;

        @JacksonXmlProperty(localName = "Reserves")
        private String reserves;

        @JacksonXmlElementWrapper(localName = "Lieu")
        private LieuEle lieu;

        @JacksonXmlElementWrapper(localName = "Transporte")
        private AccidentTransporteEle transporte;

        @JacksonXmlProperty(localName = "Type")
        private String type;

        @JacksonXmlElementWrapper(useWrapping = false, localName = "Horaire")
        @JsonProperty("Horaire")
        private List<HoraireEle> horaires;

        @JacksonXmlElementWrapper(localName = "Lesion")
        private LesionEle lesion;

        @JacksonXmlElementWrapper(localName = "ConstatAT")
        private ConstatATEle constatAT;

        @JacksonXmlElementWrapper(localName = "Victime")
        private VictimeEle victime;

        @JacksonXmlElementWrapper(localName = "TiersResponsable")
        private TiersResponsableEle tiersResponsable;

        @JacksonXmlElementWrapper(useWrapping = false, localName = "Temoin")
        @JsonProperty("Temoin")
        private List<TemoinEle> temoins;

        @JacksonXmlElementWrapper(localName = "UniteDePolice")
        private UniteDePoliceEle uniteDePolice;

        @JacksonXmlElementWrapper(localName = "PersonneAvisee")
        private PersonneAviseeEle personneAvisee;

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public String getHeure() {
            if (heure == null) {
                heure = "";
            }
            return heure;
        }

        public void setHeure(String heure) {
            this.heure = heure;
        }

        public String getAutreVictime() {
            return autreVictime;
        }

        public void setAutreVictime(String autreVictime) {
            this.autreVictime = autreVictime;
        }

        public CirconstancesEle getCirconstances() {
            if (circonstances == null) {
                circonstances = new CirconstancesEle();
            }
            return circonstances;
        }

        public void setCirconstances(CirconstancesEle circonstances) {
            this.circonstances = circonstances;
        }

        public String getReserves() {
            return reserves;
        }

        public void setReserves(String reserves) {
            this.reserves = reserves;
        }

        public LieuEle getLieu() {
            if (lieu == null) {
                lieu = new LieuEle();
            }
            return lieu;
        }

        public void setLieu(LieuEle lieu) {
            this.lieu = lieu;
        }

        public AccidentTransporteEle getTransporte() {

            if (transporte == null) {
                transporte = new AccidentTransporteEle();
            }

            return transporte;
        }

        public void setTransporte(AccidentTransporteEle transporte) {
            this.transporte = transporte;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public List<HoraireEle> getHoraires() {

            if (horaires == null) {
                horaires = new ArrayList<>();
            }

            return horaires;
        }

        public void setHoraires(List<HoraireEle> horaires) {
            this.horaires = horaires;
        }

        public LesionEle getLesion() {

            if (lesion == null) {
                lesion = new LesionEle();
            }

            return lesion;
        }

        public void setLesion(LesionEle lesion) {
            this.lesion = lesion;
        }

        public ConstatATEle getConstatAT() {

            if (constatAT == null) {
                constatAT = new ConstatATEle();
            }

            return constatAT;
        }

        public void setConstatAT(ConstatATEle constatAT) {
            this.constatAT = constatAT;
        }

        public VictimeEle getVictime() {

            if (victime == null) {
                victime = new VictimeEle();
            }

            return victime;
        }

        public void setVictime(VictimeEle victime) {
            this.victime = victime;
        }

        public TiersResponsableEle getTiersResponsable() {

            if (tiersResponsable == null) {
                tiersResponsable = new TiersResponsableEle();
            }

            return tiersResponsable;
        }

        public void setTiersResponsable(TiersResponsableEle tiersResponsable) {
            this.tiersResponsable = tiersResponsable;
        }

        public List<TemoinEle> getTemoins() {

            if (temoins == null) {
                temoins = new ArrayList<>();
            }

            return temoins;
        }

        public void setTemoins(List<TemoinEle> temoins) {
            this.temoins = temoins;
        }

        public UniteDePoliceEle getUniteDePolice() {

            if (uniteDePolice == null) {
                uniteDePolice = new UniteDePoliceEle();
            }

            return uniteDePolice;
        }

        public void setUniteDePolice(UniteDePoliceEle uniteDePolice) {
            this.uniteDePolice = uniteDePolice;
        }

        public PersonneAviseeEle getPersonneAvisee() {
            if (personneAvisee == null ) {
                personneAvisee = new PersonneAviseeEle();
            }
            return personneAvisee;
        }

        public void setPersonneAvisee(PersonneAviseeEle personneAvisee) {
            this.personneAvisee = personneAvisee;
        }

    }

    public static class CirconstancesEle {
        @JacksonXmlProperty(localName = "Activite")
        private String activite;

        @JacksonXmlProperty(localName = "Nature")
        private String nature;

        @JacksonXmlProperty(localName = "Objet")
        private String objet;

        public String getActivite() {
            return activite;
        }

        public void setActivite(String activite) {
            this.activite = activite;
        }

        public String getNature() {
            return nature;
        }

        public void setNature(String nature) {
            this.nature = nature;
        }

        public String getObjet() {
            return objet;
        }

        public void setObjet(String objet) {
            this.objet = objet;
        }

    }

    public static class LieuEle {
        @JacksonXmlProperty(localName = "SIRET")
        private String siret;

        @JacksonXmlProperty(localName = "Nom")
        private String nom;

        @JacksonXmlElementWrapper(localName = "Adresse")
        private AddressEle adresse;

        @JacksonXmlProperty(localName = "Code")
        private String code;

        public String getNom() {
            return nom;
        }

        public void setNom(String nom) {
            this.nom = nom;
        }

        public AddressEle getAdresse() {

            if (adresse == null) {
                adresse = new AddressEle();
            }

            return adresse;
        }

        public void setAdresse(AddressEle adresse) {
            this.adresse = adresse;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getSiret() {
            return siret;
        }

        public void setSiret(String siret) {
            this.siret = siret;
        }

    }

    public static class AccidentTransporteEle {
        @JacksonXmlProperty(localName = "PointRemise")
        private String pointRemise;

        @JacksonXmlProperty(localName = "Complement")
        private String complement;

        @JacksonXmlProperty(localName = "NomVoie")
        private String nomVoie;

        @JacksonXmlProperty(localName = "MentionDistribution")
        private String mentionDistribution;

        @JacksonXmlProperty(localName = "Localite")
        private String localite;

        @JacksonXmlProperty(localName = "NomPays")
        private String nomPays;

        @JacksonXmlProperty(localName = "Ligne")
        private String ligne;

        public String getPointRemise() {
            return pointRemise;
        }

        public void setPointRemise(String pointRemise) {
            this.pointRemise = pointRemise;
        }

        public String getComplement() {
            return complement;
        }

        public void setComplement(String complement) {
            this.complement = complement;
        }

        public String getNomVoie() {
            return nomVoie;
        }

        public void setNomVoie(String nomVoie) {
            this.nomVoie = nomVoie;
        }

        public String getMentionDistribution() {
            return mentionDistribution;
        }

        public void setMentionDistribution(String mentionDistribution) {
            this.mentionDistribution = mentionDistribution;
        }

        public String getLocalite() {
            return localite;
        }

        public void setLocalite(String localite) {
            this.localite = localite;
        }

        public String getNomPays() {
            return nomPays;
        }

        public void setNomPays(String nomPays) {
            this.nomPays = nomPays;
        }

        public String getLigne() {
            return ligne;
        }

        public void setLigne(String ligne) {
            this.ligne = ligne;
        }

    }

    public static class HoraireEle {

        @JacksonXmlProperty(localName = "Debut")
        private String debut;

        @JacksonXmlProperty(localName = "Fin")
        private String fin;

        public String getDebut() {
            return debut;
        }

        public void setDebut(String debut) {
            this.debut = debut;
        }

        public String getFin() {
            return fin;
        }

        public void setFin(String fin) {
            this.fin = fin;
        }

    }

    public static class LesionEle {

        @JacksonXmlProperty(localName = "InfoSiege")
        private String infoSiege;

        @JacksonXmlProperty(localName = "InfoNature")
        private String infoNature;

        public String getInfoSiege() {
            return infoSiege;
        }

        public void setInfoSiege(String infoSiege) {
            this.infoSiege = infoSiege;
        }

        public String getInfoNature() {
            return infoNature;
        }

        public void setInfoNature(String infoNature) {
            this.infoNature = infoNature;
        }

    }

    public static class ConstatATEle {

        @JacksonXmlProperty(localName = "Identifiant")
        private String identifiant;

        @JacksonXmlProperty(localName = "Constatant")
        private String constatant;

        @JacksonXmlProperty(localName = "DecritParVictime")
        private String decritParVictime;

        @JacksonXmlProperty(localName = "Mode")
        private String mode;

        @JacksonXmlProperty(localName = "Temps")
        private String temps = "";

        @JacksonXmlProperty(localName = "Enregistre")
        private Date enregistre;

        @JacksonXmlProperty(localName = "Consequence")
        private String consequence;

        @JacksonXmlProperty(localName = "DecesVictime")
        private String decesVictime;

        @JacksonXmlProperty(localName = "SousLeNumero")
        private String sousLeNumero;

        public String getIdentifiant() {
            return identifiant;
        }

        public void setIdentifiant(String identifiant) {
            this.identifiant = identifiant;
        }

        public String getConstatant() {
            return constatant;
        }

        public void setConstatant(String constatant) {
            this.constatant = constatant;
        }

        public String getDecritParVictime() {
            return decritParVictime;
        }

        public void setDecritParVictime(String decritParVictime) {
            this.decritParVictime = decritParVictime;
        }

        public String getMode() {
            return mode;
        }

        public void setMode(String mode) {
            this.mode = mode;
        }

        public String getTemps() {
            if (temps == null) {
                temps = "";
            }
            return temps;
        }

        public void setTemps(String temps) {
            this.temps = temps;
        }

        public Date getEnregistre() {
            return enregistre;
        }

        public void setEnregistre(Date enregistre) {
            this.enregistre = enregistre;
        }

        public String getConsequence() {
            return consequence;
        }

        public void setConsequence(String consequence) {
            this.consequence = consequence;
        }

        public String getDecesVictime() {
            return decesVictime;
        }

        public void setDecesVictime(String decesVictime) {
            this.decesVictime = decesVictime;
        }

        public String getSousLeNumero() {
            return sousLeNumero;
        }

        public void setSousLeNumero(String sousLeNumero) {
            this.sousLeNumero = sousLeNumero;
        }

    }

    public static class VictimeEle {

        @JacksonXmlProperty(localName = "Embauche")
        private Date embauche;

        @JacksonXmlProperty(localName = "Anciennete")
        private String anciennete;

        @JacksonXmlProperty(localName = "NIR")
        private String nir;

        @JacksonXmlElementWrapper(localName = "Adresse")
        private AddressEle adresse;

        @JacksonXmlProperty(localName = "Nationalite")
        private Integer nationalite;

        @JacksonXmlProperty(localName = "Nom")
        private String nom;

        @JacksonXmlProperty(localName = "Prenom")
        private String prenom;

        @JacksonXmlProperty(localName = "Sexe")
        private Integer sexe;

        @JacksonXmlProperty(localName = "Naissance")
        private Date naissance;

        @JacksonXmlElementWrapper(localName = "Emploi")
        private EmploiEle emploi;

        @JacksonXmlElementWrapper(localName = "Employeur")
        private EmployeurEle employeur;

        @JacksonXmlElementWrapper(localName = "EtablissementAttache")
        private EtablissementAttacheEle etablissementAttache;

        @JacksonXmlElementWrapper(localName = "EntrepriseUtilisatrice")
        private EntrepriseUtilisatriceEle entrepriseUtilisatrice;

        @JacksonXmlElementWrapper(localName = "ServiceSanteTravail")
        private ServiceSanteTravailEle serviceSanteTravail;

        public Date getEmbauche() {
            return embauche;
        }

        public void setEmbauche(Date embauche) {
            this.embauche = embauche;
        }

        public String getAnciennete() {
            return anciennete;
        }

        public void setAnciennete(String anciennete) {
            this.anciennete = anciennete;
        }

        public String getNir() {
            return nir;
        }

        public void setNir(String nir) {
            this.nir = nir;
        }

        public AddressEle getAdresse() {

            if (adresse == null) {
                adresse = new AddressEle();
            }

            return adresse;
        }

        public void setAdresse(AddressEle adresse) {
            this.adresse = adresse;
        }

        public Integer getNationalite() {
            return nationalite;
        }

        public void setNationalite(Integer nationalite) {
            this.nationalite = nationalite;
        }

        public String getNom() {
            return nom;
        }

        public void setNom(String nom) {
            this.nom = nom;
        }

        public String getPrenom() {
            return prenom;
        }

        public void setPrenom(String prenom) {
            this.prenom = prenom;
        }

        public Integer getSexe() {
            return sexe;
        }

        public void setSexe(Integer sexe) {
            this.sexe = sexe;
        }

        public Date getNaissance() {
            return naissance;
        }

        public void setNaissance(Date naissance) {
            this.naissance = naissance;
        }

        public EmploiEle getEmploi() {

            if (emploi == null) {
                emploi = new EmploiEle();
            }

            return emploi;
        }

        public void setEmploi(EmploiEle emploi) {
            this.emploi = emploi;
        }

        public EmployeurEle getEmployeur() {

            if (employeur == null) {
                employeur = new EmployeurEle();
            }

            return employeur;
        }

        public void setEmployeur(EmployeurEle employeur) {
            this.employeur = employeur;
        }

        public EtablissementAttacheEle getEtablissementAttache() {

            if (etablissementAttache == null) {
                etablissementAttache = new EtablissementAttacheEle();
            }

            return etablissementAttache;
        }

        public void setEtablissementAttache(EtablissementAttacheEle etablissementAttache) {
            this.etablissementAttache = etablissementAttache;
        }

        public EntrepriseUtilisatriceEle getEntrepriseUtilisatrice() {

            if (entrepriseUtilisatrice == null) {
                entrepriseUtilisatrice = new EntrepriseUtilisatriceEle();
            }

            return entrepriseUtilisatrice;
        }

        public void setEntrepriseUtilisatrice(EntrepriseUtilisatriceEle entrepriseUtilisatrice) {
            this.entrepriseUtilisatrice = entrepriseUtilisatrice;
        }

        public ServiceSanteTravailEle getServiceSanteTravail() {

            if (serviceSanteTravail == null) {
                serviceSanteTravail = new ServiceSanteTravailEle();
            }

            return serviceSanteTravail;
        }

        public void setServiceSanteTravail(ServiceSanteTravailEle serviceSanteTravail) {
            this.serviceSanteTravail = serviceSanteTravail;
        }

    }

    public static class TiersResponsableEle {

        @JacksonXmlProperty(localName = "NomUsage")
        private String nomUsage;

        @JacksonXmlProperty(localName = "Prenom")
        private String prenom;

        @JacksonXmlProperty(localName = "RaisonSociale")
        private String raisonSociale;

        @JacksonXmlElementWrapper(localName = "Adresse")
        private AddressEle adresse;

        @JacksonXmlElementWrapper(localName = "ContratAssurance")
        private ContratAssuranceEle contratAssurance;

        public String getNomUsage() {
            return nomUsage;
        }

        public void setNomUsage(String nomUsage) {
            this.nomUsage = nomUsage;
        }

        public String getPrenom() {
            return prenom;
        }

        public void setPrenom(String prenom) {
            this.prenom = prenom;
        }

        public String getRaisonSociale() {
            return raisonSociale;
        }

        public void setRaisonSociale(String raisonSociale) {
            this.raisonSociale = raisonSociale;
        }

        public AddressEle getAdresse() {

            if (adresse == null) {
                adresse = new AddressEle();
            }

            return adresse;
        }

        public void setAdresse(AddressEle adresse) {
            this.adresse = adresse;
        }

        public ContratAssuranceEle getContratAssurance() {

            if (contratAssurance == null) {
                contratAssurance = new ContratAssuranceEle();
            }

            return contratAssurance;
        }

        public void setContratAssurance(ContratAssuranceEle contratAssurance) {
            this.contratAssurance = contratAssurance;
        }

    }

    public static class ContratAssuranceEle {

        @JacksonXmlProperty(localName = "Identifiant")
        private String identifiant;

        @JacksonXmlElementWrapper(localName = "Assureur")
        @JsonProperty("Assureur")
        private AssureurEle assureur;

        public String getIdentifiant() {
            return identifiant;
        }

        public void setIdentifiant(String identifiant) {
            this.identifiant = identifiant;
        }

        public AssureurEle getAssureur() {

            if (assureur == null) {
                assureur = new AssureurEle();
            }

            return assureur;
        }

        public void setAssureur(AssureurEle assureur) {
            this.assureur = assureur;
        }

    }

    public static class AssureurEle {

        @JacksonXmlProperty(localName = "Identite")
        private String identite;

        @JacksonXmlProperty(localName = "RaisonSociale")
        private String raisonSociale;

        @JacksonXmlElementWrapper(localName = "Adresse")
        @JsonProperty("Adresse")
        private AddressEle adresse;

        public String getIdentite() {
            return identite;
        }

        public void setIdentite(String identite) {
            this.identite = identite;
        }

        public String getRaisonSociale() {
            return raisonSociale;
        }

        public void setRaisonSociale(String raisonSociale) {
            this.raisonSociale = raisonSociale;
        }

        public AddressEle getAdresse() {

            if (adresse == null) {
                adresse = new AddressEle();
            }

            return adresse;
        }

        public void setAdresse(AddressEle adresse) {
            this.adresse = adresse;
        }

    }

    public static class TemoinEle {

        @JacksonXmlProperty(localName = "NomUsage")
        private String nomUsage;

        @JacksonXmlProperty(localName = "Prenom")
        private String prenom;

        @JacksonXmlElementWrapper(localName = "Adresse")
        @JsonProperty("Adresse")
        private AddressEle adresse;

        public String getNomUsage() {
            return nomUsage;
        }

        public void setNomUsage(String nomUsage) {
            this.nomUsage = nomUsage;
        }

        public String getPrenom() {
            return prenom;
        }

        public void setPrenom(String prenom) {
            this.prenom = prenom;
        }

        public AddressEle getAdresse() {

            if (adresse == null) {
                adresse = new AddressEle();
            }

            return adresse;
        }

        public void setAdresse(AddressEle adresse) {
            this.adresse = adresse;
        }

    }

    public static class UniteDePoliceEle {

        @JacksonXmlProperty(localName = "RaisonSociale")
        private String raisonSociale;

        public String getRaisonSociale() {
            return raisonSociale;
        }

        public void setRaisonSociale(String raisonSociale) {
            this.raisonSociale = raisonSociale;
        }

    }

    public static class EmployeurEle {

        @JacksonXmlProperty(localName = "RaisonSociale")
        private String raisonSociale;

        @JacksonXmlElementWrapper(localName = "Adresse")
        @JsonProperty("Adresse")
        private AddressEle adresse;

        public String getRaisonSociale() {
            return raisonSociale;
        }

        public void setRaisonSociale(String raisonSociale) {
            this.raisonSociale = raisonSociale;
        }

        public AddressEle getAdresse() {

            if (adresse == null) {
                adresse = new AddressEle();
            }

            return adresse;
        }

        public void setAdresse(AddressEle adresse) {
            this.adresse = adresse;
        }

    }

    public static class EtablissementAttacheEle {

        @JacksonXmlProperty(localName = "RisqueSS")
        private String risqueSS;

        @JacksonXmlProperty(localName = "SIRET")
        private String siret;

        @JacksonXmlElementWrapper(localName = "Adresse")
        @JsonProperty("Adresse")
        private AddressEle adresse;

        @JacksonXmlElementWrapper(localName = "Telecom")
        @JsonProperty("Telecom")
        private TelecomEle telecom;

        public String getRisqueSS() {
            return risqueSS;
        }

        public void setRisqueSS(String risqueSS) {
            this.risqueSS = risqueSS;
        }

        public String getSiret() {
            return siret;
        }

        public void setSiret(String siret) {
            this.siret = siret;
        }

        public AddressEle getAdresse() {

            if (adresse == null) {
                adresse = new AddressEle();
            }

            return adresse;
        }

        public void setAdresse(AddressEle adresse) {
            this.adresse = adresse;
        }

        public TelecomEle getTelecom() {

            if (telecom == null) {
                telecom = new TelecomEle();
            }

            return telecom;
        }

        public void setTelecom(TelecomEle telecom) {
            this.telecom = telecom;
        }

    }

    public static class EntrepriseUtilisatriceEle {

        @JacksonXmlProperty(localName = "RisqueSS")
        private String risqueSS;

        @JacksonXmlProperty(localName = "SIRET")
        private String siret;

        @JacksonXmlProperty(localName = "RaisonSociale")
        private String raisonSociale;

        @JacksonXmlElementWrapper(localName = "Adresse")
        @JsonProperty("Adresse")
        private AddressEle adresse;

        @JacksonXmlElementWrapper(localName = "Telecom")
        @JsonProperty("Telecom")
        private TelecomEle telecom;

        public String getRisqueSS() {
            return risqueSS;
        }

        public void setRisqueSS(String risqueSS) {
            this.risqueSS = risqueSS;
        }

        public String getSiret() {
            return siret;
        }

        public void setSiret(String siret) {
            this.siret = siret;
        }

        public String getRaisonSociale() {
            return raisonSociale;
        }

        public void setRaisonSociale(String raisonSociale) {
            this.raisonSociale = raisonSociale;
        }

        public AddressEle getAdresse() {

            if (adresse == null) {
                adresse = new AddressEle();
            }

            return adresse;
        }

        public void setAdresse(AddressEle adresse) {
            this.adresse = adresse;
        }

        public TelecomEle getTelecom() {

            if (telecom == null) {
                telecom = new TelecomEle();
            }

            return telecom;
        }

        public void setTelecom(TelecomEle telecom) {
            this.telecom = telecom;
        }

    }

    public static class ServiceSanteTravailEle {

        @JacksonXmlProperty(localName = "Nom")
        private String nom;

        @JacksonXmlElementWrapper(localName = "Adresse")
        @JsonProperty("Adresse")
        private AddressEle adresse;

        public String getNom() {
            return nom;
        }

        public void setNom(String nom) {
            this.nom = nom;
        }

        public AddressEle getAdresse() {

            if (adresse == null) {
                adresse = new AddressEle();
            }

            return adresse;
        }

        public void setAdresse(AddressEle adresse) {
            this.adresse = adresse;
        }

    }

    public static class TelecomEle {

        @JacksonXmlProperty(localName = "Telephone")
        private String telephone;

        @JacksonXmlProperty(localName = "Email")
        private String email;

        public String getTelephone() {
            return telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

    }

    public static class EmploiEle {

        @JacksonXmlElementWrapper(useWrapping = false)
        @JsonProperty("AttributProf")
        private List<AttributProfEle> attributProfs;

        @JacksonXmlElementWrapper(localName = "ContratDeTravail")
        @JsonProperty("ContratDeTravail")
        private ContratDeTravailEle contratDeTravail;

        public List<AttributProfEle> getAttributProfs() {

            if (attributProfs == null) {
                attributProfs = new ArrayList<>();
            }

            return attributProfs;
        }

        public void setAttributProfs(List<AttributProfEle> attributProfs) {
            this.attributProfs = attributProfs;
        }

        public ContratDeTravailEle getContratDeTravail() {

            if (contratDeTravail == null) {
                contratDeTravail = new ContratDeTravailEle();
            }

            return contratDeTravail;
        }

        public void setContratDeTravail(ContratDeTravailEle contratDeTravail) {
            this.contratDeTravail = contratDeTravail;
        }

    }

    public static class AttributProfEle {

        @JacksonXmlProperty(isAttribute = true, localName = "Q")
        private String q;

        @JacksonXmlText
        private String value;

        public String getQ() {
            return q;
        }

        public void setQ(String q) {
            this.q = q;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

    }

    public static class ContratDeTravailEle {

        @JacksonXmlProperty(localName = "Nature")
        private String nature;

        public String getNature() {
            return nature;
        }

        public void setNature(String nature) {
            this.nature = nature;
        }

    }

    public static class PersonneAviseeEle {
        @JacksonXmlProperty(localName = "NomUsage")
        private String nomUsage;

        @JacksonXmlProperty(localName = "Prenom")
        private String prenom;

        @JacksonXmlElementWrapper(localName = "Adresse")
        @JsonProperty("Adresse")
        private AddressEle adresse;

        public String getNomUsage() {
            return nomUsage;
        }

        public void setNomUsage(String nomUsage) {
            this.nomUsage = nomUsage;
        }

        public String getPrenom() {
            return prenom;
        }

        public void setPrenom(String prenom) {
            this.prenom = prenom;
        }

        public AddressEle getAdresse() {

            if (adresse == null) {
                adresse = new AddressEle();
            }

            return adresse;
        }

        public void setAdresse(AddressEle adresse) {
            this.adresse = adresse;
        }
    }

    public static String jsonString = "[{ 'code': '01', 'label': 'Officiers des forces armées', 'professionType': '11' }," +
            "{'code': '02', 'label': 'Sous-officiers des forces armées', 'professionType': '11' }," +
            "{'code': '03', 'label': 'Autres membres des forces armées', 'professionType': '11' }," +
            "{'code': '11', 'label': 'Directeurs généraux, cadres supérieurs et membres de lExécutif et des corps législatifs','professionType': '02'}," +
            "{'code': '12', 'label': 'Directeurs de services administratifs et commerciaux', 'professionType': '02'}," +
            "{'code': '13', 'label': 'Directeurs et cadres de direction, production et services spécialisés','professionType': '02'}," +
            "{'code': '14', 'label': 'Directeurs et gérants de lhôtellerie, la restauration, le commerce et autres services','professionType': '02'}," +
            "{'code': '22', 'label': 'Spécialistes de la santé','professionType': '03' }," +
            "{'code': '23', 'label': 'Spécialistes de lenseignement', 'professionType': '03' }," +
            "{'code': '24', 'label': 'Spécialistes en administration dentreprises', 'professionType': '03' },"+
            "{'code': '25', 'label': 'Spécialistes des technologies de linformation et des communications', 'professionType': '03'}," +
            "{'code': '26', 'label': 'Spécialistes de la justice, des sciences sociales et de la culture', 'professionType': '03'}," +
            "{'code': '31', 'label': 'Professions intermédiaires des sciences et techniques','professionType': '04'}," +
            "{'code': '32', 'label': 'Professions intermédiaires de la santé','professionType': '04' }," +
            "{'code': '33', 'label': 'Professions intermédiaires, finance et administration', 'professionType': '04'}," +
            "{'code': '34', 'label': 'Professions intermédiaires des services juridiques, des services sociaux et assimilés', 'professionType': '04'}," +
            "{'code': '35', 'label': 'Techniciens de linformation et des communications','professionType': '04'}," +
            "{'code': '41', 'label': 'Employés de bureau', 'professionType': '05' }," +
            "{'code': '42', 'label': 'Employés de réception, guichetiers et assimilés', 'professionType': '05' }," +
            "{'code': '43', 'label': 'Employés des services comptables et dapprovisionnement', 'professionType': '05'}," +
            "{'code': '44', 'label': 'Autres employés de type administratif', 'professionType': '05' }," +
            "{'code': '51', 'label': 'Personnel des services directs aux particuliers', 'professionType': '06' }," +
            "{'code': '52', 'label': 'Commerçants et vendeurs', 'professionType': '06' }," +
            "{'code': '53', 'label': 'Personnel soignant', 'professionType': '06' }," +
            "{'code': '54', 'label': 'Personnel des services de protection et de sécurité','professionType': '06'}," +
            "{'code': '61', 'label': 'Agriculteurs et ouvriers qualifiés de lagriculture commerciale','professionType': '07'}," +
            "{'code': '62', 'label': 'Professions commerciales qualifiées de la sylviculture, de la pêche et de la chasse','professionType': '07'}," +
            "{'code': '63', 'label': 'Agriculteurs, pêcheurs, chasseurs et cueilleurs de subsistance', 'professionType': '07'}," +
            "{'code': '71', 'label': 'Métiers qualifiés du bâtiment et assimilés, sauf électriciens', 'professionType': '08'}," +
            "{'code': '72', 'label': 'Métiers qualifiés de la métallurgie, de la construction mécanique et assimilés', 'professionType': '08'}," +
            "{'code': '73', 'label': 'Métiers qualifiés de lartisanat et de limprimerie', 'professionType': '08'}," +
            "{'code': '74', 'label': 'Métiers de lélectricité et de lélectrotechnique','professionType': '08'}," +
            "{'code': '75', 'label': 'Métiers de lalimentation, du travail sur bois, de lhabillement et autres métiers qualifiés de lindustrie et de lartisanat', 'professionType': '08'}," +
            "{'code': '81', 'label': 'Conducteurs de machines et dinstallations fixes','professionType': '09' }," +
            "{'code': '82', 'label': 'Ouvriers de lassemblage', 'professionType': '09' }," +
            "{'code': '83', 'label': 'Conducteurs de véhicules et dengins lourds de levage', 'professionType': '09'}," +
            "{'code': '91', 'label': 'Aides de ménage','professionType': '10' }," +
            "{'code': '92', 'label': 'Manoeuvres de lagriculture, de la pêche et de la sylviculture', 'professionType': '10'}," +
            "{'code': '93', 'label': 'Manoeuvres des mines, du bâtiment et des travaux publics, des industries manufacturières et des transports', 'professionType': '10'}," +
            "{'code': '94', 'label': 'Assistants de fabrication de lalimentation', 'professionType': '10' }," +
            "{'code': '95', 'label': 'Vendeurs ambulants et autres travailleurs des petits métiers des rues et assimilés', 'professionType': '10'}," +
            "{'code': '96', 'label': 'Eboueurs et autres travailleurs non qualifiés','professionType': '10' }," +
            "{'code': '97', 'label': 'Non Precisee', 'professionType': '01'}]";

}