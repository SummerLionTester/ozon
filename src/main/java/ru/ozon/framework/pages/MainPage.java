package ru.ozon.framework.pages;

import org.junit.jupiter.api.Assertions;

public class MainPage extends BasePage {

    public void checkPageOpening() {
        Assertions.assertTrue(
                waitUntilTitleIs("OZON — интернет-магазин. Миллионы товаров по выгодным ценам"),
                "Главная страница не открыта"
        );
    }
}
