package hexlet.code.schemas;

import java.util.Objects;

public final class NumberSchema extends BaseSchema<Integer> {


    public NumberSchema required() {
        addCheck(SchemaChecks.REQUIRED, Objects::nonNull);
        setRequired(true);
        return this;
    }

    public NumberSchema positive() {
        addCheck(SchemaChecks.POSITIVE, this.getHasRequired()
                ? s -> s > 0
                : s -> s == null || s > 0);
        return this;
    }

    public NumberSchema range(int min, int max) {
        addCheck(SchemaChecks.RANGE, s -> s >= min && s <= max);
        return this;
    }
}

