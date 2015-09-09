package cfa.vo.speclib;

import org.w3c.dom.Node;
import org.xml.sax.SAXException;
import uk.ac.starlink.table.Tables;
import uk.ac.starlink.votable.*;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by olaurino on 9/9/15.
 */
public class StilDynamicProxy implements InvocationHandler {

    private TableElement element;
    private String prefix;
    //FIXME Introducing pos is a workaround to a possible issue in stil that prevents xpath from being applied to non-root elements.
    private int pos;

    public StilDynamicProxy(TableElement element, String prefix, int pos) throws IOException, SAXException {
        this.element = element;
        this.prefix = prefix;
        this.pos = pos;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if ("toString".equals(method.getName())) {
            return element.getName();
        }
        try {
            String utype = Utils.getUtypeForMethod(method);
            XPath xPath = XPathFactory.newInstance().newXPath();
            VODocument doc = (VODocument) element.getOwnerDocument();
            String expression = String.format("//TABLE[%s]//*[@utype='%s:%s']", pos + 1, prefix, utype);
            Node FIELDorPARAM = (Node) xPath.evaluate(expression, doc, XPathConstants.NODE);
            if (FIELDorPARAM instanceof ParamElement) {
                return ((ParamElement) FIELDorPARAM).getValue();
            }
            if (FIELDorPARAM instanceof FieldElement) {
                expression = String.format("count(//TABLE[%s]//FIELD[@utype='%s:%s']/preceding-sibling::FIELD)", pos + 1, prefix, utype);
                String idxS = xPath.evaluate(expression, doc);
                Integer idx = Integer.parseInt(idxS);
                TabularData data = element.getData();
                int rows = Tables.checkedLongToInt(data.getRowCount());
                // FIXME these are arrays should probably be cached, or something smarter should be done
                double[] ret = new double[rows];
                for (int i = 0; i < rows; i++) {
                    ret[i] = (Double) data.getCell(i, idx);
                }
                return ret;
            }
        } catch (Exception ex) {
            // FIXME overlaps with IOFactory
            return Proxy.newProxyInstance(proxy.getClass().getClassLoader(), new Class[]{method.getReturnType()}, this);
        }
        return null;
    }

}
