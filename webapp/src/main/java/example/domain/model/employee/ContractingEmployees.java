package example.domain.model.employee;

import java.util.List;

/**
 * 契約中従業員一覧
 */
public class ContractingEmployees {
    List<Employee> list;

    public ContractingEmployees(List<Employee> list) {
        this.list = list;
    }

    public List<Employee> list() {
        return list;
    }

    @Override
    public String toString() {
        return list.toString();
    }
}
