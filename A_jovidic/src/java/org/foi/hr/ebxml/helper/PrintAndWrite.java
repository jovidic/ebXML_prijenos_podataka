
package org.foi.hr.ebxml.helper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

/**
 * Klasa koja sadrži pomoćnu metodu za ispis trenutne SOAP poruke u konzolu te u
 * datoteku na disku.
 *
 * @author jovidic
 */
public class PrintAndWrite {

    public static void printCurrMessage(SOAPMessage message, String uputa, String filename) throws SOAPException, IOException {

        System.out.println("\n************************************************************************");
        System.out.println(uputa);
        System.out.println("Zapisano u datoteku : " + filename + ".xml");
        System.out.println("\n-------------------------------------------------------------------------");
        message.writeTo(System.out);
        System.out.println("\n************************************************************************");

        File file = new File("C:\\ebMS_XML_FILES\\" + filename + ".xml");
        file.createNewFile();
        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            message.writeTo(fileOutputStream);
            fileOutputStream.flush();
        }

    }
}
