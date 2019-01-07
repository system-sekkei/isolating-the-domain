package example.domain.model.contract;

import example.domain.model.worker.Name;
import example.domain.model.worker.Worker;
import example.domain.model.worker.WorkerNumber;
import example.domain.type.date.Date;

import java.time.LocalDate;
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

    public HourlyWage todayHourlyWage() {
        Date today = new Date(LocalDate.now());
        if (contractStatus(today).disable()) {
            return HourlyWage.disable();
        }
        return availableContractAt(today).hourlyWage();
    }

    public Contract availableContractAt(Date date) {
        return contracts.list().stream()
                .filter(contract -> contract.availableAt(date))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(date.toString()));
    }

    public ContractStatus contractStatus(Date value) {
        return contractStartingDate().isAfter(value) ? ContractStatus.契約なし : ContractStatus.契約あり;
    }
}
