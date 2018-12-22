package example.domain.model.contract;

import java.util.List;

/**
 * 従業員契約一覧
 */
public class WorkerContracts {

    List<WorkerContract> list;

    public WorkerContracts(List<WorkerContract> list) {
        this.list = list;
    }

    public List<WorkerContract> list() {
        return list;
    }
}
