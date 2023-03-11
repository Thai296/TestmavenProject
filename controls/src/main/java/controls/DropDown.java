


package controls;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import exception.MSeleniumException;
import trace.ExecutionTracer;
import trace.TracedAction;
import util.CustomWebDriverWait;

/**
 * This class contains Constructor for Super class ControlledElement and contains the custom methods for
 * different operations on drop down
 *
 * Created by NiTesH bharDWAJ
 */
public class DropDown extends ControlledElement
{

    /**
     * Initializes a new default instance of the DropDown class.
     *
     * @param webDriver Webdriver class object
     * @param by By class object
     */
    public DropDown(WebDriver webDriver, By by) {
        super(webDriver, by);
    }

    /**
     * Initializes a new default instance of the DropDown class with friendly name
     *
     * @param webDriver Webdriver class object
     * @param by By class object
     * @param friendlyName Friendly name for the DropDown
     */
    public DropDown(WebDriver webDriver, By by, String friendlyName) {
        super(webDriver, by, friendlyName);
    }

    private Select dropDown;

    // ==========
    // Commands	=
    // ==========

    /**
     * Method to select value from dropdown using visible text
     *
     * @param value dropdown value to select
     */
    public void select(final String value) {
        TracedAction action = ExecutionTracer.traceSelectAction(webDriver, this, value);
        performAction(new ManagedAction() {
            @Override
            public void execute() {
                retrieveControlledObject();
                long selectStart = System.currentTimeMillis();

                dropDown().selectByVisibleText(value);

                long timeToFindOption = (System.currentTimeMillis() - selectStart);
                println(value.toString() + " was selected in " + timeToFindOption + "ms");
                ExecutionTracer.updateMaxRetrievalTime(timeToFindOption);
            }
        });
        ExecutionTracer.add(action);
    }

    /**
     * Method to select option from drop down using value
     *
     * @param value dropdown value to select
     */
    public void selectByValue(final String value) {
        TracedAction action = ExecutionTracer.traceSelectAction(webDriver, this, value);
        performAction(new ManagedAction() {
            @Override
            public void execute() {
                retrieveControlledObject();
                long selectStart = System.currentTimeMillis();

                dropDown().selectByValue(value);

                long timeToFindOption = (System.currentTimeMillis() - selectStart);
                println(value.toString() + " was selected in " + timeToFindOption + "ms");
                ExecutionTracer.updateMaxRetrievalTime(timeToFindOption);
            }
        });
        ExecutionTracer.add(action);
    }

    /**
     * Method to select value from dropdown providing index
     *
     * @param itemIndex index of item to select
     */
    public void selectByIndex(final int itemIndex) {
        TracedAction action = ExecutionTracer.traceSelectAction(webDriver, this, "" + itemIndex);
        performAction(new ManagedAction() {
            @Override
            public void execute() {
                retrieveControlledObject();
                long selectStart = System.currentTimeMillis();

                dropDown().selectByIndex(itemIndex);

                long timeToFindOption = (System.currentTimeMillis() - selectStart);
                println(itemIndex + " was selected in " + timeToFindOption + "ms");
                ExecutionTracer.updateMaxRetrievalTime(timeToFindOption);
            }
        });
        ExecutionTracer.add(action);
    }

    /**
     * Method to select option from drop down using visible text
     *
     * @param value dropdown value text to select
     */
    public void selectByVisibleText(final String value) {
        select(value);
    }

    /**
     * Method to select a dropdown value using index
     *
     * @param value dropdown value
     */
    public void selectByIndex(final String value) {
        TracedAction action = ExecutionTracer.traceSelectAction(webDriver, this, value);
        performAction(new ManagedAction() {
            @Override
            public void execute() {
                set(value);
            }
        });
        ExecutionTracer.add(action);
    }

    /**
     * Method to select dropdown value using index
     *
     * @param value dropdown value to select
     * @param element controlled object to check
     */
    public void selectAndCheck(final String value, final ControlledElement element) {
        TracedAction action = ExecutionTracer.traceSelectAction(webDriver, this, value);
        performAction(new ManagedAction() {
            @Override
            public void execute() {
                set(value);
                element.checkDisplay();
            }
        });
        ExecutionTracer.add(action);
    }

