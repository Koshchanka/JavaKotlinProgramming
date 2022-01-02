package Case1;

import javax.inject.Inject;

public class SecondChild {
    SingletonDependency singletonDependency;

    @Inject
    public SecondChild(SingletonDependency singletonDependency) {
        this.singletonDependency = singletonDependency;
    }

    public SingletonDependency getSingleton() {
        return singletonDependency;
    }
}
