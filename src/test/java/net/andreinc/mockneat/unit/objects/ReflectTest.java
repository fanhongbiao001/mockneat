package net.andreinc.mockneat.unit.objects;

import net.andreinc.mockneat.unit.objects.model.Customer1;
import net.andreinc.mockneat.unit.objects.model.FinalValue;
import net.andreinc.mockneat.unit.objects.model.SerialPojo;
import net.andreinc.mockneat.unit.objects.model.TheAbstractClass;
import net.andreinc.mockneat.utils.NamesCheckUtils;
import org.junit.Test;

import static java.time.LocalDate.of;
import static net.andreinc.mockneat.Constants.M;
import static net.andreinc.mockneat.Constants.MOCKS;
import static net.andreinc.mockneat.Constants.OBJS_CYCLES;
import static net.andreinc.mockneat.types.enums.CreditCardType.AMERICAN_EXPRESS;
import static net.andreinc.mockneat.types.enums.NameType.FIRST_NAME;
import static net.andreinc.mockneat.types.enums.NameType.LAST_NAME;
import static net.andreinc.mockneat.utils.LoopsUtils.loop;
import static org.junit.Assert.*;

public class ReflectTest {

    @Test(expected = NullPointerException.class)
    public void testReflectNullClass() {
        M.reflect(null).val();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testReflectInvalidParam() {
        M.reflect(Customer1.class)
                .field("f Name", M.names().full())
                .val();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testReflectNotExistentParam() {
        M.reflect(Customer1.class)
                .field("firstNamex", M.names().full())
                .val();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testReflectionFinalValue() {
        M.reflect(FinalValue.class)
                .field("name", "Test")
                .val();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testReflectionAbstractClass() {
        M.reflect(TheAbstractClass.class)
                .field("name", "Test")
                .val();
    }

    @Test
    public void testReflectionConstruct() {
        loop(
            OBJS_CYCLES,
            MOCKS,
            m -> m.reflect(Customer1.class)
                    .field("firstName", m.names().first())
                    .field("lastName", m.names().last())
                    .field("age", m.ints().range(18, 100))
                    .field("email", m.emails())
                    .field("country", m.countries().names())
                    .field("cardId", m.uuids())
                    .field("dateOfBirth", m.localDates().past(of(1907, 1, 1)))
                    .field("ipv4", m.ipv4s())
                    .field("ipv6", m.iPv6s())
                    .field("description", m.strings().size(128))
                    .field("amex", m.creditCards().type(AMERICAN_EXPRESS))
                    .field("cvv", m.cvvs())
                    .val(),
            c -> {
                assertNotNull(c);
                assertTrue(NamesCheckUtils.isNameOfType(c.getFirstName(), FIRST_NAME));
                assertTrue(NamesCheckUtils.isNameOfType(c.getLastName(), LAST_NAME));
            }
        );
    }

    @Test(expected = NullPointerException.class)
    public void testNullClassDefaults() {
        M.reflect(Customer1.class)
                .useDefaults(true)
                .type(null, M.strings().size(15))
                .val();
    }

    @Test(expected = NullPointerException.class)
    public void testNullMockUnitDefaults() {
        M.reflect(Customer1.class)
                .useDefaults(true)
                .type(String.class, null)
                .val();
    }

    @Test
    public void testReflectionConstructWithDefaultsDisabled() {
        Customer1 customer1 = M.reflect(Customer1.class)
                .useDefaults(false)
                .val();

        assertNull(customer1.getAge());
        assertNull(customer1.getAmex());
        assertNull(customer1.getCardId());
        assertNull(customer1.getCountry());
        assertNull(customer1.getCvv());
        assertNull(customer1.getDateOfBirth());
        assertNull(customer1.getDescription());
        assertNull(customer1.getEmail());
        assertNull(customer1.getFirstName());
        assertNull(customer1.getIpv4());
        assertNull(customer1.getIpv6());
        assertNull(customer1.getLastName());
    }

    @Test
    public void testReflectionConstructWithDefaultsEnabled() {
        Customer1 customer1 = M.reflect(Customer1.class).useDefaults(true).val();

        assertNotNull(customer1.getAge());
        assertTrue(customer1.getAge() < 100);

        assertNotNull(customer1.getAge());
        assertNotNull(customer1.getAmex());
        assertNotNull(customer1.getCardId());
        assertNotNull(customer1.getCountry());
        assertNotNull(customer1.getCvv());
        assertNull(customer1.getDateOfBirth());
        assertNotNull(customer1.getDescription());
        assertNotNull(customer1.getEmail());
        assertNotNull(customer1.getFirstName());
        assertNotNull(customer1.getIpv4());
        assertNotNull(customer1.getIpv6());
        assertNotNull(customer1.getLastName());
    }

    @Test
    public void testReflectionConstructWithConstantDefaults() {
        loop(
                OBJS_CYCLES,
                MOCKS,
                m -> m.reflect(Customer1.class)
                        .useDefaults(true)
                        .type(Integer.class, 10)
                        .type(String.class, "DEFAULT")
                        .field("email", M.emails())
                        .val(),
                c -> {
                    assertNotNull(c.getAge());
                    assertEquals(10, (int) c.getAge());

                    assertNotNull(c.getLastName());
                    assertEquals("DEFAULT", c.getLastName());

                    assertNotNull(c.getEmail());
                    assertNotEquals("DEFAULT", c.getEmail());
                }
        );
    }

    @Test
    public void testSerialUIDPojo() {
        // Checks issue with static fields
        M.reflect(SerialPojo.class).field("name", M.names()).val();
    }
}