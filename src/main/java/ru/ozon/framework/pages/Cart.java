package ru.ozon.framework.pages;

import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.ozon.framework.utils.Excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Cart extends BasePage {

    @FindBy(xpath = "//div[@data-widget='split']//a/span")
    private List<WebElement> addedProducts;

    @FindBy(xpath = "//div[@style='overflow-y: auto;']//*[name()='svg']/ancestor::button")
    private WebElement closePopupButton;

    @FindBy(xpath = "//section[@data-widget='total']//span[contains(text(), 'Ваша корзина')]/following-sibling::span")
    private WebElement yourCartText;

    @FindBy(xpath = "//span[contains(text(), 'Удалить выбранные')]")
    private WebElement deleteSelected;

    @FindBy(xpath = "//span[contains(text(), 'Удалить')]/ancestor::button")
    private WebElement deleteSelectedPopupButton;

    @FindBy(xpath = "//h1")
    private WebElement emptyCartHeader;

    public void closePopupBuyAsALegalEntity() {
        waitUntilElementToBeClickable(closePopupButton).click();
        Assertions.assertTrue(waitUntilInvisibilityOf(
                        "//div[@style='overflow-y: auto;']//*[name()='svg']/ancestor::button"),
                "Попап не закрыт"
        );
    }

    public void checkAddedProducts() {
        List<String> productNamesFromSavedFile = Excel.getAllValuesFromColumn(0);
        List<String> productNamesFromCart = new ArrayList<>();
        for (WebElement addedProductName : addedProducts) {
            scrollToElementByJs(addedProductName);
            productNamesFromCart.add(addedProductName.getText());
        }

        Assertions.assertEquals(
                productNamesFromSavedFile.size(),
                productNamesFromCart.size(),
                "Колличество добавленных в корзину товаров не совпадает с кол-вом товаров в сохранённом файле"
        );

        productNamesFromSavedFile.forEach(productNameFromsavedFile -> {
            Assertions.assertTrue(
                    productNamesFromCart.contains(productNameFromsavedFile),
                    "В корзину добавился не тот товар"
            );
        });
    }

    public void checkTextOnYourCartTotalBlock() {
        int valuesCount = Excel.getAllValuesFromColumn(0).size();
        String text =" товар";
        Assertions.assertTrue(
                yourCartText.getText().trim().startsWith(valuesCount +  text),
                "Текст 'Ваша корзина " + valuesCount + text + "' не отображается"
        );
    }

    public void clearCart() {
        waitUntilElementToBeClickable(deleteSelected).click();
        waitUntilElementToBeClickable(deleteSelectedPopupButton).click();

        Assertions.assertTrue(waitUntilInvisibilityOf(
                        "//span[contains(text(), 'Удалить')]/ancestor::button"),
                "Попап удаления товаров не закрыт"
        );

        Assertions.assertTrue(
                waitUntilElementToBeVisible(emptyCartHeader)
                        .getText()
                        .contains("Корзина пуста"),
                "Не появился заголовок - ''Корзина пуста"
        );
    }

    public void checkCartIsEmpty() {
        Assertions.assertEquals(0, addedProducts.size(), "В корзине всё ещё есть добавленные товары. Корзина не пуста");
    }
}
