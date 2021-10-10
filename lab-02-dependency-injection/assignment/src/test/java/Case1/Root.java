package Case1;

import javax.inject.Inject;

public class Root {
    private final FirstChild firstChild;
    private final SecondChild secondChild;

    @Inject
    public Root(FirstChild firstChild, SecondChild secondChild) {
        this.firstChild = firstChild;
        this.secondChild = secondChild;
    }

    public SecondChild getSecond() {
        return secondChild;
    }
}
