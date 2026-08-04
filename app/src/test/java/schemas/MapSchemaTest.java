package schemas;

import hexlet.code.Validator;
import hexlet.code.schemas.BaseSchema;
import hexlet.code.schemas.MapSchema;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MapSchemaTest {
    private final Validator v = new Validator();
    private MapSchema schema;
    private static Map<Object, Object> mapOneValue;
    private static Map<Object, Object>  mapSeveralValues;
    private static Map<Object, Object> emptyMap = new HashMap<>();



    @BeforeAll
    static void preset() {
        mapOneValue = Map.of("k1", "v1");
        mapSeveralValues = Map.of("k1", "v1", "k2", "v2", "k3", "v3", "k4", "v4");
    }

    @BeforeEach
    void reset() {
        this.schema = v.map();
    }

    @Test
    void required() {
        assertTrue(schema.isValid(null));
        schema.required();
        assertFalse(schema.isValid(null));
        assertTrue(schema.isValid(emptyMap));
    }

    @Test
    void sizeof() {
        assertTrue(schema.isValid(mapOneValue));
        schema.sizeof(3);
        assertFalse(schema.isValid(mapOneValue));
        assertTrue(schema.isValid(mapSeveralValues));
        assertFalse(schema.isValid(null));
    }

    @Test
    void isValid() {
        assertTrue(schema.isValid(null));
        assertTrue(schema.isValid(emptyMap));
        assertTrue(schema.isValid(mapSeveralValues));
        assertFalse(schema.isValid("Not a map"));
    }

    @Test
    void shape() {
        Map<String, BaseSchema> schemas = new HashMap<>();
        schemas.put("name", v.string().required());
        schemas.put("age", v.number().positive());
        schema.shape(schemas);
        Map<String, Object> human1 = new HashMap<>();
        human1.put("name", "Kolya");
        human1.put("age", 100);
        assertTrue(schema.isValid(human1));

        Map<String, Object> human2 = new HashMap<>();
        human2.put("name", "Maya");
        human2.put("age", null);
        assertTrue(schema.isValid(human2));


        Map<String, Object> human3 = new HashMap<>();
        human3.put("name", "");
        human3.put("age", null);
        assertFalse(schema.isValid(human3));

        Map<String, Object> human4 = new HashMap<>();
        human4.put("name", "Valya");
        human4.put("age", -5);
        assertFalse(schema.isValid(human4));

        schemas.put("name", v.string().required().minLength(5).contains("Al"));
        schemas.put("age", v.number().positive().range(0, 90));
        schema.shape(schemas);
        Map<String, Object> human5 = new HashMap<>();
        human5.put("name", "Alex");
        human5.put("age", 99);
        assertFalse(schema.isValid(human5));
        Map<String, Object> human6 = new HashMap<>();
        human6.put("name", "Alexander");
        human6.put("age", 90);
        assertTrue(schema.isValid(human6));
    }
}
