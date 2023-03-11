package controls;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import exception.MSeleniumException;
import trace.ExecutionTracer;
import trace.TracedAction;
import util.CommonUtil;

/**
 * This class contains customized methods of selenium webdriver for Text Field.
 *
 * Created by NiTesH bharDWAJ
 */


public class TextField extends ControlledElement
{

    private final boolean validateEnteredValue;

    public TextField(WebDriver webDriver, By by) {
        super(webDriver, by);
        validateEnteredValue = true;
    }

    public TextField(WebDriver webDriver, By by, String friendlyName) {
        super(webDriver, by, friendlyName);
        validateEnteredValue = true;
    }

    public TextField(WebDriver webDriver, By by, String friendlyName, boolean validateEnteredValue) {
        super(webDriver, by, friendlyName);
        this.validateEnteredValue = validateEnteredValue;
    }

    /**
     * clear element
     */
    public void clear() {
        performAction(new ManagedAction() {
            @Override
            public void execute() {
                retrieveControlledObject();
                controlledObject.clear();
            }
        });
    }

    /**
     * To perform type keys action with java script
     *
     * @param data value of text field.
     */
    public void typeKeysWithJavaScript(String data) {
        TracedAction action = ExecutionTracer.traceTypeAction(webDriver, this, data);
        String object = by.toString().replace("By.id: ", "");
        executeScript("document.getElementById('" + object + "').value = '" + data + "';");
        ExecutionTracer.add(action);
    }

    /**
     * Clear a field by deleting number of characters
     *
     * @param numberOfCharacters number of chracters to delete
     */
    public void clear(final int numberOfCharacters) {
        performAction(new ManagedAction() {
            @Override
            public void execute() {
                retrieveControlledObject();
                for (int i = 0; i < numberOfCharacters; i++) {
                    controlledObject.sendKeys(Keys.BACK_SPACE);
                }
            }
        });
    }

    /**
     * Type value
     *
     * @param value value to type
     */
    public void typeKeys(final String value) {
        TracedAction action = ExecutionTracer.traceTypeAction(webDriver, this, value);
        performAction(new ManagedAction() {
            @Override
            public void execute() {
                retrieveControlledObject();
                controlledObject.sendKeys(value);
                println("sending keys " + value);
            }
        });
        ExecutionTracer.add(action);
    }

    /**
     * type a value and check element
     *
     * @param value value to type
     * @param element element to check
     */
    public void typeKeysAndCheck(final String value, final ControlledElement element) {
        TracedAction action = ExecutionTracer.traceTypeAction(webDriver, this, value);
        performAction(new ManagedAction() {
            @Override
            public void execute() {
                retrieveControlledObject();
                println("sending keys " + value);
                controlledObject.sendKeys(value);
                element.shouldBeDisplayed();
            }
        });
        ExecutionTracer.add(action);
    }

    /**
     * Type value
     *
     * @param keysToSend value to type
     */
    public void typeKeys(final CharSequence... keysToSend) {
        TracedAction action = ExecutionTracer.traceTypeAction(webDriver, this, "" + keysToSend);
        performAction(new ManagedAction() {
            @Override
            public void execute() {
                retrieveControlledObject();
                controlledObject.sendKeys(keysToSend);
            }
        });
        ExecutionTracer.add(action);
    }

    /**
     * Clear, type a value,
     *
     * @param keysToSend value to type
     * @param element element to check
     */
    public void clearTypeKeysAndCheck(final String keysToSend, final ControlledElement element) {
        TracedAction action = ExecutionTracer.traceTypeAction(webDriver, this, keysToSend);
        performAction(new ManagedAction() {
            @Override
            public void execute() {
                retrieveControlledObject();
                controlledObject.clear();
                controlledObject.sendKeys(keysToSend);
                element.shouldBeDisplayed();
            }
        });
        ExecutionTracer.add(action);
    }

    /**
     * clear, type the value and check the new value is the entered value
     *
     * @param element element on which text field is dependent for display
     * @param value value entered
     */
    public void clearAndTypeKeys(final ControlledElement element, final String value) {
        TracedAction action = ExecutionTracer.traceTypeAction(webDriver, this, value);
        performAction(new ManagedAction() {
            @Override
            public void execute() {
                retrieveControlledObject();
                controlledObject.clear();
                controlledObject.sendKeys(value);
                if (validateEnteredValue) {
                    String valueFromElement = controlledObject.getAttribute("value");
                    if (!value.equals(valueFromElement)) {
                        LOGGER.info(CommonUtil.logPrefix() + "WARN: value from field " + valueFromElement
                                + " is not the same as typed value " + value + " - fix script");
                        if (!value.startsWith(valueFromElement) && !value.endsWith(valueFromElement)) {
                            throw new MSeleniumException("Value is supposed to be " + value + " but is still " + valueFromElement,
                                    webDriver);
                        }
                    }
                }
            }

            @Override
            public void reset() {
                element.click();
            }
        });
        ExecutionTracer.add(action);
    }

