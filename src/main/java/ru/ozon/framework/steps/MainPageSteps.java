package ru.ozon.framework.steps;

import io.cucumber.java.ru.И;
import ru.ozon.framework.managers.PageManager;
import ru.ozon.framework.pages.MainPage;

public class MainPageSteps {
    PageManager pageManager = PageManager.getInstance();

    @И("^Проверить загрузку главной страницы$")
    public void checkPageOpening() {
        pageManager.getPage(MainPage.class).checkPageOpening();
    }
}
