/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.hr.ebxml.helper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

/**
 * Pomoćna klasa koja služi za ispis trenutne soap poruke u konzolu te upis u
 * datoteku na disku u direktoriju C:\ebMS_XML_FILES\
 *
 * @author jovidic
 */
public class PrintAndWrite {

    /**
     * Metoda kao parametre preuzima SOAP poruku, uputu za ispis u konzolu te
     * naziv imena datoteke koja će biti spremljena na disk
     *
     * @param message
     * @param uputa
     * @param filename
     * @throws SOAPException
     * @throws IOException
     */
    public static void printCurrMessage(SOAPMessage message, String uputa, String filename) throws SOAPException, IOException {

        System.out.println("\n************************************************************************");
        System.out.println(uputa);
        System.out.println("Zapisano u datoteku : " + filename + ".xml");
        System.out.println("\n-------------------------------------------------------------------------");
        message.writeTo(System.out);
        System.out.println("\n************************************************************************");

        File file = new File("C:\\ebMS_XML_FILES\\" + filename + ".xml");
        file.createNewFile();
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        message.writeTo(fileOutputStream);
        fileOutputStream.flush();
        fileOutputStream.close();

    }
}
