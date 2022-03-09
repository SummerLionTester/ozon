package ru.ozon.framework.pages;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.ozon.framework.managers.PageManager;
import ru.ozon.framework.utils.Excel;

import java.util.*;

public class ProductsPage extends BasePage {

    @FindBy(xpath = "//aside[@data-widget='searchResultsFilters']")
    private WebElement searchResultsFilters;

    @FindBy(xpath = "//div[@data-widget='searchResultsV2']/div/div")
    private List<WebElement> searchResults;

    public void checkPageOpening(String title) {
        Assertions.assertTrue(
                waitUntilTitleIs(title),
                "Главная страница не открыта"
        );
    }

    public void fillRangedFilters(String params) {
        Map<String, String> paramsMap = getParamsForRangedFilters(params);
        for(Map.Entry<String, String> entry: paramsMap.entrySet()) {
            String filterName = entry.getKey();
            String priceTo = entry.getValue();

            WebElement priceFilterTo = findRangedFilter(filterName);
            fillInputField(priceFilterTo, String.valueOf(priceTo));
            priceFilterTo.sendKeys(Keys.ENTER);
            wait.until(ExpectedConditions.stalenessOf(priceFilterTo));

            PageFactory.initElements(driverManager.getDriver(), this);
            waitForPageToLoad();
            WebElement priceFilterToAfterFilling = waitUntilElementToBeVisible(findRangedFilter(filterName));
            scrollToElementByJs(priceFilterToAfterFilling);
            Assertions.assertTrue(
                    checkInputFilling(priceFilterToAfterFilling, String.valueOf(priceTo)),
                    "Цена до не заполнена"
            );
        }
    }

    private WebElement findRangedFilter(String filterName) {
        WebElement rangedFilter = null;

        try {
            rangedFilter = searchResultsFilters.findElement(By.xpath(".//div[contains(text(), '" + filterName + "')]"));
        } catch (Exception e) {
            Assertions.fail("Фильтр '" + filterName + "' не найден");
        }

        return rangedFilter.findElement(By.xpath("./..//p[contains(text(), 'до')]/..//input"));
    }

    public void setSingleCheckboxOn(String checkboxNames) {
        String[] checkboxes = checkboxNames.split(",");
        for (int i = 0; i < checkboxes.length; i++) {
            String checkboxName = checkboxes[i];
            WebElement checkboxInput = findSingleCheckbox(checkboxName);

            if (!checkboxInput.isSelected()) {
                toggleCheckbox(findSingleCheckboxLabel(checkboxName));
                wait.until(ExpectedConditions.stalenessOf(checkboxInput));
            }

            PageFactory.initElements(driverManager.getDriver(), this);
            waitForPageToLoad();
            WebElement checkboxInputAfterFilling = waitUntilElementToBeVisible(findSingleCheckbox(checkboxName));
            scrollToElementByJs(checkboxInputAfterFilling);
            Assert.assertTrue(
                    "Чекбокс '" + checkboxName + "' не активирован",
                    checkboxInputAfterFilling.isSelected()
            );
        }

    }

    private WebElement findSingleCheckbox(String checkboxName) {
        return  findSingleCheckboxLabel(checkboxName).findElement(By.xpath(".//input"));
    }

    private WebElement findSingleCheckboxLabel(String checkboxName) {
        WebElement checkboxLabel = null;
        try {
            checkboxLabel = searchResultsFilters.findElement(By.xpath(".//div[@value='" + checkboxName + "']//label"));
        } catch (Exception e) {
            Assertions.fail("Фильтр '" + checkboxName + "' не найден");
        }
        return  checkboxLabel;
    }

    public void setCheckboxOnFromCheckboxesFamily(String params) {
        Map<String, String[]> paramsMap = getParamsForFamilyOfCheckboxes(params);
        for(Map.Entry<String, String[]> entry: paramsMap.entrySet()) {
            Arrays.stream(entry.getValue()).forEach(checkboxName -> {
                String familyName = entry.getKey();
                WebElement family = findCheckboxFamily(familyName);
                List<WebElement> seeAll = family.findElements(By.xpath(".//span[contains(text(), 'Посмотреть все')]"));

                if(seeAll.size() == 1) {
                    WebElement seeAllButton = seeAll.get(0);
                    scrollToElementByJsWithOffset(seeAllButton, 0, -200);
                    waitUntilElementToBeClickable(seeAllButton).click();
                    WebElement search = family.findElement(By.xpath(".//input[@type='text']"));
                    fillInputField(search, checkboxName);
                    checkInputFilling(search, checkboxName);
                }

                WebElement checkboxInput = findSingleCheckboxFromFamily(family, familyName, checkboxName);
                if(!checkboxInput.isSelected()) {
                    toggleCheckbox(findSingleCheckboxLabelFromFamily(family, familyName, checkboxName));
                    wait.until(ExpectedConditions.stalenessOf(checkboxInput));
                }

                PageFactory.initElements(driverManager.getDriver(), this);
                waitForPageToLoad();
                WebElement checkboxInputAfterFilling = waitUntilElementToBeVisible(findSingleCheckboxFromFamily(findCheckboxFamily(familyName), familyName, checkboxName));
                scrollToElementByJs(checkboxInputAfterFilling);
                Assert.assertTrue(
                        "Чекбокс '" + checkboxName + "' в семействе чекбоксов '" + familyName +"' не активирован",
                        wait.until(ExpectedConditions.elementToBeSelected(checkboxInputAfterFilling))
                );
            });
        }

    }

