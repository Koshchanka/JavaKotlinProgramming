package DependencyInjector;

public interface DependencyInjector {
    void register(Class<?> cl);

    void register(Class<?> interf, Class<?> cl);

    void completeRegistration();

    Object resolve(Class<?> cl);
}
