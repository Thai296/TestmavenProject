package controls;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import trace.ExecutionTracer;
import trace.TracedAction;

/**
 * This class contains the overridden methods of selenium webdriver and customized them. Also contain the
 * custom methods for different operations for Searchable DropDown
 *
 * Created by NiTesH bharDWAJ
 */

public class SearchableDropDown extends DropDown
{
    private final TextField searchTextField;

    /**
     * Initializes a new default instance of the SearchableDropDown class.
     *
     * @param webDriver Webdriver class object
     * @param listBy List of dropdown values
     * @param textBy Text to search
     * @param friendlyName Friendly name for the SearchableDropDown
     */
    public SearchableDropDown(WebDriver webDriver, By listBy, By textBy, String friendlyName) {
        super(webDriver, listBy, friendlyName);
        searchTextField = new TextField(webDriver, textBy, friendlyName + " search");
    }

    // ==========
    // Commands	=
    // ==========

    /**
     * Select option from drop down by typing the given value in searchable dropdown
     *
     * @param value value to be typed and selected in dropdown
     */
    public void selectByTyping(final String value) {
        TracedAction action = ExecutionTracer.traceSelectAction(webDriver, this, value);
        performAction(new ManagedAction() {
            @Override
            public void execute() {
                retrieveControlledObject();

                long selectStart = System.currentTimeMillis();

                // Move the web element to view port.
                ((JavascriptExecutor) webDriver).executeScript("arguments[0].scrollIntoView(true);", controlledObject);
                click();
                searchTextField.clearAndTypeKeys(value + Keys.ENTER);

                long timeToFindOption = (System.currentTimeMillis() - selectStart);
                println(value.toString() + " was selected in " + timeToFindOption + "ms");
                ExecutionTracer.updateMaxRetrievalTime(timeToFindOption);
            }
        });
        ExecutionTracer.add(action);
    }

    /**
     * Filter the drop down options by clicking the dropdown and entering the value in search field
     *
     * @param value value to be typed in searchable text field
     */
    public void filter(final String value) {
        TracedAction action = ExecutionTracer.traceSelectAction(webDriver, this, value);
        performAction(new ManagedAction() {
            @Override
            public void execute() {
                retrieveControlledObject();

                long selectStart = System.currentTimeMillis();

                click();
                searchTextField.clearAndTypeKeys(value);

                long timeToFindOption = (System.currentTimeMillis() - selectStart);
                println(value.toString() + " was selected in " + timeToFindOption + "ms");
                ExecutionTracer.updateMaxRetrievalTime(timeToFindOption);
            }
        });
        ExecutionTracer.add(action);
    }

    public void selectFilteredValue() {
        TracedAction action = ExecutionTracer.traceSelectAction(webDriver, this, "filtered value");
        performAction(new ManagedAction() {
            @Override
            public void execute() {
                searchTextField.retrieveControlledObject();

                long selectStart = System.currentTimeMillis();

                searchTextField.typeKeys(Keys.ENTER);

                long timeToFindOption = (System.currentTimeMillis() - selectStart);
                println("filtered value was selected in " + timeToFindOption + "ms");
                ExecutionTracer.updateMaxRetrievalTime(timeToFindOption);
            }
        });
        ExecutionTracer.add(action);
    }

    public void cancelFilteredValue() {
        TracedAction action = ExecutionTracer.traceSelectAction(webDriver, this, "filter canceled");
        performAction(new ManagedAction() {
            @Override
            public void execute() {
                searchTextField.retrieveControlledObject();

                long selectStart = System.currentTimeMillis();

                searchTextField.typeKeys(Keys.TAB);

                long timeToFindOption = (System.currentTimeMillis() - selectStart);
                println("filtered value was canceled in " + timeToFindOption + "ms");
                ExecutionTracer.updateMaxRetrievalTime(timeToFindOption);
            }
        });
        ExecutionTracer.add(action);
    }

}
