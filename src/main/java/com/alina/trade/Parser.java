package com.alina.trade;

import jdk.nashorn.internal.ir.debug.JSONWriter;
import jdk.nashorn.internal.parser.JSONParser;
import jdk.nashorn.internal.runtime.JSONFunctions;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import com.alina.trade.Trade;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;


public class Parser {

    public static String parse(String path) {
        List<Trade> result = new ArrayList<Trade>();
        InputStream in;
        HSSFWorkbook wb = null;
        try {
            in = new FileInputStream(path);
            wb = new HSSFWorkbook(in);
        } catch (IOException e) {
            e.printStackTrace();
        }

        int article = 0, title = 1, countType = 2, price = 3, discount = 4, category = 5, tradeCode = 6;

        assert wb != null;
        Sheet sheet = wb.getSheetAt(0);
        for (Row row : sheet) {
            if(row.getRowNum() == 0) {
                continue;
            }
            Trade trade = new Trade();
            for (Cell cell : row) {
                int cellType = cell.getCellType();
                switch (cellType) {
                    case Cell.CELL_TYPE_STRING:
                        if (cell.getColumnIndex() == article) {
                            trade.article = cell.getStringCellValue();
                        } else if(cell.getColumnIndex() == title) {
                            trade.title = cell.getStringCellValue();
                        } else if(cell.getColumnIndex() == countType) {
                            trade.countType = cell.getStringCellValue();
                        } else if(cell.getColumnIndex() == discount) {
                            trade.discount = cell.getStringCellValue();
                        } else if(cell.getColumnIndex() == category) {
                            trade.category = cell.getStringCellValue();
                        } else if(cell.getColumnIndex() == tradeCode) {
                            trade.tradeCode = cell.getStringCellValue();
                        }
                        break;
                    case Cell.CELL_TYPE_NUMERIC:
                        if(cell.getColumnIndex() == price) {
                            trade.price = cell.getNumericCellValue();
                        }
                        break;
                    case Cell.CELL_TYPE_FORMULA:
                        break;
                    default:
                        break;
                }
            }
            result.add(trade);
        }

        for(Trade object : result) {
            System.out.println(
                    "Article: " + object.article + "\n" +
                    "Title: " + object.title + "\n" +
                    "Count Type: " + object.countType + "\n" +
                    "Price: " + object.price + "\n" +
                    "Discount: " + object.discount + "\n" +
                    "category: " + object.category + "\n" +
                    "Trade Code: " + object.tradeCode + "\n"
            );
        }

        return "";
    }

}
