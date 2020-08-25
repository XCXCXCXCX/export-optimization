package com.xcxcxcxcx.export.v2;

import com.xcxcxcxcx.export.ExcelExporter;
import com.xcxcxcxcx.export.Static;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *
 * 一个excel，一个sheet，填充所有数据
 *
 * @author XCXCXCXCX
 * @date 2020/8/24 11:29 上午
 */
public class ExcelExporterV2 implements ExcelExporter {

    private List<List<String>> dataList;

    public ExcelExporterV2(List<String> data) {
        this.dataList = split(data);
    }

    private List<List<String>> split(List<String> data) {
        List<List<String>> dataList = new ArrayList<>();
        int size = data.size();
        int shardNum = size / 100000;
        int lastShardSize = size % 100000;
        for (int i = 0; i < shardNum; i++) {
            List<String> tmp = new ArrayList<>();
            for (int j = 0; j < 100000; j++) {
                tmp.add(data.get(i * 100000 + j));
            }
            dataList.add(tmp);
        }
        if (lastShardSize != 0) {
            List<String> tmp = new ArrayList<>();
            for (int j = shardNum * 100000; j < data.size(); j++) {
                tmp.add(data.get(j));
            }
            dataList.add(tmp);
        }
        return dataList;
    }

    @Override
    public void export0() {
        SXSSFWorkbook workbook = new SXSSFWorkbook();
        SXSSFSheet sheet = workbook.createSheet("sheet0");
        int size = dataList.stream().map(List::size).reduce(Integer::sum).orElse(0);
        for (int i = 0; i < size; i++) {
            int dataListIndex = i / 100000;
            int dataListDataIndex = i % 100000;
            int index = i / 1000000;
            int rowNum = i % 1000000;
            if (index > workbook.getNumberOfSheets() - 1) {
                workbook.createSheet("sheet" + index);
            }
            sheet = workbook.getSheetAt(index);
            SXSSFRow row = sheet.createRow(rowNum);
            SXSSFCell cell = row.createCell(0);
            cell.setCellValue(dataList.get(dataListIndex).get(dataListDataIndex));
            dataList.get(dataListIndex).set(dataListDataIndex, null);
            if (i % 100 == 0) {
                try {
                    sheet.flushRows(100);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        int lastRow = size % 100;
        if (lastRow != 0) {
            try {
                sheet.flushRows(lastRow);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        File file = Static.getFile("v2-" + UUID.randomUUID() + ".xlsx");
        try (FileOutputStream fos = new FileOutputStream(file)){
            workbook.write(fos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
