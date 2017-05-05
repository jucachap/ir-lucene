/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.co.usbcali.ir.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author jromero
 */
public class SgmToXmlConverter
{
    public void generateXmlFromSgm(String path)
    {
        String xmlContent = "";
        String lineSeparator = System.getProperty("line.separator");
        
        try
        {
            File sgmFile = new File(path);
            try (FileReader reader = new FileReader(sgmFile))
            {
                BufferedReader buffer = new BufferedReader(reader);

                String line;
                
                xmlContent += "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + lineSeparator;
                xmlContent += "<collection>" + lineSeparator;

                while((line = buffer.readLine()) != null)
                {
                    if (!line.contains("<!DOCTYPE lewis SYSTEM \"lewis.dtd\">"))
                    {
                        line = processCharactersInLine(line);
                        xmlContent += line + lineSeparator;
                    }
                }
                
                xmlContent += "</collection>";
            }
            
            String xmlPath = FilenameUtils.removeExtension(sgmFile.getName()) + ".xml";
            
            WriteFile.writeFileContent(xmlContent, xmlPath);
        }
        catch (FileNotFoundException ex)
        {
            Logger.getLogger(SgmToXmlConverter.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IOException ex)
        {
            Logger.getLogger(SgmToXmlConverter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private String processCharactersInLine(String line)
    {
        for (int i = 0; i < 160; i++)
        {
            line = line.replace("&#" + i + ";", "");
            line = line.replace("&#" + String.format("%03d", i) + ";", "");
        }
        
        line = line.replace("&lt;", "");
        
        return line;
    }
}
