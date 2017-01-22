package com.alina.trade;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class Parser {

    public static String parse(String path) {
        String result = "";
        InputStream in;
        HSSFWorkbook wb = null;
        try {
            in = new FileInputStream(path);
            wb = new HSSFWorkbook(in);
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert wb != null;
        Sheet sheet = wb.getSheetAt(0);
        for (Row row : sheet) {
            for (Cell cell : row) {
                int cellType = cell.getCellType();
                switch (cellType) {
                    case Cell.CELL_TYPE_STRING:
                        result += cell.getStringCellValue() + "=";
                        break;
                    case Cell.CELL_TYPE_NUMERIC:
                        result += "[" + cell.getNumericCellValue() + "]";
                        break;

                    case Cell.CELL_TYPE_FORMULA:
                        result += "[" + cell.getNumericCellValue() + "]";
                        break;
                    default:
                        result += "|";
                        break;
                }
            }
            result += "\n";
        }

        return result;
    }

    public static void SendData(String path) {
        Map<String, Object> dict = new HashMap<String, Object>();
        InputStream in;
        HSSFWorkbook wb = null;
        try {
            in = new FileInputStream(path);
            wb = new HSSFWorkbook(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert wb != null;
        Sheet sheet = wb.getSheetAt(0);
        int n = sheet.getLastRowNum();
        for(int i=1; i < n; ++i) {
            switch (sheet.getRow(i).getCell(1).getCellType()) {
                case Cell.CELL_TYPE_STRING:
                    dict.put(sheet.getRow(i).getCell(0).getStringCellValue(), sheet.getRow(i).getCell(1).getStringCellValue());
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    dict.put(sheet.getRow(i).getCell(0).getStringCellValue(), sheet.getRow(i).getCell(1).getNumericCellValue());
                    break;
            }
        }

        for(String k : dict.keySet()) {
            System.out.println(dict.get(k));
        }
    }
}
