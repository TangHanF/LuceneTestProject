package com.gf.lucene;

import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.gf.utils.FileUtils;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

/**
 * Title: 用于索引的原始数据，这样我们就可以使用Lucene库，使其可搜索<br>
 * Packet:com.gf.lucene<br>
 * Description: <br>
 * Author:GuoFu<br>
 * Create Date: 2018/9/7.<br>
 * Modify User: <br>
 * Modify Date: <br>
 * Modify Description: <br>
 */

public class Indexer {

    private IndexWriter writer;

    public Indexer(String indexDirectoryPath) throws IOException {
        // 包含索引的目录
        Directory indexDirectory = FSDirectory.open(new File(indexDirectoryPath));

        // 创建索引器
        writer = new IndexWriter(indexDirectory, new StandardAnalyzer(Version.LUCENE_36), true, IndexWriter.MaxFieldLength.UNLIMITED);
    }

    public void close() throws CorruptIndexException, IOException {
        writer.close();
    }

    private Document getDocument(File file) throws IOException {
        Document document = new Document();

        // 索引文件内容
        Field contentField = new Field(LuceneConstants.CONTENTS, new FileReader(file));
        // 索引文件名称
        Field fileNameField = new Field(LuceneConstants.FILE_NAME, file.getName(), Field.Store.YES, Field.Index.NOT_ANALYZED);
        // 索引文件路径
        Field filePathField = new Field(LuceneConstants.FILE_PATH, file.getCanonicalPath(), Field.Store.YES, Field.Index.NOT_ANALYZED);

        document.add(contentField);
        document.add(fileNameField);
        document.add(filePathField);

        return document;
    }

    private void indexFile(File file) throws IOException {
        System.out.println("Indexing " + file.getCanonicalPath());
        Document document = getDocument(file);
        writer.addDocument(document);
    }

    public int createIndex(String dataDirPath, FileFilter filter, boolean isDiGui) throws IOException {
        //获取所有指定的数据目录中的文件
        File[] files = null;
        if (!isDiGui) {
            files = new File(dataDirPath).listFiles();
            for (File file : files) {
                if (!file.isDirectory() && !file.isHidden() && file.exists() && file.canRead() && filter.accept(file)) {
                    indexFile(file);
                }
            }
        }
        else {
            ArrayList<File> fileList = FileUtils.getListFiles(dataDirPath, null);
            for (File file : fileList) {
                if (!file.isDirectory() && !file.isHidden() && file.exists() && file.canRead() && filter.accept(file)) {
                    indexFile(file);
                }
            }
        }
        return writer.numDocs();
    }
}
