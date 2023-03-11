
package trace;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import util.CommonUtil;

/**
 * This class contains method to create screen trace and trace for action type
 *
 * Created by NiTesH bharDWAJ
 */
public class TracedAction
{
    private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(TracedAction.class);
    private final long startTime = System.currentTimeMillis();
    private final int actionIndex;
    private final TraceActionType actionType;
    private final String description;
    private final byte[] screenshot;

    private String screenshotFileName;
    private File screenshotFile;

    // ==============
    // Constructors	=
    // ==============

    /**
     * Initializes a new default instance of the TracedAction class.
     *
     * @param actionIndex Index of the action
     * @param actionType Action Type click, type...
     * @param description Friendly name as description
     * @param screenshot Screenshot name
     */
    public TracedAction(int actionIndex, TraceActionType actionType, String description, byte[] screenshot) {
        this.actionIndex = actionIndex;
        this.actionType = actionType;
        this.description = description;
        this.screenshotFileName = "screenshot" + System.currentTimeMillis() + ".png";
        this.screenshot = screenshot;

        CommonUtil.copyScreenshot(this.screenshotFileName, screenshot);

        LOGGER.info(CommonUtil.logPrefix() + "Action traced with screenshot " + screenshotFileName);
    }

    /**
     * Initializes a new default instance of the TracedAction class.
     *
     * @param actionIndex Index of the action
     * @param actionType Action Type click, type...
     * @param description Friendly name as description
     */
    public TracedAction(int actionIndex, TraceActionType actionType, String description) {
        this.actionIndex = actionIndex;
        this.actionType = actionType;
        this.description = description;
        this.screenshot = null;
    }

    // ==========
    // Commands	=
    // ==========

    /**
     * Method to get created file from provided file path
     *
     * @param folderPath path of the file
     * @return screenshot as file
     */
    public File getScreenshotFileName(String folderPath) {
        if (screenshotFile == null) {
            String fullFileName = FilenameUtils.concat(folderPath, screenshotFileName);
            LOGGER.info(CommonUtil.logPrefix() + "Screenshot saved as " + fullFileName);
            screenshotFile = new File(fullFileName);
            try {
                FileUtils.writeByteArrayToFile(screenshotFile, screenshot);
            } catch (IOException exc) {
                exc.printStackTrace();
            }
        }
        return screenshotFile;
    }

    /**
     * Method to Get Action Index
     *
     * @return integer value of action index
     */
    public int actionIndex() {
        return actionIndex;
    }

    /**
     * Method to get current trace action type
     *
     * @return TracedActionType as 'CHECK', 'CLICK', 'CLICKALL', 'SELECT' or 'TYPE'
     */
    public TraceActionType actionType() {
        return actionType;
    }

    /**
     * Method to get description of Traced Action
     *
     * @return string value of description
     */
    public String description() {
        return description;
    }

    /**
     * Method to get screenshot of the Traced Action
     *
     * @return screenshot as byte array
     */
    public byte[] screenshot() {
        return screenshot;
    }

    /**
     * Method to Get Start Time of Traced Action
     *
     * @return long value of start time
     */
    public long startTime() {
        return startTime;
    }
}

