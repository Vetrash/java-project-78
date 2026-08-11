package hexlet.code.schemas;

import java.util.Map;
import java.util.Objects;

public final class MapSchema extends BaseSchema<Map<String, String>> {

    @Override
    public boolean isValid(Map<String, String> value) {
        var result = super.isValid(value);
        if (result && !getChecks().isEmpty()) {
            result = value.keySet()
                    .stream()
                    .allMatch(k -> {
                        if (!getChecks().containsKey(k)) {
                            return true;
                        }
                        return getChecks().get(k).test(value);
                    });
        }
        return result;
    }
    public MapSchema sizeof(int minSize) {
        addCheck(SchemaChecks.SIZE, s -> s.size() == minSize);
        return this;
    }

    public MapSchema required() {
        addCheckFirst(SchemaChecks.REQUIRED, Objects::nonNull);
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
