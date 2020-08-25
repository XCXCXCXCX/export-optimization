package com.xcxcxcxcx.export.v4;

import com.xcxcxcxcx.export.Data;
import com.xcxcxcxcx.export.ExcelExporter;
import com.xcxcxcxcx.export.Result;
import com.xcxcxcxcx.export.v2.ExcelExporterV2;

import java.util.Collections;

/**
 * @author XCXCXCXCX
 * @date 2020/8/24 3:32 下午
 */
public class ExcelExporterV4Test {

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            Data data = new Data(1024, 1000000);
            ExcelExporter excelExporter = new ExcelExporterV4(data.getString());
            Result.showResults(Collections.singletonList(excelExporter.export()));
            System.out.println("===== loop " + (i + 1) + "\n");
        }
        //Static.reset();
    }
}
