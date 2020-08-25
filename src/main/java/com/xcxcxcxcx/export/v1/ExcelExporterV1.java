package com.xcxcxcxcx.export.v1;

import com.xcxcxcxcx.export.Data;
import com.xcxcxcxcx.export.ExcelExporter;
import com.xcxcxcxcx.export.Static;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.UUID;

/**
 *
 * 一个excel，一个sheet，填充所有数据
 *
 * @author XCXCXCXCX
 * @date 2020/8/24 11:29 上午
 */
public class ExcelExporterV1 implements ExcelExporter {

    private List<String> data;

    public ExcelExporterV1(List<String> data) {
        this.data = data;
    }

    @Override
    public void export0() {
        SXSSFWorkbook workbook = new SXSSFWorkbook();
        SXSSFSheet sheet = workbook.createSheet("sheet0");;
        for (int i = 0; i < data.size(); i++) {
            int index = i / 1000000;
            int rowNum = i % 1000000;
            if (index > workbook.getNumberOfSheets() - 1) {
                workbook.createSheet("sheet" + index);
            }
            sheet = workbook.getSheetAt(index);
            SXSSFRow row = sheet.createRow(rowNum);
            SXSSFCell cell = row.createCell(0);
            cell.setCellValue(data.get(i));
        }
        try {
            sheet.flushRows();
        } catch (IOException e) {
            e.printStackTrace();
        }
        File file = Static.getFile("v1-" + UUID.randomUUID() + ".xlsx");
        try (FileOutputStream fos = new FileOutputStream(file)){
            workbook.write(fos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
