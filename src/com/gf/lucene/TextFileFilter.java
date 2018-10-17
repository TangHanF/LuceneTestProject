package com.gf.lucene;
import java.io.File;
import java.io.FileFilter;
/**
 * Title:用于为 .txt 文件过滤器 <br>
 * Packet:com.gf.lucene<br>
 * Description: <br>
 * Author:GuoFu<br>
 * Create Date: 2018/9/7.<br>
 * Modify User: <br>
 * Modify Date: <br>
 * Modify Description: <br>
 */


public class TextFileFilter implements FileFilter {

    @Override
    public boolean accept(File pathname) {
        System.out.println("===");
        return pathname.getName().toLowerCase().endsWith(".txt");
    }
}
