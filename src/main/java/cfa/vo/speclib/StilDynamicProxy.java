package cfa.vo.speclib;

import org.w3c.dom.Node;
import org.xml.sax.SAXException;
import uk.ac.starlink.table.StarTable;
import uk.ac.starlink.votable.*;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by olaurino on 9/9/15.
 */
public class StilDynamicProxy implements InvocationHandler {

    private TableElement element;
    private String prefix;
    private Long pointId;
    private XPath xPath = XPathFactory.newInstance().newXPath();
    private static Map<String, Node> utypesCache = Collections.synchronizedMap(new HashMap<String, Node>());
    private static Map<Method, Object> proxyCache = Collections.synchronizedMap(new HashMap<Method, Object>());
    //FIXME Introducing tablePosition is a workaround to a possible issue in stil that prevents xpath from being applied to non-root elements.
    private Integer tablePosition;

    // Each point has a Proxy that shares the same TableElement, but with a different pointId
    public StilDynamicProxy(TableElement element, String prefix, Integer tablePosition, Long pointId) throws IOException, SAXException {
        this.element = element;
        this.prefix = prefix + (prefix.isEmpty()? "" : ":");
        this.tablePosition = tablePosition;
        this.pointId = pointId;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // FIXME implement the setters. Right now only getters are covered.

        if ("toString".equals(method.getName())) {
            return element.getName();
        }

        try {
            String utype = Utils.getUtypeForMethod(method);
            return getValueByUtype(utype);
        } catch (Exception ex) {
            // FIXME this should be done only for getters that do not return primitives. It works now because those methods are not annotated, but we cannot assume that in general.

            Object ret = proxyCache.get(method);

            if (ret == null) {
                ret = Proxy.newProxyInstance(proxy.getClass().getClassLoader(), new Class[]{method.getReturnType()}, this);
                proxyCache.put(method, ret);
            }
            return ret;
        }
    }

    //TODO All the complexity of the deserialization rules should go in this method.
    private Object getValueByUtype(String utype) throws XPathExpressionException, IOException {
        VODocument doc = (VODocument) element.getOwnerDocument();
        Node FIELDorPARAM = utypesCache.get(utype);

        if (FIELDorPARAM == null) {
            String expression = String.format("//TABLE[%s]//*[@utype='%s%s']", tablePosition + 1, prefix, utype);
            FIELDorPARAM = (Node) xPath.evaluate(expression, doc, XPathConstants.NODE);
            utypesCache.put(utype, FIELDorPARAM);
        }


        if (FIELDorPARAM instanceof ParamElement) {
            return ((ParamElement) FIELDorPARAM).getValue();
        }

        // At this point of the implementation, a utype must have been identified for the method.
        // If this is not a point, only PARAMs should be accessed; we will assume that if
        // a PARAM is not found for a legit getter in a root object, then the value to be returned is null.
        // Since the Point interface extends Spectrum, if the utype is associated to a field, then each point
        // will return the
        // TODO smarter values than null could be provided by inspecting the primitive value returned.
        // FIXME before assuming this case should return null, one should check that there is not a FIELD associated
        // to this utype. If there is no FIELD, then the default value defined by the model should be returned.
        if (pointId == null) {
            return null;
        }

        if (FIELDorPARAM instanceof FieldElement) {
            String expression = String.format("count(//TABLE[%s]//FIELD[@utype='%s%s']/preceding-sibling::FIELD)", tablePosition + 1, prefix, utype);
            String idxS = xPath.evaluate(expression, doc);
            Integer idx = Integer.parseInt(idxS);
            TabularData data = element.getData();
            return data.getCell(pointId, idx);
        }

        return null;
    }

    public StarTable getStarTable() throws IOException {
        return new VOStarTable(element);
    }

}
