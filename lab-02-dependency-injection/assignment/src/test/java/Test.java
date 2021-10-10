import Case0.*;
import Case1.*;
import InterfaceImplementation.*;
import WrongAnnotations.*;
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
        {
            Di.register(Root.class);
            Di.register(FirstChild.class);
            Di.register(SecondChild.class);
            Di.register(SingletonDependency.class);
            Di.completeRegistration();
            Root firstRoot = (Root) Di.resolve(Root.class);
            Root secondRoot = (Root) Di.resolve(Root.class);
            assertNotEquals(firstRoot, secondRoot);
            assertNotEquals(firstRoot.getChild(), secondRoot.getChild());
            assertEquals(firstRoot.getChild().getSingleton(), secondRoot.getChild().getSingleton());
        }
        {
            SingletonDependency singletonDependency1 = (SingletonDependency) Di.resolve(SingletonDependency.class);
            SingletonDependency singletonDependency2 = (SingletonDependency) Di.resolve(SingletonDependency.class);
            assertEquals(singletonDependency1, singletonDependency2);
        }
    }

    @org.junit.jupiter.api.Test
    public void TestInterface() {
        DependencyInjector Di = new DependencyInjectorImpl();
        Di.register(MyInterface.class, MyImplementation.class);
        Di.completeRegistration();
        MyInterface interf = (MyInterface) Di.resolve(MyInterface.class);
        assertEquals(interf.GetInt(), 42);
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
