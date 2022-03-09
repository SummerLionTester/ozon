package ru.ozon.framework.constants;

public enum APP_PROPS {
    BASE_URL("base.url"),
    TYPE_BROWSER("type.browser"),
    IMPLICITLY_WAIT("implicitly.wait"),
    PAGE_LOAD_TIMEOUT("page.load.timeout"),
    PATH_DRIVER_WINDOWS("path.driver.windows."),
    PATH_DRIVER_MAC("path.driver.mac."),
    PATH_DRIVER_UNIX("path.driver.unix.");

    private final String prop;

    APP_PROPS(String prop) {
        this.prop = prop;
    }
    public String toString() {
        return this.prop;
    }
}
