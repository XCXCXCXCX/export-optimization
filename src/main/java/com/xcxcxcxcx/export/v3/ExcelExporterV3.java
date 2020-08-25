package com.xcxcxcxcx.export.v3;

import com.xcxcxcxcx.export.ExcelExporter;
import com.xcxcxcxcx.export.Static;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author XCXCXCXCX
 * @date 2020/8/25 11:23 上午
 */
public class ExcelExporterV3 implements ExcelExporter {

    private List<SXSSFWorkbook> excels = new ArrayList<>();

    private SXSSFSheet currentSheet;

    private int sheetIndex;

    private List<String> data;

    private int maxSheetNum;
    private int maxSheetRow;

    public ExcelExporterV3(int maxSheetNum, int maxSheetRow) {
        this.maxSheetNum = maxSheetNum;
        this.maxSheetRow = maxSheetRow;
    }

    public void startExport() {
        SXSSFWorkbook excel = new SXSSFWorkbook();
        currentSheet = excel.createSheet("sheet" + sheetIndex++);
        excels.add(excel);
    }

    public void setData(List<String> data) {
        this.data = data;
    }

    @Override
    public void export0() {
        if (data != null && !data.isEmpty()) {
            int rowNum = currentSheet.getLastRowNum();
            int remain = maxSheetRow - rowNum;
            if (data.size() > remain) {
                for (int i = 0; i < remain; i++) {
                    SXSSFRow row = currentSheet.createRow(++rowNum);
                    SXSSFCell cell = row.createCell(0);
                    cell.setCellValue(data.get(i));
                    if (i % 100 == 0) {
                        try {
                            currentSheet.flushRows(100);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                try {
                    currentSheet.flushRows(100);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //需要扩容
                if (excels.size() < maxSheetNum) {
                    // excel未达到上限
                    currentSheet = excels.get(excels.size() - 1).createSheet("sheet" + sheetIndex++);
                } else {
                    // excel达到上限
                    SXSSFWorkbook excel = new SXSSFWorkbook();
                    currentSheet = excel.createSheet("sheet" + sheetIndex++);
                    excels.add(excel);
                }
                //递归
                setData(data.subList(remain, data.size()));
                export0();
            } else {
                for (int i = 0; i < data.size(); i++) {
                    SXSSFRow row = currentSheet.createRow(++rowNum);
                    SXSSFCell cell = row.createCell(0);
                    cell.setCellValue(data.get(i));
                    if (i % 100 == 0) {
                        try {
                            currentSheet.flushRows(100);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                try {
                    currentSheet.flushRows(100);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //无需扩容
            }
        }
    }

    public void endExport() {
        File file = Static.getFile("v3-" + UUID.randomUUID() + ".xlsx");
        if (excels.size() == 0) {

        } else if (excels.size() == 1) {
            try (FileOutputStream fos = new FileOutputStream(file)){
                excels.get(0).write(fos);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try (FileOutputStream fos = new FileOutputStream(file)){
                writeZipBytes(fos);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void writeZipBytes(OutputStream outputStream) throws IOException {
        ZipOutputStream zipOutputStream = null;
        try {
            zipOutputStream = new ZipOutputStream(outputStream);
            ZipEntry zipEntry = null;
            for (int i = 0; i < excels.size(); i++) {
                SXSSFWorkbook workbook = excels.get(i);
                zipEntry = new ZipEntry("excel-" + (i + 1) + ".xlsx");
                zipOutputStream.putNextEntry(zipEntry);
                workbook.write(zipOutputStream);
            }
            zipOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (zipOutputStream != null) {
                zipOutputStream.closeEntry();
                zipOutputStream.close();
            }
        }
    }
}
