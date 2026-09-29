package lab2;

import org.junit.jupiter.api.Test;
import nl.jqno.equalsverifier.EqualsVerifier;

class PersonTest {
    @Test
    void testEqualsAndHash() {
        EqualsVerifier.simple().forClass(Person.class).verify();
    }
}