    /**
     * clear and type value
     *
     * @param value value to type
     */
    public void clearAndTypeKeys(final String value) {
        TracedAction action = ExecutionTracer.traceTypeAction(webDriver, this, value);
        performAction(new ManagedAction() {
            @Override
            public void execute() {
                retrieveControlledObject();
                controlledObject.clear();
                println("sending keys " + value);
                controlledObject.sendKeys(value);
                if (validateEnteredValue) {
                    String valueFromElement = controlledObject.getAttribute("value");
                    if (!value.equals(valueFromElement)) {
                        LOGGER.info(CommonUtil.logPrefix() + "WARN: value from field " + valueFromElement
                                + " is not the same as typed value " + value + " - fix script");
                        if (!value.startsWith(valueFromElement) && !value.endsWith(valueFromElement)) {
                            throw new MSeleniumException("Value is supposed to be " + value + " but is still " + valueFromElement,
                                    webDriver);
                        }
                    }
                }
            }
        });
        ExecutionTracer.add(action);
    }

    /**
     * Clear, tab, type value & tab away.
     *
     * @param value value to type
     */
    public void clearAndTypeKeysWithTabAways(final String value) {
        TracedAction action = ExecutionTracer.traceTypeAction(webDriver, this, value);
        performAction(new ManagedAction() {
            @Override
            public void execute() {
                retrieveControlledObject();
                controlledObject.clear();
                controlledObject.sendKeys(Keys.TAB);
                println("sending keys " + value);
                controlledObject.sendKeys(value);
                if (validateEnteredValue) {
                    String valueFromElement = controlledObject.getAttribute("value");
                    if (!value.equals(valueFromElement)) {
                        LOGGER.info(CommonUtil.logPrefix() + "WARN: value from field " + valueFromElement
                                + " is not the same as typed value " + value + " - fix script");
                        if (!value.startsWith(valueFromElement)) {
                            throw new MSeleniumException("Value is supposed to be " + value + " but is still " + valueFromElement,
                                    webDriver);
                        }
                    }
                }
                controlledObject.sendKeys(Keys.TAB);
            }
        });
        ExecutionTracer.add(action);
    }

    /**
     * Clear, type value & tab away.
     *
     * @param value value to type
     */
    public void clearTypeKeysAndTab(final String value) {
        TracedAction action = ExecutionTracer.traceTypeAction(webDriver, this, value);
        performAction(new ManagedAction() {
            @Override
            public void execute() {
                retrieveControlledObject();
                controlledObject.clear();
                controlledObject.sendKeys(value);
                println("sending keys " + value);
                if (validateEnteredValue) {
                    String valueFromElement = controlledObject.getAttribute("value");
                    if (!value.equals(valueFromElement)) {
                        LOGGER.info(CommonUtil.logPrefix() + "WARN: value from field " + valueFromElement
                                + " is not the same as typed value " + value + " - fix script");
                        if (!value.startsWith(valueFromElement)) {
                            throw new MSeleniumException("Value is supposed to be " + value + " but is still " + valueFromElement,
                                    webDriver);
                        }
                    }
                }
                controlledObject.sendKeys(Keys.TAB);
            }
        });
        ExecutionTracer.add(action);
    }

    /**
     * Clear, tab, type value & tab away.
     *
     * @param value value to type
     */
    public void clearAndTypeKeysWithTabAways(final int numberOfCharacters, final String value) {
        TracedAction action = ExecutionTracer.traceTypeAction(webDriver, this, value);
        performAction(new ManagedAction() {
            @Override
            public void execute() {
                retrieveControlledObject();
                clear(numberOfCharacters);
                controlledObject.sendKeys(Keys.TAB);
                println("sending keys " + value);
                controlledObject.sendKeys(value);
                if (validateEnteredValue) {
                    String valueFromElement = controlledObject.getAttribute("value");
                    if (!value.equals(valueFromElement)) {
                        LOGGER.info(CommonUtil.logPrefix() + "WARN: value from field " + valueFromElement
                                + " is not the same as typed value " + value + " - fix script");
                        if (!value.startsWith(valueFromElement)) {
                            throw new MSeleniumException("Value is supposed to be " + value + " but is still " + valueFromElement,
                                    webDriver);
                        }
                    }
                }
                controlledObject.sendKeys(Keys.TAB);
            }
        });
        ExecutionTracer.add(action);
    }

//    /**
//     * Select Value, type value & tab away
//     *
//     * @param value value to type
//     */
//    public void overwriteValueAndTab(final String value) {
//        TracedAction action = ExecutionTracer.traceTypeAction(webDriver, this, value);
//        performAction(new ManagedAction() {
//            @Override
//            public void execute() {
//                retrieveControlledObject();
//                controlledObject.sendKeys(Keys.chord(Keys.CONTROL, "a"));
//                controlledObject.sendKeys(value);
//                println("sending keys " + value);
//                if (validateEnteredValue) {
//                    String valueFromElement = controlledObject.getAttribute("value");
//                    if (!value.equals(valueFromElement)) {
//                        LOGGER.info(CommonUtil.logPrefix() + "WARN: value from field " + valueFromElement
//                                + " is not the same as typed value " + value + " - fix script");
//                        if (!value.startsWith(valueFromElement)) {
//                            throw new MSeleniumException("Value is supposed to be " + value + " but is still " + valueFromElement,
//                                    webDriver);
//                        }
//                    }
//                }
//                controlledObject.sendKeys(Keys.TAB);
//            }
//        });
//        ExecutionTracer.add(action);
//    }
}
