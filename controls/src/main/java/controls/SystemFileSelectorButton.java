package controls;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import exception.MSeleniumException;
import trace.ExecutionTracer;
import trace.TracedAction;

/**
 * This class contains the overridden methods of selenium webdriver and customized them. Also contain the
 * custom methods for different operations for System File Selector Button
 *
 * Created by NiTesH bharDWAJ
 */

public class SystemFileSelectorButton extends Button
{
    /**
     * Initializes a new default instance of the SystemFileSelectorButton class.
     *
     * @param webDriver Webdriver class object
     * @param by By class object
     */
    public SystemFileSelectorButton(WebDriver webDriver, By by) {
        super(webDriver, by);
    }

    /**
     * Initializes a new default instance of the SystemFileSelectorButton class with friendly name
     *
     * @param webDriver Webdriver class object
     * @param by By class object
     * @param friendlyName Friendly name for the SystemFileSelectorButton
     */
    public SystemFileSelectorButton(WebDriver webDriver, By by, String friendlyName) {
        super(webDriver, by, friendlyName);
    }

    /**
     * Upload a file using Robot class
     *
     * @param absolutePathFile full path including file name of the file to select
     */
    public void selectFile(String absolutePathFile) {
        TracedAction action = ExecutionTracer.traceClickAction(webDriver, this);
        click();
        try {
            Robot r = new Robot();
            for (char character : absolutePathFile.toCharArray()) {
                if (character != ':') {
                    if (Character.isUpperCase(character)) {
                        r.keyPress(KeyEvent.VK_SHIFT);
                    }
                    r.keyPress(KeyEvent.getExtendedKeyCodeForChar(character));
                    r.keyRelease(KeyEvent.getExtendedKeyCodeForChar(character));
                    if (Character.isUpperCase(character)) {
                        r.keyRelease(KeyEvent.VK_SHIFT);
                    }
                } else {
                    r.keyPress(KeyEvent.VK_SHIFT);
                    r.keyPress(KeyEvent.VK_SEMICOLON);
                    r.keyRelease(KeyEvent.VK_SEMICOLON);
                    r.keyRelease(KeyEvent.VK_SHIFT);
                }
            }
            wait("because of system file selection", 2);
            r.keyPress(KeyEvent.VK_ENTER);
            r.keyRelease(KeyEvent.VK_ENTER);
        } catch (AWTException exc) {
            throw new MSeleniumException("Unable to select file " + absolutePathFile + " through system file selector", webDriver);
        }
        ExecutionTracer.add(action);
    }

    /**
     * Upload a file using Robot class using copy and paste
     *
     * @param absolutePathFile full path including file name of the file to select
     */
    public void selectFileByCopyPaste(String absolutePathFile) {
        TracedAction action = ExecutionTracer.traceClickAction(webDriver, this);
        click();
        // Copying absolute path file to system clipboard (Copy)
        StringSelection s = new StringSelection(absolutePathFile);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(s, null);
        try {
            Robot r = new Robot();
            // Pasting absolute path file from system clipboard
            r.keyPress(KeyEvent.VK_CONTROL);
            r.keyPress(KeyEvent.VK_V);
            r.keyRelease(KeyEvent.VK_V);
            r.keyRelease(KeyEvent.VK_CONTROL);
            wait("because of system file selection", 2);
            r.keyPress(KeyEvent.VK_ENTER);
            r.keyRelease(KeyEvent.VK_ENTER);
        } catch (AWTException exc) {
            throw new MSeleniumException("Unable to select file " + absolutePathFile + " through system file selector", webDriver);
        }
        ExecutionTracer.add(action);
    }
}
