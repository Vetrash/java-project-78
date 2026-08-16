package hexlet.code.schemas;

import java.util.Map;
import java.util.Objects;

public final class MapSchema extends BaseSchema<Map<String, String>> {

    public MapSchema sizeof(int minSize) {
        addCheck(SchemaChecks.SIZE, s -> s.size() == minSize);
        return this;
    }

    public MapSchema required() {
        addCheck(SchemaChecks.REQUIRED, Objects::nonNull);
        setRequired(true);
        return this;
    }

    public MapSchema shape(Map<String, BaseSchema<String>> schemas) {
        addCheck(SchemaChecks.SHAPE, (map) -> {
            if (map == null) {
                return true; // или false, в зависимости от требований
            }

            return schemas.entrySet().stream()
                    .allMatch(entry -> {
                        String key = entry.getKey();
                        BaseSchema<String> schema = entry.getValue();
                        Object value = map.get(key);
                        return schema.isValid((String) value);
                    });
        });

        return this;
    }
}
