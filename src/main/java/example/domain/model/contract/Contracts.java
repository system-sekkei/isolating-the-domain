package example.domain.model.contract;

import java.util.List;

/**
 * 雇用契約一覧
 */
public class Contracts {
    List<Contract> list;

    public Contracts(List<Contract> contracts) {
        this.list = contracts;
    }

    public List<Contract> list() {
        return list;
    }
}
