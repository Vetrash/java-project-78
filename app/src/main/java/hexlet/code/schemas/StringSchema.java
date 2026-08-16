package hexlet.code.schemas;


public final class StringSchema extends BaseSchema<String> {

    public StringSchema required() {
        addCheck(SchemaChecks.REQUIRED, s -> s != null && !s.isEmpty());
        setRequired(true);
        return this;
    }

    public StringSchema minLength(int min) {
        addCheck(SchemaChecks.MIN_LENGTH, s -> s.length() >= min);
        return this;
    }

    public StringSchema contains(String substring) {
        addCheck(SchemaChecks.CONTAINS, s -> s.contains(substring));
        return this;
    }

}
