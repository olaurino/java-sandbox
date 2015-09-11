package cfa.vo.speclib.generic;

import cfa.vo.speclib.domain.SpectrumImpl;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import uk.ac.starlink.table.*;
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

    public static boolean isGetter(Method method) throws Exception {
        Class<?> clazz = method.getDeclaringClass();
        BeanInfo info = Introspector.getBeanInfo(clazz);
        PropertyDescriptor[] props = info.getPropertyDescriptors();
        for (PropertyDescriptor pd : props) {
            if (method.getName().equals(pd.getReadMethod().getName())) {
                return true;
            }
        }
        return false;
    }

    public static boolean isSetter(Method method) throws Exception {
        Class<?> clazz = method.getDeclaringClass();
        BeanInfo info = Introspector.getBeanInfo(clazz);
        PropertyDescriptor[] props = info.getPropertyDescriptors();
        for (PropertyDescriptor pd : props) {
            if (method.getName().equals(pd.getWriteMethod().getName())) {
                return true;
            }
        }
        return false;
    }

    public static boolean isTransient(Method method) throws Exception {
        String name = getFieldName(method);
        Class<?> clazz = method.getDeclaringClass();
        BeanInfo info = Introspector.getBeanInfo(clazz);
        PropertyDescriptor[] props = info.getPropertyDescriptors();
        for (PropertyDescriptor pd : props) {
            if (name.equals(pd.getName())) {
                Method getter = pd.getReadMethod();
                return getter.isAnnotationPresent(Transient.class);
            }
        }
        throw new Exception("Beans should have a getter if they have an annonated setter");
    }

    public static DescribedValue findParamByUtype(StarTable table, String utype) {
        for (DescribedValue param: (List<DescribedValue>) table.getParameters()) {
            if (utype.equals(param.getInfo().getUtype())) {
                return param;
            }
        }
        return null;
    }

    public static boolean returnsPrimitive(Method method) {
        return isPrimitive(method.getReturnType());
    }

    public static boolean isPrimitive(Class clazz) {
        return clazz.isPrimitive()
                || String.class == clazz
                || Number.class.isAssignableFrom(clazz)
                || (clazz.isArray() && isPrimitive(clazz.getComponentType()))
                ;
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
            if (proxy instanceof SpectrumImpl) {
                proxy = ((SpectrumImpl) proxy).getProxy();
            }
            Object handler = Proxy.getInvocationHandler(proxy);
            return ((StarTableInvocationHandler) handler).getStarTable();
        } catch (Throwable ex) {
            throw new Exception("could not get StarTable from proxy");
        }
    }

    private static List<TableElement> asList(NodeList n) {
        return n.getLength() == 0 ?
                Collections.<TableElement>emptyList() : new NodeListWrapper(n);
    }

    public static Integer findColumnIndexByUtype(StarTable table, String utype) {
        for (int i=0; i<table.getColumnCount(); i++) {
            ColumnInfo colInfo = table.getColumnInfo(i);
            if (utype.equals(colInfo.getUtype())) {
                return i;
            }
        }
        return null;
    }

    public static RowListStarTable getRowListStarTable(StarTable orig) throws IOException {
        RowListStarTable editable = new RowListStarTable(orig);
        RowSequence rowSequence = orig.getRowSequence();
        RowSequence rseq = rowSequence;
        try {
            while ( rseq.next() ) {
                Object[] row = rseq.getRow();
                editable.addRow(row);
            }
        }
        finally {
            rseq.close();
        }
        return editable;
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
