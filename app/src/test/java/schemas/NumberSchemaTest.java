package schemas;

import hexlet.code.Validator;
import hexlet.code.schemas.NumberSchema;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NumberSchemaTest {
    private NumberSchema schema;
    private Validator v = new Validator();

    private final int num5 = 5;
    private final int num10 = 10;
    private final int num11 = 11;
    private final int num7 = 7;
    private final int numneg7 = -7;

    @BeforeEach
    void preset() {
        this.schema = v.number();
    }

    @Test
    void range() {
        schema.range(num5, num10);
        assertTrue(schema.isValid(num7));
        assertTrue(schema.isValid(num5));
        assertTrue(schema.isValid(num10));
        assertFalse(schema.isValid(0));
        assertFalse(schema.isValid(null));
        assertFalse(schema.isValid(num11));
    }

    @Test
    void required() {
        schema.required();
        assertFalse(schema.isValid(null));
    }

    @Test
    void positive() {
        assertTrue(schema.isValid(numneg7));
        schema.positive();
        assertFalse(schema.isValid(numneg7));
        assertFalse(schema.isValid(0));
        assertTrue(schema.isValid(num7));
    }

    @Test
    void isValid() {
        assertTrue(schema.isValid(null));
        assertTrue(schema.isValid(num7));
        assertTrue(schema.isValid(0));
        assertTrue(schema.isValid(numneg7));
    }
}
