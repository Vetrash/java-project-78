package schemas;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import hexlet.code.Validator;
import hexlet.code.schemas.BaseSchema;

class ValidatorTest {
    private final int num7 = 7;
    private final int num10 = 10;
    private final int num4 = 4;
    private final int numneg10 = -10;
    private final int num5 = 5;
    private final int num6 = 6;
    private final int num9 = 9;
    private final int num2 = 2;
    private final int num11 = 11;

    @Test
    public void testStringValidator() {
        var v = new Validator();
        var schema = v.string();

        assertThat(schema.isValid("")).isTrue();

        schema.required();
        assertThat(schema.isValid("what does the fox say")).isTrue();
        assertThat(schema.isValid("hexlet")).isTrue();
        assertThat(schema.isValid("")).isFalse();
        assertThat(schema.isValid(null)).isFalse();

        schema.minLength(num7);
        assertThat(schema.isValid("what does the fox say")).isTrue();
        assertThat(schema.isValid("hexlet")).isFalse();

        assertThat(
                schema.contains("what").isValid("what does the fox say")
        ).isTrue();

        assertThat(
                schema.contains("whatthe").isValid("what does the fox say")
        ).isFalse();

        var schema1 = v.string().required().minLength(num10).minLength(num4);
        assertThat(schema1.isValid("hexlet")).isTrue();
    }

    @Test
    public void testNumberValidator() {
        var v = new Validator();
        var schema = v.number();

        assertThat(schema.isValid(num5)).isTrue();
        assertThat(schema.isValid(null)).isTrue();

        schema.positive();
        assertThat(schema.isValid(null)).isTrue();

        schema.required();
        assertThat(schema.isValid(null)).isFalse();
        assertThat(schema.isValid(numneg10)).isFalse();
        assertThat(schema.isValid(0)).isFalse();
        assertThat(schema.isValid(num10)).isTrue();

        schema.range(num5, num10);
        assertThat(schema.isValid(num5)).isTrue();
        assertThat(schema.isValid(num10)).isTrue();
        assertThat(schema.isValid(num4)).isFalse();
        assertThat(schema.isValid(num11)).isFalse();

        schema.range(num6, num9);
        assertThat(schema.isValid(num5)).isFalse();
        assertThat(schema.isValid(num10)).isFalse();
    }

    @Test
    public void testMapValidator() {
        var v = new Validator();
        var schema = v.map();

        assertThat(schema.isValid(null)).isTrue();
        assertThat(schema.isValid(new HashMap<>())).isTrue();

        schema.required();
        assertThat(schema.isValid(null)).isFalse();
        assertThat(schema.isValid(new HashMap<>())).isTrue();

        schema.sizeof(num2);
        assertThat(schema.isValid(new HashMap<>())).isFalse();
        Map<String, String> actual1 = new HashMap<>();
        actual1.put("key1", "value1");
        assertThat(schema.isValid(actual1)).isFalse();
        actual1.put("key2", "value2");
        assertThat(schema.isValid(actual1)).isTrue();

        Map<String, BaseSchema<String>> schemas = new HashMap<>();
        schemas.put("firstName", v.string().required().contains("ya"));
        schemas.put("lastName", v.string().required().contains("ov"));
        schema.shape(schemas);

        Map<String, String> actual2 = new HashMap<>();
        actual2.put("firstName", "Kolya");
        actual2.put("lastName", "Ivanov");
        assertThat(schema.isValid(actual2)).isTrue();

        Map<String, String> actual3 = new HashMap<>();
        actual3.put("firstName", "Maya");
        actual3.put("lastName", "Krasnova");
        assertThat(schema.isValid(actual3)).isTrue();

        Map<String, String> actual4 = new HashMap<>();
        actual4.put("firstName", "John");
        actual4.put("lastName", "Jones");
        assertThat(schema.isValid(actual4)).isFalse();
    }
}
