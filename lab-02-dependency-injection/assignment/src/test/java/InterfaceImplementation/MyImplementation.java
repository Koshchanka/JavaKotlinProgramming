package InterfaceImplementation;

import javax.inject.Inject;

public class MyImplementation implements MyInterface {
    @Inject
    public MyImplementation() {
    }

    @Override
    public int GetInt() {
        return 42;
    }
}
