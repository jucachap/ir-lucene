/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.co.usbcali.ir.processes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexOptions;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;

/**
 *
 * @author jromero
 */
public class Indexer
{
    private IndexWriter writer;

    public Indexer(String indexDirectoryPath) throws IOException
    {
        FSDirectory indexDirectory = FSDirectory.open(Paths.get(indexDirectoryPath));
        IndexWriterConfig config = new IndexWriterConfig(new StandardAnalyzer());
        
        writer = new IndexWriter(indexDirectory, config);
    }

    public void close() throws CorruptIndexException, IOException
    {
        writer.close();
    }

    private Document getDocument(File file) throws IOException
    {
        Document document = new Document();
        
        FieldType type = new FieldType();
        type.setIndexOptions(IndexOptions.DOCS);
        type.setStored(true);
        type.setTokenized(true);
        type.setStoreTermVectors(true);
        type.setStoreTermVectorPositions(true);
        type.setStoreTermVectorOffsets(true);
        type.setStoreTermVectorPayloads(true);
        
        Field contentField = new Field(LuceneConstants.CONTENTS, getContent(file), type);
        Field fileNameField = new Field(LuceneConstants.FILE_NAME, file.getName(), type);
        Field filePathField = new Field(LuceneConstants.FILE_PATH, file.getCanonicalPath(), type);

        document.add(contentField);
        document.add(fileNameField);
        document.add(filePathField);

        return document;
    }
    
    private String getContent(File file) throws IOException
    {
        String content = "";
        try (FileReader reader = new FileReader(file))
        {
            BufferedReader buffer = new BufferedReader(reader);
            String line;
            
            while((line = buffer.readLine()) != null)
            {
                content += line;
            }
        }
        
        return content;
    }

    private void indexFile(File file) throws IOException
    {
        System.out.println("Indexing " + file.getCanonicalPath());
        Document document = getDocument(file);
        writer.addDocument(document);
    }

    public int createIndex(String dataDirPath, FileFilter filter) throws IOException
    {
        File[] files = new File(dataDirPath).listFiles();

        for (File file : files)
        {
            if (!file.isDirectory() && !file.isHidden() && file.exists() && file.canRead() 
                    && filter.accept(file))
            {
                indexFile(file);
            }
        }
        return writer.numDocs();
    }
}
