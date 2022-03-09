package ru.ozon.framework.utils;

import io.qameta.allure.Allure;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Assertions;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Excel {
    private final static String pathName = "target/testData.xlsx";

    public static void writeToExcel(List<List<String>> data) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Test Data");

        for (int i = 0; i < data.size(); i++) {
            XSSFRow row = sheet.createRow(i);
            for(int j = 0; j < data.get(i).size(); j++) {
                XSSFCell cell = row.createCell(j);
                cell.setCellValue(data.get(i).get(j));
            }
        }

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(new File(pathName));
            workbook.write(fileOutputStream);
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> getAllValuesFromColumn(int colNumber) {
        List<String> values = new ArrayList<>();
        try {
            FileInputStream file = new FileInputStream(new File("target/testData.xlsx"));
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheetAt(0);

            Iterator<Row> rowIterator = sheet.iterator();
            while(rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Cell cell = row.getCell(colNumber);

                values.add(cell.getStringCellValue());
            }

            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return values;
    }

    public static List<List<String>> getAllValues() {
        List<List<String>> values = new ArrayList<>();
        try {
            FileInputStream file = new FileInputStream(new File(pathName));
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheetAt(0);

            Iterator<Row> rowIterator = sheet.iterator();
            while(rowIterator.hasNext()) {
                List<String> rowValues = new ArrayList<>();
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();

                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    rowValues.add(cell.getStringCellValue());
                }
                values.add(rowValues);
            }

            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return values;
    }

    public static void recordItemWithHighestPrice(int priceColumnValues) {
        List<List<String>> values = Excel.getAllValues();
        values.add(getHighestPriceProduct(values, priceColumnValues));
        Excel.writeToExcel(values);
    }

    private static List<String> getHighestPriceProduct(List<List<String>> addedProducts, int priceColumnIndex) {
        int max = 0;
        int maxIndex = 0;
        for(int i = 0; i< addedProducts.size(); i++) {
            int price = 0;
            try {
                price =  Integer.parseInt(addedProducts.get(i).get(priceColumnIndex).replaceAll("\\D", ""));
            } catch(NumberFormatException e) {
                Assertions.fail("Цена в ячейке еэкселя указана в неверном формате");
            }

            if (price > max) {
                max = price;
                maxIndex = i;
            }
        }

        return List.of(new String[]{"Товар с наибольшей ценой: " + String.join(" - ", addedProducts.get(maxIndex))});
    }

    public static void attachDataFileToReportToAllure(){
        try {
            Allure.addAttachment("Вложение", "text/xlsx",  new FileInputStream(new File(pathName)), ".xlsx");
        } catch (FileNotFoundException e) {
            Assertions.fail("Файл с данными не найден");
        }
    }

}
