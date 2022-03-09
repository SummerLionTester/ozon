package ru.ozon.framework.pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class Header extends BasePage {

    @FindBy(xpath = "//form[@action='/search']//input[@type='text']")
    private WebElement searchInput;

    @FindBy(xpath = "//form[@action='/search']//button")
    private WebElement searchButton;

    @FindBy(xpath = "//a[contains(@href, '/cart')]")
    private WebElement cartLink;

    @FindBy(xpath = "//div[@data-widget='topBar']//button")
    private WebElement location;

    @FindBy(xpath = "//h2[contains(text(), 'Выберите город')]/..//input")
    private WebElement selectCityInput;

    public void fillSearchInput(String textToSearch) {
        fillInputField(searchInput, textToSearch);

        Assertions.assertTrue(
                checkInputFilling(searchInput,textToSearch ),
                "Поле поиска не заполнено значением '" + textToSearch + "'"
        );
    }

    public void clickOnSearchButton() {
        waitUntilElementToBeClickable(searchButton).click();

        try {
            boolean isUrlWithSearchText = wait.until(ExpectedConditions.urlContains("text=" + URLEncoder.encode(searchInput.getAttribute("value"), StandardCharsets.UTF_8.toString())));
            Assertions.assertTrue(
                    isUrlWithSearchText,
                    "Поиск по тексту не выполнен."
            );
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void goToCart() {
        waitUntilElementToBeClickable(cartLink).click();
        Assertions.assertTrue(waitUntilTitleIs("OZON.ru - Моя корзина"), "Корзина не открыта");
    }

    public int getCartAddedProductQuantity() {
        WebElement count = cartLink.findElement(By.xpath(".//span[contains(@class, 'f-caption--bold')]"));
        return Integer.parseInt(count.getText());
    }

    public void setLocation(String city) {
        String currentLocation = location.getText().trim().toLowerCase();
        String lowerCasedCity = city.toLowerCase();
        if(!currentLocation.equals(lowerCasedCity)) {
            waitUntilElementToBeClickable(location).click();
            fillInputField(selectCityInput, lowerCasedCity);
            waitForTextToBePresentInElement("//h2[contains(text(), 'Выберите город')]/..//li[1]", city);
            selectCityInput.sendKeys(Keys.ENTER);

            waitUntilInvisibilityOf("//h2[contains(text(), 'Выберите город')]/..//input");
            Assertions.assertEquals(
                    lowerCasedCity,
                    location.getText().trim().toLowerCase(),
                    "Город не выбран"
            );
        }
    }

}
