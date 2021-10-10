package Case1;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SingletonDependency {
    @Inject
    public SingletonDependency() {
    }
    int i = 10;

    public int getInt() {
        return i;
    }
}
