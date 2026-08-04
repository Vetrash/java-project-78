package hexlet.code.schemas;

import java.util.Map;

public final class MapSchema extends BaseSchema {


    public MapSchema() {
        addCheck(o -> o instanceof Map);
    }

    public MapSchema sizeof(int minSize) {
        addCheck(o -> ((Map) o).size() >= minSize);
        return this.required();
    }

    public MapSchema required() {
        setOptional(false);
        return this;
    }

    public MapSchema shape(Map<String, BaseSchema> schemas) {
        addCheck(value -> schemas.entrySet().stream()
                .allMatch(e -> {
                    Object v = ((Map) value).get(e.getKey());
                    return e.getValue().isValid(v);
                }));
        return this;
    }
}
