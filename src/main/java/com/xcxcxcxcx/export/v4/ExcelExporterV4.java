package com.xcxcxcxcx.export.v4;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.annotation.ExcelProperty;
import com.xcxcxcxcx.export.ExcelExporter;
import com.xcxcxcxcx.export.Static;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * easyexcel test
 *
 * @author XCXCXCXCX
 * @date 2020/8/25 12:43 下午
 */
public class ExcelExporterV4 implements ExcelExporter {

    private List<String> data;

    public ExcelExporterV4(List<String> data) {
        this.data = data;
    }

    @Override
    public void export0() {
        EasyExcel.write(Static.getFile("v4-" + UUID.randomUUID() +".xlsx"), Str.class)
                .autoTrim(false)
                .sheet("sheet0")
                .doWrite(data.stream().map(Str::new).collect(Collectors.toList()));
    }

    static class Str {

        @ExcelProperty("字符串标题")
        private String string;

        Str(String string) {
            this.string = string;
        }

        public String getString() {
            return string;
        }

        public void setString(String string) {
            this.string = string;
        }
    }
}
