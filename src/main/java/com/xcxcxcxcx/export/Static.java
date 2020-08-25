package com.xcxcxcxcx.export;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

/**
 * @author XCXCXCXCX
 * @date 2020/8/24 3:21 下午
 */
public class Static {

    private static final String PATH = "temp";

    private static final File FILE = new File(PATH);
    static {
        if (!FILE.exists()) {
            FILE.mkdir();
        }
    }

    public static File getFile(String filename) {
        File[] files = FILE.listFiles((dir, name) -> name.equals(filename));
        if (files != null && files.length > 0) {
            return files[0];
        } else {
            File file = new File(PATH + "/" + filename);
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return file;
        }
    }

    public static void reset() {
        if (FILE.exists()) {
            File[] files = FILE.listFiles();
            for (File file : files) {
                if (file.exists()) {
                    file.delete();
                }
            }
            FILE.delete();
        }
    }
}
