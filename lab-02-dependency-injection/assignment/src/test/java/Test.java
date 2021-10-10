import WrongAnnotations.*;
import Case0.*;
import Case1.*;
import DependencyInjector.DependencyInjector;
import DependencyInjector.DependencyInjectorImpl;

import static org.junit.jupiter.api.Assertions.*;

public class Test {
    @org.junit.jupiter.api.Test
    public void TestCase0() {
        DependencyInjector Di = new DependencyInjectorImpl();
        Di.register(MailClient.class);
        Di.register(Network.class);
        Di.completeRegistration();
        MailClient client = (MailClient) Di.resolve(MailClient.class);
        assertNotNull(client);
        MailClient client2 = (MailClient) Di.resolve(MailClient.class);
        assertNotNull(client2);
        assertNotEquals(client2, client);
        assertNotEquals(client2.getNetwork(), client.getNetwork());
    }

    @org.junit.jupiter.api.Test
    public void TestCase1() {
        DependencyInjector Di = new DependencyInjectorImpl();
        Di.register(Root.class);
        Di.register(FirstChild.class);
        Di.register(SecondChild.class);
        Di.register(SingletonDependency.class);
        Di.completeRegistration();
        Root mc1 = (Root) Di.resolve(Root.class);
        Root mc2 = (Root) Di.resolve(Root.class);
        assertNotEquals(mc1, mc2);
        assertNotEquals(mc1.getSecond(), mc2.getSecond());
        assertEquals(mc1.getSecond().getSingleton(), mc2.getSecond().getSingleton());
        SingletonDependency scd1 = (SingletonDependency) Di.resolve(SingletonDependency.class);
        SingletonDependency scd2 = (SingletonDependency) Di.resolve(SingletonDependency.class);
        assertEquals(scd1, scd2);
    }

    @org.junit.jupiter.api.Test
    public void TestThrow() {
        {
            DependencyInjector Di = new DependencyInjectorImpl();
            assertThrows(RuntimeException.class, () -> Di.register(NoInject.class));
        }
        {
            DependencyInjector Di = new DependencyInjectorImpl();
            assertThrows(RuntimeException.class, () -> Di.register(PrivateInject.class));
        }
        {
            DependencyInjector Di = new DependencyInjectorImpl();
            assertThrows(RuntimeException.class, () -> Di.register(MultipleInjects.class));
        }
        {
            DependencyInjector Di = new DependencyInjectorImpl();
            assertThrows(RuntimeException.class, () -> Di.resolve(DummyClass.class));
        }
        {
            DependencyInjector Di = new DependencyInjectorImpl();
            Di.register(DummyClass.class);
            assertThrows(RuntimeException.class, () -> Di.resolve(DummyClass.class));
            Di.completeRegistration();
            Di.resolve(DummyClass.class);
        }
    }
}
