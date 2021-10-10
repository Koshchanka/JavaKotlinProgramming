package WrongAnnotations;

import javax.inject.Inject;

public class MultipleInjects {
    @Inject
    public MultipleInjects() {
    }

    @Inject
    public MultipleInjects(int a) {
    }
}