    /**
     * Method to check the selected item with the given text value and throws exception if not found
     *
     * @param text dropdown value to verify
     */
    public void selectedValueShouldEqual(final String text) {
        performAction(new ManagedAction() {
            @Override
            public void execute() {
                retrieveControlledObject();
                String selectedValue = dropDown().getFirstSelectedOption().getText().trim();
                if (selectedValue == null || !selectedValue.equals(text)) {
                    throw new MSeleniumException("selected value of element " + by + " is not equal to " + text, webDriver);
                }
            }
        });
    }
    /**
     * Select option from drop down using Value.
     *
     * @param value dropdown value
     */
    public void selectByKeystrokes(final String value) {
        TracedAction action = ExecutionTracer.traceSelectAction(webDriver, this, value);
        performAction(new ManagedAction() {
            @Override
            public void execute() {
                retrieveControlledObject();
                long selectStart = System.currentTimeMillis();

                controlledObject.click();

                List<WebElement> options = dropDown().getOptions();
                String currentValue = dropDown().getFirstSelectedOption().getText();
                int numberOfDownKeyStrokes = getNumberOfKeystrokes(options, value, currentValue);

                println(numberOfDownKeyStrokes + " keystrokes are necessary to get to " + value + " from " + currentValue);

                if (numberOfDownKeyStrokes > 0) {
                    for (int i = 0; i < numberOfDownKeyStrokes; i++) {
                        controlledObject.sendKeys(Keys.ARROW_DOWN);
                    }
                } else {
                    numberOfDownKeyStrokes = -numberOfDownKeyStrokes;
                    for (int i = 0; i < numberOfDownKeyStrokes; i++) {
                        controlledObject.sendKeys(Keys.ARROW_UP);
                    }
                }
                controlledObject.sendKeys(Keys.ENTER);

                long timeToFindOption = (System.currentTimeMillis() - selectStart);
                println(value.toString() + " was selected in " + timeToFindOption + "ms");
                ExecutionTracer.updateMaxRetrievalTime(timeToFindOption);
            }
        });
        ExecutionTracer.add(action);
    }

    /**
     * Method to get available options of the dropdown
     *
     * @return all options as a list
     */
    public List<WebElement> getOptions() {
        return performAction(new ManagedActionWithReturn<List<WebElement>>() {
            @Override
            public List<WebElement> execute() {
                retrieveControlledObject();
                return dropDown().getOptions();
            }
        });
    }

    /**
     * Method to get the selected visible value of the dropdown
     *
     * @return the visible text of the selected option
     */
    public String getSelectedVisibleText() {
        return performAction(new ManagedActionWithReturn<String>() {
            @Override
            public String execute() {
                retrieveControlledObject();
                return dropDown().getFirstSelectedOption().getText();
            }
        });
    }

    /**
     * Deselect all the selected options in dropdown having type multiple.
     */
    public void deselectAll() {
        performAction(new ManagedAction() {
            @Override
            public void execute() {
                retrieveControlledObject();
                dropDown().deselectAll();
            }
        });
    }

    // ==================
    // Private methods	=
    // ==================

    /**
     * Method to get the dropdown controlled object
     *
     * @return the drop-down
     */
    private Select dropDown() {
        if (dropDown == null) {
            dropDown = new Select(controlledObject);
        }
        return dropDown;
    }

    /**
     * Method to set the value in dropdown
     *
     * @param value value to set
     */
    private void set(String value) {
        retrieveControlledObject();
        long selectStart = System.currentTimeMillis();

        List<WebElement> options = dropDown().getOptions();
        WebElement option = getOption(options, value);
        dropDown().selectByIndex(options.indexOf(option));
        controlledObject.sendKeys(Keys.ENTER);

        long timeToFindOption = (System.currentTimeMillis() - selectStart);
        println(value.toString() + " was selected in " + timeToFindOption + "ms");
        ExecutionTracer.updateMaxRetrievalTime(timeToFindOption);
        shouldBeSelected(option);
    }

