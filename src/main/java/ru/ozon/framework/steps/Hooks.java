package ru.ozon.framework.steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import ru.ozon.framework.managers.FrameworkManager;

public class Hooks {

    @Before
    public void before() {
        FrameworkManager.init();
    }

    @After
    public void after() {
        FrameworkManager.quit();
    }
}
