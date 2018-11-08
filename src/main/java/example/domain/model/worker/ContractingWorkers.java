package example.domain.model.worker;

import java.util.List;

/**
 * 契約中従業員一覧
 */
public class ContractingWorkers {
    List<Worker> list;

    public ContractingWorkers(List<Worker> list) {
        this.list = list;
    }

    public List<Worker> list() {
        return list;
    }

    @Override
    public String toString() {
        return list.toString();
    }
}
