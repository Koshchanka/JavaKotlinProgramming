package DependencyInjector;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import javax.inject.Inject;
import javax.inject.Singleton;

import java.util.*;

public class DependencyInjectorImpl implements DependencyInjector {

    private final List<Class<?>> types = new ArrayList<>();
    private final Map<Class<?>, Object> singletons = new HashMap<>();
    private final Map<Class<?>, Constructor<?>> constructors = new HashMap<>();
    private final Map<Class<?>, List<Class<?>>> graph = new HashMap<>();

    private boolean registrationComplete = false;

    @Override
    public void register(Class<?> cl) {
        if (registrationComplete) {
            throw new RuntimeException("`register` called after `completeRegistration`");
        }

        if (!types.contains(cl)) {
            types.add(cl);
            Constructor<?> constructor = getInjectConstructor(cl);
            graph.put(cl, List.of(constructor.getParameterTypes()));
            constructors.put(cl, constructor);
        }
    }

    @Override
    public void completeRegistration() {
        registrationComplete = true;
        checkGraphSanity();
        constructSingletons();
    }

    @Override
    public Object resolve(Class<?> cl) {
        if (!registrationComplete) {
            throw new RuntimeException("`resolve` called before `completeRegistration`");
        }
        if (!types.contains(cl)) {
            throw new RuntimeException("Attempted to `resolve` unregistered class");
        }
        return getInstance(cl);
    }

    public void constructSingletons() {
        for (var type : types) {
            if (type.isAnnotationPresent(Singleton.class)) {
                singletons.put(type, getInstance(type));
            }
        }
    }

    public void checkGraphSanity() {
        int cnt = 0;
        Map<Class<?>, Integer> visitTime = new HashMap<>();
        Stack<Class<?>> stack = new Stack<>();
        for (var key : graph.keySet()) {
            if (visitTime.containsKey(key)) {
                continue;
            }
            visitTime.put(key, ++cnt);
            stack.push(key);
            while (!stack.empty()) {
                var top = stack.pop();
                for (Class<?> argType : graph.get(top)) {
                    if (!graph.containsKey(argType)) {
                        throw new RuntimeException("A registered class has an unregistered dependency");
                    }
                    if (visitTime.containsKey(argType)) {
                        if (visitTime.get(argType) == cnt) {
                            throw new RuntimeException("Dependencies graph is not acyclic");
                        }
                        continue;
                    }
                    stack.push(argType);
                    visitTime.put(argType, cnt);
                }
            }
        }
    }

    private Object getInstance(Class<?> cl) {
        if (singletons.containsKey(cl)) {
            return singletons.get(cl);
        }
        return constructInstance(cl);
    }

    private Object constructInstance(Class<?> cl) {
        Object result = null;
        Class<?>[] parameterTypes = constructors.get(cl).getParameterTypes();
        List<Object> instantiatedArgs = new ArrayList<>();
        for (var type : parameterTypes) {
            instantiatedArgs.add(getInstance(type));
        }
        Object[] array = instantiatedArgs.toArray();
        try {
            result = constructors.get(cl).newInstance(array);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException exception) {
            exception.printStackTrace();
        }
        return result;
    }

    private Constructor<?> getInjectConstructor(Class<?> cl) {
        var constructors = cl.getConstructors();

        Constructor<?> injectConstructor = null;
        for (var constructor : constructors) {
            if (constructor.isAnnotationPresent(Inject.class)) {
                if (injectConstructor != null) {
                    throw new RuntimeException("`register` called on class with more than one public @Inject constructor");
                } else {
                    injectConstructor = constructor;
                }
            }
        }
        if (injectConstructor == null) {
            throw new RuntimeException("`register` called on class with no public @Inject constructors");
        }
        return injectConstructor;
    }
}
