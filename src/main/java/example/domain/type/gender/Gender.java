package example.domain.type.gender;

import javax.validation.constraints.NotNull;

/**
 * 性別
 */
public class Gender {
    @NotNull(message = "性別を選択してください。")
    GenderType value;

    public Gender() {
    }

    public Gender(GenderType value) {
        this.value = value;
    }

    @Override
    public String toString() {
        if (value == null) return "";
        return value.name();
    }
}
