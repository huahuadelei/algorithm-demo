package algo.demo.reflect.invoker;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class GetterInvoker extends Invoker {

    public GetterInvoker(Field field) {
        super(field);
    }

    public GetterInvoker(Method method) {
        super(method);
    }

    @Override
    public Object invoke(Object target, Object[] args) throws InvocationTargetException, IllegalAccessException {
        return this.fieldInvoke?doFieldInvoke(target,args):doMethodInvoke(target,args);
    }

    private Object doMethodInvoke(Object target, Object[] args) throws InvocationTargetException, IllegalAccessException {
        if (!method.isAccessible()) {
            method.setAccessible(true);
        }
        return this.method.invoke(target,args);
    }

    private Object doFieldInvoke(Object target, Object[] args) throws IllegalAccessException {
        if (!field.isAccessible()) {
            field.setAccessible(true);
        }
        return field.get(target);
    }
}
