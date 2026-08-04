package hexlet.code.schemas;

public final class StringSchema extends BaseSchema {

    public StringSchema() {
        addCheck(o -> o instanceof String);
    }

    public StringSchema contains(String str) {
        addCheck(o -> ((String) o).contains(str));
        return this;
    }

    public StringSchema minLength(int length) {
        addCheck(o -> ((String) o).length() >= length);
        return this;
    }

    public StringSchema required() {
        addCheck(o -> !((String) o).isEmpty());
        setOptional(false);
        return this;
    }
}
