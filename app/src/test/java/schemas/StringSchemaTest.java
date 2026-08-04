package schemas;

import hexlet.code.Validator;
import hexlet.code.schemas.StringSchema;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StringSchemaTest {
    private StringSchema schema;
    private Validator v = new Validator();

    private final int num5 = 5;
    private final int num7 = 7;


    @BeforeEach
    void reset() {
        this.schema = v.string();
    }

    @Test
    void required() {
        assertTrue(schema.isValid(""));
        assertTrue(schema.isValid(null));
        schema.required();
        assertTrue(schema.isValid("what does the fox say"));
        assertTrue(schema.isValid("hexlet"));
        assertFalse(schema.isValid(null));
        assertFalse(schema.isValid(num5));
        assertFalse(schema.isValid(""));
    }

    @Test
    void contains() {
        assertTrue(schema.contains("wh").isValid("what does the fox say"));
        assertTrue(schema.contains("what").isValid("what does the fox say"));
        assertFalse(schema.contains("whatthe").isValid("what does the fox say"));
        assertFalse(schema.isValid("what does the fox say"));
    }

    @Test
    void setMinLength() {
        assertTrue(schema.isValid("what does the fox say"));
        assertTrue(schema.isValid("hexlet"));
        schema.minLength(num7);
        assertTrue(schema.isValid("what does the fox say"));
        assertFalse(schema.isValid("hexlet"));
    }
}
