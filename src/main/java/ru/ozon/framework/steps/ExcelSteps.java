package ru.ozon.framework.steps;

import io.cucumber.java.ru.И;
import ru.ozon.framework.utils.Excel;

public class ExcelSteps {
    @И("^Записать товар с наибольшей ценой в файл$")
    public void recordItemWithHighestPrice() {
        Excel.recordItemWithHighestPrice(1);
    }

    @И("^Приложить файл с данными в отчёт Allure$")
    public void attachDataFileToReport() {
        Excel.attachDataFileToReportToAllure();
    }
}
