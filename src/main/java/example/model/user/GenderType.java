package example.model.user;

import java.util.Arrays;

public enum GenderType {

    男性("1"),
    女性("2"),
    不明("3");

    private String code;

    GenderType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static GenderType of(String code) {
        return Arrays.stream(values())
                .filter(type -> type.code.equals(code))
                .findFirst()
                .orElse(GenderType.不明);
    }
}
