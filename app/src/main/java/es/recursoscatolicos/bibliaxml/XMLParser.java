package es.recursoscatolicos.bibliaxml;

import android.content.Context;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class XMLParser {

    public static List<Verse> parseXML(Context context) {
        List<Verse> verseList = new ArrayList<>();
        try {
            InputStream inputStream = context.getResources().openRawResource(R.raw.bible);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputStream);

            NodeList testaments = doc.getElementsByTagName("testament");

            for (int i = 0; i < testaments.getLength(); i++) {
                Node testament = testaments.item(i);
                NodeList books = testament.getChildNodes();
                for (int j = 0; j < books.getLength(); j++) {
                    Node book = books.item(j);
                    NodeList chapters = book.getChildNodes();
                    for (int k = 0; k < chapters.getLength(); k++) {
                        Node chapter = chapters.item(k);
                        NodeList verses = chapter.getChildNodes();
                        for (int l = 0; l < verses.getLength(); l++) {
                            Node verse = verses.item(l);
                            if (verse.getNodeType() == Node.ELEMENT_NODE) {
                                String number = verse.getAttributes().getNamedItem("number").getNodeValue();
                                String text = verse.getTextContent();
                                verseList.add(new Verse(i+1, j + 1, Integer.parseInt(number), text));
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return verseList;
    }
}

