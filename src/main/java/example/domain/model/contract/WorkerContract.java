package example.domain.model.contract;

import example.domain.model.worker.Name;
import example.domain.model.worker.Worker;
import example.domain.model.worker.WorkerNumber;
import example.domain.type.date.Date;

import java.util.List;

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
        return contracts.list().stream()
                .min((c1, c2) -> c1.startDate().value().compareTo(c2.startDate().value()))
                .map(contract -> new ContractStartingDate(contract.startDate().value()))
                .orElse(ContractStartingDate.none());
    }

    public List<Contract> listContracts() {
        return contracts.list();
    }

    public boolean notContractedAt(Date value) {
        return contractStartingDate().isAfter(value);
    }

    public Contract availableContractAt(Date date) {
        return listContracts().stream()
                .sorted((o1, o2) -> o2.startDate().value().compareTo(o1.startDate().value()))
                .filter(contract -> contract.availableAt(date))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(date.toString()));
    }
}
