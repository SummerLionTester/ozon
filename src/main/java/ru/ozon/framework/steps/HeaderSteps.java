package ru.ozon.framework.steps;

import io.cucumber.java.ru.И;
import ru.ozon.framework.managers.PageManager;
import ru.ozon.framework.pages.Header;

public class HeaderSteps {
    PageManager pageManager = PageManager.getInstance();

    @И("^Ввести в поле поиска \"(.+)\"$")
    public void fillSearchInput(String texToSearch) {
        pageManager.getPage(Header.class).fillSearchInput(texToSearch);
    }

    @И("^Нажать кнопку поиска$")
    public void clickSearchButton() {
        pageManager.getPage(Header.class).clickOnSearchButton();
    }

    @И("^Перейти в корзину$")
    public void goToCart() {
        pageManager.getPage(Header.class).goToCart();
    }

    @И("^Выбрать город - \"(.+)\"$")
    public void setLocation(String cityName) {
        pageManager.getPage(Header.class).setLocation(cityName);
    }
}
