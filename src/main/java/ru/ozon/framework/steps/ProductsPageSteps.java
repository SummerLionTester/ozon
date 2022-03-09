package ru.ozon.framework.steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.ru.И;
import ru.ozon.framework.managers.PageManager;
import ru.ozon.framework.pages.ProductsPage;

public class ProductsPageSteps {
    PageManager pageManager = PageManager.getInstance();

    @И("^Проверить, что открылась страница с заголовком \"(.+)\"$")
    public void checkCategory(String title) {
        pageManager.getPage(ProductsPage.class).checkPageOpening(title);
    }

    @И("^В диапозонном фильтре \"(.+)\" установить значение 'до' равное \"(\\d+)\"$")
    public void fillRangedFilters(String filterName, int to) {
        pageManager.getPage(ProductsPage.class).fillRangedFilters(filterName, to);
    }

    @И("^Установить единичный чекбокс \"(.+)\"$")
    public void setSingleCheckboxOn(String checkboxName) {
        pageManager.getPage(ProductsPage.class).setSingleCheckboxOn(checkboxName);
    }

    @И("^Установить для семейства чекбоксов  следующие чекбоксы:$")
    public void setCheckboxOnFromCheckboxesFamily(DataTable table) {
        pageManager.getPage(ProductsPage.class).setCheckboxOnFromCheckboxesFamily(table);
    }

    @И("^Добавить в корзину чётные товары. Кол-во \"(\\d+)\"$")
    public void addToCart(int count) {
        pageManager.getPage(ProductsPage.class).addToCartEvenProductsFromPage(count);
    }

    @И("^Добавить в корзину все чётные товары со страницы.$")
    public void addToCartAll() {
        pageManager.getPage(ProductsPage.class).addToCartAllEvenProductsFromPage();
    }
}
