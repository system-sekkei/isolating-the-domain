package example.domain.model.contract;

import java.util.List;

/**
 * 雇用契約一覧
 */
public class Contracts {
    List<Contract> value;

    public Contracts(List<Contract> contracts) {
        this.value = contracts;
    }

    public List<Contract> value() {
        return value;
    }
}
