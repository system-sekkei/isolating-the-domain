package example.domain.model.contract;

import java.util.List;

/**
 * 従業員契約一覧
 */
public class Contracts {

    List<Contract> list;

    public Contracts(List<Contract> list) {
        this.list = list;
    }

    public List<Contract> list() {
        return list;
    }
}
