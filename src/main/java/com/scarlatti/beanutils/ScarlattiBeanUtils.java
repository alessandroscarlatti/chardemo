package com.scarlatti.beanutils;

import org.apache.commons.beanutils.ConstructorUtils;
import org.apache.commons.beanutils.PropertyUtils;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author Alessandro Scarlatti
 * @since Saturday, 7/13/2019
 */
public class ScarlattiBeanUtils {

    private static final Object[] NOARGS = new Object[]{};

    public static <T> T bean(Class<T> clazz, Consumer<T> config) {
        try {
            T bean = ConstructorUtils.invokeConstructor(clazz, NOARGS);
            config.accept(bean);
            return bean;
        } catch (Exception e) {
            throw new RuntimeException("Error instantiating bean for class " + clazz, e);
        }
    }

    public static <T> void generalBean(Class<T> clazz, Consumer<GeneralBean<T>> config) {
        try {
            T bean = ConstructorUtils.invokeConstructor(clazz, NOARGS);
            config.accept(new GeneralBean<>(bean));
        } catch (Exception e) {
            throw new RuntimeException("Error instantiating bean for class " + clazz, e);
        }
    }

    public static Object getProperty(Object object, String property) {
        try {
            return PropertyUtils.getProperty(object, property);
        } catch (Exception e) {
            throw new RuntimeException("Error getting property " + property + " on object " + object, e);
        }
    }

    public static void setProperty(Object object, String property, Object value) {
        try {
            PropertyUtils.setProperty(object, property, value);
        } catch (Exception e) {
            throw new RuntimeException("Error setting property " + property + " on object " + object, e);
        }
    }

    public static <T> void newListItem(List<T> list, Consumer<T> config) {
        Class<T> clazz = new GeneralBean.TypeReference<T>(){}.getTypeParameterClass();
        bean(clazz, config);
    }

    public static <T> void withProperty(Object object, String property, Consumer<T> config) {
        config.accept((T) getProperty(object, property));
    }

    public static class GeneralBean<T> {

        private T bean;

        private GeneralBean(T bean) {
            this.bean = bean;
        }

        public T get() {
            return bean;
        }

        public Object get(String property){
            return getProperty(bean, property);
        }

        public T get(String property, Class<T> clazz) {
            return (T) getProperty(bean, property);
        }

        public void set(String property, Object value) {
            setProperty(bean, property, value);
        }

        public <E> void with(String property, Consumer<GeneralBean<E>> config) {
            config.accept(new GeneralBean<>((E)getProperty(bean, property)));
        }

        public <E> void with(String property, TypeReference<E> typeReference, Consumer<GeneralBean<E>> config) {
            config.accept(new GeneralBean<>((E)getProperty(bean, property)));
        }

        public static abstract class TypeReference<E> {
            Class<E> getTypeParameterClass() {
                return ((Class<E>) ((this.getClass().getGenericSuperclass()).getClass()));
            }
        }
    }
}
