package cfa.vo.speclib;

import org.w3c.dom.NodeList;
import uk.ac.starlink.table.StoragePolicy;
import uk.ac.starlink.votable.TableElement;
import uk.ac.starlink.votable.VODocument;
import uk.ac.starlink.votable.VOElementFactory;

import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.lang.reflect.Proxy;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by olaurino on 9/9/15.
 */
public class IOFactory {
    public static List<Spectrum> getSpectra(File f) {
        try {
            Source s = new StreamSource(f);
            VOElementFactory vof = new VOElementFactory(StoragePolicy.ADAPTIVE);
            DOMSource docSource = vof.transformToDOM(s, false);
            VODocument doc = (VODocument)docSource.getNode();
            NodeList ns = doc.getElementsByTagName("TABLE");
            List<TableElement> nodes = asList(ns);
            List<Spectrum> spectra = new ArrayList();
            int index = 0;
            for (TableElement node : nodes) {
                spectra.add((Spectrum) Proxy.newProxyInstance(IOFactory.class.getClassLoader(), new Class[]{Spectrum.class}, new StilDynamicProxy(node, "spec", index++)));
            }
            return spectra;
        } catch (Throwable ex) {
            throw new RuntimeException("oops");
        }
    }

    public static List<Point> getPoints(Spectrum s) {

    }

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
