package cfa.vo.speclib.generic;

import cfa.vo.speclib.domain.SpectrumImpl;
import cfa.vo.speclib.generic.quantity.Quantity;
import cfa.vo.speclib.generic.quantity.ValuedColumnInfo;
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
        return getGetter(method).isAnnotationPresent(Transient.class);
    }

    public static Method getGetter(Method method) throws Exception {
        String name = getFieldName(method);
        Class<?> clazz = method.getDeclaringClass();
        BeanInfo info = Introspector.getBeanInfo(clazz);
        PropertyDescriptor[] props = info.getPropertyDescriptors();
        for (PropertyDescriptor pd : props) {
            if (name.equals(pd.getName())) {
                return pd.getReadMethod();
            }
        }
        throw new Exception("no getter");
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
        return Quantity.class.isAssignableFrom(clazz);
//        return clazz.isPrimitive()
//                || String.class == clazz
//                || Number.class.isAssignableFrom(clazz)
//                || (clazz.isArray() && isPrimitive(clazz.getComponentType()))
//                ;
    }

    public static String getUtypeForMethod(String prefix, Method method) throws Exception {
        Method getter = getGetter(method);
        return getUtypeForGetter(prefix, getter);
    }

    public static String getUtypeForGetter(String prefix, Method getter) throws Exception {
        try {
            return prefix+getter.getAnnotation(VOModel.class).utype();
        }
        catch(NullPointerException ex) {
            // FIXME should do something smarter;
            throw ex;
        }
    }

    public static VOModel getModelDefinitionForMethod(Method method) throws Exception {
        Method getter = getGetter(method);
        return getter.getAnnotation(VOModel.class);
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

    public static ValuedColumnInfo findColumnIndexByUtype(RowWrapperStarTable table, String utype, Long row) {
        for (int i=0; i<table.getColumnCount(); i++) {
            ColumnInfo colInfo = table.getColumnInfo(i);
            if (utype.equals(colInfo.getUtype())) {
                return new ValuedColumnInfo(colInfo, table, row);
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

    public static ColumnInfo makeColumInfo(Method method) throws Exception {
        VOModel model = Utils.getModelDefinitionForMethod(method);
        ColumnInfo info = new ColumnInfo(model.utype());
        info.setContentClass(model.contentType());
        info.setUtype(model.utype());
        info.setUCD(model.UCD());
        info.setUnitString(model.unit());
        info.setShape(model.shape());
        return info;
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

//    public static class IndexedValuedColumnInfo {
//        private ValuedColumnInfo info;
//        private Integer index;
//
//        public IndexedValuedColumnInfo(ValuedColumnInfo info, Integer index) {
//            this.info = info;
//            this.index = index;
//        }
//
//        public ValuedColumnInfo getInfo() {
//            return info;
//        }
//
//        public void setInfo(ValuedColumnInfo info) {
//            this.info = info;
//        }
//
//        public Integer getIndex() {
//            return index;
//        }
//
//        public void setIndex(Integer index) {
//            this.index = index;
//        }
//    }

}
