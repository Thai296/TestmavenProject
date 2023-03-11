package driver;

/**
 * This class contains methods to set and get current method
 */
public class Util
{
    public static Long maxWaitTimeToFindElement = (long) 30;
    public String currentMethod;

    /**
     * Method to set current method name
     *
     * @param methodName method name to set
     */
    public void setCurrentMethodName(String methodName) {
        currentMethod = methodName;
    }

    /**
     * Method to get current method name
     *
     * @return current method name
     */
    public String getCurrentMethodName() {
        return currentMethod;
    }

}
