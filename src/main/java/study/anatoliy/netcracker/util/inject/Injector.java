package study.anatoliy.netcracker.util.inject;

import study.anatoliy.netcracker.util.Entry;
import study.anatoliy.netcracker.util.inject.bean.Bean;
import study.anatoliy.netcracker.util.inject.bean.BeanFactories;
import study.anatoliy.netcracker.util.inject.bean.BeanFactory;
import study.anatoliy.netcracker.util.inject.bean.SyntaxBeanException;

import java.io.File;
import java.lang.reflect.*;
import java.net.URL;
import java.util.*;

/**
 * Injector allows you to scan class packages for bean factories and inject them into objects
 *
 * @see Bean
 * @see Inject
 * @see InjectCollection
 *
 * @author Udarczev Anatoliy
 */
public class Injector {

    /**
     * Map of the found bean factories according to the types of beans they generate
     */
    private final Map<Class<?>, List<BeanFactory>> beanFactory;

    public Injector() {
        beanFactory = new HashMap<>();
    }

    /**
     * Scans package for bean annotations and turns them into factories
     *
     * @param pkg scanned package
     *
     * @throws SyntaxBeanException using the Bean annotation incorrectly
     */
    public void scan(String pkg) throws SyntaxBeanException {
        List<Class<?>> classes = find(pkg);

        for (Class<?> clazz :
                classes) {
            scanClass(clazz);
        }
    }

    /**
     * Scans class for bean annotations and turns them into factories
     *
     * @param clazz scanned class
     *
     * @throws SyntaxBeanException if using the Bean annotation incorrectly
     */
    void scanClass(Class<?> clazz) throws SyntaxBeanException {
        Bean a = clazz.getAnnotation(Bean.class);
        if (a != null) {
            Constructor<?> finalFactory = getEmptyConstructor(clazz);
            List<BeanFactory> factoryList = beanFactory.getOrDefault(clazz, new ArrayList<>());
            factoryList.add(BeanFactories.get(a.value(), () -> {
                try {
                    return finalFactory.newInstance();
                } catch (Exception e) {
                    // ignored
                }
                return null;
            }));
            beanFactory.put(clazz, factoryList);
        }

        Constructor<?> constructor = null;
        List<Entry<Bean, Method>> methods = new ArrayList<>();
        for (Method method :
                clazz.getDeclaredMethods()) {
            a = method.getAnnotation(Bean.class);
            if (a == null) {
                continue;
            }

            if (method.getParameterCount() != 0) {
                throw new SyntaxBeanException("Bean method must have zero parameters in class " + clazz.getName());
            }

            if ((method.getModifiers() & Modifier.PUBLIC) == 0) {
                throw new SyntaxBeanException("Bean method must be public in class " + clazz.getName());
            }

            Class<?> returnClass = method.getReturnType();
            if (returnClass.equals(Void.TYPE)) {
                throw new SyntaxBeanException("Bean method must return some in class " + clazz.getName());
            }

            if (!Modifier.isStatic(method.getModifiers())) {
                constructor = getEmptyConstructor(clazz);
            }
            methods.add(new Entry<>(a, method));
        }

        try {
            Object invoked = constructor == null ? null : constructor.newInstance();

            for (Entry<Bean, Method> entry:
                    methods) {
                Method method = entry.getValue();
                Bean bean = entry.getKey();
                List<BeanFactory> factoryList = beanFactory.getOrDefault(method.getReturnType(), new ArrayList<>());
                factoryList.add(BeanFactories.get(bean.value(), () -> {
                    try {
                        return method.invoke(invoked);
                    } catch (Exception e) {
                        // ignored
                    }
                    return null;
                }));
                beanFactory.put(method.getReturnType(), factoryList);
            }
        } catch (Exception e) {
            // ignored
        }
    }

    /**
     * Trying to get empty constructor of given class
     *
     * @param clazz the constructor class
     *
     * @return empty constructor of given class
     *
     * @throws SyntaxBeanException if it is not possible to get an empty constructor
     */
    private Constructor<?> getEmptyConstructor(Class<?> clazz) throws SyntaxBeanException {
        Constructor<?> factory = null;
        for (Constructor<?> constructor :
                clazz.getConstructors()) {
            if (constructor.getParameterCount() == 0) {
                factory = constructor;
                break;
            }
        }
        if (factory == null) {
            throw new SyntaxBeanException("Empty constructor missing in class " + clazz.getName());
        } else if ((factory.getModifiers() & Modifier.PUBLIC) == 0) {
            throw new SyntaxBeanException("Empty constructor must be public in class " + clazz.getName());
        }

        return factory;
    }

