package com.gf.utils;

import java.io.File;
import java.util.ArrayList;

/**
 * Title: <br>
 * Packet:com.gf.utils<br>
 * Description: <br>
 * Author:GuoFu<br>
 * Create Date: 2018/9/7.<br>
 * Modify User: <br>
 * Modify Date: <br>
 * Modify Description: <br>
 */
public class FileUtils {
    /***
     * 获取指定目录下的所有的文件（不包括文件夹），采用了递归
     *
     * @param obj
     * @param sufixStr 后缀名
     * @return
     */
    public static ArrayList<File> getListFiles(Object obj, String sufixStr) {
        File directory = null;
        if (obj instanceof File) {
            directory = (File) obj;
        } else {
            directory = new File(obj.toString());
        }
        ArrayList<File> files = new ArrayList<File>();
        if (directory.isFile()) {

            if (sufixStr == null)
                files.add(directory);
            else if ( directory.getName().endsWith(sufixStr)) {
                files.add(directory);
            }
            return files;
        } else if (directory.isDirectory()) {
            File[] fileArr = directory.listFiles();
            for (int i = 0; i < fileArr.length; i++) {
                File fileOne = fileArr[i];
                files.addAll(getListFiles(fileOne, sufixStr));
            }
        }
        return files;
    }
}
