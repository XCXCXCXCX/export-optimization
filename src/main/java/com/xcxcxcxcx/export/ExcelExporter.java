package com.xcxcxcxcx.export;

import java.io.File;
import java.util.List;

/**
 *
 *
 *
 * @author XCXCXCXCX
 * @date 2020/8/24 11:06 上午
 */
public interface ExcelExporter {

    void export0();

    default Result export() {
        Result result = new Result();
        long start = System.currentTimeMillis();
        try {
            export0();
        } catch (Throwable e) {
            result.setException(e);
        } finally {
            result.setCost(System.currentTimeMillis() - start);
        }
        return result;
    }

}
