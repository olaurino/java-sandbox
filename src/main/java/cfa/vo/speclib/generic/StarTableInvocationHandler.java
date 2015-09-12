package cfa.vo.speclib.generic;

import org.xml.sax.SAXException;
import uk.ac.starlink.table.*;

import javax.naming.OperationNotSupportedException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by olaurino on 9/9/15.
 */
public class StarTableInvocationHandler implements InvocationHandler {

    private RowWrapperStarTable table;
    private String prefix;
    private Long pointId;
    //TODO check if xpath is thread safe and make it static if possible
    private XPath xPath = XPathFactory.newInstance().newXPath();
    private Cache cache;
    private Map<String, Object> transients = new HashMap<String, Object>();

    // Each point has a Proxy that shares the same TableElement, but with a different pointId
    public StarTableInvocationHandler(Cache cache, RowWrapperStarTable table, String prefix, Long pointId) throws IOException, SAXException {
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

        // If it is a setter (and not transient) and it does not get a primitive argument, throw an exception
        // This implementation does not allow to set structured objects, but only primitive types in the leaves.
        if (!isGetter && !Utils.isPrimitive(args[0].getClass())) {
            throw new OperationNotSupportedException("This implementation does not allow to set structured objects, but only primitive types in the leaves.");
        }

        // Finally, if it is a setter (and not transient, and does get a primitive argument), set the value
        if (!isGetter) {
            setValue(method, args[0]);
        }

        return null;

    }

    private void setValue(Method method, Object value) throws Exception {
        String utype = prefix+Utils.getUtypeForMethod(method);
        // if this is a container, get or create a Parameter and sets its value
        if (pointId == null) {
            DescribedValue parameter = Utils.findParamByUtype(table, utype);
            if (parameter != null) {
                parameter.setValue(value);
            } else {
                DefaultValueInfo vinfo = new DefaultValueInfo();
                //FIXME need more stuff, e.g. UCD, Unit
                vinfo.setUtype(utype);
                vinfo.setContentClass(value.getClass());
                parameter = new DescribedValue(vinfo);
                parameter.setValue(value);
            }
            return;
        }

        // if this is a child (point), get or create the Column corresponding to the utype and set the value for this point's row
        Integer columnIndex = Utils.findColumnIndexByUtype(table, utype);
        if (columnIndex != null) {
            table.setCell(pointId, columnIndex, value);
        } else {
            DefaultValueInfo vinfo = new DefaultValueInfo(Utils.getFieldName(method));
            //FIXME need more stuff, e.g. UCD, Unit
            vinfo.setUtype(utype);
            vinfo.setContentClass(value.getClass());
            table.setCellInNewColumn(pointId, value, vinfo);
        }
    }

    //TODO All the complexity of the deserialization rules should go in this method.
    private Object getValueByUtype(String utype) throws XPathExpressionException, IOException {
        DescribedValue param = Utils.findParamByUtype(table, utype);
        if (param != null) {
            return param.getValue();
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

        Integer columnIndex = Utils.findColumnIndexByUtype(table, utype);
        if (columnIndex != null) {
            return table.getCell(pointId, columnIndex);
        }

        return null;
    }

    public RowWrapperStarTable getStarTable() throws IOException {
        return table;
    }

    public Cache getCache() {
        return cache;
    }

    public String getPrefix() {
        return prefix;
    }

}
