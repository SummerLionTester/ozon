package ru.ozon.framework.pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.ozon.framework.managers.DriverManager;

public class BasePage {
    protected DriverManager driverManager = DriverManager.getInstance();
    protected WebDriverWait wait = new WebDriverWait(driverManager.getDriver(), 5, 1000);
    protected JavascriptExecutor js = (JavascriptExecutor) driverManager.getDriver();

    public BasePage() {
        PageFactory.initElements(driverManager.getDriver(), this);
    }

    protected WebElement waitUntilElementToBeClickable(WebElement element) {
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    protected WebElement waitUntilElementToBeVisible(WebElement element) {

        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    protected Boolean waitUntilTitleIs(String title) {
        return wait.until(ExpectedConditions.titleIs(title));
    }

    protected Boolean waitUntilInvisibilityOf(String elementXpath) {
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(elementXpath)));
    }

    protected Boolean waitUntilInvisibilityOf(WebElement element) {
        return wait.until(ExpectedConditions.invisibilityOf(element));
    }

    protected WebElement scrollToElementByJs(WebElement element) {
        js.executeScript("arguments[0].scrollIntoView(true);", element);
        return element;
    }

    public WebElement scrollWithOffset(WebElement element, int x, int y) {
        String code = "window.scroll(" + (element.getLocation().x + x) + ","
                + (element.getLocation().y + y) + ");";
        js.executeScript(code, element, x, y);

        return element;
    }

    protected WebElement scrollToElementByJsWithOffset(WebElement element, int offsetX, int offsetY) {
        scrollWithOffset(scrollToElementByJs(element), offsetX, offsetY);

        return element;
    }

    protected void fillInputField(WebElement field, String value) {
        waitUntilElementToBeClickable(scrollWithOffset(scrollToElementByJs(field), 0, -150)).click();
        field.sendKeys(Keys.CONTROL + "a");
        field.sendKeys(Keys.DELETE);
        field.sendKeys(value);
    }

    protected void toggleCheckbox(WebElement field) {
        waitUntilElementToBeClickable(scrollWithOffset(scrollToElementByJs(field), 0, -200)).click();
    }

    protected Boolean checkInputFilling(WebElement field, String value) {
        waitUntilElementToBeClickable(field);
        return field.getAttribute("value").equals(value);
    }

    protected void waitForPageToLoad() {
        WebDriver driver = driverManager.getDriver();
        ExpectedCondition< Boolean > pageLoad = new ExpectedCondition <Boolean> () {
                    public Boolean apply(WebDriver driver) {
                        return ((JavascriptExecutor) driverManager.getDriver()).executeScript("return document.readyState").equals("complete");
                    }
                };

        try {
            wait.until(pageLoad);
        } catch (Throwable pageLoadWaitError) {
            Assertions.fail("Timeout during page load");
        }
    }

    protected void waitForTextToBePresentInElement(String xpath, String text) {
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath(xpath), text));
    }

}
