package com.simplicia.pages.web.utils;

public interface TestData
{
   String EMAIL = "paulbedoucha@gmail.com";
   String PASSWORD = "test"; // used with paulbedoucha@gmail.com +
   String PASSWORD1 = "D0S1Sm04jaIF#XhFYd"; // for creating new user as per new password policy
   String LAWYEREMAIL = "paul.bedoucha@gmail.com";
   String ADMIN_EMAIL = "admin@simplicia.co";
   String ADMIN_PASSWORD = "Simplicia@Admin";
   //String APPLICATION_URL = System.getenv("APP_URL")+"/login";
   String APPLICATION_URL = "https://qa2.simplicia.co:7443/login";
   //String MY_DATS_URL = System.getenv("APP_URL")+"/dat/all";
   String MY_DATS_URL = "https://qa2.simplicia.co:7443/dat/all";
   //String API_URL  = System.getenv("APP_URL")+"/api" ;
   String API_URL  = "https://qa2.simplicia.co:7443/api";
   String CLIENT_ID = "2c9380837458523b01745858ade60003";
   //String REGISTRATION_URL = System.getenv("APP_URL")+"/register?referralId=2c9380837458523b017458525d580001&type=C";
   String REGISTRATION_URL = "https://qa2.simplicia.co:7443/register?referralId=2c9380837458523b017458525d580001&type=C";
   // Table Filters
   String TOUS = "Tous";
   String NINETY_J ="+90J";
   String FOURTY_FIVE_J ="+45J";
   String RENTES = "Rentes";
   String CETTE_SEMAINE= "Cette semaine";
   String CE_MOIS_CI =  "Ce mois-ci";
   String AT = "AT";
   String MP = "MP";

   // Table Headers
   String SIRET= "Siret";
   String SOCIETE ="Société";
   String NOM ="Nom";
   String PRENOM ="Prenom";
   String TYPE ="Type";
   String DATE_DE_NOTIFICATION ="Date de notification";
   String NB_JOURS_ARRET_PRECEDENT ="NB jours arrêt précédent";
   String NB_JOURS_ARRET_ACTUEL ="NB jours arrêt actuel";
   String PERCENT_IP ="%IP";
   String CEC ="CEC";
   String EXERCICE = "Exercice";
   String NIC = "Nic";
   String RISQUE = "Risque";
   String CUSTOM1 = "Custom1";
   String CUSTOM2 = "Custom2";
   String NNS = "NNS";
   String DATE_SINISTRE = "Date sinistre";
   String DATE_DE_FRAICHEUR = "Date de Fraîcheur";
   String NIVEAU_CCM_IT = "Niveau CCM IT";
   String VALUER_RISQUE = "Valeur risque";
   String TARIF_CCM_IT = "Tarif CCM IT";
   String DERNIERE_MISE_A_JOUR = "Dernière mise à jour";
}
