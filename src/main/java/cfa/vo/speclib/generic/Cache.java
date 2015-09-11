package cfa.vo.speclib.generic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Omar on 9/10/2015.
 */
public class Cache {
    private Map<InvocationHandler, Map<Method, Object>> proxyCache = Collections.synchronizedMap(new HashMap<InvocationHandler, Map<Method, Object>>());

    public Object getProxy(InvocationHandler handler, Method method) {
        Map<Method, Object> m2 = proxyCache.get(handler);
        return m2 == null? m2 : m2.get(method);
    }

    public void storeProxy(InvocationHandler handler, Method method, Object proxy) {
        Map<Method, Object> m2 = proxyCache.get(handler);
        if (m2 == null) {
            m2 = new HashMap<Method, Object>();
            proxyCache.put(handler, m2);
        }
        m2.put(method, proxy);
    }
}
