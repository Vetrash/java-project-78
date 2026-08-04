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

    @BeforeEach
    void preset() {
        this.schema = v.number();
    }

    @Test
    void range() {
        schema.range(5, 10);
        assertTrue(schema.isValid(7));
        assertTrue(schema.isValid(5));
        assertTrue(schema.isValid(10));
        assertFalse(schema.isValid(0));
        assertFalse(schema.isValid(null));
        assertFalse(schema.isValid(11));
    }

    @Test
    void required() {
        schema.required();
        assertFalse(schema.isValid(null));
    }

    @Test
    void positive() {
        assertTrue(schema.isValid(-7));
        schema.positive();
        assertFalse(schema.isValid(-7));
        assertFalse(schema.isValid(0));
        assertTrue(schema.isValid(7));
    }

    @Test
    void isValid() {
        assertTrue(schema.isValid(null));
        assertTrue(schema.isValid(7));
        assertTrue(schema.isValid(0));
        assertTrue(schema.isValid(-7));
    }
}
