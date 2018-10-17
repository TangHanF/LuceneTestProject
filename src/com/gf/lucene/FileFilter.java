package com.gf.lucene;

import java.io.File;

/**
 * Title:文件过滤器 <br>
 * Packet:com.gf.lucene<br>
 * Description: <br>
 * Author:GuoFu<br>
 * Create Date: 2018/9/7.<br>
 * Modify User: <br>
 * Modify Date: <br>
 * Modify Description: <br>
 */


public class FileFilter implements java.io.FileFilter {

    private String fileType = "";

    public FileFilter(String fileType) {
        this.fileType = fileType;
    }

    @Override
    public boolean accept(File pathname) {
        return pathname.getName().toLowerCase().endsWith(this.fileType);
    }
}
