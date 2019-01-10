package example.domain.model.employee;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 氏名
 */
public class Name {

    @NotBlank(message = "名前を入力してください。")
    @Size(max = 40, message = "40文字以内で入力してください。")
    String value = "";

    public Name() {
    }

    public Name(String name) {
        value = name;
    }

    @Override
    public String toString() {
        return value;
    }
}
