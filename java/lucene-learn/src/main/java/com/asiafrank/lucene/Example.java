package com.asiafrank.lucene;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.MMapDirectory;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * @author zhangxiaofan 2020/08/14-10:25
 */
public class Example {
    public static void main(String[] args) throws IOException {
        Directory memoryIndex = new MMapDirectory(Paths.get("data"));
        StandardAnalyzer analyzer = new StandardAnalyzer();
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
        IndexWriter writter = new IndexWriter(memoryIndex, indexWriterConfig);
        Document document = new Document();

        document.add(new TextField("title", "xfasdfasdfasdf", Field.Store.YES));
        document.add(new TextField("body", "asdfasdfasdf", Field.Store.YES));

        writter.addDocument(document);
        writter.close();
    }
}
