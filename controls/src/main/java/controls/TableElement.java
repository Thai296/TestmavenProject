package controls;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class TableElement extends ControlledElement {
    public TableElement(WebDriver webDriver, By by) {
        super(webDriver, by);
        waitForDisplayed();
        retrieveControlledObject();
    }

    //translate(.,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqorstuvwxyz')
    public int getRowCount() {
        List<WebElement> eles = controlledObject.findElements(By.xpath("./tbody/tr"));
        return eles.size();
    }

    public int getColumnCount() {
        List<WebElement> eles = controlledObject.findElements(By.xpath("./thead/tr/th"));
        return eles.size();
    }

    public WebElement findRowByText(String text) {
        String xpath = "//tr[contains(.,\"" + text + "\")]";
        WebElement row = controlledObject.findElement(By.xpath(xpath));
        return row;
    }

    public WebElement findCellByText(String text) {
        String xpath = "//tr/th[contains(.,\"" + text + "\")]";
        waitForElementExists(xpath);
        WebElement row = controlledObject.findElement(By.xpath(xpath));
        return row;
    }

    public List<WebElement> findButtonsInRow(String text) {
        String xpath = "//tr[contains(.,\"" + text + "\")]/th/button";
        List<WebElement> btns = controlledObject.findElements(By.xpath(xpath));
        return btns;
    }

    public WebElement findButtonInRow(String text, String btnText) {
        String xpath = "//tr[contains(.,\"" + text +"\")]/th/button[contains(.,\"" + btnText + "\")]";
        waitForElementExists(xpath);
        WebElement btn = controlledObject.findElement(By.xpath(xpath));
        return btn;
    }

}
