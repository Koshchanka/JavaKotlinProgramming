package Case0;

import javax.inject.Inject;

public class MailClient {
    private final Network network;

    @Inject
    public MailClient(Network net) {
        this.network = net;
    }

    public Network getNetwork() {
        return network;
    }

    @Inject
    private MailClient(boolean ignore) {
        /* Dummy private inject constructor.  DI must not throw.  */
        this.network = new Network();
    }
}
