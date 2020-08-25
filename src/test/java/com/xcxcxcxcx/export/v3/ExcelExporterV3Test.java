package com.xcxcxcxcx.export.v3;

import com.xcxcxcxcx.export.Data;
import com.xcxcxcxcx.export.ExcelExporter;
import com.xcxcxcxcx.export.Result;
import com.xcxcxcxcx.export.v2.ExcelExporterV2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author XCXCXCXCX
 * @date 2020/8/25 11:52 上午
 */
public class ExcelExporterV3Test {

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            ExcelExporterV3 excelExporter = new ExcelExporterV3(5, 1000000);
            excelExporter.startExport();
            List<Result> results = new ArrayList<>();
            for (int j = 0; j < 100; j++) {
                Data data = new Data(1024, 10000);
                List<String> dataString = data.getString();
                excelExporter.setData(data.getString());
                results.add(excelExporter.export());
                dataString.clear();
            }
            Result r = new Result();
            long cost = 0;
            for (Result result : results) {
                cost += result.getCost();
            }
            long start = System.currentTimeMillis();
            excelExporter.endExport();
            r.setCost(cost + (System.currentTimeMillis() - start));
            Result.showResults(Collections.singletonList(r));
            System.out.println("===== loop " + (i + 1) + "\n");
        }
    }
}
