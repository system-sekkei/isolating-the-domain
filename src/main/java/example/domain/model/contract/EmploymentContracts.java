package example.domain.model.contract;

import java.util.List;

/**
 * 従業員契約一覧
 */
public class EmploymentContracts {

    List<EmploymentContract> list;

    public EmploymentContracts(List<EmploymentContract> list) {
        this.list = list;
    }

    public List<EmploymentContract> list() {
        return list;
    }
}
