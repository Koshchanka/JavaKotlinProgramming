package Case1;

import javax.inject.Inject;

public class Root {
    private final SecondChild secondChild;

    @Inject
    public Root(FirstChild firstChild, SecondChild secondChild) {
        this.secondChild = secondChild;
    }

    public SecondChild getChild() {
        return secondChild;
    }
}
