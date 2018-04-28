package com.nortal.expenditure;

import java.io.FileInputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import com.nortal.expenditure.model.Expenditure;

import javafx.util.converter.DateStringConverter;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.ss.usermodel.*;

import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExpenditureDataExtractor {
    private static final Logger LOG = LoggerFactory.getLogger(ExpenditureDataExtractor.class);

    public List<Expenditure> readFromFile(InputStream inputStream) {
        // TODO needs more implementation. See Apache POI documentation for further details
//read the excel
        //return some sort of list containing
        List<Expenditure> expenditureList = new ArrayList();
        DateFormat df3 = new SimpleDateFormat("dd.mm.yyyy");
        try (Workbook workbook = WorkbookFactory.create(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0);
            sheet.forEach(row -> {
                if(! (row.getRowNum()==0)) {
                    Expenditure expenditureObj = new Expenditure();
                    row.forEach(cell -> {
                        int columnIndex = cell.getColumnIndex();
                        switch (columnIndex) {
                            case 0:
                                try {
                                    expenditureObj.setDate(df3.parse(cell.getStringCellValue()));
                                    break;
                                } catch (ParseException e) {
                                    LOG.warn("date is invalid " + String.valueOf(getCellValue(cell)));
                                }
                            case 1:
                                expenditureObj.setSupplier(String.valueOf(getCellValue(cell)));
                                break;
                            case 2:
                                expenditureObj.setType(cell.getStringCellValue());
                                break;
                            case 3:
                                expenditureObj.setProduct(cell.getStringCellValue());
                                break;
                            case 4:
                                expenditureObj.setUnits((cell.getNumericCellValue()));
                                break;
                            case 5:
                                expenditureObj.setUnitPrice((cell.getNumericCellValue()));
                                break;
                            case 6:
                                expenditureObj.setTaxPercentage((cell.getNumericCellValue()));
                                break;
                        }
                    });
                    expenditureList.add(expenditureObj);
                }
            });

            workbook.close();
        } catch (Exception e) {
            LOG.error("Error processing stream", e);
        }

        return expenditureList;
    }
    private Object getCellValue(Cell cell) {
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_STRING:{
                return cell.getStringCellValue();
            }
            case Cell.CELL_TYPE_BOOLEAN:
                return cell.getBooleanCellValue();

            case Cell.CELL_TYPE_NUMERIC:{
                return cell.getNumericCellValue();
            }
        }
        return null;
    }
}