    /**
     * Injects beans into class fields with annotations Inject and InjectCollection
     *
     * @param o the object we want to inject beans into
     *
     * @throws InjectSyntaxException if using the Inject annotation incorrectly
     * @throws InjectException if impossible to determine what to inject
     */
    public void inject(Object o) throws InjectSyntaxException, InjectException {
        Class<?> clazz = o.getClass();

        Field[] fields = clazz.getDeclaredFields();
        for (Field field :
                fields) {
            field.setAccessible(true);
            Inject i = field.getAnnotation(Inject.class);
            InjectCollection ic = field.getAnnotation(InjectCollection.class);

            if (ic != null && i != null) {
                throw new InjectSyntaxException(String.format("Cannot be used simultaneously %s and %s at %s", Inject.class.getSimpleName(), InjectCollection.class.getSimpleName(), clazz.getName()));
            } else if (i != null) {
                injectFiled(clazz, o, field, i);
            } else if (ic != null) {
                injectCollection(clazz, o, field, ic);
            }
        }
    }

    /**
     * Injects a bean into the field
     *
     * @param root the class of the object in which the injection takes place
     * @param o the object in which the injection takes place
     * @param field injecting field
     * @param i inject annotation
     *
     * @throws InjectSyntaxException if using the inject annotation incorrectly
     * @throws InjectException if impossible to determine what to inject
     */
    private void injectFiled(Class<?> root, Object o, Field field, Inject i) throws InjectSyntaxException, InjectException {
        Class<?> fieldClass = field.getType();
        Class<?> injectedClass = i.value();

        if (injectedClass == Null.class) {
            injectedClass = fieldClass;
        }

        if (!fieldClass.isAssignableFrom(injectedClass)) {
            throw new InjectSyntaxException("Given class is not sub class of field in class " + root.getName());
        }

        List<BeanFactory> factories = beanFactory.get(injectedClass);

        if (factories == null) {
            throw new InjectException(String.format("No bean factories found for %s, inject exception at %s", injectedClass.getName(), root.getName()));
        } else if (factories.size() > 1) {
            throw new InjectException("Number of factories bean large 1, inject exception at " + root.getName());
        } else {
            field.setAccessible(true);
            try {
                field.set(o, factories.get(0).factory());
            } catch (IllegalAccessException ignore) {
            }
        }
    }

    /**
     * Injects a beans into the field of type of collection
     *
     * @param root the class of the object in which the injection takes place
     * @param o the object in which the injection takes place
     * @param field injecting field
     * @param ic inject collection annotation
     *
     * @throws InjectSyntaxException if using the inject annotation incorrectly
     */
    private void injectCollection(Class<?> root, Object o, Field field, InjectCollection ic) throws InjectSyntaxException {
        Class<?> collectionClass = field.getType();

        if (!collectionClass.equals(Collection.class)){
            throw new InjectSyntaxException("Class field is not collection at " + root.getName());
        }

        Type t = field.getGenericType();
        if (!(t instanceof ParameterizedType)) {
            throw new InjectSyntaxException("Given class is not sub class of class at " + root.getName());
        }

        Class<?> genericClass = ((Class<?>)((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0]);

        List<Object> list = new ArrayList<>();
        for (Map.Entry<Class<?>, List<BeanFactory>> e :
                beanFactory.entrySet()) {
            if (genericClass.isAssignableFrom(e.getKey())) {
                e.getValue().forEach(f -> list.add(f.factory()));
            }
        }

        field.setAccessible(true);
        try {
            field.set(o, list);
        } catch (IllegalAccessException ignore) {
        }
    }

    private static final char PKG_SEPARATOR = '.';

    private static final char DIR_SEPARATOR = '/';

    private static final String CLASS_FILE_SUFFIX = ".class";

    /**
     * Scans the package for java classes
     *
     * @param scannedPackage scanned package
     * @return list of found classes
     */
    static List<Class<?>> find(String scannedPackage) {
        String scannedPath = scannedPackage.replace(PKG_SEPARATOR, DIR_SEPARATOR);
        URL scannedUrl = Thread.currentThread().getContextClassLoader().getResource(scannedPath);
        if (scannedUrl == null) {
            throw new IllegalArgumentException(
                    String.format(
                            "Unable to get resources from path '%s'. Are you sure the package '%s' exists?",
                            scannedPath,
                            scannedPackage
                    )
            );
        }
        File scannedDir = new File(scannedUrl.getFile());
        List<Class<?>> classes = new ArrayList<>();
        for (File file : scannedDir.listFiles()) {
            classes.addAll(find(file, scannedPackage));
        }
        return classes;
    }


    /**
     * @param file the file to check for a directory or class
     * @param scannedPackage scanned package
     * @return list of found classes
     */
    private static List<Class<?>> find(File file, String scannedPackage) {
        List<Class<?>> classes = new ArrayList<>();
        String resource = scannedPackage + PKG_SEPARATOR + file.getName();
        if (file.isDirectory()) {
            for (File child : file.listFiles()) {
                classes.addAll(find(child, resource));
            }
        } else if (resource.endsWith(CLASS_FILE_SUFFIX)) {
            int endIndex = resource.length() - CLASS_FILE_SUFFIX.length();
            String className = resource.substring(0, endIndex);
            try {
                classes.add(Class.forName(className));
            } catch (ClassNotFoundException ignore) {
            }
        }
        return classes;
    }
}
