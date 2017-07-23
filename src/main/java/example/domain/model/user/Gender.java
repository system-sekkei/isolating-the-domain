package example.domain.model.user;

import javax.validation.constraints.NotNull;

public class Gender {
	@NotNull(message = "性別を選択してください。")
	GenderType value;

	@Override
	public String toString() {
		if(value == null ) return "";
		return value.name();
	}
}
