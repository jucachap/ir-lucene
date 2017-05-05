/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.co.usbcali.ir.util;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FilenameUtils;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

/**
 *
 * @author jromero
 */
public class ExtractReutersNews
{
    public void extractNewsFromXml(String path)
    {
        SAXBuilder builder = new SAXBuilder();
        File xmlFile = new File(path);
        
        String baseFileName = FilenameUtils.removeExtension(xmlFile.getName());
        String lineSeparator = System.getProperty("line.separator");
        
        try
        {
            Document document = (Document) builder.build(xmlFile);
            Element rootNode = document.getRootElement();
            List listReuters = rootNode.getChildren("REUTERS");
            
            for (Object listElement : listReuters)
            {
                Element reuters = (Element) listElement;
                
                String newId = reuters.getAttributeValue("NEWID");
                String date = reuters.getChildText("DATE");
                
                List listText = reuters.getChildren("TEXT");
                Element text = (Element) listText.get(0);
                
                String title = text.getChildText("TITLE");
                String body = text.getChildText("BODY");
                
                String reuterContent = title + lineSeparator + date + lineSeparator + lineSeparator + body;
                String reuterPath = "reuters/" + baseFileName + "-" + newId + ".txt";
                
                WriteFile.writeFileContent(reuterContent, reuterPath);
            }
        }
        catch (JDOMException | IOException ex)
        {
            Logger.getLogger(ExtractReutersNews.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
