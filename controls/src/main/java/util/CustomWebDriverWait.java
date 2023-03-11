package util;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CustomWebDriverWait extends WebDriverWait {
    private final WebDriver driver;

    public CustomWebDriverWait(WebDriver driver, long timeOutInSeconds) {
        super(driver,timeOutInSeconds);

        this.driver = driver;
    }

    @Override
    protected RuntimeException timeoutException(String message, Throwable lastException) {
        String screenshotFileName = CommonUtil.takeScreenshot(driver);
        CommonUtil.log("Screenshot taken on exception: " + screenshotFileName + ", error message: " + message);
        return super.timeoutException(message + ", screenshot: " + screenshotFileName, lastException);
    }
}
