package ru.ozon.framework.managers;

import ru.ozon.framework.pages.BasePage;;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class PageManager {
    private final Map<String, BasePage> pageStorage = new HashMap<>();
    private static PageManager pageManager;

    private PageManager() {}

    public static PageManager getInstance() {
        if (pageManager == null) {
            pageManager = new PageManager();
        }

        return pageManager;
    }

    public <T extends BasePage> T getPage(Class<T> page) {
        if (pageStorage.isEmpty() || pageStorage.get(page.getName()) == null) {
            try {
                pageStorage.put(page.getName(), page.getDeclaredConstructor().newInstance());
            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return (T) pageStorage.get(page.getName());
    }

    void clearPageStorage() {
        pageStorage.clear();
    }
}
