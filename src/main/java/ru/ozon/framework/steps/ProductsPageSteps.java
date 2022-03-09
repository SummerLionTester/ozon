package ru.ozon.framework.steps;

import io.cucumber.java.ru.И;
import ru.ozon.framework.managers.PageManager;
import ru.ozon.framework.pages.ProductsPage;
import ru.ozon.framework.utils.Utils;

public class ProductsPageSteps {
    PageManager pageManager = PageManager.getInstance();

    @И("^Проверить, что открылась страница с заголовком \"(.+)\"$")
    public void checkCategory(String title) {
        pageManager.getPage(ProductsPage.class).checkPageOpening(title);
    }

    @И("^Установить для диапозонных фильтров значение 'до' \"(.+)\"$")
    public void fillRangedFilters(String params) {
        pageManager.getPage(ProductsPage.class).fillRangedFilters(params);
    }

    @И("^Установить единичные чекбоксы \"(.+)\"$")
    public void setSingleCheckboxOn(String checkboxNames) {
        pageManager.getPage(ProductsPage.class).setSingleCheckboxOn(checkboxNames);
    }

    @И("^Добавить в корзину чётные товары. Кол-во \"(.+)\"$")
    public void addToCart(String count) {
        if (Utils.isNumeric(count)) {
            pageManager.getPage(ProductsPage.class).addToCartEvenProductsFromPage(Integer.parseInt(count));
        } else {
            pageManager.getPage(ProductsPage.class).addToCartAllEvenProductsFromPage();
        }
    }

    @И("^Установить для семейств чекбоксов  следующие чекбоксы \"(.+)\"$")
    public void setCheckboxOnFromCheckboxesFamily(String params) {
        pageManager.getPage(ProductsPage.class).setCheckboxOnFromCheckboxesFamily(params);
    }
}
