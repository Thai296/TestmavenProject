package com.simplicia.web.leftnavigationmenu;

import com.simplicia.web.LoginOutLawyerPerMethodBaseTest;
import io.qameta.allure.Description;
import org.testng.annotations.Test;

/**
 * Test Cases of Left navigation menu
 *
 * - Lawyer should switch between clients
 * - Lawyer should be able to see all the menu items as client sees.
 * - Then logout successfully
 *
 */
public class LeftNavigationMenuUIValidationForLawyerTest extends LoginOutLawyerPerMethodBaseTest {

    @Test(enabled = true)
    @Description("Check the menu items from Lawyer point of view on client Adobe")
    public void testDashboardIconAndMenu() {

        lawyerSwitchClient("Adobe");

        homePage.openLeftPanel();
        simpliciaReusableActions.checkPresenceOfIcon("dashboard");
        leftNavigationMenuPage.checkPresenceOfLeftNavigationMenuOption("Menu");
        leftNavigationMenuPage.CheckPresenceOfSaasText();
        leftNavigationMenuPage.checkPresenceOfLeftNavigationMenuOption("Accueil");
        leftNavigationMenuPage.checkPresenceOfLeftNavigationMenuOption("Questionnaires Risques Pro");

        // DAT
        leftNavigationMenuPage.checkPresenceOfLeftNavigationMenuOption("DAT");
        leftNavigationMenuPage.clickExpandIconToOpenMenu("DAT");
        leftNavigationMenuPage.checkPresenceOfLeftNavigationMenuOption("Mes DATs");
        leftNavigationMenuPage.clickExpandIconToOpenMenu("Mes DATs");
        leftNavigationMenuPage.checkPresenceOfLeftNavigationMenuOption("● DATs en cours de rédaction");
        leftNavigationMenuPage.checkPresenceOfLeftNavigationMenuOption("● DATs complétées");
        leftNavigationMenuPage.checkPresenceOfLeftNavigationMenuOption("● DATs en révision par l’avocat");
        leftNavigationMenuPage.checkPresenceOfLeftNavigationMenuOption("● DATs validées Avocat");
        leftNavigationMenuPage.checkPresenceOfLeftNavigationMenuOption("● DATs en cours d’envoi Net-entreprises");
        leftNavigationMenuPage.checkPresenceOfLeftNavigationMenuOption("● DATs envoyées Net-entreprises");
        leftNavigationMenuPage.checkPresenceOfLeftNavigationMenuOption("● DATs archivées");

        // ATMP
        leftNavigationMenuPage.checkPresenceOfLeftNavigationMenuOption("Comptes employeurs");
        leftNavigationMenuPage.clickExpandIconToOpenMenu("Comptes employeurs");
        leftNavigationMenuPage.checkPresenceOfLeftNavigationMenuOption("Tous mes ATMPs");
        // remove open to expand (Tous mes ATMPs)
        //leftNavigationMenuPage.clickExpandIconToOpenMenu("Tous mes ATMPs");
        leftNavigationMenuPage.checkPresenceOfLeftNavigationMenuOption("PDFs comptes employeur");
        leftNavigationMenuPage.checkPresenceOfLeftNavigationMenuOption("Taux Définitifs");
        leftNavigationMenuPage.checkPresenceOfLeftNavigationMenuOption("Ticket de changement de taux");

        // BPIJ
        leftNavigationMenuPage.checkPresenceOfLeftNavigationMenuOption("BPIJ");
        leftNavigationMenuPage.clickExpandIconToOpenMenu("BPIJ");
        leftNavigationMenuPage.checkPresenceOfLeftNavigationMenuOption("Mes BPIJs");
        leftNavigationMenuPage.checkPresenceOfLeftNavigationMenuOption("Toutes mes IJs");
        leftNavigationMenuPage.checkPresenceOfLeftNavigationMenuOption("Graphe Global IJ");
        leftNavigationMenuPage.checkPresenceOfLeftNavigationMenuOption("Ecarts IJSS");
        leftNavigationMenuPage.checkPresenceOfLeftNavigationMenuOption("Suivi tickets IJSS");

        // Mes accès
        leftNavigationMenuPage.checkPresenceOfLeftNavigationMenuOption("Mes accès");
        leftNavigationMenuPage.clickExpandIconToOpenMenu("Mes accès");
        leftNavigationMenuPage.checkPresenceOfLeftNavigationMenuOption("Risques Pro Améli");
        leftNavigationMenuPage.checkPresenceOfLeftNavigationMenuOption("Net-Entreprises");
        leftNavigationMenuPage.clickExpandIconToOpenMenu("Net-Entreprises");
        leftNavigationMenuPage.checkPresenceOfLeftNavigationMenuOption("● Comptes ATMP OK");
        leftNavigationMenuPage.checkPresenceOfLeftNavigationMenuOption("● Comptes ATMP sans AT");
        leftNavigationMenuPage.checkPresenceOfLeftNavigationMenuOption("● Comptes avec Expiration du mdp");
        leftNavigationMenuPage.checkPresenceOfLeftNavigationMenuOption("● Comptes avec Connexion Echouée");

        leftNavigationMenuPage.checkPresenceOfLeftNavigationMenuOption("Mon Compte");
        leftNavigationMenuPage.checkPresenceOfLeftNavigationMenuOption("Notifications");
        leftNavigationMenuPage.checkPresenceOfLeftNavigationMenuOption("Se Déconnecter");
    }

}