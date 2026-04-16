package utils;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import java.io.File;

public class XmlUtils {

    public static String lerTag(String caminhoArquivo, String tag) {

        try {
            File arquivo = new File(caminhoArquivo);

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            Document documento = builder.parse(arquivo);

            return documento.getElementsByTagName(tag)
                    .item(0)
                    .getTextContent();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}