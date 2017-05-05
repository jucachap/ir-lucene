/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.co.usbcali.ir.main;

import edu.co.usbcali.ir.processes.Indexer;
import edu.co.usbcali.ir.processes.LuceneConstants;
import edu.co.usbcali.ir.processes.Searcher;
import edu.co.usbcali.ir.util.ExtractReutersNews;
import edu.co.usbcali.ir.util.SgmToXmlConverter;
import edu.co.usbcali.ir.util.TextFileFilter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

/**
 *
 * @author jromero
 */
public class Run
{

    public static void main(String[] args)
    {
        
        //SgmToXmlConverter converter = new SgmToXmlConverter();
        //converter.generateXmlFromSgm("reut2-000.sgm");

        //ExtractReutersNews extractor = new ExtractReutersNews();
        //extractor.extractNewsFromXml("reut2-000.xml");
            
        /*try
        {
            Indexer indexer = new Indexer("index");
            
            long startTime = System.currentTimeMillis();
            int numIndexed = indexer.createIndex("reuters", new TextFileFilter());
            long endTime = System.currentTimeMillis();
            
            indexer.close();
            
            System.out.println(numIndexed + " File indexed, time taken: " + (endTime - startTime) + " ms");
        }
        catch (IOException ex)
        {
            Logger.getLogger(Run.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        
        try
        {    
            Searcher searcher = new Searcher("index");
            long startTime = System.currentTimeMillis();
            TopDocs hits = searcher.search("activities");
            long endTime = System.currentTimeMillis();
            
            System.out.println(hits.totalHits + " documents found. Time :" + (endTime - startTime));
            
            for (ScoreDoc scoreDoc : hits.scoreDocs)
            {
                Document doc = searcher.getDocument(scoreDoc);
                System.out.println("File: " + doc.get(LuceneConstants.FILE_PATH));
            }
        }
        catch (IOException | ParseException ex)
        {
            Logger.getLogger(Run.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
