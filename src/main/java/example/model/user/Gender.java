package example.model.user;

public class Gender {

    String value;

    public GenderType type() {
        return GenderType.of(value);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
