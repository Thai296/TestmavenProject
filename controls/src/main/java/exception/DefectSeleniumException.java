package exception;

/**
 * This class extends MSeleniumException to get defect in logs with step number, defect ID and summary
 */
public class DefectSeleniumException extends MSeleniumException
{
    private static final long serialVersionUID = 1L;

    /**
     * Initializes a new default instance of the DefectSeleniumException class.
     *
     * @param step Step Number
     * @param defectID Defect ID
     * @param defectTitle Defect Summary
     * @param cause Cause of failure
     */
    public DefectSeleniumException(int step, String defectID, String defectTitle, MSeleniumException cause) {
        super(cause, "STEP " + step + "- Defect [" + defectID + "]:" + defectTitle);
    }

    /**
     * Initializes a new default instance of the DefectSeleniumException class.
     *
     * @param defectID Defect ID
     * @param defectTitle Defect Summary
     * @param cause Cause of failure
     */
    public DefectSeleniumException(String defectID, String defectTitle, MSeleniumException cause) {
        super(cause, "Defect [" + defectID + "]:" + defectTitle);
    }
}