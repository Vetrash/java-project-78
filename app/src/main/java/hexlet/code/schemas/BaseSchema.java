package hexlet.code.schemas;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.function.Predicate;
import java.util.Map;



public abstract class BaseSchema<T> {
    private Deque<Map.Entry<SchemaChecks, Predicate<T>>> checks = new ArrayDeque<>();
    private boolean isRequired = false;

    public final void setRequired(boolean required) {
        isRequired = required;
    }

    public final boolean getHasRequired() {
        return checks.stream()
                .anyMatch(entry -> entry.getKey().equals(SchemaChecks.REQUIRED));
    }

    public final  Deque<Map.Entry<SchemaChecks, Predicate<T>>>  getChecks() {
        return  checks;
    }


    public final void addCheck(SchemaChecks checkName, Predicate<T> predicate) {
        // Удаляем существующую запись с таким же ключом
        checks.removeIf(entry -> entry.getKey().equals(checkName));
        // Добавляем в конец
        checks.addLast(Map.entry(checkName, predicate));
    }



    public final boolean isValid(T value) {
        if (!isRequired) {
            Map.Entry<SchemaChecks, Predicate<T>> entry = checks.stream()
                    .filter(e -> e.getKey() == SchemaChecks.REQUIRED)
                    .findFirst()
                    .orElse(null);

            if (entry != null) {
                Predicate<T> validate = entry.getValue();
                if (!validate.test(value)) {
                    return true;
                }
            }
        }
        return checks.stream()
                .map(Map.Entry::getValue)
                .allMatch(predicate -> predicate.test(value));
    }
    public abstract BaseSchema<T> required();
}
