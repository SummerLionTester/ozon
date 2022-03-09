package ru.ozon.framework.steps;

import io.cucumber.java.ru.И;
import ru.ozon.framework.managers.PageManager;
import ru.ozon.framework.pages.Cart;

public class CartSteps {
    PageManager pageManager = PageManager.getInstance();

    @И("^Закрыть попап 'Покупайте как юридическое лицо'$")
    public void closePopupBuyAsALegalEntity() {
        pageManager.getPage(Cart.class).closePopupBuyAsALegalEntity();
    }

    @И("^Проверить, что все добавленные ранее товары находятся в корзине$")
    public void checkAddedProducts() {
        pageManager.getPage(Cart.class).checkAddedProducts();
    }

    @И("^Проверить, что отображается текст «Ваша корзина N товаров»$")
    public void checkTextOnYourCartTotalBlock() {
        pageManager.getPage(Cart.class).checkTextOnYourCartTotalBlock();
    }

    @И("^Удалить все товары из корзины$")
    public void clearCart() {
        pageManager.getPage(Cart.class).clearCart();
    }

    @И("^Проверьте, что корзина не содержит никаких товаров$")
    public void checkCartIsEmpty() {
        pageManager.getPage(Cart.class).checkCartIsEmpty();
    }
}