    /**
     * Method to get option from dropdown and throws exception if item not found in dropdown
     *
     * @param options dropdown items
     * @param value item to select
     * @return dropdown item
     */
    private WebElement getOption(List<WebElement> options, String value) {
        WebElement webElement = null;
        int index = 0;
        int i = 0;
        for (WebElement option : options) {
            if (option.getText().trim().equals(value)) {
                if (webElement == null) {
                    webElement = option;
                    index = i;
                } else {
                    println("WARN: options " + index + " and " + i + "have same value " + value);
                }
            }
            i = i + 1;
        }
        if (webElement != null) {
            return webElement;
        } else {
            println("ERROR: Option " + value + " in menu " + by + " cannot be found");
            println("ERROR: Options available are: ");
            for (WebElement option : options) {
                println("DEBUG: option " + index + " is " + option.getText());
                i = i + 1;
            }

            throw new MSeleniumException("Option " + value + " in menu " + by + " cannot be found", webDriver);

        }
    }

    private int getNumberOfKeystrokes(List<WebElement> options, String valueToSelect, String currentValue) {
        int i = 0;
        Integer indexOfValueToSelect = null;
        Integer indexOfCurrentValue = null;
        for (WebElement option : options) {
            if (option.getText().trim().equals(valueToSelect)) {
                indexOfValueToSelect = i;
            }
            if (option.getText().trim().equals(currentValue)) {
                indexOfCurrentValue = i;
            }
            i = i + 1;
        }
        if (indexOfValueToSelect != null && indexOfCurrentValue != null) {
            return indexOfValueToSelect - indexOfCurrentValue;
        } else {
            i = 0;
            for (WebElement option : options) {
                println("DEBUG: option " + i + " is " + option.getText());
                i = i + 1;
            }
            throw new MSeleniumException("Selecting  " + valueToSelect + " from " + currentValue + "in menu " + by + " cannot be found",
                    webDriver);
        }
    }

    /**
     * Method to check dropdown item is selected and throws exception if not selected
     *
     * @param option item that should be selected
     */
    private void shouldBeSelected(WebElement option) {
        long findStart = System.currentTimeMillis();

        boolean isSelected = new CustomWebDriverWait(webDriver, TIME_OUT_IN_SECONDS).until(ExpectedConditions.elementToBeSelected(option));
        long timeToRetrieveElement = (System.currentTimeMillis() - findStart);
        println(option.toString() + " was found in " + timeToRetrieveElement + "ms");
        ExecutionTracer.updateMaxRetrievalTime(timeToRetrieveElement);

        if (!isSelected) {
            throw new MSeleniumException("value " + option + " is not selected", webDriver);
        }
    }

    /**
     * Method to get the size of dropdown list
     *
     * @return size of the drop down list
     */
    public int getSize() {
        retrieveControlledObject();
        List<WebElement> options = dropDown().getOptions();
        int size = options.size();
        return size;
    }

    /**
     * Method to check presence of option in dropdown and throws exception if not found in list
     *
     * @param expected expected drop down option
     */
    public void optionShouldBePresent(String expected) {
        List<WebElement> options = getOptions();
        for (WebElement opt : options) {
            if (opt.getText().trim().equals(expected)) {
                return;
            }
        }
        throw new MSeleniumException("option " + expected + " is not present in the list", webDriver);
    }

    /**
     * Method to check absence of option in dropdown and throws exception if option found in list
     *
     * @param notExpected drop down option
     */
    public void optionShouldNotBePresent(String notExpected) {
        List<WebElement> options = getOptions();
        for (WebElement opt : options) {
            if (opt.getText().equals(notExpected)) {
                throw new MSeleniumException("option " + notExpected + " was found in the list", webDriver);
            }
        }
    }

    /**
     * Method to clear dropdown state
     */
    @Override
    protected void clearState() {
        super.clearState();
        dropDown = null;
    }
}
