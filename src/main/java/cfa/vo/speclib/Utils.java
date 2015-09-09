package cfa.vo.speclib;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

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
}
