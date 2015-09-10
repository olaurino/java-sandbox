package cfa.vo.speclib.generic;

import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import uk.ac.starlink.table.StarTable;
import uk.ac.starlink.table.StoragePolicy;
import uk.ac.starlink.votable.TableElement;
import uk.ac.starlink.votable.VODocument;
import uk.ac.starlink.votable.VOElementFactory;

import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.AbstractList;
import java.util.Collections;
import java.util.List;

/**
 * Created by olaurino on 9/9/15.
 */
public class Utils {
    public static String getFieldName(Method method) throws Exception {
        try {
            Class<?> clazz = method.getDeclaringClass();
            BeanInfo info = Introspector.getBeanInfo(clazz);
            PropertyDescriptor[] props = info.getPropertyDescriptors();
            for (PropertyDescriptor pd : props) {
                if (method.equals(pd.getWriteMethod()) || method.equals(pd.getReadMethod())) {
                    return pd.getName();
                }
            }
        } catch (IntrospectionException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        throw new Exception("not an accessor");
    }

    public static String getUtypeForMethod(Method method) throws Exception {
        Method getter = new PropertyDescriptor(getFieldName(method), method.getDeclaringClass()).getReadMethod();
        return getUtypeForGetter(getter);
    }

    public static String getUtypeForGetter(Method getter) throws Exception {
        try {
            return getter.getAnnotation(UTYPE.class).value();
        }
        catch(NullPointerException ex) {
            // FIXME should do something smarter;
            throw ex;
        }
    }

    public static List<TableElement> getTableElements(File f) throws IOException, SAXException {
        Source s = new StreamSource(f);
        VOElementFactory vof = new VOElementFactory(StoragePolicy.ADAPTIVE);
        DOMSource docSource = vof.transformToDOM(s, false);
        VODocument doc = (VODocument)docSource.getNode();
        NodeList ns = doc.getElementsByTagName("TABLE");
        return asList(ns);
    }

    public static StarTable getStarTableforProxy(Object proxy) throws Exception {
        try {
            Object handler = Proxy.getInvocationHandler(proxy);
            return ((StilDynamicProxy) handler).getStarTable();
        } catch (Throwable ex) {
            throw new Exception("could not get StarTable from proxy");
        }
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
