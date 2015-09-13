package cfa.vo.speclib.generic;

import cfa.vo.speclib.generic.quantity.Quantity;
import uk.ac.starlink.table.StarTable;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Omar on 9/10/2015.
 */
public class Cache {
    // FIXME Should use a Weak map eventually, or Guava's Cache
    private Map<InvocationHandler, Map<Method, Object>> proxyCache = Collections.synchronizedMap(new HashMap<InvocationHandler, Map<Method, Object>>());
    private Map<StarTable, Map<String, Quantity>> paramCache = Collections.synchronizedMap(new HashMap<StarTable, Map<String, Quantity>>());
    private Map<InvocationHandler, Map<String, Quantity>> columnCache = Collections.synchronizedMap(new HashMap<InvocationHandler, Map<String, Quantity>>());

    public Object getProxy(InvocationHandler handler, Method method) {
        Map<Method, Object> m2 = proxyCache.get(handler);
        return m2 == null? null : m2.get(method);
    }

    public void storeProxy(InvocationHandler handler, Method method, Object proxy) {
        Map<Method, Object> m2 = proxyCache.get(handler);
        if (m2 == null) {
            m2 = Collections.synchronizedMap(new HashMap<Method, Object>());
            proxyCache.put(handler, m2);
        }
        m2.put(method, proxy);
    }

    public Quantity getColumn(InvocationHandler handler, String utype) {
        Map<String, Quantity> m2 = columnCache.get(handler);
        return m2 == null? null : m2.get(utype);
    }

    public void storeColumn(InvocationHandler handler, String utype, Quantity column) {
        Map<String, Quantity> m2 = columnCache.get(handler);
        if (m2 == null) {
            m2 = Collections.synchronizedMap(new HashMap<String, Quantity>());
            columnCache.put(handler, m2);
        }
        m2.put(utype, column);
    }

    public Quantity getParam(StarTable table, String utype) {
        Map<String, Quantity> m2 = paramCache.get(table);
        return m2 == null? null : m2.get(utype);
    }

    public void storeParam(StarTable table, String utype, Quantity quantity) {
        Map<String, Quantity> m2 = paramCache.get(table);
        if (m2 == null) {
            m2 = Collections.synchronizedMap(new HashMap<String, Quantity>());
            paramCache.put(table, m2);
        }
        m2.put(utype, quantity);
    }
}
