package example.domain.model.user;

import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by haljik on 15/06/04.
 */
public class Name {

    @NotBlank(message = "名前を入力してください。")
    String text;

    public Name(String text)  {
        this.text = text;
    }

    public Name() {
        this.text = "";
    }

    @Override
    public String toString() {
        return text;
    }
}
