package algo.demo.reflect.meta;


import algo.demo.reflect.test.Animal;
import algo.demo.reflect.test.DelegateAnimal;
import algo.demo.reflect.test.Dog;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class MetaObject {

    private Object originObject;
    private ObjectWrapper objectWrapper;

    public MetaObject(Object target) {
        this.originObject = target;

        if (target instanceof ObjectWrapper) {
            this.objectWrapper = (ObjectWrapper) target;
        } else if (target instanceof Map) {
            this.objectWrapper = new MapObjectWrapper((Map<String, Object>) target);
        } else {
            this.objectWrapper = new BeanObjectWrapper(target);
        }
    }

    public static MetaObject forObject(Object target) {
        if (target == null) {
            return null;
        }

        return new MetaObject(target);
    }

    public Object getValue(String name) {
        PropertyTokenizer propertyTokenizer = new PropertyTokenizer(name);
        if (propertyTokenizer.hasNext()) {
            Object target = getValue(propertyTokenizer.getCurrent());
            MetaObject metaObject = MetaObject.forObject(target);
            if (metaObject == null) {
                return null;
            }
            return metaObject.getValue(propertyTokenizer.getChildren());
        }
        return objectWrapper.get(propertyTokenizer.getCurrent());
    }

    public void setValue(String name,Object value) {
        PropertyTokenizer propertyTokenizer = new PropertyTokenizer(name);
        if (propertyTokenizer.hasNext()) {
            Object target = getValue(propertyTokenizer.getCurrent());
            MetaObject metaObject = MetaObject.forObject(target);
            if (metaObject == null) {
                return;
            }
            metaObject.setValue(propertyTokenizer.getChildren(),value);
        }else{
            objectWrapper.set(propertyTokenizer.getCurrent(),value);
        }
    }

    public static void main(String[] args) {
        Animal animal = new DelegateAnimal(new Dog("白", "可乐"));
        HashMap<String, Object> subMap = new HashMap<>();
        HashMap<String, Object> map = new HashMap<>();
        map.put("sub",subMap);

        subMap.put("animal", animal);

        MetaObject metaObject = MetaObject.forObject(map);
        Object name = metaObject.getValue("sub.animal.delegate.name");
        metaObject.setValue("sub.animal.delegate.name","张三三");

        System.out.println(name);
        System.out.println(metaObject.getValue("sub.animal.delegate.name"));
    }
}
