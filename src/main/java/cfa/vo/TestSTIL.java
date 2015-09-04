package cfa.vo;

import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import uk.ac.starlink.table.StoragePolicy;
import uk.ac.starlink.votable.TableElement;
import uk.ac.starlink.votable.VODocument;
import uk.ac.starlink.votable.VOElementFactory;
import uk.ac.starlink.votable.VOStarTable;

import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by olaurino on 9/4/15.
 */
public class TestSTIL {

    public static void main(String[] args) throws IOException, SAXException, XPathExpressionException {
        File f = new File("/Users/olaurino/asdcMulti.vot");
        Source s = new StreamSource(f);
        VOElementFactory vof = new VOElementFactory(StoragePolicy.ADAPTIVE);
        DOMSource docSource = vof.transformToDOM(s, false);
        VODocument doc = (VODocument)docSource.getNode();
        NodeList ns = doc.getElementsByTagName("TABLE");
        List<TableElement> nodes = asList(ns);
        List<VOStarTable> tables = new ArrayList();
        for (TableElement node : nodes) {
            tables.add(new VOStarTable(node));
        }
        XPath xPath = XPathFactory.newInstance().newXPath();
        NodeList res = (NodeList) xPath.evaluate(".//FIELDref", nodes.get(0), XPathConstants.NODESET);
        System.out.println();
    }

    public static class VOStarTableWrapper {
        private TableElement element;
        private VOStarTable table;

        public VOStarTableWrapper(TableElement element, VOStarTable table) {
            this.element = element;
            this.table = table;
        }
    }

//    public static class VOTableWrapper extends {
//
//    }


    private static List<TableElement> asList(NodeList n) {
        return n.getLength() == 0 ?
                Collections.<TableElement>emptyList() : new NodeListWrapper(n);
    }

    private static final class NodeListWrapper extends AbstractList<TableElement> {
        private final NodeList list;

        NodeListWrapper(NodeList l) {
            list = l;
        }

        public TableElement get(int index) {
            return (TableElement) list.item(index);
        }

        public int size() {
            return list.getLength();
        }
    }
}