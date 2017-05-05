/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.co.usbcali.ir.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jromero
 */
public class WriteFile
{
    public static void writeFileContent(String content, String path)
    {
        try
        {
            File xmlFile = new File(path);
            try (BufferedWriter buffer = new BufferedWriter(new FileWriter(xmlFile)))
            {
                buffer.write(content);
            }
        }
        catch (IOException ex)
        {
            Logger.getLogger(SgmToXmlConverter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
