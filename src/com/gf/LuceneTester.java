package com.gf;

import com.gf.lucene.*;
import com.gf.utils.FileUtils;
import org.apache.lucene.document.Document;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

/**
 * Title: <br>
 * Packet:com.gf<br>
 * Description: <br>
 * Author:GuoFu<br>
 * Create Date: 2018/9/7.<br>
 * Modify User: <br>
 * Modify Date: <br>
 * Modify Description: <br>
 */
public class LuceneTester {
    String indexDir = "/Users/guofu/tmp临时区/Lucene/Index";
    //String dataDir = "/Users/guofu/tmp临时区/Lucene/Data";
    String dataDir = "/Users/guofu/Projects/p个人仓库/【Mine】ProjectRecord";
    Indexer indexer;
    Searcher searcher;

    public static void main(String[] args) {
        ArrayList<File> files = FileUtils.getListFiles("/Users/guofu/Projects/p个人仓库/【Mine】ProjectRecord", null);
        LuceneTester tester;
        try {
            tester = new LuceneTester();
            //tester.createIndex();// 创建索引
            tester.search("VSTO");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (org.apache.lucene.queryParser.ParseException e) {
            e.printStackTrace();
        }
    }

    private void createIndex() throws IOException {
        indexer = new Indexer(indexDir);
        int numIndexed;
        long startTime = System.currentTimeMillis();
        numIndexed = indexer.createIndex(dataDir, new FileFilter(".md"),true);
        long endTime = System.currentTimeMillis();
        indexer.close();
        System.out.println(numIndexed + " 个文件创建索引完成, 耗时: " + (endTime - startTime) + " ms");
    }

    private void search(String searchQuery) throws IOException, ParseException, org.apache.lucene.queryParser.ParseException {
        searcher = new Searcher(indexDir);
        long startTime = System.currentTimeMillis();
        TopDocs hits = searcher.search(searchQuery);
        long endTime = System.currentTimeMillis();

        System.out.println(hits.totalHits + " 个文件被找到。 耗时 :" + (endTime - startTime));

        //输出匹配的文件
        for (ScoreDoc scoreDoc : hits.scoreDocs) {
            Document doc = searcher.getDocument(scoreDoc);
            System.out.println("File: " + doc.get(LuceneConstants.FILE_PATH));
        }
        searcher.close();
    }
}
