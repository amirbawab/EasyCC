package reflection;

import java.lang.reflect.Method;

/**
 * Store an object with a specific method
 */

public class ObjectMethod {
    private Method method;
    private Object object;

    public ObjectMethod(Method method, Object object) {
        this.method = method;
        this.object = object;
    }

    public Method getMethod() {
        return method;
    }

    public Object getObject() {
        return object;
    }
}