package com.simplicia.web.dashboard;

import com.simplicia.web.LoginOutLawyerPerMethodBaseTest;
import io.qameta.allure.Description;
import org.testng.annotations.Test;

/**
 *
 */
public class LawyerDashboardUIValidationTest extends LoginOutLawyerPerMethodBaseTest {

    @Test
    @Description("Test the dashboard of lawyer and checking switching client")
    public void testSwitchingClient() {
        lawyerSwitchClient("adequat");
        // switch back to Adobe
        lawyerSwitchClient("Adobe");
    }

}