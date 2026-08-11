package hexlet.code.schemas;

import java.util.Map;
import java.util.Objects;
import java.util.HashMap;

public final class MapSchema extends BaseSchema<Map<String, String>> {

    @Override
    public boolean isValid(Map<String, String> value) {
        var result = super.isValid(value);
        if (result && !checks.isEmpty()) {
            result = value.keySet()
                    .stream()
                    .allMatch(k -> {
                        if (!checks.containsKey(k)) {
                            return true;
                        }
                        return checks.get(k).isValid(value.get(k));
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

    public MapSchema shape(Map<String, BaseSchema<String>> newSchemas) {
        this.schemas = new HashMap<>(newSchemas);
        return this;
    }
}
