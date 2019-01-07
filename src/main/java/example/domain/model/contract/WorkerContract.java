package example.domain.model.contract;

import example.domain.model.worker.Name;
import example.domain.model.worker.Worker;
import example.domain.model.worker.WorkerNumber;
import example.domain.type.date.Date;

import java.util.ArrayList;

/**
 * 従業員契約
 */
public class WorkerContract {
    Worker worker;
    Contracts contracts;

    public WorkerContract(Worker worker, Contracts contracts) {
        this.worker = worker;
        this.contracts = contracts;
    }

    public WorkerNumber workerNumber() {
        return worker.workerNumber();
    }

    public Name workerName() {
        return worker.name();
    }

    public ContractStartingDate contractStartingDate() {
        ArrayList<Contract> list = new ArrayList<>(contracts.list());
        if (list.isEmpty()) {
            return ContractStartingDate.none();
        }
        return list.get(list.size() - 1).startDate();
    }

    public boolean notContractedAt(Date value) {
        return contractStartingDate().isAfter(value);
    }

    public Contract availableContractAt(Date date) {
        return contracts.list().stream()
                .filter(contract -> contract.availableAt(date))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(date.toString()));
    }
}
