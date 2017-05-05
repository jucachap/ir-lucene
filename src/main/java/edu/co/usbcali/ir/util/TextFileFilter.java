/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.co.usbcali.ir.util;

import java.io.File;
import java.io.FileFilter;

/**
 *
 * @author jromero
 */
public class TextFileFilter implements FileFilter
{
    @Override
    public boolean accept(File path)
    {
        return path.getName().toLowerCase().endsWith(".txt");
    }
}
