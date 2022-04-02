package algo.demo.reflect;

import algo.demo.reflect.invoker.GetterInvoker;
import algo.demo.reflect.invoker.Invoker;
import algo.demo.reflect.invoker.SetterInvoker;
import algo.demo.reflect.test.Dog;

import java.lang.reflect.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Reflector {

    static final Map<Class<?>, Reflector> REFLECTOR_CACHE = new ConcurrentHashMap<>();

    private final Class<?> targetClass;
    private Constructor<?> defaultConstructor;
    private final Map<String, Invoker> setterInvokeMap = new HashMap<>(); // setter
    private final Map<String, Invoker> getterInvokeMap = new HashMap<>(); // getter
    private final Map<String, Class<?>> setTypes = new HashMap<>();
    private final Map<String, Class<?>> getTypes = new HashMap<>();

    private Reflector(Class<?> clazz) {
        this.targetClass = clazz;

        // 初始化所有类中的属性
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            initFieldInvokes(clazz);
            initOtherInvokes(clazz);
        }

        try {
            defaultConstructor = targetClass.getConstructor();
        } catch (NoSuchMethodException e) {
            defaultConstructor = null;
        }
    }

    private void initOtherInvokes(Class<?> clazz) {
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            String fieldName;

            if (isGetter(method.getName())) {
                fieldName = firstToLowerCase(method.getName().substring(3));
                if (!this.getterInvokeMap.containsKey(fieldName)) {
                    this.getterInvokeMap.put(fieldName, new GetterInvoker(method));
                    this.getTypes.put(fieldName, method.getReturnType());
                }
            } else if (isSetter(method.getName()) && method.getParameterTypes().length == 1) {
                fieldName = firstToLowerCase(method.getName().substring(3));
                if (!this.setterInvokeMap.containsKey(fieldName)) {
                    this.setterInvokeMap.put(fieldName, new SetterInvoker(method));
                    this.setTypes.put(fieldName, method.getParameterTypes()[0]);
                }
            }
        }
    }

    private boolean isGetter(String name) {
        return name.startsWith("get") && name.length() > 3;
    }

    private boolean isSetter(String name) {
        return name.startsWith("set") && name.length() > 3;
    }

    private void initFieldInvokes(Class<?> clazz) {

        // 初始化field
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            if (Modifier.isFinal(field.getModifiers())) {
                continue;
            }

            // 初始化setter
            addSetterField(clazz, field);

            // 初始化getter
            addGetterField(clazz, field);
        }

    }

    private void addGetterField(Class<?> clazz, Field field) {
        String getterName = toGetterMethodName(field.getName());
        try {
            Method method = clazz.getDeclaredMethod(getterName);
            this.getterInvokeMap.put(field.getName(), new GetterInvoker(method));
        } catch (NoSuchMethodException e) {
            this.getterInvokeMap.put(field.getName(), new GetterInvoker(field));
        }

        this.getTypes.put(field.getName(), field.getType());
    }

    private void addSetterField(Class<?> clazz, Field field) {
        String setterName = toSetterMethodName(field.getName());
        try {
            Method method = clazz.getDeclaredMethod(setterName, field.getType());
            this.setterInvokeMap.put(field.getName(), new SetterInvoker(method));
        } catch (NoSuchMethodException e) {
            this.setterInvokeMap.put(field.getName(), new SetterInvoker(field));
        }
        this.setTypes.put(field.getName(), field.getType());
    }

    // field name转 setter
    private String toSetterMethodName(String name) {
        return "set" + firstToUpperCase(name);
    }

    // field name转 getter
    private String toGetterMethodName(String name) {
        return "get" + firstToUpperCase(name);
    }

    private String firstToUpperCase(String str) {
        char[] chars = str.toCharArray();
        chars[0] = Character.toUpperCase(chars[0]);
        return String.valueOf(chars);
    }

    private String firstToLowerCase(String str) {
        char[] chars = str.toCharArray();
        chars[0] = Character.toLowerCase(chars[0]);
        return String.valueOf(chars);
    }


    public static Reflector forClass(Class<?> clazz) {
        return REFLECTOR_CACHE.computeIfAbsent(clazz, Reflector::new);
    }

    public boolean hasGetter(String getterName) {
        return this.getterInvokeMap.containsKey(getterName);
    }

    public boolean hasSetter(String setterName) {
        return this.setterInvokeMap.containsKey(setterName);
    }

    public Invoker getGetterInvoke(String getterName) {
        return this.getterInvokeMap.get(getterName);
    }

    public Invoker getSetterInvoke(String setterName) {
        return this.setterInvokeMap.get(setterName);
    }

    public Constructor<?> getDefaultConstructor() {
        if (defaultConstructor == null) {
            throw new RuntimeException(targetClass.getName() + " is not a no_args constructor");
        }

        if (defaultConstructor.isAccessible()) {
            defaultConstructor.setAccessible(true);
        }
        return defaultConstructor;
    }

    public static void main(String[] args) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        Reflector reflector = Reflector.forClass(Dog.class);

        Constructor<?> defaultConstructor = reflector.getDefaultConstructor();
        Object object = defaultConstructor.newInstance();

        boolean color = reflector.hasGetter("color");

    }
}
