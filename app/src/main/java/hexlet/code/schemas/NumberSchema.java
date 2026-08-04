package hexlet.code.schemas;

public final class NumberSchema extends BaseSchema {

    public NumberSchema() {
        addCheck(o -> o instanceof Integer);
    }

    public NumberSchema range(int start, int end) {
        addCheck(o -> (Integer) o >= start && (Integer) o <= end);
        return this.required();
    }

    public NumberSchema positive() {
        addCheck(o -> (Integer) o > 0);
        return this;
    }

    public NumberSchema required() {
        setOptional(false);
        return this;
    }
}
