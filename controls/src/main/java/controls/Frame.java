package controls;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * This class contains customized methods of selenium webdriver for Frame
 *
 * Created by NiTesH bharDWAJ
 */

public class Frame extends ControlledObject<WebDriver>
{

    public static ThreadLocal<String> currentFrameIndentifier = new ThreadLocal<String>();

    private final Frame parentFrame;

    /**
     * Initializes a new default instance of the Frame class.
     *
     * @param webDriver Webdriver class object
     * @param by By class object
     * @param identifier Frame Identifier
     */
    public Frame(WebDriver webDriver, By by, String identifier) {
        super(webDriver, by, ExpectedConditions.frameToBeAvailableAndSwitchToIt(by));
        this.parentFrame = null;
        currentFrameIndentifier.set(identifier);
    }

    /**
     * Initializes a new default instance of the Frame class.
     *
     * @param webDriver Webdriver class object
     * @param by By class object
     * @param identifier Frame Identifier
     * @param parentFrame Parent Frame
     */
    public Frame(WebDriver webDriver, By by, String identifier, Frame parentFrame) {
        super(webDriver, by, ExpectedConditions.frameToBeAvailableAndSwitchToIt(by));
        this.parentFrame = parentFrame;
        currentFrameIndentifier.set(identifier);
    }

    /**
     * Initializes a new default instance of the Frame class.
     *
     * @param webDriver Webdriver class object
     * @param by By class object
     */
    public Frame(WebDriver webDriver, By by) {
        super(webDriver, by, ExpectedConditions.frameToBeAvailableAndSwitchToIt(by));
        this.parentFrame = null;
    }

    /**
     * Initializes a new default instance of the Frame class.
     *
     * @param webDriver Webdriver class object
     * @param by By class object
     * @param parentFrame Parent Frame
     */
    public Frame(WebDriver webDriver, By by, Frame parentFrame) {
        super(webDriver, by, ExpectedConditions.frameToBeAvailableAndSwitchToIt(by));
        this.parentFrame = parentFrame;
    }

    /**
     * Initializes a new default instance of the Frame class.
     *
     * @param webDriver Webdriver class object
     * @param by By class object
     * @param identifier Frame Identifier
     * @param friendlyName Friendly name for the Frame
     */
    public Frame(WebDriver webDriver, By by, String identifier, String friendlyName) {
        super(webDriver, by, ExpectedConditions.frameToBeAvailableAndSwitchToIt(by), friendlyName);
        this.parentFrame = null;
        currentFrameIndentifier.set(identifier);
    }

    /**
     * Initializes a new default instance of the Frame class.
     *
     * @param webDriver Webdriver class object
     * @param by By class object
     * @param identifier Frame Identifier
     * @param parentFrame Parent Frame
     * @param friendlyName Friendly name for the Frame
     */
    public Frame(WebDriver webDriver, By by, String identifier, Frame parentFrame, String friendlyName) {
        super(webDriver, by, ExpectedConditions.frameToBeAvailableAndSwitchToIt(by), friendlyName);
        this.parentFrame = parentFrame;
        currentFrameIndentifier.set(identifier);
    }

    /**
     * Method for switch to frame
     */
    public void switchToFrame() {
        performAction(new ManagedAction() {
            @Override
            public void execute() {
                try {
                    retrieveControlledObject();
                } catch (TimeoutException exc) {
                    if (parentFrame != null) {
                        println("WARN: frame " + by
                                + " could not be found, but parent is specified, switching to frame from default content");
                        switchToFrameFromDefaultContent();
                        println("FRAME RECOVERY SUCCESSFUL !!!");
                    } else {
                        throw exc;
                    }
                }

            }
        });
    }

    /**
     * Method to switch to frame and check control object display
     *
     * @param element controlled object
     */
    public void switchToFrameAndCheck(final ControlledElement element) {
        performAction(new ManagedAction() {
            @Override
            public void execute() {
                retrieveControlledObject();
                element.checkDisplay();
            }
        });
    }

    /**
     * Method to get full frame path
     *
     * @return list of frames
     */
    private List<Frame> fullFramePath() {
        List<Frame> fullFramePath = new ArrayList<Frame>();
        if (parentFrame != null) {
            fullFramePath.addAll(parentFrame.fullFramePath());
        }
        fullFramePath.add(this);
        return fullFramePath;
    }

    /**
     * Method to switch to frame from default content
     */
    private void switchToFrameFromDefaultContent() {
        println("frame recovery switching to default content");
        webDriver.switchTo().defaultContent();
        println("switching to default content successful");
        for (Frame frame : fullFramePath()) {
            println("frame recovery switching to " + frame.by);
            frame.switchToFrame();
            println("switching to " + frame.by + " successful");
        }
    }
}

