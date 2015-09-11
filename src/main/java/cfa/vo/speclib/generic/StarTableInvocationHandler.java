package cfa.vo.speclib.generic;

import org.xml.sax.SAXException;
import uk.ac.starlink.table.ColumnInfo;
import uk.ac.starlink.table.DescribedValue;
import uk.ac.starlink.table.StarTable;

import javax.naming.OperationNotSupportedException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by olaurino on 9/9/15.
 */
public class StarTableInvocationHandler implements InvocationHandler {

    private StarTable table;
    private String prefix;
    private Long pointId;
    //TODO check if xpath is thread safe and make it static if possible
    private XPath xPath = XPathFactory.newInstance().newXPath();
    private Cache cache;
    private Map<String, Object> transients = new HashMap<String, Object>();
    //FIXME Introducing tablePosition is a workaround to a possible issue in stil that prevents xpath from being applied to non-root elements.
    private Integer tablePosition;

    // Each point has a Proxy that shares the same TableElement, but with a different pointId
    public StarTableInvocationHandler(Cache cache, StarTable table, String prefix, Long pointId) throws IOException, SAXException {
        this.table = table;
        this.prefix = prefix + (prefix.isEmpty()? "" : ":");
        this.pointId = pointId;
        this.cache = cache;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // FIXME implement the setters. Right now only getters are covered.

        if ("toString".equals(method.getName())) {
            return table.toString();
        }

        boolean isGetter = Utils.isGetter(method);

        // If it is a getter and transient, then return the transiet object instance or null.
        if (isGetter && Utils.isTransient(method)) {
            return transients.get(Utils.getFieldName(method));
        }

        // If it is a getter and the return type is not primitive (including String), then return a new Proxy of the return type
        if (isGetter && !Utils.returnsPrimitive(method)) {
            Object ret = cache.getProxy(this, method);

            if (ret == null) {
                ret = Proxy.newProxyInstance(proxy.getClass().getClassLoader(), new Class[]{method.getReturnType()}, this);
                cache.storeProxy(this, method, ret);
            }
            return ret;
        }

        // If it is setter and transient, then store the transient object instance.
        if (!isGetter && Utils.isTransient(method)) {
            transients.put(Utils.getFieldName(method), args[0]);
            return null;
        }

        // If it is a getter (and not transient and it does return a primitive) return the value, if any, or null.
        if (isGetter) {
            String utype = Utils.getUtypeForMethod(method);
            return getValueByUtype(prefix+utype);
        }

        // If it is a setter (and not transient) and it does not get a primitive argument, throw and exception
        // This implementation does not allow to set structured objects, but only primitive types in the leaves.
        if (!isGetter && Utils.isPrimitive(args[0].getClass())) {
            throw new OperationNotSupportedException("This implementation does not allow to set structured objects, but only primitive types in the leaves.");
        }

//        // Finally, if it is a setter (and not transient, and does get a primitive argument), set the value
//        if (!isGetter) {
//            String utype = Utils.getUtypeForMethod(method);
//            setValueByUtype(utype, args[0]);
//        }

        return null;

    }

//    private void setValueByUtype(String utype, Object value) throws XPathExpressionException, IllegalAccessException {
//        // First, check if there is already a node with this UTYPE
//        Node FIELDorPARAM = cache.getNode(utype);
//        VODocument doc = (VODocument) element.getOwnerDocument();
//
//        if (FIELDorPARAM == null) {
//            String expression = String.format("//TABLE[%s]//*[@utype='%s%s']", tablePosition + 1, prefix, utype);
//            FIELDorPARAM = (Node) xPath.evaluate(expression, doc, XPathConstants.NODE);
//
//            if (FIELDorPARAM != null) {
//                cache.storeNode(utype, FIELDorPARAM);
//            }
//        }
//
//        // If there is no existing element, then create one.
//
//        // If the request comes from a container object, create and append a new PARAM
//        ParamElement param = doc
//
//        if (FIELDorPARAM instanceof ParamElement) {
//            // a child instance must not set a PARAM
//            if (pointId != null) {
//                throw new IllegalAccessException("a child instance must not set a PARAM");
//            } else {
//                // Append a new column
//                // First get an handle on the
//            }
//        }
//    }

    //TODO All the complexity of the deserialization rules should go in this method.
    private Object getValueByUtype(String utype) throws XPathExpressionException, IOException {
        for (DescribedValue param: (List<DescribedValue>) table.getParameters()) {
            if (utype.equals(param.getInfo().getUtype())) {
                return param.getValue();
            }
        }

        // At this point of the implementation, a utype must have been identified for the method.
        // If this is not a point, only PARAMs should be accessed; we will assume that if
        // a PARAM is not found for a legit getter in a root object, then the value to be returned is null.
        // Since the SpectrumPoint interface extends Spectrum, if the utype is associated to a field, then each point
        // will return the
        // TODO smarter values than null could be provided by inspecting the primitive value returned.
        // FIXME before assuming this case should return null, one should check that there is not a FIELD associated
        // to this utype. If there is no FIELD, then the default value defined by the model should be returned.
        if (pointId == null) {
            return null;
        }

        for (int i=0; i<table.getColumnCount(); i++) {
            ColumnInfo colInfo = table.getColumnInfo(i);
            if (utype.equals(colInfo.getUtype())) {
                return table.getCell(pointId, i);
            }
        }

        return null;
    }

    public StarTable getStarTable() throws IOException {
        return table;
    }

}
