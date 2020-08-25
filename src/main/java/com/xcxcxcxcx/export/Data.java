package com.xcxcxcxcx.export;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * dataCount条数据导出
 * 每条数据占用dataLength个字节
 *
 * @author XCXCXCXCX
 * @date 2020/8/24 3:08 下午
 */
public class Data {

    private int dataLength;

    private int dataCount;

    public Data(int dataLength, int dataCount) {
        this.dataLength = dataLength;
        this.dataCount = dataCount;
    }

    public List<String> getString() {
        List<String> data = new ArrayList<>();
        for (int i = 0; i < dataCount; i++) {
            data.add(createString());
        }
        return data;
    }

    private String createString() {
        byte[] bytes = new byte[dataLength];
        return new String(bytes);
    }

}
