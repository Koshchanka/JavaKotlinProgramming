package WrongAnnotations;

import javax.inject.Inject;

public class PrivateInject {
    @Inject
    private PrivateInject() {
    }
}
