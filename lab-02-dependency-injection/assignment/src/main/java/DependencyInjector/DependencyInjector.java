package DependencyInjector;

public interface DependencyInjector {
    void register(Class<?> cl);
    void completeRegistration();
    Object resolve(Class<?>cl);
}