    private Map<String, String[]> getParamsForFamilyOfCheckboxes(String params) {
        Map<String,String[]> paramsMap = new HashMap<>();
        String[] families = params.split("&");
        for (int i = 0; i < families.length; i++) {
            String[] nameAndValues = families[i].split(":");
            paramsMap.put(nameAndValues[0], nameAndValues[1].split(","));
        }

        return paramsMap;
    }

    private Map<String, String> getParamsForRangedFilters(String params) {
        Map<String,String> paramsMap = new HashMap<>();
        String[] families = params.split("&");
        for (int i = 0; i < families.length; i++) {
            String[] nameAndValues = families[i].split(":");
            paramsMap.put(nameAndValues[0], nameAndValues[1]);
        }

        return paramsMap;
    }

    private WebElement findCheckboxFamily(String familyName) {
        WebElement family = null;
        try {
            family= searchResultsFilters.findElement(By.xpath(".//div[contains(text(), '"+ familyName +"')]/.."));
        } catch (Exception e) {
            Assertions.fail("Семейство чекбоксов '" + familyName + "' не найдено");
        }
        return family;
    }

    private WebElement findSingleCheckboxFromFamily(WebElement family, String familyName, String checkboxName) {
        return  findSingleCheckboxLabelFromFamily(family, familyName, checkboxName).findElement(By.xpath(".//input"));
    }

    private WebElement findSingleCheckboxLabelFromFamily(WebElement family, String familyName, String checkboxName) {
        WebElement checkboxLabel = null;
        try {
            checkboxLabel = family.findElement(By.xpath(".//span[contains(text(), '" + checkboxName +"')]/ancestor::label"));
        } catch (Exception e) {
            Assertions.fail("Чекбокс '"+ checkboxName +"' в семействе чекбоксов '" + familyName +"' не найден");
        }
        return  checkboxLabel;
    }

    public void addToCartEvenProductsFromPage(int count) {
        List<List<String>> addedProductsData = new ArrayList<>();
        int addedCount = 0;

        for (int i = 1; i < searchResults.size(); i += 2) {
            addedCount = addToCartRegularDelivery(i, addedCount, addedProductsData);
            if (addedCount >= count) {
                break;
            }
        }

        Excel.writeToExcel(addedProductsData);
        checkAddedToCartQuantity(count);
    }

    public void addToCartAllEvenProductsFromPage() {
        List<List<String>> addedProductsData = new ArrayList<>();
        int addedCount = 0;

        for (int i = 1; i < searchResults.size(); i += 2) {
            addedCount = addToCartRegularDelivery(i, addedCount, addedProductsData);
        }

        Excel.writeToExcel(addedProductsData);
        checkAddedToCartQuantity(addedCount);
    }

    private int addToCartRegularDelivery(int i, int count, List<List<String>> addedProductsData) {
        WebElement currentProduct = searchResults.get(i);
        List<WebElement> addToCartRegularDeliveryButton = searchResults.get(i).findElements(By.xpath(".//span[contains(text(), 'доставит')]/../..//button"));

        if (addToCartRegularDeliveryButton.size() == 1) {
            scrollToElementByJsWithOffset(currentProduct, 0, -200);

            waitUntilElementToBeClickable(addToCartRegularDeliveryButton.get(0)).click();
            waitUntilInvisibilityOf(addToCartRegularDeliveryButton.get(0));
            count += 1;

            String productName = currentProduct.findElement(By.xpath(".//a[contains(@href, '/product')]/span")).getText();
            String productPrice = currentProduct.findElement(By.xpath(".//div/span[contains(text(), '₽') and not(contains(text(), '−'))][1]")).getText();

            addedProductsData.add(List.of(new String[]{productName, productPrice}));
        }

        return count;
    }

    private void checkAddedToCartQuantity(int quantity) {
        int addedToCartQnt = PageManager.getInstance().getPage(Header.class).getCartAddedProductQuantity();
        Assertions.assertEquals(
                quantity,
                addedToCartQnt,
                "Колличество добавленных товаров не совпадает с ожидаемым. Возможно на странице недостачточное кол-во товаров"
        );
    }
}
