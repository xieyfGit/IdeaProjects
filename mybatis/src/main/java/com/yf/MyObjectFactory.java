package com.yf;

import org.apache.ibatis.reflection.ReflectionException;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.util.*;

public class MyObjectFactory implements ObjectFactory ,Serializable {

    private static final long serialVersionUID = 8747649804149100939L;
    private static Logger log = LogManager.getLogger(MyObjectFactory.class);

    public <T> T create(Class<T> type) {
        return (T)this.create(type,null,null);
    }

    public <T> T create(Class<T> type, List<Class<?>> constructorArgTypes, List<Object> constructorArgs) {
        Class<?> classToCreate = this.resolveInterface(type);
        return (T)this.instantiateClass(classToCreate, constructorArgTypes, constructorArgs);
    }

    public void setProperties(Properties properties) {
        log.debug("列出所有已经加载的属性...");
        properties.setProperty("option","添加一个属性");
        for (Map.Entry entry:properties.entrySet()) {
            log.debug(entry.getKey()+":"+entry.getValue());
        }
    }

    <T> T instantiateClass(Class<T> type, List<Class<?>> constructorArgTypes, List<Object> constructorArgs) {
        try {
            Constructor constructor;
            if (constructorArgTypes != null && constructorArgs != null) {
                constructor = type.getDeclaredConstructor((Class[])constructorArgTypes.toArray(new Class[constructorArgTypes.size()]));
                if (!constructor.isAccessible()) {
                    constructor.setAccessible(true);
                }

                return (T) constructor.newInstance(constructorArgs.toArray(new Object[constructorArgs.size()]));
            } else {
                constructor = type.getDeclaredConstructor();
                if (!constructor.isAccessible()) {
                    constructor.setAccessible(true);
                }

                return (T) constructor.newInstance();
            }
        } catch (Exception var9) {
            StringBuilder argTypes = new StringBuilder();
            if (constructorArgTypes != null && !constructorArgTypes.isEmpty()) {
                Iterator var6 = constructorArgTypes.iterator();

                while(var6.hasNext()) {
                    Class<?> argType = (Class)var6.next();
                    argTypes.append(argType.getSimpleName());
                    argTypes.append(",");
                }

                argTypes.deleteCharAt(argTypes.length() - 1);
            }

            StringBuilder argValues = new StringBuilder();
            if (constructorArgs != null && !constructorArgs.isEmpty()) {
                Iterator var11 = constructorArgs.iterator();

                while(var11.hasNext()) {
                    Object argValue = var11.next();
                    argValues.append(String.valueOf(argValue));
                    argValues.append(",");
                }

                argValues.deleteCharAt(argValues.length() - 1);
            }

            throw new ReflectionException("Error instantiating " + type + " with invalid types (" + argTypes + ") or values (" + argValues + "). Cause: " + var9, var9);
        }
    }

    protected Class<?> resolveInterface(Class<?> type) {
        Class classToCreate;
        if (type != List.class && type != Collection.class && type != Iterable.class) {
            if (type == Map.class) {
                classToCreate = HashMap.class;
            } else if (type == SortedSet.class) {
                classToCreate = TreeSet.class;
            } else if (type == Set.class) {
                classToCreate = HashSet.class;
            } else {
                classToCreate = type;
            }
        } else {
            classToCreate = ArrayList.class;
        }

        return classToCreate;
    }

    public <T> boolean isCollection(Class<T> type) {
        return Collection.class.isAssignableFrom(type);
    }
}
